
import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.payam.test.counters.NamedCounters;
import org.glassfish.jersey.client.ClientConfig;

public class APITest {

    public static void main(String[] args) {
        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);

        WebTarget target = client.target(getBaseURI());
        // Get XML
        String respAdd = target.path("payam").path("add").request()
                .accept(MediaType.APPLICATION_JSON).get(String.class);
        String respAddAgain = target.path("payam").path("add").request()
                .accept(MediaType.APPLICATION_JSON).get(String.class);
        String respInc = target.path("payam").path("inc").request()
                .accept(MediaType.APPLICATION_JSON).get(String.class);
        String respAddTwice = target.path("payam").path("add").request()
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

        try {
            String respAddUnEscaped = target.path("a/bcd").path("add").request()
                    .accept(MediaType.APPLICATION_JSON).get(String.class);
        } catch (Exception e) {
            if (e.getClass().getCanonicalName() == "javax.ws.rs.NotFoundException") {
                System.out.print("Should fail with: " + "javax.ws.rs.NotFoundException\n" );
            }
        }

        System.out.println(respAdd);
        System.out.println(respAddAgain);
        System.out.println(respInc);
        System.out.println(respAddTwice);
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

