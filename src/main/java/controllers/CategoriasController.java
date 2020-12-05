package controllers;

import GeSoc.Organizacion.Entidad.Entidad;
import GeSoc.Organizacion.EntidadJuridica.Categoria.Categoria;
import GeSoc.Organizacion.EntidadJuridica.Categoria.RepoCategorias;
import GeSoc.Organizacion.RepositorioDeEntidades;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CategoriasController {
    public ModelAndView getCategorias(Request request, Response response) {
        ValidarUsuario validarUsuario = new ValidarUsuario();
        validarUsuario.validarUsuario(request, response);

        Map<String, Object> modelo = new HashMap<>();

        Long idUsuario = request.session().attribute("id");
        List<Entidad> entidades = RepositorioDeEntidades.getInstance().listarEntidadesPorUsuario(idUsuario);
        //categorias del usuario sin repetidos
        List<Categoria> categorias = RepoCategorias.getInstance().getCategoriasByUsuario(idUsuario);
        modelo.put("categorias", categorias);
        return new ModelAndView(modelo, "categorias.html.hbs");
    }

    public ModelAndView getEntidadesPorCategoria(Request request, Response response){
        Map<String, Object> modelo = new HashMap<>();
        Long categoriaId = Long.parseLong(request.params("id"));
        Categoria categoria = RepoCategorias.getInstance().getCategoriaById(categoriaId).get(0);

        modelo.put("categoria",categoria);
        return new EntidadController().getEntidadesPorCategoria(modelo,categoriaId);
    }

}
