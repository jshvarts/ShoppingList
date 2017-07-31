package com.jshvarts.shoppinglist.lobby.fragments;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.jshvarts.shoppinglist.common.domain.model.ShoppingList;
import com.jshvarts.shoppinglist.rx.SchedulersFacade;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class AddShoppingListItemViewModel extends ViewModel {

    private final AddShoppingListItemUseCase addShoppingListItemUseCase;

    private final SchedulersFacade schedulersFacade;

    private final CompositeDisposable disposables = new CompositeDisposable();

    MutableLiveData<Boolean> shoppingListItemAdded = new MutableLiveData<>();

    AddShoppingListItemViewModel(AddShoppingListItemUseCase addShoppingListItemUseCase,
                                 SchedulersFacade schedulersFacade) {
        this.addShoppingListItemUseCase = addShoppingListItemUseCase;
        this.schedulersFacade = schedulersFacade;
    }

    MutableLiveData<Boolean> isItemAdded() {
        return shoppingListItemAdded;
    }

    public void addShoppingListItem(ShoppingList shoppingList, String shoppingListItemName) {
        disposables.add(addShoppingListItemUseCase.execute(shoppingList, shoppingListItemName)
                    .subscribeOn(schedulersFacade.io())
                .observeOn(schedulersFacade.ui())
                .subscribe(updatedShoppingList -> {
                            Timber.d("updated shopping list with id " + updatedShoppingList.getId());
                            shoppingListItemAdded.setValue(true);
                        }, throwable -> Timber.e(throwable)
                )
        );
    }
}
