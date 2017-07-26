package com.jshvarts.shoppinglist.lobby;

import com.jshvarts.shoppinglist.common.domain.model.ShoppingList;
import com.jshvarts.shoppinglist.common.domain.model.ShoppingListRepository;

import javax.inject.Inject;

import io.reactivex.Completable;

class CreateShoppingListUseCase {
    private final ShoppingListRepository repository;

    @Inject
    CreateShoppingListUseCase(ShoppingListRepository repository) {
        this.repository = repository;
    }

    Completable execute(String shoppingListName) {
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setName(shoppingListName);
        shoppingList.setTemplate(true);
        return repository.save(shoppingList);
    }
}
