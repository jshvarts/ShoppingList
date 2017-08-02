package com.jshvarts.shoppinglist.lobby.fragments;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.jshvarts.shoppinglist.common.domain.model.ShoppingListDataHelper;
import com.jshvarts.shoppinglist.rx.SchedulersFacade;

public class DeleteCompletedItemsViewModelFactory implements ViewModelProvider.Factory {

    private final Application application;

    private final LoadShoppingListUseCase loadShoppingListUseCase;

    private final UpdateShoppingListUseCase updateShoppingListUseCase;

    private final SchedulersFacade schedulersFacade;

    private final ShoppingListDataHelper shoppingListDataHelper;

    DeleteCompletedItemsViewModelFactory(Application application,
                                         LoadShoppingListUseCase loadShoppingListUseCase,
                                         UpdateShoppingListUseCase updateShoppingListUseCase,
                                         SchedulersFacade schedulersFacade,
                                         ShoppingListDataHelper shoppingListDataHelper) {
        this.application = application;
        this.loadShoppingListUseCase = loadShoppingListUseCase;
        this.updateShoppingListUseCase = updateShoppingListUseCase;
        this.schedulersFacade = schedulersFacade;
        this.shoppingListDataHelper = shoppingListDataHelper;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DeleteCompletedItemsViewModel.class)) {
            return (T) new DeleteCompletedItemsViewModel(application,
                    loadShoppingListUseCase,
                    updateShoppingListUseCase,
                    schedulersFacade,
                    shoppingListDataHelper);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
