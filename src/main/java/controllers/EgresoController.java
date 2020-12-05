package controllers;

import GeSoc.MedioDePago.MedioDePago;
import GeSoc.MedioDePago.RepositorioDeMedioDePago;
import GeSoc.OperacionDeEgreso.*;
import GeSoc.Organizacion.Entidad.Entidad;
import GeSoc.Organizacion.RepositorioDeEntidades;
import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EgresoController implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps {

    public ModelAndView getFormularioEgreso(Request request, Response response) {
        ValidarUsuario validarUsuario = new ValidarUsuario();
        validarUsuario.validarUsuario(request, response);

        Map<String, Object> modelo = new HashMap<>();
        List<String> tipoDocumentos = Arrays.asList(TipoDocumento.FACTURA.name(), TipoDocumento.NOTACREDITO.name(), TipoDocumento.NOTADEBITO.name(), TipoDocumento.TICKET.name());
        List<MedioDePago> mediosDePago = RepositorioDeMedioDePago.getInstance().getMediosDePago().stream().distinct().collect(Collectors.toList());
        List<Proveedor> proveedores = RepositorioDeProveedores.getInstance().getProveedores().stream().distinct().collect(Collectors.toList());

        modelo.put("tiposDocumento", tipoDocumentos);
        modelo.put("mediosDePago", mediosDePago);
        modelo.put("proveedores", proveedores);
        modelo.put("entidad", Long.parseLong(request.queryParams("entidad")));
        return new ModelAndView(modelo, "egresos.html.hbs");
    }

    public Void cargarEgreso(Request request, Response response){
        try{
            String nombreTipo = request.queryParams("tipo");
            Long medioDePagoId = Long.parseLong(request.queryParams("pago"));
            Integer numeroComercial = Integer.parseInt(request.queryParams("nro comercial"));
            Long proveedorId = Long.parseLong(request.queryParams("proveedor"));
            Long entidadId = Long.parseLong(request.queryParams("entidad"));

            Proveedor proveedor = RepositorioDeProveedores.getInstance().buscarProveedor(proveedorId).get(0);
            MedioDePago medioDePago = RepositorioDeMedioDePago.getInstance().buscarMedioDePago(medioDePagoId).get(0);
            DocumentoComercial documentoComercial = new DocumentoComercial(TipoDocumento.valueOf(nombreTipo), numeroComercial);
            Entidad entidad = RepositorioDeEntidades.getInstance().buscarEntidad(entidadId).get(0);
            OperacionDeEgreso nuevoEgreso = new OperacionDeEgreso(proveedor, medioDePago, documentoComercial);
            entidad.agregarOpEgreso(nuevoEgreso);

            withTransaction(() -> RepositorioDeEgresos.getInstance().agregarEgreso(nuevoEgreso));

            response.redirect("/egresos/" + nuevoEgreso.getId());
            return null;
        } catch(RuntimeException otra){
            response.status(500);
            response.redirect("/error500");
            return null;
        }

    }

    public ModelAndView getEgreso(Request request, Response response) {

        Long egresoId = Long.parseLong(request.params("id"));
        OperacionDeEgreso egreso = RepositorioDeEgresos.getInstance().buscarEgreso(egresoId).get(0);

        Map<String, Object> modelo = new HashMap<>();

        modelo.put("proveedor", egreso.getProveedor().razonSocial);
        modelo.put("medioDePago", egreso.getMedioDePago().getTipo());
        modelo.put("tipoDocumento", egreso.getDocumentoComercial().getTipoDocumento());
        modelo.put("numeroOperacion", egreso.getDocumentoComercial().getNumeroDeOperacion());
        modelo.put("egresoId", egresoId);

        return new ModelAndView(modelo, "egreso.html.hbs");
    }

}