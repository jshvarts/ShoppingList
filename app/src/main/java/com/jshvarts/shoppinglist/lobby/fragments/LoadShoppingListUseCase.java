package com.jshvarts.shoppinglist.lobby.fragments;

import com.jshvarts.shoppinglist.common.domain.model.ItemByIdSpecification;
import com.jshvarts.shoppinglist.common.domain.model.ShoppingList;
import com.jshvarts.shoppinglist.common.domain.model.firebase.FirebaseShoppingListRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

class LoadShoppingListUseCase {
    private final static String SHOPPING_LIST_ID = "-KqA71srmdEerhd_B6Et";
    private final FirebaseShoppingListRepository repository;

    @Inject
    LoadShoppingListUseCase(FirebaseShoppingListRepository repository) {
        this.repository = repository;
    }

    /**
     * Loads default shopping list
     *
     * @return
     */
    Observable<ShoppingList> execute() {
        ItemByIdSpecification byIdSpecification = new ItemByIdSpecification(SHOPPING_LIST_ID);
        return repository.getItem(byIdSpecification);
    }
}
