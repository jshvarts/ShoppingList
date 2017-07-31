package com.jshvarts.shoppinglist.lobby;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import com.jshvarts.shoppinglist.rx.SchedulersFacade;

public class ShoppingListViewModelFactory implements ViewModelProvider.Factory {

    private final LoadShoppingListUseCase loadShoppingListUseCase;

    private final CreateShoppingListUseCase createShoppingListUseCase;

    private final UpdateShoppingListUseCase updateShoppingListUseCase;

    private final SchedulersFacade schedulersFacade;

    ShoppingListViewModelFactory(LoadShoppingListUseCase loadShoppingListUseCase,
                                 CreateShoppingListUseCase createShoppingListUseCase,
                                 UpdateShoppingListUseCase updateShoppingListUseCase,
                                 SchedulersFacade schedulersFacade) {
        this.loadShoppingListUseCase = loadShoppingListUseCase;
        this.createShoppingListUseCase = createShoppingListUseCase;
        this.updateShoppingListUseCase = updateShoppingListUseCase;
        this.schedulersFacade = schedulersFacade;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ShoppingListViewModel.class)) {
            return (T) new ShoppingListViewModel(loadShoppingListUseCase, createShoppingListUseCase, updateShoppingListUseCase, schedulersFacade);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
