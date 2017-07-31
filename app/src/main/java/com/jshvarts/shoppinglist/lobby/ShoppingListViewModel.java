package com.jshvarts.shoppinglist.lobby;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.jshvarts.shoppinglist.common.domain.model.ShoppingList;
import com.jshvarts.shoppinglist.common.domain.model.ShoppingListItem;
import com.jshvarts.shoppinglist.rx.SchedulersFacade;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class ShoppingListViewModel extends ViewModel {

    private final LoadShoppingListUseCase loadShoppingListUseCase;

    private final CreateShoppingListUseCase createShoppingListUseCase;

    private final UpdateShoppingListUseCase updateShoppingListUseCase;

    private final SchedulersFacade schedulersFacade;

    private final CompositeDisposable disposables = new CompositeDisposable();

    private MutableLiveData<ShoppingList> liveShoppingList = new MutableLiveData<>();

    ShoppingListViewModel(LoadShoppingListUseCase loadShoppingListUseCase,
                          CreateShoppingListUseCase createShoppingListUseCase,
                          UpdateShoppingListUseCase updateShoppingListUseCase,
                          SchedulersFacade schedulersFacade) {
        this.loadShoppingListUseCase = loadShoppingListUseCase;
        this.createShoppingListUseCase = createShoppingListUseCase;
        this.updateShoppingListUseCase = updateShoppingListUseCase;
        this.schedulersFacade = schedulersFacade;
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }

    public void completeShoppingListItem(int index) {
        ShoppingList shoppingList = liveShoppingList.getValue();
        ShoppingListItem completedShoppingListItem = shoppingList.getItems().remove(index);
        completedShoppingListItem.setCompleted(true);
        shoppingList.getItems().add(completedShoppingListItem);
        liveShoppingList.setValue(shoppingList);
        updateShoppingListUseCase.updateShoppingList(shoppingList);
    }

    public void loadShoppingList() {
        disposables.add(loadShoppingListUseCase.loadAvailableShoppingLists()
                .subscribeOn(schedulersFacade.io())
                .observeOn(schedulersFacade.ui())
                .firstOrError()
                .filter(shoppingLists -> !shoppingLists.isEmpty())
                .map(shoppingLists -> shoppingLists.iterator().next())
                .subscribe(shoppingList -> liveShoppingList.setValue(shoppingList), throwable -> createShoppingList())
        );
    }

    private void createShoppingList() {
        disposables.add(createShoppingListUseCase.execute()
                .subscribeOn(schedulersFacade.io())
                .observeOn(schedulersFacade.ui())
                .subscribe(shoppingList -> {
                            Timber.d("shopping list created id " + shoppingList.getId());
                            liveShoppingList.setValue(shoppingList);
                        },
                        throwable -> Timber.e(throwable)
                )
        );
    }

    public MutableLiveData<ShoppingList> getCurrentShoppingList() {
        return liveShoppingList;
    }
}
