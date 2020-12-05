package GeSoc.ServiciosExternos;

public class MockServicioGeografico implements ServicioGeograficoInterfaz {
	public String devolverCiudad(int zipCode) {
		return "null";
	}

    public String devolverProvincia(int zipCode) {
    	return "Córdoba";
    }

    public String devolverPais(int zipCode) {
    	return "Argentina";
    }

    public String descripcionDeMoneda(String id) {
    	return "Peso argentino";
    }

    public String simboloDeMondeda(String id) {
    	return "$";
    }

    public int lugaresDecimalMoneda(String id) {
    	return 2;
    }

}
