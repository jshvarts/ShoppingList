package com.jshvarts.shoppinglist.lobby.fragments;

import com.jshvarts.shoppinglist.rx.SchedulersFacade;

import dagger.Module;
import dagger.Provides;

/**
 * Define ShoppingListItemFragment-specific dependencies here.
 */
@Module
public class AddShoppingListItemModule {

    @Provides
    AddShoppingListItemViewModelFactory provideAddShoppingListItemViewModelFactory(
            AddShoppingListItemUseCase addShoppingListItemUseCase,
            LoadShoppingListUseCase loadShoppingListUseCase,
            SchedulersFacade schedulersFacade) {
        return new AddShoppingListItemViewModelFactory(addShoppingListItemUseCase, loadShoppingListUseCase, schedulersFacade);
    }
}
