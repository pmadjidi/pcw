package com.payam.test.counters;

import com.google.gson.Gson;
import org.codehaus.jettison.json.JSONObject;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/api/counters")
@Produces(MediaType.APPLICATION_JSON)
public class RestResource {

    @Inject
    public NamedCounters counters;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{key}/add")
    public Response addJSON(@PathParam("key") String key) {
        String val;
        Map<String, String> result = new HashMap<>();
        try {
            val =  counters.add(key).toString();
            result.put(key, val);
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (Exception e) {
            e.printStackTrace();
           return  Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }



    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{key}/get")
    public Response getJSON(@PathParam("key") String key) {
        String val;
        Map<String, String> result = new HashMap<>();
        try {
            val =  counters.get(key).toString();
            result.put(key, val);
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (NullPointerException e) {
            result.put("KEYMISSING", key);
           return  Response.status(Response.Status.OK).entity(result).build();
        } catch (Exception e) {
            e.printStackTrace();
            return  Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{key}/inc")
    public Response incJSON(@PathParam("key") String key) {
        String val;
        Map<String, String> result = new HashMap<>();
        try {
            val =  counters.inc(key).toString();
            result.put(key, val);
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (NullPointerException e) {
            result.put("KEYMISSING", key);
           return  Response.status(Response.Status.OK).entity(result).build();
        } catch (Exception e) {
            e.printStackTrace();
            return  Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public  Response listJSON() {
        Map<String, String> result;
        try {
            result = counters.scan();
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (Exception e) {
            e.printStackTrace();
            return  Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
