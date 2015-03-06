package com.thathurleyguy.resources;

import com.codahale.metrics.annotation.Timed;
import com.datastax.driver.core.Session;
import com.thathurleyguy.models.Group;
import com.thathurleyguy.db.GroupMapper;
import redis.clients.jedis.Jedis;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("data/")
@Produces(MediaType.APPLICATION_JSON)
public class GroupResource {
    private final GroupMapper groupMapper;
    private final Jedis jedis;

    public GroupResource(Session cassandra, Jedis jedis) {
        this.groupMapper = new GroupMapper(cassandra);
        this.jedis = jedis;
    }

    @Timed
    @GET
    public List<Group> getGroups() {
        return groupMapper.findAll();
    }
}
