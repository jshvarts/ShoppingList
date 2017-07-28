package com.jshvarts.shoppinglist.lobby;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.jshvarts.shoppinglist.rx.SchedulersFacade;

class LobbyViewModelFactory implements ViewModelProvider.Factory {

    private final LoadShoppingListUseCase loadShoppingListUseCase;

    private final CreateShoppingListUseCase createShoppingListUseCase;

    private final SchedulersFacade schedulersFacade;

    LobbyViewModelFactory(LoadShoppingListUseCase loadShoppingListUseCase,
                          CreateShoppingListUseCase createShoppingListUseCase,
                          SchedulersFacade schedulersFacade) {
        this.loadShoppingListUseCase = loadShoppingListUseCase;
        this.createShoppingListUseCase = createShoppingListUseCase;
        this.schedulersFacade = schedulersFacade;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LobbyViewModel.class)) {
            return (T) new LobbyViewModel(loadShoppingListUseCase, createShoppingListUseCase, schedulersFacade);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
