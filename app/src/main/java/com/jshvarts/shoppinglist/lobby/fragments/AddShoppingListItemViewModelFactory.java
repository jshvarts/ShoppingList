package com.jshvarts.shoppinglist.lobby.fragments;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.jshvarts.shoppinglist.rx.SchedulersFacade;

class AddShoppingListItemViewModelFactory implements ViewModelProvider.Factory {

    private final AddShoppingListItemUseCase addShoppingListItemUseCase;

    private final LoadShoppingListUseCase loadShoppingListUseCase;

    private final SchedulersFacade schedulersFacade;

    AddShoppingListItemViewModelFactory(AddShoppingListItemUseCase addShoppingListItemUseCase,
                                        LoadShoppingListUseCase loadShoppingListUseCase,
                                        SchedulersFacade schedulersFacade) {
        this.addShoppingListItemUseCase = addShoppingListItemUseCase;
        this.loadShoppingListUseCase = loadShoppingListUseCase;
        this.schedulersFacade = schedulersFacade;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AddShoppingListItemViewModel.class)) {
            return (T) new AddShoppingListItemViewModel(addShoppingListItemUseCase, loadShoppingListUseCase, schedulersFacade);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
