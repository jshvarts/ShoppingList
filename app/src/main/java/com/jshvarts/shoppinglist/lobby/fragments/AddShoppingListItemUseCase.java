package com.jshvarts.shoppinglist.lobby.fragments;

import com.jshvarts.shoppinglist.common.domain.model.ShoppingList;
import com.jshvarts.shoppinglist.common.domain.model.ShoppingListItem;
import com.jshvarts.shoppinglist.common.domain.model.firebase.FirebaseShoppingListRepository;

import javax.inject.Inject;

import io.reactivex.Completable;

public class AddShoppingListItemUseCase {
    private final FirebaseShoppingListRepository repository;

    @Inject
    AddShoppingListItemUseCase(FirebaseShoppingListRepository repository) {
        this.repository = repository;
    }

    Completable execute(ShoppingList shoppingList, String shoppingListItemName) {
        addShoppingListItem(shoppingList, shoppingListItemName);
        return repository.update(shoppingList);
    }

    private void addShoppingListItem(ShoppingList shoppingList, String shoppingListItemName) {
        ShoppingListItem shoppingListItem = new ShoppingListItem(shoppingListItemName);
        shoppingList.getItems().add(shoppingListItem);
    }
}
