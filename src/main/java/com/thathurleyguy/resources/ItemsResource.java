package com.thathurleyguy.resources;

import com.codahale.metrics.annotation.Timed;
import com.datastax.driver.core.Session;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thathurleyguy.models.Item;
import com.thathurleyguy.db.ItemMapper;
import com.thathurleyguy.models.ItemRequest;
import redis.clients.jedis.Jedis;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("data/{groupId}")
@Produces(MediaType.APPLICATION_JSON)
public class ItemsResource {
    private final ItemMapper itemMapper;
    private final Jedis jedis;

    public ItemsResource(Session cassandra, Jedis jedis) {
        this.itemMapper = new ItemMapper(cassandra);
        this.jedis = jedis;
    }

    @Timed
    @POST
    public void createItem(@PathParam("groupId") String groupId, ItemRequest itemRequest) {
        Item item = new Item(groupId, itemRequest.getItemId(), itemRequest.getData());
        this.itemMapper.create(item);
        ObjectMapper oj = new ObjectMapper();
        try {
            String payload = oj.writeValueAsString(item);
            jedis.rpush("queue", payload);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Timed
    @GET
    public List<Item> listItems(@PathParam("groupId") String groupId) {
        List<Item> items = itemMapper.findAll(groupId);
        return items;
    }
}
