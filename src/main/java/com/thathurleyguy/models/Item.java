package com.thathurleyguy.models;

import com.datastax.driver.core.Row;

public class Item {
    private String groupId;
    private String itemId;
    private String data;

    public Item() {
    }

    public Item(String groupId, String itemId, String data) {
        this.groupId = groupId;
        this.itemId = itemId;
        this.data = data;
    }

    public Item(Row row) {
        this.groupId = row.getString("group_id");
        this.itemId = row.getString("item_id");
        this.data = row.getString("data");
    }

    public String getGroupId() {
        return groupId;
    }

    public String getData() {
        return data;
    }

    public String getItemId() {
        return itemId;
    }
}
