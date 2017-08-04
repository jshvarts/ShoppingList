package com.jshvarts.shoppinglist.lobby.fragments;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.jshvarts.shoppinglist.common.domain.model.DatabaseConstants;
import com.jshvarts.shoppinglist.common.domain.model.ShoppingList;
import com.jshvarts.shoppinglist.common.domain.model.ShoppingListDataHelper;
import com.jshvarts.shoppinglist.rx.SchedulersFacade;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

class ShoppingListViewModel extends ViewModel {

    private final LoadShoppingListUseCase loadShoppingListUseCase;

    private final UpdateShoppingListUseCase updateShoppingListUseCase;

    private final SchedulersFacade schedulersFacade;

    private final ShoppingListDataHelper shoppingListDataHelper;

    private final CompositeDisposable disposables = new CompositeDisposable();

    private MutableLiveData<ShoppingList> liveShoppingList = new MutableLiveData<>();

    private MutableLiveData<Boolean> loadingIndicatorStatus = new MutableLiveData<>();

    ShoppingListViewModel(LoadShoppingListUseCase loadShoppingListUseCase,
                          UpdateShoppingListUseCase updateShoppingListUseCase,
                          SchedulersFacade schedulersFacade,
                          ShoppingListDataHelper shoppingListDataHelper) {
        this.loadShoppingListUseCase = loadShoppingListUseCase;
        this.updateShoppingListUseCase = updateShoppingListUseCase;
        this.schedulersFacade = schedulersFacade;
        this.shoppingListDataHelper = shoppingListDataHelper;
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }

    LiveData<ShoppingList> getShoppingList() {
        return liveShoppingList;
    }

    LiveData<Boolean> getLoadingIndicatorStatus() {
        return loadingIndicatorStatus;
    }

    void loadShoppingList() {
        disposables.add(loadShoppingListUseCase.loadShoppingList(DatabaseConstants.DEFAULT_SHOPPING_LIST_ID)
                .subscribeOn(schedulersFacade.io())
                .observeOn(schedulersFacade.ui())
                .doOnSubscribe(s -> loadingIndicatorStatus.setValue(true))
                .subscribe(shoppingList -> {
                        liveShoppingList.setValue(shoppingList);
                        loadingIndicatorStatus.setValue(false);
                    }, throwable -> {
                        Timber.e(throwable);
                        loadingIndicatorStatus.setValue(false);
                    }
                ));
    }

    void completeShoppingListItem(int itemIndex) {
        ShoppingList shoppingList = liveShoppingList.getValue();
        if (shoppingList.getItems().get(itemIndex).getCompleted()) {
            // item already completed. trigger UI refresh only.
            liveShoppingList.setValue(shoppingList);
            return;
        }
        shoppingListDataHelper.completeItem(shoppingList, itemIndex);

        disposables.add(updateShoppingListUseCase.updateShoppingList(shoppingList)
                .subscribeOn(schedulersFacade.io())
                .observeOn(schedulersFacade.ui())
                .subscribe(updatedShoppingList -> liveShoppingList.setValue(updatedShoppingList),
                        throwable -> Timber.e(throwable)
                )
        );
    }
}
