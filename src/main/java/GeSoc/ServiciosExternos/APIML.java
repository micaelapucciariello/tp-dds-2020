package GeSoc.ServiciosExternos;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.core.MediaType;

public class APIML {

    private Client client;

    public APIML() {
        this.client = Client.create();
    }

    String getInfoByZipCode(int zipCode, String dato) {
        WebResource recurso = this.client.resource("https://api.mercadolibre.com/").path("/countries/AR/zip_codes/" + zipCode);
        WebResource.Builder builder = recurso.accept(MediaType.APPLICATION_JSON);
        return builder.get(ClientResponse.class).getEntity(String.class);

    }

    String getCurrencyById(String id) {
        WebResource recurso = this.client.resource("https://api.mercadolibre.com/").path("/currencies/" + id);
        WebResource.Builder builder = recurso.accept(MediaType.APPLICATION_JSON);
        return builder.get(ClientResponse.class).getEntity(String.class);
    }
}
