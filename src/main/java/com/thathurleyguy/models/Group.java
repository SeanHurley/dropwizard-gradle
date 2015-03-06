package com.thathurleyguy.models;

import com.datastax.driver.core.Row;

public class Group {
    private String groupId;

    public Group(){
    }

    public Group(Row row) {
        this.groupId = row.getString("group_id");
    }

    public String getGroupId() {
        return groupId;
    }
}
