package com.jshvarts.shoppinglist.lobby.fragments;

import com.jshvarts.shoppinglist.common.domain.model.ShoppingList;
import com.jshvarts.shoppinglist.common.domain.model.ShoppingListItem;
import com.jshvarts.shoppinglist.common.domain.model.firebase.FirebaseShoppingListRepository;

import javax.inject.Inject;

import io.reactivex.Single;
import timber.log.Timber;

public class AddShoppingListItemUseCase {
    private final FirebaseShoppingListRepository repository;

    @Inject
    AddShoppingListItemUseCase(FirebaseShoppingListRepository repository) {
        this.repository = repository;
    }

    Single<ShoppingList> execute(ShoppingList shoppingList, String shoppingListItemName) {
        addItem(shoppingList, new ShoppingListItem(shoppingListItemName));
        return repository.update(shoppingList);
    }

    // TODO create a helper class for this logic to comply with SOLID
    private void addItem(ShoppingList shoppingList, ShoppingListItem item) {
        Timber.d("james adding item");
        int itemCount = shoppingList.getItems().size();
        if (itemCount == 0) {
            shoppingList.getItems().add(item);
        } else if (shoppingList.getItems().get(itemCount - 1).getCompleted()) { // there are completed items in the list
            // insert new non-completed item before first completed item
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
}
