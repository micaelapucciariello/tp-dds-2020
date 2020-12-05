package GeSoc;

import GeSoc.MedioDePago.Efectivo;
import GeSoc.MedioDePago.TipoDeEfectivo;
import GeSoc.OperacionDeEgreso.DocumentoComercial;
import GeSoc.OperacionDeEgreso.OperacionDeEgreso;
import GeSoc.OperacionDeEgreso.Proveedor;
import GeSoc.OperacionDeEgreso.TipoDocumento;
import GeSoc.Organizacion.Entidad.Empresa;
import GeSoc.Organizacion.Entidad.Entidad;
import GeSoc.Organizacion.Entidad.EntidadBase;
import GeSoc.Organizacion.EntidadJuridica.Categoria.Categoria;
import GeSoc.Usuario.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

public class TestUsuarios {

    OperacionDeEgreso egresoTest;
    Usuario usuarioTest;
    Entidad entidadTest;

    @Before
    public void init() {
        Empresa entidadJuridica = new Empresa();
        Categoria categoria = new Categoria();
        usuarioTest = new Usuario("usuarioTest","contraseniaDificil".getBytes(),new Estandar());
        entidadTest = new EntidadBase("a", "nombre", entidadJuridica, categoria,usuarioTest);
        Proveedor proveedor = new Proveedor("José Chatruc", "proveedor1");
        Efectivo efectivo = new Efectivo(TipoDeEfectivo.PAGOFACIL);
        DocumentoComercial documentoComercial = new DocumentoComercial(TipoDocumento.FACTURA,1);
        egresoTest = new OperacionDeEgreso(proveedor, efectivo, documentoComercial);
        entidadTest.agregarOpEgreso(egresoTest);

    }

    @Test
    public void suscribirseAOperacionYRecibirNotificacion() {
        usuarioTest.suscribirseAOperacion(egresoTest);
        egresoTest.enviarMensaje(new Mensaje(egresoTest,true));
        Assert.assertFalse(usuarioTest.getBandejaDeMensajes().isEmpty());
    }


}
