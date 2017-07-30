package com.jshvarts.shoppinglist.lobby;

import com.jshvarts.shoppinglist.rx.SchedulersFacade;

import dagger.Module;
import dagger.Provides;

/**
 * Define LobbyActivity-specific dependencies here.
 */
@Module
public class LobbyActivityModule {

    @Provides
    ShoppingListViewModelFactory provideShoppingListViewModelFactory(LoadShoppingListUseCase loadShoppingListUseCase,
                                                       CreateShoppingListUseCase createShoppingListUseCase,
                                                       SchedulersFacade schedulersFacade) {
        return new ShoppingListViewModelFactory(loadShoppingListUseCase, createShoppingListUseCase, schedulersFacade);
    }
}
