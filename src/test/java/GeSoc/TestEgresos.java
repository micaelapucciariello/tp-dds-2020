package GeSoc;

import GeSoc.CreadorDeUsuarios.CreadorDeUsuarios;
import GeSoc.Etiquetas.Etiqueta;
import GeSoc.Etiquetas.RepoEtiquetas;
import GeSoc.Etiquetas.Reporte;
import GeSoc.MedioDePago.Efectivo;
import GeSoc.MedioDePago.TipoDeEfectivo;
import GeSoc.OperacionDeEgreso.*;
import GeSoc.OperacionDeEgreso.ResultadoDeValidacion.*;
import GeSoc.Organizacion.*;
import GeSoc.Organizacion.Entidad.Empresa;
import GeSoc.Organizacion.Entidad.Entidad;
import GeSoc.Organizacion.Entidad.EntidadBase;
import GeSoc.Organizacion.EntidadJuridica.Categoria.Categoria;
import GeSoc.ServiciosExternos.MockServicioGeografico;
import GeSoc.ServiciosExternos.ServicioGeograficoInterfaz;
import GeSoc.Usuario.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestEgresos implements WithGlobalEntityManager, TransactionalOps {
    OperacionDeEgreso egresoTest;
    Usuario usuarioTest;
    Presupuesto unPresupuesto;
    Presupuesto otroPresupuesto;
    Presupuesto presupuestoConItem;
    Presupuesto presupuestoConItem2;
    Moneda pesos;
    Entidad entidadTest;
    OperacionDeEgreso egreso2;
    OperacionDeEgreso egreso3;
    Etiqueta etiquetaIndumentaria;
    Etiqueta etiquetaAmoblamiento;
    ServicioGeograficoInterfaz servicioGeografico;
    CreadorMoneda creadorMoneda;
    List<Item> listaItems;
    List<Item> lista0;
    Item item;
    Item itemVacio;

    @Before
    public void init() {
        CreadorDeUsuarios creadorDeUsuarios = CreadorDeUsuarios.getInstance();
        Usuario user = creadorDeUsuarios.crearUsuario("rasta","rastarastin", new Estandar());
        etiquetaAmoblamiento = new Etiqueta("Amoblamiento");
        etiquetaIndumentaria = new Etiqueta("Indumentaria");
        servicioGeografico = new MockServicioGeografico();
        Empresa entidadJuridica = new Empresa();
        Categoria categoria = new Categoria();
        entidadTest = new EntidadBase("a", "nombre", entidadJuridica, categoria,user);
        Proveedor proveedor = new Proveedor("José Chatruc", "proveedor1");
        Efectivo efectivo = new Efectivo(TipoDeEfectivo.PAGOFACIL);
        DocumentoComercial documentoComercial = new DocumentoComercial(TipoDocumento.FACTURA,1);
        egresoTest = new OperacionDeEgreso(proveedor, efectivo, documentoComercial);
        entidadTest.agregarOpEgreso(egresoTest);
        egreso2 = new OperacionDeEgreso(proveedor, efectivo, documentoComercial);
        egreso3 = new OperacionDeEgreso(proveedor, efectivo, documentoComercial);
        RepositorioDeEgresos.getInstance().agregarEgreso(egresoTest);
        RepositorioDeEgresos.getInstance().agregarEgreso(egreso2);
        RepositorioDeEgresos.getInstance().agregarEgreso(egreso3);
        creadorMoneda = new CreadorMoneda();
        pesos = creadorMoneda.generarMoneda("ARS", servicioGeografico);
        listaItems = new ArrayList<>();
        lista0 = new ArrayList<>();
        item = new Item(pesos, new BigDecimal(100), egresoTest);
        itemVacio = new Item(pesos, new BigDecimal(0), egresoTest);
        listaItems.add(item);
        lista0.add(itemVacio);
        presupuestoConItem = new Presupuesto(listaItems);
        presupuestoConItem2 = new Presupuesto(listaItems);
        unPresupuesto = new Presupuesto(lista0);
        otroPresupuesto = new Presupuesto(lista0);
        usuarioTest = new Usuario("usuarioTest","contraseniaDificil".getBytes(),new Estandar());
    }

    @Test 
    public void ingresarUnPresupuestoConEgresoAsociado() {
    	Presupuesto presupuesto = new Presupuesto(listaItems);
    	Assert.assertTrue(presupuesto instanceof Presupuesto);
    }

    @Test (expected = NullPointerException.class)
    public void ingresarUnItemSinEgresoAsociado() {
        new Item(pesos, new BigDecimal(100), null);
    }
      
    @Test 
    public void aceptarEgreso() {
    	egresoTest.presupuestos.add(unPresupuesto);
    	egresoTest.presupuestos.add(otroPresupuesto);

        egresoTest.agregarValidacion(new CantidadPresupuestos());
        egresoTest.agregarValidacion(new CriterioSeleccionProveedor());
        egresoTest.agregarValidacion(new EnBaseAPresupuesto());

        egresoTest.elegirPresupuesto(unPresupuesto);
        egresoTest.validar();
    	Assert.assertEquals(ResultadoDeValidacion.ACEPTADO, egresoTest.resultadoDeValidacion);
    }

    @Test 
    public void rechazarEgresoPorCantidadDePresupuestos() {
    	egresoTest.presupuestos.add(unPresupuesto);
        egresoTest.agregarValidacion(new CantidadPresupuestos());
        egresoTest.validar();
        Assert.assertEquals(ResultadoDeValidacion.RECHAZADO, egresoTest.resultadoDeValidacion);
    }

    @Test 
    public void rechazarEgresoPorSeleccionDeProveedor() {
    	egresoTest.presupuestos.add(presupuestoConItem);
    	egresoTest.presupuestos.add(otroPresupuesto);
        egresoTest.agregarValidacion(new CriterioSeleccionProveedor());
        egresoTest.elegirPresupuesto(presupuestoConItem);

        egresoTest.validar();
        Assert.assertEquals(ResultadoDeValidacion.RECHAZADO, egresoTest.resultadoDeValidacion);
    }

    @Test 
    public void rechazarEgresoPorNoEstarDefinidoEnBaseAPresupuestos() {
    	egresoTest.presupuestos.add(unPresupuesto);
    	egresoTest.presupuestos.add(otroPresupuesto);
        egresoTest.agregarValidacion(new EnBaseAPresupuesto());
        egresoTest.elegirPresupuesto(presupuestoConItem);

        egresoTest.validar();
        Assert.assertEquals(ResultadoDeValidacion.RECHAZADO, egresoTest.resultadoDeValidacion);
    }

    @Test
    public void recibirNotificacionCorrespondienteTrasAceptarEgreso() {
    	egresoTest.presupuestos.add(unPresupuesto);
    	egresoTest.presupuestos.add(otroPresupuesto);
    	usuarioTest.suscribirseAOperacion(egresoTest);
        egresoTest.agregarValidacion(new CantidadPresupuestos());
        egresoTest.validar();
    	Assert.assertEquals(true, usuarioTest.getBandejaDeMensajes().get(0).egresoValido);
    }

    @Test
    public void recibirNotificacionCorrespondienteTrasRechazarEgreso() {
    	egresoTest.presupuestos.add(unPresupuesto);
    	usuarioTest.suscribirseAOperacion(egresoTest);
        egresoTest.agregarValidacion(new CantidadPresupuestos());
        egresoTest.validar();
    	Assert.assertEquals(false, usuarioTest.getBandejaDeMensajes().get(0).egresoValido);
    }

    @Test
    public void SeAgreganEtiquetasYSeGeneraReporte() {
        withTransaction(() -> {
            RepoEtiquetas.getInstance().agregarEtiqueta(etiquetaIndumentaria);
            RepoEtiquetas.getInstance().agregarEtiqueta(etiquetaAmoblamiento);
        });
        egresoTest.agregarEtiqueta(etiquetaIndumentaria);
        egresoTest.elegirPresupuesto(presupuestoConItem);
        egreso2.agregarEtiqueta(etiquetaIndumentaria);
        egreso2.elegirPresupuesto(presupuestoConItem2);
        egreso3.agregarEtiqueta(etiquetaAmoblamiento);
        List<Reporte> reportes = entidadTest.generarReportePorEtiqueta();

        Assert.assertEquals(2, reportes.size());
    }

}
