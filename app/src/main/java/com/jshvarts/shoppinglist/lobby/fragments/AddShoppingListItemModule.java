package com.jshvarts.shoppinglist.lobby.fragments;

import dagger.Module;
import dagger.Provides;

/**
 * Define ShoppingListItemFragment-specific dependencies here.
 */
@Module
public class AddShoppingListItemModule {

    @Provides
    AddShoppingListItemViewModelFactory provideAddShoppingListItemViewModelFactory(
            AddShoppingListItemUseCase addShoppingListItemUseCase) {
        return new AddShoppingListItemViewModelFactory(addShoppingListItemUseCase);
    }
}
