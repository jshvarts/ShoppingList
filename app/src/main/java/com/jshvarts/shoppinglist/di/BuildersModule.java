package com.jshvarts.shoppinglist.di;

import com.jshvarts.shoppinglist.lobby.LobbyActivity;
import com.jshvarts.shoppinglist.lobby.fragments.AddShoppingListItemFragment;
import com.jshvarts.shoppinglist.lobby.fragments.AddShoppingListItemModule;
import com.jshvarts.shoppinglist.lobby.fragments.ShoppingListFragment;
import com.jshvarts.shoppinglist.lobby.fragments.ShoppingListModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Binds all sub-components within the app.
 */
@Module
public abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract LobbyActivity bindLobbyActivity();

    @ContributesAndroidInjector(modules = ShoppingListModule.class)
    abstract ShoppingListFragment bindShoppingListFragment();

    @ContributesAndroidInjector(modules = AddShoppingListItemModule.class)
    abstract AddShoppingListItemFragment bindAddShoppingListItemFragment();

    // Add bindings for other sub-components here
}
