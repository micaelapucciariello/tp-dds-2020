package GeSoc;

import GeSoc.CreadorDeUsuarios.CreadorDeUsuarios;
import GeSoc.Etiquetas.Etiqueta;
import GeSoc.MedioDePago.*;
import GeSoc.OperacionDeEgreso.*;
import GeSoc.Organizacion.CantidadPresupuestos;
import GeSoc.Organizacion.CriterioSeleccionProveedor;
import GeSoc.Organizacion.EnBaseAPresupuesto;
import GeSoc.Organizacion.Entidad.Empresa;
import GeSoc.Organizacion.Entidad.OSC;
import GeSoc.Organizacion.Entidad.EntidadBase;
import GeSoc.Organizacion.EntidadJuridica.Categoria.Categoria;
import GeSoc.Organizacion.EntidadJuridica.Categoria.Funcionalidad;
import GeSoc.Organizacion.EntidadJuridica.Categoria.RepoCategorias;
import GeSoc.Organizacion.RepositorioDeEntidades;
import GeSoc.Organizacion.TipoEmpresa.TipoEmpresa;
import GeSoc.ServiciosExternos.MockServicioGeografico;
import GeSoc.ServiciosExternos.ServicioGeografico;
import GeSoc.ServiciosExternos.ServicioGeograficoInterfaz;
import GeSoc.Usuario.Estandar;
import GeSoc.Usuario.Mensaje;
import GeSoc.Usuario.RepositorioDeUsuarios;
import GeSoc.Usuario.Usuario;
import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Ejecutar antes de levantar el servidor por primera vez
 *
 */
public class Bootstrap implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps {

    public static void main(String[] args) {
        new Bootstrap().run();
    }

    public void run() {

        CreadorDeUsuarios creadorDeUsuarios = CreadorDeUsuarios.getInstance();
        Usuario usuario = creadorDeUsuarios.crearUsuario("usuario","contraseniaDificil", new Estandar());
        Usuario usuario2 = creadorDeUsuarios.crearUsuario("usuario2","contraseniaMasDificil", new Estandar());
        List<Funcionalidad> funcionalidades = new ArrayList<>();
        Categoria categoria1 = new Categoria(funcionalidades, "Judiciales",usuario);
        Categoria categoria2 = new Categoria(funcionalidades, "Agropecuarias",usuario);
        Categoria categoria3 = new Categoria(funcionalidades, "Deportivas",usuario);
        Categoria categoria4 = new Categoria(funcionalidades, "categoriaMala",usuario2);
        Proveedor proveedor = new Proveedor("José Chatruc", "proveedor1");
        Efectivo efectivo = new Efectivo(TipoDeEfectivo.PAGOFACIL);
        DocumentoComercial documentoComercial = new DocumentoComercial(TipoDocumento.FACTURA,1);
        OperacionDeEgreso egreso = new OperacionDeEgreso(proveedor, efectivo, documentoComercial);
        OperacionDeEgreso egreso2 = new OperacionDeEgreso(proveedor, efectivo, documentoComercial);
        usuario.recibirMensaje(new Mensaje(egreso, true));
        usuario.recibirMensaje(new Mensaje(egreso2, false));
        usuario.suscribirseAOperacion(egreso);
        usuario.suscribirseAOperacion(egreso2);
        Empresa entidadJuridica = new Empresa(TipoEmpresa.MICRO, "EmpresaPiola", 1829515, categoria2, usuario);
        Empresa entidadJuridica2 = new Empresa(TipoEmpresa.MICRO, "Empresita", 1829516, categoria3, usuario);
        OSC entidadJuridica3 = new OSC("OSCar", 4, categoria2, usuario);
        Empresa entidadJuridica4 = new Empresa(TipoEmpresa.MICRO, "Nodeberiasveresto", 1829511, categoria4, usuario2);
        entidadJuridica.agregarOpEgreso(egreso);
        EntidadBase entidadBase = new EntidadBase("a","SuperEntidadBase", entidadJuridica, categoria1, usuario);
        entidadBase.agregarOpEgreso(egreso);

        //para las validaciones

        ServicioGeograficoInterfaz servicioGeografico = new MockServicioGeografico();
        CreadorMoneda creadorMoneda = new CreadorMoneda();
        Moneda pesos = creadorMoneda.generarMoneda("ARS", servicioGeografico);
        List<Item> listaItems = new ArrayList<>();
        Item item = new Item(pesos, new BigDecimal(100), egreso);
        listaItems.add(item);
        Presupuesto unPresupuesto = new Presupuesto(listaItems);
        Presupuesto otroPresupuesto = new Presupuesto(listaItems);
        Etiqueta etiquetaIndumentaria = new Etiqueta("Indumentaria");

        egreso.presupuestos.add(unPresupuesto);
        egreso.presupuestos.add(otroPresupuesto);
        egreso.agregarValidacion(new CantidadPresupuestos());
        egreso.agregarValidacion(new CriterioSeleccionProveedor());
        egreso.agregarValidacion(new EnBaseAPresupuesto());
        egreso.agregarEtiqueta(etiquetaIndumentaria);

        egreso2.presupuestos.add(unPresupuesto);
        egreso2.agregarValidacion(new CantidadPresupuestos());
        egreso2.agregarValidacion(new CriterioSeleccionProveedor());
        egreso2.agregarValidacion(new EnBaseAPresupuesto());
        egreso2.agregarEtiqueta(etiquetaIndumentaria);

        withTransaction(() -> {
            //RepositorioDeUsuarios.getInstance().agregarUsuario(usuario);
            RepoCategorias.getInstance().agregarCategoria(categoria4);
            RepositorioDeEntidades.getInstance().agregarEntidad(entidadBase);
            RepositorioDeEntidades.getInstance().agregarEntidad(entidadJuridica2);
            RepositorioDeEntidades.getInstance().agregarEntidad(entidadJuridica3);
            RepositorioDeEntidades.getInstance().agregarEntidad(entidadJuridica4);
            RepositorioDeMedioDePago.getInstance().agregarMedioDePago(new Cajero(TipoDeCajero.BANELCO));
            RepositorioDeMedioDePago.getInstance().agregarMedioDePago(new Credito(TipoDeCredito.VISA));
            RepositorioDeMedioDePago.getInstance().agregarMedioDePago(new Cuenta());
            RepositorioDeMedioDePago.getInstance().agregarMedioDePago(new Debito());
        });
    }

   private void limpiarDatos() {
        withTransaction(() -> {
            RepositorioDeEntidades.getInstance().borrarEntidades();
            RepositorioDeUsuarios.getInstance().borrarUsuarios();
            RepoCategorias.getInstance().borrarCategorias();
        });
    }

}
