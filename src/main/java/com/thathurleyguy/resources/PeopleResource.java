package com.thathurleyguy.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.thathurleyguy.Person;
import com.thathurleyguy.PersonDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("people")
@Produces(MediaType.APPLICATION_JSON)
public class PeopleResource {
    private final PersonDAO dao;

    public PeopleResource(PersonDAO dao) {
        this.dao = dao;
    }

    @POST
    public void createPerson(Person person) {
        dao.create(person.getId(), person.getName());
    }

    @GET
    public List<Person> listPeople() {
        return dao.findAll();
    }
}

