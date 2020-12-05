package GeSoc;

import GeSoc.CreadorDeUsuarios.CreadorDeUsuarios;
import GeSoc.MedioDePago.Efectivo;
import GeSoc.MedioDePago.TipoDeEfectivo;
import GeSoc.OperacionDeEgreso.*;
import GeSoc.Organizacion.EntidadJuridica.Categoria.*;
import GeSoc.Organizacion.Entidad.*;
import GeSoc.ServiciosExternos.MockServicioGeografico;
import GeSoc.ServiciosExternos.ServicioGeograficoInterfaz;
import GeSoc.Usuario.Estandar;
import GeSoc.Usuario.TipoUsuario;
import GeSoc.Usuario.Usuario;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestCategorias implements WithGlobalEntityManager, TransactionalOps {
    RepoCategorias repoCategorias;
    Usuario user;
    List<Funcionalidad> listaFuncionalidades;
    Funcionalidad prohibirPertenecerAEntJuridica;
    Funcionalidad cantOperacionesMenorAN;
    Funcionalidad bloquearNuevaEntBase;
    OperacionDeEgreso egresoTest;
    Presupuesto presupuestoConItem;
    Moneda pesos;
    EntidadBase entidadBase;
    EntidadJuridica entidadJuridica;
    ServicioGeograficoInterfaz servicioGeografico;
    CreadorMoneda creadorMoneda;
    List<Item> listaItems;
    Item item;

    @Before
    public void init() {
        CreadorDeUsuarios creadorDeUsuarios = CreadorDeUsuarios.getInstance();
        user = creadorDeUsuarios.crearUsuario("rasta","rastarastin", new Estandar());
        servicioGeografico = new MockServicioGeografico();
        entidadJuridica = new Empresa();
        Categoria categoria = new Categoria();
        entidadBase = new EntidadBase("a","nombre", entidadJuridica, categoria,user);
        repoCategorias = RepoCategorias.getInstance();
        prohibirPertenecerAEntJuridica = new ProhibirPertenecerAEntJuridica(entidadJuridica);
        cantOperacionesMenorAN = new CantOperacionesMenorAN(0);
        bloquearNuevaEntBase = new BloquearNuevaEntBase();
        listaFuncionalidades = new ArrayList<>();
        listaFuncionalidades.add(prohibirPertenecerAEntJuridica);
        listaFuncionalidades.add(cantOperacionesMenorAN);
        listaFuncionalidades.add(bloquearNuevaEntBase);
        Proveedor proveedor = new Proveedor("José Chatruc", "proveedor1");
        Efectivo efectivo = new Efectivo(TipoDeEfectivo.PAGOFACIL);
        DocumentoComercial documentoComercial = new DocumentoComercial(TipoDocumento.FACTURA,1);
        egresoTest = new OperacionDeEgreso(proveedor, efectivo, documentoComercial);
        creadorMoneda = new CreadorMoneda();
        pesos = creadorMoneda.generarMoneda("ARS", servicioGeografico);
        listaItems = new ArrayList<>();
        item = new Item(pesos, new BigDecimal(100), egresoTest);
        listaItems.add(item);
        presupuestoConItem = new Presupuesto(listaItems);
    }

  @Test
    public void SeCreaCategoria() {
      withTransaction(() -> RepoCategorias.getInstance().agregarCategoria(new Categoria(listaFuncionalidades, "a",user)));
      Assert.assertEquals(1, repoCategorias.getCategorias().size());

    }
    
    @Test(expected = RuntimeException.class)
    public void BloquearNuevosEgresos() {
        entidadBase.agregarCategoria(new Categoria(listaFuncionalidades, "b",user));
        entidadBase.aceptarEgreso(egresoTest);
    }

    @Test(expected = RuntimeException.class)
    public void prohibirAgregarBaseAJuridica() {
        entidadJuridica.agregarCategoria(new Categoria(listaFuncionalidades, "c",user));
       entidadJuridica.agregarAEntBase(entidadBase);
    }

    @Test(expected = RuntimeException.class)
    public void ProhibirQueBaseSeaParteDeJuridica() {
        entidadBase.agregarCategoria(new Categoria(listaFuncionalidades, "d",user));
        entidadBase.asignarEntJuridica(entidadJuridica);
    }
}
