package GeSoc;

import GeSoc.ServiciosExternos.MockServicioGeografico;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class TestServicioGeografico {

    MockServicioGeografico requesterML;

    @Before
    public void init() {
        this.requesterML = new MockServicioGeografico();
    }

    //Se espera que de null porque es lo que devuelve ML para muchas ciudades
    @Test
    public void obtenerCiudad() {
        String response = this.requesterML.devolverCiudad(5000);
        Assert.assertEquals("null", response);
    }

    @Test
    public void obtenerProvincia() {
        String response = this.requesterML.devolverProvincia(5000);
        Assert.assertEquals("Córdoba", response);
    }

    @Test
    public void obtenerPais() {
        String response = this.requesterML.devolverPais(5000);
        Assert.assertEquals("Argentina", response);
    }

    @Test
    public void obtenerDescripcionDeMoneda() {
        String response = this.requesterML.descripcionDeMoneda("ARS");
        Assert.assertEquals("Peso argentino", response);
    }

    @Test
    public void obtenerSimboloDeMondeda() {
        String response = this.requesterML.simboloDeMondeda("ARS");
        Assert.assertEquals("$", response);
    }

    @Test
    public void obtenerLugaresDecimalMoneda() {
        int response = this.requesterML.lugaresDecimalMoneda("ARS");
        Assert.assertEquals(2, response);
    }
}
