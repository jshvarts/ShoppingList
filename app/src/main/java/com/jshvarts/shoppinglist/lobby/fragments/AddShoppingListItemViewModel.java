package com.jshvarts.shoppinglist.lobby.fragments;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.jshvarts.shoppinglist.common.domain.model.ShoppingList;

public class AddShoppingListItemViewModel extends ViewModel {
    private final AddShoppingListItemUseCase addShoppingListItemUseCase;

    MutableLiveData<Boolean> itemAdded = new MutableLiveData<>();

    AddShoppingListItemViewModel(AddShoppingListItemUseCase addShoppingListItemUseCase) {
        this.addShoppingListItemUseCase = addShoppingListItemUseCase;
    }

    MutableLiveData<Boolean> isItemAdded() {
        return itemAdded;
    }

    public void addShoppingListItem(ShoppingList shoppingList, String shoppingListItemName) {
        itemAdded.setValue(true);
        // TODO create subscription
        addShoppingListItemUseCase.execute(shoppingList, shoppingListItemName);
    }
}
