package com.thathurleyguy.resources;

import com.thathurleyguy.Person;
import io.dropwizard.jersey.params.IntParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("people/{id}")
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {

    public PersonResource() {
    }

    @GET
    public Person show(@PathParam("id") IntParam id) {
        return null;
    }
}
