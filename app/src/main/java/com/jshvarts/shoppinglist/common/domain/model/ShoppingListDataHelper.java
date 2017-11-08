package com.jshvarts.shoppinglist.common.domain.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates common functionality for a shopping list.
 */
public class ShoppingListDataHelper {

    public boolean completeItem(final ShoppingList shoppingList, final int itemIndex) {
        if (shoppingList.getItems().isEmpty()) {
            return false;
        }
        if (itemIndex > shoppingList.getItems().size()-1) {
            return false;
        }
        ShoppingListItem completedShoppingListItem = shoppingList.getItems().get(itemIndex);
        completedShoppingListItem.setCompleted(true);
        return true;
    }

    public void addItem(final ShoppingList shoppingList, final String itemName) {
        ShoppingListItem item = new ShoppingListItem(itemName);
        shoppingList.getItems().add(item);
    }

    public boolean removeCompletedItems(final ShoppingList shoppingList) {
        if (shoppingList.getItems().isEmpty()) {
            return false;
        }

        List<ShoppingListItem> completedItems = new ArrayList<>();
        for (ShoppingListItem shoppingListItem : shoppingList.getItems()) {
            if (shoppingListItem.getCompleted()) {
                completedItems.add(shoppingListItem);
            }
        }
        return shoppingList.getItems().removeAll(completedItems);
    }
}
