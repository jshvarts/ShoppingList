package com.jshvarts.shoppinglist.lobby.fragments;

import com.jshvarts.shoppinglist.common.domain.model.ShoppingList;
import com.jshvarts.shoppinglist.common.domain.model.ShoppingListDataHelper;
import com.jshvarts.shoppinglist.common.domain.model.firebase.FirebaseShoppingListRepository;

import javax.inject.Inject;

import io.reactivex.Single;

class AddShoppingListItemUseCase {

    private final FirebaseShoppingListRepository repository;

    private final ShoppingListDataHelper shoppingListDataHelper;

    @Inject
    AddShoppingListItemUseCase(FirebaseShoppingListRepository repository, ShoppingListDataHelper shoppingListDataHelper) {
        this.repository = repository;
        this.shoppingListDataHelper = shoppingListDataHelper;
    }

    Single<ShoppingList> execute(ShoppingList shoppingList, String itemName) {
        shoppingListDataHelper.addItem(shoppingList, itemName);
        return repository.update(shoppingList);
    }
}
