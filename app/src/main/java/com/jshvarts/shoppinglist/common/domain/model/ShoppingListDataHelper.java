package com.jshvarts.shoppinglist.common.domain.model;

/**
 * Encapsulates common functionality for a shopping list.
 */
public class ShoppingListDataHelper {

    /**
     * Finds item completed, updates its status, removes it from previous spot and appends it
     * at the end of the list.
     *
     * @param shoppingList
     * @param itemIndex
     */
    public void completeItem(final ShoppingList shoppingList, final int itemIndex) {
        // TODO if item is at the end of the list, just set completed flag
        ShoppingListItem completedShoppingListItem = shoppingList.getItems().remove(itemIndex);
        completedShoppingListItem.setCompleted(true);
        shoppingList.getItems().add(completedShoppingListItem);
    }

    /**
     * Adds item at the end of the item list or, if there are completed items, before first completed item.
     *
     * @param shoppingList
     * @param itemName
     */
    public void addItem(final ShoppingList shoppingList, final String itemName) {
        ShoppingListItem item = new ShoppingListItem(itemName);
        int itemCount = shoppingList.getItems().size();
        if (itemCount == 0) {
            shoppingList.getItems().add(item);
        } else if (shoppingList.getItems().get(itemCount - 1).getCompleted()) {
            // there are completed items--insert new non-completed item before first completed item
            for (int i = 0; i < itemCount; i++) {
                ShoppingListItem currentItem = shoppingList.getItems().get(i);
                if (currentItem.getCompleted()) {
                    shoppingList.getItems().add(i, item);
                    break;
                }
            }
        } else {
            shoppingList.getItems().add(item);
        }
    }

    /**
     * Recursively removes one completed item at a time
     */
    public void removeCompletedItems(final ShoppingList shoppingList) {
        if (shoppingList.getItems().isEmpty()) {
            return;
        }

        int lastItemIndex = shoppingList.getItems().size()-1;
        if (shoppingList.getItems().get(lastItemIndex).getCompleted()) {
            shoppingList.getItems().remove(lastItemIndex);
        } else {
            return;
        }

        removeCompletedItems(shoppingList);
    }
}
