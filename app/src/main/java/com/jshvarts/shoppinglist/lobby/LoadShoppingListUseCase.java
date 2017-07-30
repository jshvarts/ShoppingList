package com.jshvarts.shoppinglist.lobby;

import com.jshvarts.shoppinglist.common.domain.model.ItemByIdSpecification;
import com.jshvarts.shoppinglist.common.domain.model.ItemsSpecification;
import com.jshvarts.shoppinglist.common.domain.model.ShoppingList;
import com.jshvarts.shoppinglist.common.domain.model.firebase.FirebaseShoppingListRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class LoadShoppingListUseCase {
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
    public Observable<List<ShoppingList>> loadAvailableShoppingLists() {
        ItemsSpecification itemsSpecification = new ItemsSpecification(1);
        return repository.getItems(itemsSpecification);
    }

    public Observable<ShoppingList> loadCurrentShoppingList(String id) {
        ItemByIdSpecification byIdSpecification = new ItemByIdSpecification(id);
        return repository.getItem(byIdSpecification);
    }
}
