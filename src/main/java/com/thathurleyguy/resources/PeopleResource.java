package com.thathurleyguy.resources;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.thathurleyguy.Person;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Path("people")
@Produces(MediaType.APPLICATION_JSON)
public class PeopleResource {
    private final Session cassandra;

    public PeopleResource(Session cassandra) {
        this.cassandra = cassandra;
    }

    @POST
    public void createPerson(Person person) {
        cassandra.execute("INSERT INTO people (id, name) VALUES (" + UUID.randomUUID().toString() + ", '" + person.getName() + "')");
    }

    @GET
    public List<Person> listPeople() {
        ResultSet results = cassandra.execute("SELECT * FROM PEOPLE");
        List<Person> people = new ArrayList<>();
        results.forEach(row -> people.add(new Person(row)));
        return people;
    }
}

