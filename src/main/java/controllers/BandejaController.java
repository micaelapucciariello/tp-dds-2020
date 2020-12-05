package controllers;

import GeSoc.Organizacion.Entidad.Entidad;
import GeSoc.Organizacion.EntidadJuridica.Categoria.Categoria;
import GeSoc.Organizacion.EntidadJuridica.Categoria.RepoCategorias;
import GeSoc.Organizacion.RepositorioDeEntidades;
import GeSoc.Usuario.RepositorioDeUsuarios;
import GeSoc.Usuario.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BandejaController {

    public ModelAndView getBandeja(Request request, Response response) {

        ValidarUsuario validarUsuario = new ValidarUsuario();
        validarUsuario.validarUsuario(request, response);

        Long id = request.session().attribute("id");
        Usuario usuario = RepositorioDeUsuarios.getInstance().obtenerUsuarioPorId(id).get(0);

        Map<String, Object> modelo = new HashMap<>();
        modelo.put("mensajes", usuario.getBandejaDeMensajes());

        return new ModelAndView(modelo, "bandeja.html.hbs");
    }

}
