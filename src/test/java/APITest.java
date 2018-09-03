
import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.payam.test.counters.NamedCounters;
import org.glassfish.jersey.client.ClientConfig;

public class APITest {

    @Test
    public static void main(String[] args) {
        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);

        WebTarget target = client.target(getBaseURI());

        Response respAdd =  target.path("payam").path("add").request(MediaType.APPLICATION_JSON).post(Entity.json(null));
        System.out.println(respAdd.toString());
        assertEquals(201, respAdd.getStatus(), "must be 201");

        Response respAddAgain = target.path("payam").path("add").request(MediaType.APPLICATION_JSON).post(Entity.json(null));
        System.out.println(respAddAgain.toString());
        assertEquals(409, respAddAgain.getStatus(), "must be 409");

        Response respInc = target.path("payam").path("inc").request(MediaType.APPLICATION_JSON).post(Entity.json(null));
        System.out.println(respInc.toString());
        assertEquals(200, respInc.getStatus(), "must be 200");

        Response respAddTwice = target.path("payam").path("add").request(MediaType.APPLICATION_JSON).post(Entity.json(null));
        System.out.println(respAddTwice.toString());
        assertEquals(409, respAddTwice.getStatus(), "must be 409");


        Response respGet = target.path("payam").path("get").request(MediaType.APPLICATION_JSON).get();
        System.out.println(respGet.toString());
        assertEquals(200, respGet.getStatus(), "must be 200");

        Response respMissingGet = target.path("does not exist").path("get").request(MediaType.APPLICATION_JSON).get();
        System.out.println(respMissingGet.toString());
        assertEquals(404, respMissingGet.getStatus(), "must be 404");

        Response respMissingInc = target.path("does not exist").path("inc").request(MediaType.APPLICATION_JSON).post(Entity.json(null));
        System.out.println(respMissingInc.toString());
        assertEquals(404, respMissingInc.getStatus(), "must be 404");

        Response respList = target.path("list").request(MediaType.APPLICATION_JSON).get();
        System.out.println(respList.toString());
        assertEquals(200, respList.getStatus(), "must be 200");


        Response respAddOneMore = target.path("Apsis").path("add").request(MediaType.APPLICATION_JSON).post(Entity.json(null));
        System.out.println(respAddOneMore.toString());
        assertEquals(201, respAddOneMore.getStatus(), "must be 200");

        Response respListAgain = target.path("list").request(MediaType.APPLICATION_JSON).get();
        System.out.println(respListAgain.toString());
        assertEquals(200, respList.getStatus(), "must be 200");



        Response respAddUnEscaped = target.path("a/bcd").path("add").request(MediaType.APPLICATION_JSON).post(Entity.json(null));
        System.out.println(respAddUnEscaped.toString());
        assertEquals(404, respAddUnEscaped.getStatus(), "must be 404");
    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri(
                "http://localhost:9998/api/counters").build();
    }

}

