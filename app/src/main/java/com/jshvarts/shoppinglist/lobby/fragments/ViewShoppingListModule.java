package com.jshvarts.shoppinglist.lobby.fragments;

import com.jshvarts.shoppinglist.rx.SchedulersFacade;

import dagger.Module;
import dagger.Provides;

/**
 * Define LobbyActivity-specific dependencies here.
 */
@Module
public class ViewShoppingListModule {

    @Provides
    ShoppingListViewModelFactory provideShoppingListViewModelFactory(LoadShoppingListUseCase loadShoppingListUseCase,
                                                                     UpdateShoppingListUseCase updateShoppingListUseCase,
                                                                     SchedulersFacade schedulersFacade) {
        return new ShoppingListViewModelFactory(loadShoppingListUseCase, updateShoppingListUseCase, schedulersFacade);
    }
}
