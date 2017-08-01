package com.jshvarts.shoppinglist.lobby.fragments;

import com.jshvarts.shoppinglist.common.domain.model.ItemByIdSpecification;
import com.jshvarts.shoppinglist.common.domain.model.ItemsSpecification;
import com.jshvarts.shoppinglist.common.domain.model.ShoppingList;
import com.jshvarts.shoppinglist.common.domain.model.firebase.FirebaseShoppingListRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;

class LoadShoppingListUseCase {
    private final FirebaseShoppingListRepository repository;

    @Inject
    LoadShoppingListUseCase(FirebaseShoppingListRepository repository) {
        this.repository = repository;
    }

    /**
     * Loads all available shopping lists, if any
     *
     * @return
     */
    Single<List<ShoppingList>> loadAvailableShoppingLists() {
        ItemsSpecification itemsSpecification = new ItemsSpecification(1);
        return repository.getItems(itemsSpecification);
    }

    Observable<ShoppingList> loadShoppingList(String id) {
        ItemByIdSpecification byIdSpecification = new ItemByIdSpecification(id);
        return repository.getItem(byIdSpecification);
    }
}
