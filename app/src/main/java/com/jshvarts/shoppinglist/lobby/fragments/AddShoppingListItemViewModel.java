package com.jshvarts.shoppinglist.lobby.fragments;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.jshvarts.shoppinglist.common.domain.model.ShoppingList;

public class AddShoppingListItemViewModel extends ViewModel {
    private final AddShoppingListItemUseCase addShoppingListItemUseCase;

    MutableLiveData<Boolean> hideKeyboard = new MutableLiveData<>();

    AddShoppingListItemViewModel(AddShoppingListItemUseCase addShoppingListItemUseCase) {
        this.addShoppingListItemUseCase = addShoppingListItemUseCase;
    }

    MutableLiveData<Boolean> shouldHideKeyboard() {
        return hideKeyboard;
    }

    public void addShoppingListItem(ShoppingList shoppingList, String shoppingListItemName) {
        hideKeyboard.setValue(true);
        addShoppingListItemUseCase.execute(shoppingList, shoppingListItemName);
    }
}
