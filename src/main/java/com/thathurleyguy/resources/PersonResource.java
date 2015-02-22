package com.thathurleyguy.resources;

import com.thathurleyguy.Person;
import com.thathurleyguy.PersonDAO;
import io.dropwizard.jersey.params.IntParam;
import io.dropwizard.jersey.params.LongParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("people/{id}")
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {
    private final PersonDAO dao;

    public PersonResource(PersonDAO dao) {
        this.dao = dao;
    }

    @GET
    public Person show(@PathParam("id") IntParam id) {
        return dao.findById(id.get());
    }
}
