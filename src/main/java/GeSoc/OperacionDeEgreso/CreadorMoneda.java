package GeSoc.OperacionDeEgreso;

import GeSoc.ServiciosExternos.ServicioGeograficoInterfaz;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public class CreadorMoneda implements WithGlobalEntityManager {
    public Moneda generarMoneda(String id, ServicioGeograficoInterfaz servicioGeografico) {
        String descripcion = servicioGeografico.descripcionDeMoneda(id);
        String simbolo = servicioGeografico.simboloDeMondeda(id);
        int lugaresDecimal = servicioGeografico.lugaresDecimalMoneda(id);

        Moneda moneda = new Moneda(id, descripcion, simbolo, lugaresDecimal);
        return moneda;
    }
}
