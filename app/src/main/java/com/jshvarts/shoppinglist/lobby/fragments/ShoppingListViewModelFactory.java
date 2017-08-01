package com.jshvarts.shoppinglist.lobby.fragments;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.jshvarts.shoppinglist.rx.SchedulersFacade;

public class ShoppingListViewModelFactory implements ViewModelProvider.Factory {

    private final LoadShoppingListUseCase loadShoppingListUseCase;

    private final UpdateShoppingListUseCase updateShoppingListUseCase;

    private final SchedulersFacade schedulersFacade;

    ShoppingListViewModelFactory(LoadShoppingListUseCase loadShoppingListUseCase,
                                 UpdateShoppingListUseCase updateShoppingListUseCase,
                                 SchedulersFacade schedulersFacade) {
        this.loadShoppingListUseCase = loadShoppingListUseCase;
        this.updateShoppingListUseCase = updateShoppingListUseCase;
        this.schedulersFacade = schedulersFacade;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ShoppingListViewModel.class)) {
            return (T) new ShoppingListViewModel(loadShoppingListUseCase, updateShoppingListUseCase, schedulersFacade);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
