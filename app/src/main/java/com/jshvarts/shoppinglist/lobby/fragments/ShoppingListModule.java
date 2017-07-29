package com.jshvarts.shoppinglist.lobby.fragments;

import com.jshvarts.shoppinglist.rx.SchedulersFacade;

import dagger.Module;
import dagger.Provides;

/**
 * Define LobbyActivity-specific dependencies here.
 */
@Module
public class ShoppingListModule {

    @Provides
    ShoppingListViewModelFactory provideShoppingListViewModelFactory(LoadShoppingListUseCase loadShoppingListUseCase,
                                                       CreateShoppingListUseCase createShoppingListUseCase,
                                                       SchedulersFacade schedulersFacade) {
        return new ShoppingListViewModelFactory(loadShoppingListUseCase, createShoppingListUseCase, schedulersFacade);
    }
}
