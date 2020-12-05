package controllers;

import spark.Request;
import spark.Response;

public class ValidarUsuario {
    public Void validarUsuario(Request request, Response response) {
        String username = request.session().attribute("username");

        if(username == null) {
            response.redirect("/login");
        }
        return null;
    }
}
