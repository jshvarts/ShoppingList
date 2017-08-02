package com.jshvarts.shoppinglist.lobby.fragments;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import com.jshvarts.shoppinglist.common.domain.model.DatabaseConstants;
import com.jshvarts.shoppinglist.common.domain.model.ShoppingList;
import com.jshvarts.shoppinglist.common.domain.model.ShoppingListDataHelper;
import com.jshvarts.shoppinglist.rx.SchedulersFacade;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class DeleteCompletedItemsViewModel extends AndroidViewModel {

    private final LoadShoppingListUseCase loadShoppingListUseCase;

    private final UpdateShoppingListUseCase updateShoppingListUseCase;

    private final SchedulersFacade schedulersFacade;

    private final ShoppingListDataHelper shoppingListDataHelper;

    private final CompositeDisposable disposables = new CompositeDisposable();

    public DeleteCompletedItemsViewModel(Application application,
                                         LoadShoppingListUseCase loadShoppingListUseCase,
                                         UpdateShoppingListUseCase updateShoppingListUseCase,
                                         SchedulersFacade schedulersFacade,
                                         ShoppingListDataHelper shoppingListDataHelper) {
        super(application);
        this.loadShoppingListUseCase = loadShoppingListUseCase;
        this.updateShoppingListUseCase = updateShoppingListUseCase;
        this.schedulersFacade = schedulersFacade;
        this.shoppingListDataHelper = shoppingListDataHelper;
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }

    void deleteCompletedItems() {
        loadAndDeleteShoppingList();
    }

    void loadAndDeleteShoppingList() {
        disposables.add(loadShoppingListUseCase.loadShoppingList(DatabaseConstants.DEFAULT_SHOPPING_LIST_ID)
                .subscribeOn(schedulersFacade.io())
                .observeOn(schedulersFacade.ui())
                .subscribe(shoppingList -> deleteCompletedItems(shoppingList),
                        throwable -> Timber.e(throwable))
        );
    }

    private void deleteCompletedItems(ShoppingList shoppingList) {
        Timber.d("deleting shoppingList items");
        shoppingListDataHelper.removeCompletedItems(shoppingList);
        disposables.add(updateShoppingListUseCase.updateShoppingList(shoppingList)
                .subscribeOn(schedulersFacade.io())
                .observeOn(schedulersFacade.ui())
                .subscribe(updatedShoppingList -> Timber.d("updated shopping list."),
                        throwable -> Timber.e(throwable)
                )
        );
    }
}
