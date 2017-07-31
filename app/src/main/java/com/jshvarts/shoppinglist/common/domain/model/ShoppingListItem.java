package com.jshvarts.shoppinglist.common.domain.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.util.Map;

public class ShoppingListItem {
    private String name;
    private boolean completed;
    private Long timestamp;

    public ShoppingListItem() {
        // required by Firebase
    }

    public ShoppingListItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Map<String, String> getTimestamp() {
        return ServerValue.TIMESTAMP;
    }

    @Exclude
    public Long getTimestampLong() {
        return timestamp;
    }
}
