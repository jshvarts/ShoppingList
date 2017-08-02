package com.jshvarts.shoppinglist.lobby.fragments;

import android.app.Application;

import com.jshvarts.shoppinglist.common.domain.model.ShoppingListDataHelper;
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
                                                                     SchedulersFacade schedulersFacade,
                                                                     ShoppingListDataHelper shoppingListDataHelper) {
        return new ShoppingListViewModelFactory(loadShoppingListUseCase,
                updateShoppingListUseCase,
                schedulersFacade,
                shoppingListDataHelper);
    }

    @Provides
    DeleteCompletedItemsViewModelFactory provideDeleteCompletedItemsViewModelFactory(Application application,
                                                                                     LoadShoppingListUseCase loadShoppingListUseCase,
                                                                                     UpdateShoppingListUseCase updateShoppingListUseCase,
                                                                                     SchedulersFacade schedulersFacade,
                                                                                     ShoppingListDataHelper shoppingListDataHelper) {
        return new DeleteCompletedItemsViewModelFactory(application,
                loadShoppingListUseCase,
                updateShoppingListUseCase,
                schedulersFacade,
                shoppingListDataHelper);
    }
}
