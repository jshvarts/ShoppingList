package com.jshvarts.shoppinglist.lobby;

import com.jshvarts.shoppinglist.rx.SchedulersFacade;

import dagger.Module;
import dagger.Provides;

/**
 * Define LobbyActivity-specific dependencies here.
 */
@Module
public class LobbyModule {

    @Provides
    LobbyViewModelFactory provideLobbyViewModelFactory(CreateShoppingListUseCase loadLobbyGreetingUseCase,
                                                       SchedulersFacade schedulersFacade) {
        return new LobbyViewModelFactory(loadLobbyGreetingUseCase, schedulersFacade);
    }
}
