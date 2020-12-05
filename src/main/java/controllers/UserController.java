package controllers;

import GeSoc.CreadorDeUsuarios.CreadorDeUsuarios;
import GeSoc.Organizacion.Entidad.Entidad;
import GeSoc.Organizacion.Entidad.EntidadJuridica;
import GeSoc.Organizacion.EntidadJuridica.Categoria.Categoria;
import GeSoc.Organizacion.EntidadJuridica.Categoria.RepoCategorias;
import GeSoc.Organizacion.RepositorioDeEntidades;
import GeSoc.Usuario.Estandar;
import GeSoc.Usuario.RepositorioDeUsuarios;

import GeSoc.Usuario.Usuario;
import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateEngine;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class UserController implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps {

    public ModelAndView getFormularioLogin(Request request, Response response) {
        return new ModelAndView(null, "login.html.hbs");
    }

    public Void validarUsuario(Request request, Response response) {

        String nombreBuscado = request.queryParams("usuario");
        CreadorDeUsuarios creadorDeUsuarios = CreadorDeUsuarios.getInstance();

        String password = request.queryParams("password");
        List<Usuario> usuario = RepositorioDeUsuarios.getInstance().buscarUsuario(nombreBuscado, creadorDeUsuarios.encriptarPassword(password));

        // Si el usuario ingresado está en el repo
        if(usuario.size() > 0) {
            request.session().attribute("username", usuario.get(usuario.size()-1).getUser());
            request.session().attribute("id", usuario.get(usuario.size()-1).getId());
            response.redirect("/");
            return null;
        }

        // si el usuario no está en el repo
        response.redirect("/login");
        return null;
    }

    static public String getUsuarioLogueado(Request request) {
        String username = request.session().attribute("username");

        if(username == null) {
            username = "Ingresar usuario";
        }

        return username;
    }

}