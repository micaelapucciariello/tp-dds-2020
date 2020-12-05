package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import java.util.HashMap;
import java.util.Map;

public class HomeController {

    public ModelAndView getHome(Request request, Response response) {
        ValidarUsuario validarUsuario = new ValidarUsuario();
        validarUsuario.validarUsuario(request, response);

        Map<String, Object> usuario = new HashMap<>();
        usuario.put("username", UserController.getUsuarioLogueado(request));

        return new ModelAndView(usuario, "index.html.hbs");
    }

    public ModelAndView getError500(Request request, Response response) {
        return new ModelAndView(null, "error500.html.hbs");
    }

}