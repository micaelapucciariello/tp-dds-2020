package GeSoc.ServiciosExternos;


public interface ServicioGeograficoInterfaz {

	    public String devolverCiudad(int zipCode);

	    public String devolverProvincia(int zipCode) ;

	    public String devolverPais(int zipCode) ;

	    public String descripcionDeMoneda(String id) ;

	    public String simboloDeMondeda(String id) ;

	    public int lugaresDecimalMoneda(String id) ;
}
