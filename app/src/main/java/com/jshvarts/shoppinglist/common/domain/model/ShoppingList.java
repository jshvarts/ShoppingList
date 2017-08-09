package com.jshvarts.shoppinglist.common.domain.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ServerValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@IgnoreExtraProperties
public class ShoppingList {

    private String id;
    // Only List is supported by Firebase. Set would be more is more applicable though
    private List<ShoppingListItem> items = new ArrayList<>();
    private Long timestamp;

    public ShoppingList() {
        // required by Firebase
    }

    public ShoppingList(List<ShoppingListItem> items) {
        this.items = items;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ShoppingListItem> getItems() {
        return items;
    }

    public void setItems(List<ShoppingListItem> items) {
        this.items = items;
    }

    public Map<String, String> getTimestamp() {
        return ServerValue.TIMESTAMP;
    }

    @Exclude
    public Long getTimestampLong() {
        return timestamp;
    }
}
