package com.thathurleyguy.db;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.thathurleyguy.models.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemMapper {
    private final Session cassandra;

    public ItemMapper(Session cassandra) {
        this.cassandra = cassandra;
    }

    public Item findByGroupIdAndItemId(String groupId, String itemId) {
        ResultSet result = cassandra.execute("SELECT * FROM items  WHERE group_id = ? AND item_id = ?", groupId, itemId);
        return new Item(result.one());
    }

    public void create(Item item) {
        ResultSet result = cassandra.execute("INSERT INTO items (group_id, item_id, data) VALUES (?,?,?)", item.getGroupId(), item.getItemId(), item.getData());
    }

    public List<Item> findAll(String groupId) {
        System.out.println("TESTSETSTESTSET" + groupId);
        List<Item> items = new ArrayList<>();
        ResultSet result = cassandra.execute("SELECT * FROM items WHERE group_id = ?", groupId);
        result.forEach(row -> items.add(new Item(row)));
        return items;
    }
}
