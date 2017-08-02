package com.jshvarts.shoppinglist.lobby.fragments;

import com.jshvarts.shoppinglist.common.domain.model.ItemByIdSpecification;
import com.jshvarts.shoppinglist.common.domain.model.ShoppingList;
import com.jshvarts.shoppinglist.common.domain.model.firebase.FirebaseShoppingListRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

class LoadShoppingListUseCase {
    private final FirebaseShoppingListRepository repository;

    @Inject
    LoadShoppingListUseCase(FirebaseShoppingListRepository repository) {
        this.repository = repository;
    }

    Observable<ShoppingList> loadShoppingList(String id) {
        ItemByIdSpecification byIdSpecification = new ItemByIdSpecification(id);
        return repository.getItem(byIdSpecification);
    }
}
