package com.jshvarts.shoppinglist.lobby;

import com.jshvarts.shoppinglist.common.domain.model.ShoppingList;
import com.jshvarts.shoppinglist.common.domain.model.ShoppingListItem;
import com.jshvarts.shoppinglist.common.domain.model.firebase.FirebaseShoppingListRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;

class CreateShoppingListUseCase {
    private final FirebaseShoppingListRepository repository;

    @Inject
    CreateShoppingListUseCase(FirebaseShoppingListRepository repository) {
        this.repository = repository;
    }

    Completable execute() {
        ShoppingList shoppingList = new ShoppingList();
        return repository.add(shoppingList);
    }
}
