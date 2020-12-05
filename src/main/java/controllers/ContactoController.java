package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ContactoController {

    public ModelAndView getContacto(Request request, Response response) {
        ValidarUsuario validarUsuario = new ValidarUsuario();
        validarUsuario.validarUsuario(request, response);

        return new ModelAndView(null, "contacto.html.hbs");
    }

}
