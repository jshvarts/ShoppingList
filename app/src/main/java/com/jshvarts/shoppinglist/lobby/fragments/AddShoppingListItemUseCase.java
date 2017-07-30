package com.jshvarts.shoppinglist.lobby.fragments;

import com.jshvarts.shoppinglist.common.domain.model.ShoppingList;
import com.jshvarts.shoppinglist.common.domain.model.ShoppingListItem;
import com.jshvarts.shoppinglist.common.domain.model.firebase.FirebaseShoppingListRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;

public class AddShoppingListItemUseCase {
    private final FirebaseShoppingListRepository repository;

    @Inject
    AddShoppingListItemUseCase(FirebaseShoppingListRepository repository) {
        this.repository = repository;
    }

    Completable execute(ShoppingList shoppingList, String shoppingListItemName) {
        addOrUpdateShoppingListItems(shoppingList, shoppingListItemName);
        return repository.update(shoppingList);
    }

    private void addOrUpdateShoppingListItems(ShoppingList shoppingList, String shoppingListItemName) {
        ShoppingListItem shoppingListItem = new ShoppingListItem(shoppingListItemName);
        if (shoppingList.getItems() != null && !shoppingList.getItems().isEmpty()) {
            shoppingList.getItems().add(shoppingListItem);
        } else {
            List<ShoppingListItem> items = new ArrayList<>();
            items.add(shoppingListItem);
            shoppingList.setItems(items);
        }
    }
}
