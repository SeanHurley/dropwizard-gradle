package com.thathurleyguy.resources;

import com.codahale.metrics.annotation.Timed;
import com.datastax.driver.core.Session;
import com.thathurleyguy.models.Item;
import com.thathurleyguy.db.ItemMapper;
import redis.clients.jedis.Jedis;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("data/{groupId}/{itemId}")
@Produces(MediaType.APPLICATION_JSON)
public class ItemResource {
    private final ItemMapper itemMapper;
    private final Jedis jedis;

    public ItemResource(Session cassandra, Jedis jedis) {
        this.itemMapper = new ItemMapper(cassandra);
        this.jedis = jedis;
    }

    @Timed
    @GET
    public Item show(@PathParam("groupId") String groupId, @PathParam("itemId") String itemId) {
        return itemMapper.findByGroupIdAndItemId(groupId, itemId);
    }
}
