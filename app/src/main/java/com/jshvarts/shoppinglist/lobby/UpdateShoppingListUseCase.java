package com.jshvarts.shoppinglist.lobby;

import com.jshvarts.shoppinglist.common.domain.model.ShoppingList;
import com.jshvarts.shoppinglist.common.domain.model.firebase.FirebaseShoppingListRepository;

import javax.inject.Inject;

import io.reactivex.Single;

public class UpdateShoppingListUseCase {
    private final FirebaseShoppingListRepository repository;

    @Inject
    UpdateShoppingListUseCase(FirebaseShoppingListRepository repository) {
        this.repository = repository;
    }

    Single<ShoppingList> updateShoppingList(ShoppingList shoppingList) {
        return repository.update(shoppingList);
    }
}
