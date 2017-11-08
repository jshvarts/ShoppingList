package com.jshvarts.shoppinglist.common.domain.model;

import java.util.Comparator;

/**
 * Allows sorting items by name
 */
public class ShoppingListItemNameComparator implements Comparator<ShoppingListItem> {
    @Override
    public int compare(ShoppingListItem o1, ShoppingListItem o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
