package com.thathurleyguy.db;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.thathurleyguy.models.Group;

import java.util.ArrayList;
import java.util.List;

public class GroupMapper {
    private final Session cassandra;

    public GroupMapper(Session cassandra) {
        this.cassandra = cassandra;
    }

    public List<Group> findAll() {
        List<Group> groups = new ArrayList<>();
        ResultSet result = cassandra.execute("SELECT DISTINCT group_id FROM items");
        result.forEach(row -> groups.add(new Group(row)));
        return groups;
    }
}
