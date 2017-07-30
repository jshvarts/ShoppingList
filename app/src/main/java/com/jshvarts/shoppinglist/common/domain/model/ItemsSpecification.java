package com.jshvarts.shoppinglist.common.domain.model;

public class ItemsSpecification implements Specification {
    private final int maxCount;

    public ItemsSpecification(int maxCount) {
        this.maxCount = maxCount;
    }

    public int getMaxCount() {
        return maxCount;
    }
}
