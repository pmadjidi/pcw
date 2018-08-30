package com.payam.test.counters;

import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/api/counters")
public class RestResource {
    // to do depencecy injection ....
    private static NamedCounters counters = new NamedCounters();
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{key}/add")
    public String add(@PathParam("key") String key) {
        String result;
        try {
            result =  counters.add(key).toString();
            return "{" + key + ":" + result + "}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{" + key + ":ERROR" + "}";
        }
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{key}/get")
    public String get(@PathParam("key") String key) {

        String result;
        try {
            result =  counters.get(key).toString();
            return "{" + key + ":" + result + "}";
        }  catch (NullPointerException e) {
            return "{" + key + ":missing" + "}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{" + key + ":ERROR" + "}";
        }
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{key}/inc")
    public String inc(@PathParam("key") String key) {
        String result;
        try {
            result =  counters.inc(key).toString();
            return "{" + key + ":" + result + "}";
        } catch (NullPointerException e) {
            return "{" + key + ":missing" + "}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{" + key + ":ERROR" + "}";
        }
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/list")
    public String list() {
        String result;
        try {
            result =  new Gson().toJson(counters.scan());
              return "{" + "list" + ":" + result + "}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{" + "list" + ":ERROR" + "}";
        }
    }
}
