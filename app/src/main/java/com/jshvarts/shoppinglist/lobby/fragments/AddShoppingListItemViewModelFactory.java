package com.jshvarts.shoppinglist.lobby.fragments;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

class AddShoppingListItemViewModelFactory implements ViewModelProvider.Factory {

    private final AddShoppingListItemUseCase addShoppingListItemUseCase;

    AddShoppingListItemViewModelFactory(AddShoppingListItemUseCase addShoppingListItemUseCase) {
        this.addShoppingListItemUseCase = addShoppingListItemUseCase;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AddShoppingListItemViewModel.class)) {
            return (T) new AddShoppingListItemViewModel(addShoppingListItemUseCase);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
