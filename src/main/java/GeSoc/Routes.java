package GeSoc;

import controllers.*;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.after;

public class Routes {

    public static void main(String[] args) {
        System.out.println("Iniciando servidor");

        Spark.port(getHerokuAssignedPort());
        Spark.staticFileLocation("/public");

        UserController userController = new UserController();
        HomeController homeController = new HomeController();
        EgresoController egresoController = new EgresoController();
        EntidadController entidadController = new EntidadController();
        CategoriasController categoriasController = new CategoriasController();
        BandejaController bandejaController = new BandejaController();
        ContactoController contactoController = new ContactoController();

        HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();

        after((request, response) -> {
            PerThreadEntityManagers.getEntityManager();
            PerThreadEntityManagers.closeEntityManager();
        });

        Spark.get("/", homeController::getHome, engine);

        Spark.get("/login", userController::getFormularioLogin, engine);
        Spark.post("/login", userController::validarUsuario);

        Spark.get("/entidades/:id/egresos/nuevo", egresoController::getFormularioEgreso, engine);

        Spark.get("/entidadBase", entidadController::getFormularioEntidadBase, engine);
        Spark.post("/entidadBase", entidadController::cargarEntidadBase);

        Spark.get("/empresa", entidadController::getFormularioEmpresa, engine);
        Spark.post("/empresa", entidadController::cargarEmpresa);

        Spark.get("/osc", entidadController::getFormularioOSC, engine);
        Spark.post("/osc", entidadController::cargarOSC);

        Spark.post("/entidades/:id/egresos", egresoController::cargarEgreso);
        Spark.get("/egresos/:id", egresoController::getEgreso, engine);

        Spark.get("/entidades/:id", entidadController::getEntidadEspecifica, engine);
        Spark.post("/entidades/:id", entidadController::modificarCategoria);

        Spark.get("/entidades/:id/egresos", entidadController::getEgresosDeEntidad, engine);
        Spark.get("/categorias",categoriasController::getCategorias,engine);
        Spark.get("/categorias/:id", categoriasController::getEntidadesPorCategoria,engine);
        Spark.get("/bandeja", bandejaController::getBandeja, engine);
        Spark.get("/contacto", contactoController::getContacto, engine);
        Spark.get("/error500", homeController::getError500, engine);

    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }

        return 8080;
    }

}
