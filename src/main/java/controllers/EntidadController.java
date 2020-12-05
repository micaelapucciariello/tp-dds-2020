package controllers;

import GeSoc.Organizacion.Entidad.*;
import GeSoc.Organizacion.EntidadJuridica.Categoria.Categoria;
import GeSoc.Organizacion.EntidadJuridica.Categoria.RepoCategorias;
import GeSoc.Organizacion.RepositorioDeEntidades;
import GeSoc.Usuario.RepositorioDeUsuarios;
import GeSoc.Usuario.Usuario;
import GeSoc.Organizacion.TipoEmpresa.TipoEmpresa;
import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import java.util.*;
import java.util.stream.Collectors;

public class EntidadController implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps {
    public ModelAndView getEntidadesPorCategoria(Map<String,Object> modelo, Long categoriaId) {
        List<Entidad> entidades = RepositorioDeEntidades.getInstance().listarEntidadesPorCategoria(categoriaId);
        modelo.put("entidades",entidades);

        return new ModelAndView(modelo, "entidades.hbs");
    }

    public ModelAndView getEntidadEspecifica(Request request, Response response) {

        Long entidadId = Long.parseLong(request.params("id"));
        List<Entidad> entidades = RepositorioDeEntidades.getInstance().buscarEntidad(entidadId);
        int tamanioLista = entidades.size();
        Entidad entidad = entidades.get(tamanioLista-1);

        Long idUsuario = request.session().attribute("id");
        List<Categoria> categoriasDelUsuario = RepoCategorias.getInstance().getCategoriasByUsuario(idUsuario);

        Map<String, Object> modelo = new HashMap<>();

        List<Categoria> categoriasCandidatas = categoriasDelUsuario.stream().filter(cat->!cat.equals(entidad.categoria)).collect(Collectors.toList());

        modelo.put("entidad", entidad);
        modelo.put("categorias", categoriasCandidatas);
        return new ModelAndView(modelo, "entidadEspecifica.html.hbs");
    }

    public ModelAndView getFormularioEntidadBase(Request request, Response response) {
        ValidarUsuario validarUsuario = new ValidarUsuario();
        validarUsuario.validarUsuario(request, response);

        Long idUsuario = request.session().attribute("id");
        List<Entidad> entidades = RepositorioDeEntidades.getInstance().listarEntidadesPorUsuario(idUsuario);

        Map<String, Object> modelo = new HashMap<>();
        //categorias del usuario sin repetidos
        List<Categoria> categorias = RepoCategorias.getInstance().getCategoriasByUsuario(idUsuario);
        modelo.put("categorias", categorias);
        List<Entidad> entjurs =  entidades.stream().filter(entidad -> entidad instanceof EntidadJuridica).collect(Collectors.toList());
        modelo.put("entidadesJuridicas",entjurs);
        return new ModelAndView(modelo, "entidadBase.html.hbs");
    }

    public Void cargarEntidadBase(Request request, Response response) {
        try{
            Long idUsuario = request.session().attribute("id");
            List<Entidad> entidades = RepositorioDeEntidades.getInstance().listarEntidadesPorUsuario(idUsuario);

            Usuario user = RepositorioDeUsuarios.getInstance().obtenerUsuarioPorId(idUsuario).get(0);
            Categoria categoria;
            categoria = RepoCategorias.getInstance().getCategoriaById(Long.parseLong(request.queryParams("categoria"))).get(0);
            String nombre = request.queryParams("Nombre");
            String descripcion = request.queryParams("Descripcion");
            if(nombre.equals("") || descripcion.equals("")) {
                throw new RuntimeException();
            }
            Entidad entjur = entidades.stream().filter(entidad -> entidad.id.equals(Long.parseLong(request.queryParams("EntJur")))).collect(Collectors.toList()).get(0);

            EntidadBase entidadBase = new EntidadBase(descripcion,nombre,(EntidadJuridica) entjur,categoria,user);
            withTransaction(() -> RepositorioDeEntidades.getInstance().agregarEntidad(entidadBase));

            response.redirect("/entidades/" + entidadBase.getId());
            return null;
        } catch(RuntimeException e){
            response.status(500);
            response.redirect("/error500");
            return null;
        }
    }

    public ModelAndView getFormularioEmpresa(Request request, Response response) {
        ValidarUsuario validarUsuario = new ValidarUsuario();
        validarUsuario.validarUsuario(request, response);

        Long idUsuario = request.session().attribute("id");

        Map<String, Object> modelo = new HashMap<>();
        List<Entidad> entidades = RepositorioDeEntidades.getInstance().listarEntidadesPorUsuario(idUsuario);
        //categorias del usuario sin repetidos
        List<Categoria> categorias = RepoCategorias.getInstance().getCategoriasByUsuario(idUsuario);
        modelo.put("categorias", categorias);
        List<String> tiposEmpresa = Arrays.asList(TipoEmpresa.PEQUENIA.name(),TipoEmpresa.MEDIANATRAMO1.name(),TipoEmpresa.MEDIANATRAMO2.name(),TipoEmpresa.MICRO.name());
        modelo.put("tiposEmpresas",tiposEmpresa);
        return new ModelAndView(modelo, "empresa.html.hbs");

    }

    public Void cargarEmpresa(Request request, Response response) {

        try{
            Long idUsuario = request.session().attribute("id");

            Usuario user = RepositorioDeUsuarios.getInstance().obtenerUsuarioPorId(idUsuario).get(0);
            Categoria categoria;
            categoria = RepoCategorias.getInstance().getCategoriaById(Long.parseLong(request.queryParams("categoria"))).get(0);
            String razonSocial = request.queryParams("RazonSocial");
            if(razonSocial.equals("")) {
                throw new RuntimeException();
            }
            double CUIT = Double.parseDouble(request.queryParams("CUIT"));
            TipoEmpresa tipo = TipoEmpresa.valueOf(request.queryParams("tipoEmpresa"));

            Empresa empresa = new Empresa(tipo,razonSocial,CUIT,categoria,user);

            withTransaction(() -> RepositorioDeEntidades.getInstance().agregarEntidad(empresa));

            response.redirect("/entidades/" + empresa.getId());
            return null;
        } catch(RuntimeException e){
            response.status(500);
            response.redirect("/error500");
            return null;
        }

    }

    public ModelAndView getFormularioOSC(Request request, Response response) {
        ValidarUsuario validarUsuario = new ValidarUsuario();
        validarUsuario.validarUsuario(request, response);

        Long idUsuario = request.session().attribute("id");
        Map<String, Object> modelo = new HashMap<>();
        List<Entidad> entidades = RepositorioDeEntidades.getInstance().listarEntidadesPorUsuario(idUsuario);
        //categorias del usuario sin repetidos
        List<Categoria> categorias = RepoCategorias.getInstance().getCategoriasByUsuario(idUsuario);
        modelo.put("categorias", categorias);

        return new ModelAndView(modelo, "osc.html.hbs");
    }

    public Void cargarOSC(Request request, Response response) {

        try{
            Long idUsuario = request.session().attribute("id");

            Usuario user = RepositorioDeUsuarios.getInstance().obtenerUsuarioPorId(idUsuario).get(0);
            Categoria categoria;
            categoria = RepoCategorias.getInstance().getCategoriaById(Long.parseLong(request.queryParams("categoria"))).get(0);
            String razonSocial = request.queryParams("RazonSocial");
            int cantEgresosAceptados = Integer.parseInt(request.queryParams("egresos"));

            OSC osc = new OSC(razonSocial,cantEgresosAceptados,categoria,user);

            withTransaction(() -> RepositorioDeEntidades.getInstance().agregarEntidad(osc));

            response.redirect("/entidades/" + osc.getId());
            return null;
        } catch(RuntimeException e){
            response.status(500);
            response.redirect("/error500");
            return null;
        }
    }

    private Boolean validarFormularioInvalido (Request request) {
        return true;
    }

    public ModelAndView getEgresosDeEntidad(Request request, Response response) {

        Long entidadId = Long.parseLong(request.params("id"));
        Entidad entidad = RepositorioDeEntidades.getInstance().buscarEntidad(entidadId).get(0);

        Map<String, Object> egresos = new HashMap<>();
        egresos.put("egresos", entidad.getOperacionesEgreso());
        egresos.put("entidadId", entidadId);
        egresos.put("entidad", entidad);

        return new ModelAndView(egresos, "egresosDeEntidad.html.hbs");
    }

    public Void modificarCategoria(Request request, Response response) {

        String nombreCategoriaNueva = request.queryParams("categoriaNueva");
        Long idUsuario = request.session().attribute("id");
        List<Entidad> entidadesDelUsuario = RepositorioDeEntidades.getInstance().listarEntidadesPorUsuario(idUsuario);
        List<Categoria> categoriasDelUsuario = RepoCategorias.getInstance().getCategoriasByUsuario(idUsuario).stream().filter(cat->cat.nombre.equals(nombreCategoriaNueva)).collect(Collectors.toList());
        Categoria categoriaNueva = categoriasDelUsuario.get(categoriasDelUsuario.size()-1);

        Long entidadId = Long.parseLong(request.queryParams("entidad"));
        Entidad entidad = RepositorioDeEntidades.getInstance().buscarEntidad(entidadId).get(0);

        withTransaction(() -> {
            RepositorioDeEntidades.getInstance().eliminarEntidad(entidad);
            entidad.categoria=categoriaNueva;
            RepositorioDeEntidades.getInstance().agregarEntidad(entidad);
        });

        response.redirect("/categorias/" + categoriaNueva.getId());
        return null;
    }
}
