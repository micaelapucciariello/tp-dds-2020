package GeSoc.ServiciosExternos;

import org.json.JSONObject;

public class ServicioGeografico implements ServicioGeograficoInterfaz{

    String parsearDatosGeograficos(int zipCode, String dato) {
        String jString = new APIML().getInfoByZipCode(zipCode, dato);
        JSONObject json = new JSONObject(jString);
        //por alguna razón todas las ciudades (o las que probé) dan null, es algo de ML.
        // Si encuentra un null devuelve "null"
        return json.getJSONObject(dato).optString("name", "null");
    }

    public String devolverCiudad(int zipCode) {
        return parsearDatosGeograficos(zipCode, "city");
    }

    public String devolverProvincia(int zipCode) {
        return parsearDatosGeograficos(zipCode, "state");
    }

    public String devolverPais(int zipCode) {
        return parsearDatosGeograficos(zipCode, "country");
    }

    String parsearDatosMoneda(String id, String dato) {
        String jString = new APIML().getCurrencyById(id);
        JSONObject json = new JSONObject(jString);
        return json.getString(dato);
    }

    public String descripcionDeMoneda(String id) {
        return parsearDatosMoneda(id, "description");
    }

    public String simboloDeMondeda(String id) {
        return parsearDatosMoneda(id, "symbol");
    }

    public int lugaresDecimalMoneda(String id) {
        JSONObject json = new JSONObject(new APIML().getCurrencyById(id));
        return json.getInt("decimal_places");
    }

}
