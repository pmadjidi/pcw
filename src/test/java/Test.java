
import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.client.ClientConfig;

public class Test {

    public static void main(String[] args) {
        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);

        WebTarget target = client.target(getBaseURI());
        // Get XML
        String respAdd = target.path("payam").path("add").request()
                .accept(MediaType.APPLICATION_JSON).get(String.class);
        String respInc = target.path("payam").path("inc").request()
                .accept(MediaType.APPLICATION_JSON).get(String.class);
        String respGet = target.path("payam").path("get").request()
                .accept(MediaType.APPLICATION_JSON).get(String.class);
        String respMissingGet = target.path("does not exist").path("get").request()
                .accept(MediaType.APPLICATION_JSON).get(String.class);
        String respMissingInc = target.path("does not exist").path("inc").request()
                .accept(MediaType.APPLICATION_JSON).get(String.class);
        String respList = target.path("list").request()
                .accept(MediaType.APPLICATION_JSON).get(String.class);
        String respAddOneMore = target.path("Apsis").path("add").request()
                .accept(MediaType.APPLICATION_JSON).get(String.class);
        String respListAgain = target.path("list").request()
                .accept(MediaType.APPLICATION_JSON).get(String.class);

        System.out.println(respAdd);
        System.out.println(respInc);
        System.out.println(respGet);
        System.out.println(respMissingGet);
        System.out.println(respMissingInc);
        System.out.println(respList);
        System.out.println(respAddOneMore);
        System.out.println(respListAgain);
    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri(
                "http://localhost:9998/api/counters").build();
    }

}