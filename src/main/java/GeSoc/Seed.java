package GeSoc;

import GeSoc.CreadorDeUsuarios.CreadorDeUsuarios;
import GeSoc.Etiquetas.Etiqueta;
import GeSoc.Etiquetas.Reporte;
import GeSoc.MedioDePago.Efectivo;
import GeSoc.MedioDePago.TipoDeEfectivo;
import GeSoc.OperacionDeEgreso.*;
import GeSoc.Organizacion.CantidadPresupuestos;
import GeSoc.Organizacion.CriterioSeleccionProveedor;
import GeSoc.Organizacion.EnBaseAPresupuesto;
import GeSoc.Organizacion.Entidad.Entidad;
import GeSoc.Organizacion.Entidad.EntidadBase;
import GeSoc.ServiciosExternos.MockServicioGeografico;
import GeSoc.ServiciosExternos.ServicioGeograficoInterfaz;
import GeSoc.Usuario.Estandar;
import GeSoc.Usuario.RepositorioDeUsuarios;
import GeSoc.Usuario.Usuario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Seed {

    public void cargarDatosDePrueba() {
        CreadorDeUsuarios creador;
        OperacionDeEgreso egresoAAprobar;
        OperacionDeEgreso egresoARechazar;
        OperacionDeEgreso egreso3;
        ServicioGeograficoInterfaz servicioGeografico;
        Usuario usuarioTest;
        Presupuesto unPresupuesto;
        Presupuesto otroPresupuesto;
        Moneda pesos;
        Entidad entidadTest;
        Etiqueta etiquetaAmoblamiento;
        Etiqueta etiquetaIndumentaria;
        Reporte reporte;
        CreadorMoneda creadorMoneda;
        List<Item> listaItems;
        Item item;
        Proveedor proveedor = new Proveedor("José Chatruc", "proveedor1");
        Efectivo efectivo = new Efectivo(TipoDeEfectivo.PAGOFACIL);
        DocumentoComercial documentoComercial = new DocumentoComercial(TipoDocumento.FACTURA,1);
        //entidadTest = new EntidadBase();
        creador = CreadorDeUsuarios.getInstance();
        egresoAAprobar = new OperacionDeEgreso(proveedor, efectivo, documentoComercial);
        egresoARechazar = new OperacionDeEgreso(proveedor, efectivo, documentoComercial);
        servicioGeografico = new MockServicioGeografico();
        creadorMoneda = new CreadorMoneda();
        pesos = creadorMoneda.generarMoneda("ARS", servicioGeografico);
        listaItems = new ArrayList<>();
        item = new Item(pesos, new BigDecimal(100), egresoAAprobar);
        listaItems.add(item);
        unPresupuesto = new Presupuesto(listaItems);
        otroPresupuesto = new Presupuesto(listaItems);
        RepositorioDeEgresos repositorioDeEgresos = RepositorioDeEgresos.getInstance();
        RepositorioDeUsuarios repoUsuarios = RepositorioDeUsuarios.getInstance();
        egreso3 = new OperacionDeEgreso(proveedor, efectivo, documentoComercial);
        etiquetaAmoblamiento = new Etiqueta("Amoblamiento");
        etiquetaIndumentaria = new Etiqueta("Indumentaria");
        RepositorioDeEgresos.getInstance().agregarEgreso(egresoAAprobar);
        RepositorioDeEgresos.getInstance().agregarEgreso(egresoARechazar);
        RepositorioDeEgresos.getInstance().agregarEgreso(egreso3);

        egresoAAprobar.presupuestos.add(unPresupuesto);
        egresoAAprobar.presupuestos.add(otroPresupuesto);
        egresoAAprobar.agregarValidacion(new CantidadPresupuestos());
        egresoAAprobar.agregarValidacion(new CriterioSeleccionProveedor());
        egresoAAprobar.agregarValidacion(new EnBaseAPresupuesto());
        egresoAAprobar.agregarEtiqueta(etiquetaIndumentaria);

        egresoARechazar.presupuestos.add(unPresupuesto);
        egresoARechazar.agregarValidacion(new CantidadPresupuestos());
        egresoARechazar.agregarValidacion(new CriterioSeleccionProveedor());
        egresoARechazar.agregarValidacion(new EnBaseAPresupuesto());
        egresoARechazar.agregarEtiqueta(etiquetaIndumentaria);

        egreso3.agregarEtiqueta(etiquetaAmoblamiento);

        creador.crearUsuario("usuarioTest", "contraseniaDificil", new Estandar());

        usuarioTest = repoUsuarios.getUsuarios().get(0);
        usuarioTest.suscribirseAOperacion(egresoAAprobar);
        usuarioTest.suscribirseAOperacion(egresoARechazar);

        egresoAAprobar.elegirPresupuesto(unPresupuesto);
        egresoARechazar.elegirPresupuesto(unPresupuesto);

        //reporte = entidadTest.generarReporte(etiquetaIndumentaria);
    }
}
