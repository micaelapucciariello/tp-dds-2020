package GeSoc.OperacionDeEgreso;

import GeSoc.ServiciosExternos.ServicioGeograficoInterfaz;

public class CreadorDireccionPostal {
    DireccionPostal generarDireccionPostal(Direccion direccion, int codigoPostal, ServicioGeograficoInterfaz servicioGeografico) {
        String ciudad = servicioGeografico.devolverCiudad(codigoPostal);
        String provincia = servicioGeografico.devolverProvincia(codigoPostal);
        String pais = servicioGeografico.devolverPais(codigoPostal);
        return new DireccionPostal(direccion, codigoPostal, ciudad, provincia, pais);
    }
}