
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

        String respAdd = target.path("payam").path("add").request()
                .accept(MediaType.APPLICATION_JSON).get(String.class);
        System.out.println(respAdd);

        try {
            String respAddAgain = target.path("payam").path("add").request()
                    .accept(MediaType.APPLICATION_JSON).get(String.class);
        } catch (Exception e) {
            System.out.print("Got" + e.getMessage() + "\n");
            System.out.print("Should fail with: " + "HTTP 409 Conflict\n");
        }

        String respInc = target.path("payam").path("inc").request()
                .accept(MediaType.APPLICATION_JSON).get(String.class);
        System.out.println(respInc);

        try {
            String respAddTwice = target.path("payam").path("add").request()
                    .accept(MediaType.APPLICATION_JSON).get(String.class);
        } catch (Exception e) {
            System.out.print("Got" + e.getMessage() + "\n");
            System.out.print("Should fail with: " + "HTTP 409 Conflict\n");
        }

        String respGet = target.path("payam").path("get").request()
                .accept(MediaType.APPLICATION_JSON).get(String.class);
        System.out.println(respGet);

        try {
            String respMissingGet = target.path("does not exist").path("get").request()
                    .accept(MediaType.APPLICATION_JSON).get(String.class);
        } catch (Exception e) {
            System.out.print("Got" + e.getMessage() + "\n");
            System.out.print("Should fail with: " + "HTTP 404 Not Found\n");
        }

        try {
            String respMissingInc = target.path("does not exist").path("inc").request()
                    .accept(MediaType.APPLICATION_JSON).get(String.class);
        } catch (Exception e) {
            System.out.print("Got" + e.getMessage() + "\n");
            System.out.print("Should fail with: " + "HTTP 404 Not Found\n");
        }

        String respList = target.path("list").request()
                .accept(MediaType.APPLICATION_JSON).get(String.class);
        System.out.println(respList);

        String respAddOneMore = target.path("Apsis").path("add").request()
                .accept(MediaType.APPLICATION_JSON).get(String.class);
        System.out.println(respAddOneMore);

        String respListAgain = target.path("list").request()
                .accept(MediaType.APPLICATION_JSON).get(String.class);
        System.out.println(respListAgain);

        try {
            String respAddUnEscaped = target.path("a/bcd").path("add").request()
                    .accept(MediaType.APPLICATION_JSON).get(String.class);
        } catch (Exception e) {
            System.out.print("Got" + e.getMessage() + "\n");
            System.out.print("Should fail with: " + "javax.ws.rs.NotFoundException\n" );

        }
    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri(
                "http://localhost:9998/api/counters").build();
    }

}

