package com.jshvarts.shoppinglist.common.domain.model;

public class ItemByIdSpecification implements Specification {

    private final String id;

    public ItemByIdSpecification(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
