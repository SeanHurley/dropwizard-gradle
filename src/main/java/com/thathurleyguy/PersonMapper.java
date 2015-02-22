package com.thathurleyguy;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements ResultSetMapper<Person> {
    @Override
    public Person map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new Person(r.getInt("id"), r.getString("name"));
    }
}
