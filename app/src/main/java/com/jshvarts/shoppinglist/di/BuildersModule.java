package com.jshvarts.shoppinglist.di;

import com.jshvarts.shoppinglist.lobby.LobbyActivity;
import com.jshvarts.shoppinglist.lobby.LobbyModule;
import com.jshvarts.shoppinglist.lobby.fragments.AddShoppingListItemFragment;
import com.jshvarts.shoppinglist.lobby.fragments.AddShoppingListItemModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Binds all sub-components within the app.
 */
@Module
public abstract class BuildersModule {

    @ContributesAndroidInjector(modules = LobbyModule.class)
    abstract LobbyActivity bindLobbyActivity();

    @ContributesAndroidInjector(modules = AddShoppingListItemModule.class)
    abstract AddShoppingListItemFragment bindAddShoppingListItemFragment();

    // Add bindings for other sub-components here
}
