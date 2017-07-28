package com.jshvarts.shoppinglist.lobby.fragments;

import android.arch.lifecycle.ViewModel;

import com.jshvarts.shoppinglist.common.domain.model.ShoppingList;
import com.jshvarts.shoppinglist.common.domain.model.ShoppingListItem;
import com.jshvarts.shoppinglist.lobby.LoadShoppingListUseCase;
import com.jshvarts.shoppinglist.rx.SchedulersFacade;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class AddShoppingListItemViewModel extends ViewModel {
    private final AddShoppingListItemUseCase addShoppingListItemUseCase;

    private final LoadShoppingListUseCase loadShoppingListUseCase;

    private final SchedulersFacade schedulersFacade;

    private final CompositeDisposable disposables = new CompositeDisposable();

    AddShoppingListItemViewModel(AddShoppingListItemUseCase addShoppingListItemUseCase,
                   LoadShoppingListUseCase loadShoppingListUseCase,
                   SchedulersFacade schedulersFacade) {
        this.addShoppingListItemUseCase = addShoppingListItemUseCase;
        this.loadShoppingListUseCase = loadShoppingListUseCase;
        this.schedulersFacade = schedulersFacade;
    }

    void addShoppingListItem(String name) {
        disposables.add(loadShoppingListUseCase.execute()
                .subscribeOn(schedulersFacade.io())
                .observeOn(schedulersFacade.ui())
                .subscribe(
                        shoppingList -> addShoppingListItem(shoppingList, name),
                        throwable -> Timber.e("error:" + throwable)
                )
        );
    }

    private void addShoppingListItem(ShoppingList shoppingList, String shoppingListItemName) {
        ShoppingListItem shoppingListItem = new ShoppingListItem(shoppingListItemName);
        if (shoppingList.getItems() != null && !shoppingList.getItems().isEmpty()) {
            shoppingList.getItems().add(shoppingListItem);
        } else {
            List<ShoppingListItem> items = new ArrayList<>();
            items.add(shoppingListItem);
            shoppingList.setItems(items);
        }
        addShoppingListItemUseCase.execute(shoppingList);
    }
}
