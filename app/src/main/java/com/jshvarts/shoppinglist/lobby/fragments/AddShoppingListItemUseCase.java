package com.jshvarts.shoppinglist.lobby.fragments;

import com.jshvarts.shoppinglist.common.domain.model.ItemByIdSpecification;
import com.jshvarts.shoppinglist.common.domain.model.ShoppingList;
import com.jshvarts.shoppinglist.common.domain.model.firebase.FirebaseShoppingListRepository;

import javax.inject.Inject;

import io.reactivex.Completable;

public class AddShoppingListItemUseCase {
    private final FirebaseShoppingListRepository repository;

    @Inject
    AddShoppingListItemUseCase(FirebaseShoppingListRepository repository) {
        this.repository = repository;
    }

    Completable execute(ShoppingList shoppingList) {
        shoppingList.setId("-KqA71srmdEerhd_B6Et");
        return repository.update(shoppingList);
    }
}
