package com.jshvarts.shoppinglist.lobby;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.jshvarts.shoppinglist.rx.SchedulersFacade;

class LobbyViewModelFactory implements ViewModelProvider.Factory {

    private final CreateShoppingListUseCase loadLobbyGreetingUseCase;

    private final SchedulersFacade schedulersFacade;

    LobbyViewModelFactory(CreateShoppingListUseCase loadLobbyGreetingUseCase,
                          SchedulersFacade schedulersFacade) {
        this.loadLobbyGreetingUseCase = loadLobbyGreetingUseCase;
        this.schedulersFacade = schedulersFacade;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LobbyViewModel.class)) {
            return (T) new LobbyViewModel(loadLobbyGreetingUseCase, schedulersFacade);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
