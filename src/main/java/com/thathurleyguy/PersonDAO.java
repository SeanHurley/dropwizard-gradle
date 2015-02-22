package com.thathurleyguy;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(PersonMapper.class)
public interface PersonDAO {
    @SqlUpdate("INSERT INTO people (id, name) VALUES (:id, :name)")
    void create(@Bind("id") int id, @Bind("name") String name);

    @SqlQuery("SELECT * FROM people")
    List<Person> findAll();

    @SqlQuery("SELECT * FROM people WHERE id = :id")
    Person findById(@Bind("id") int id);
}
