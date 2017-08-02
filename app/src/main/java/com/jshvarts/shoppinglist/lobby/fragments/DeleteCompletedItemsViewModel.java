package com.jshvarts.shoppinglist.lobby.fragments;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.hardware.SensorManager;

import com.jshvarts.shoppinglist.common.domain.model.DatabaseConstants;
import com.jshvarts.shoppinglist.common.domain.model.ShoppingList;
import com.jshvarts.shoppinglist.common.domain.model.ShoppingListDataHelper;
import com.jshvarts.shoppinglist.rx.SchedulersFacade;
import com.squareup.seismic.ShakeDetector;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class DeleteCompletedItemsViewModel extends AndroidViewModel implements ShakeDetector.Listener {

    private final LoadShoppingListUseCase loadShoppingListUseCase;

    private final UpdateShoppingListUseCase updateShoppingListUseCase;

    private final SchedulersFacade schedulersFacade;

    private final ShoppingListDataHelper shoppingListDataHelper;

    private final SensorManager sensorManager;

    private final ShakeDetector shakeDetector;

    private final CompositeDisposable disposables = new CompositeDisposable();

    private MutableLiveData<Boolean> completedItemsDeleted = new MutableLiveData<>();

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

        sensorManager = (SensorManager) application.getSystemService(Context.SENSOR_SERVICE);
        shakeDetector = new ShakeDetector(this);
        shakeDetector.start(sensorManager);
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }

    @Override
    public void hearShake() {
        loadShoppingListAndDeleteCompletedItems();
    }

    LiveData<Boolean> getCompletedItemsDeleted() {
        return completedItemsDeleted;
    }

    private void loadShoppingListAndDeleteCompletedItems() {
        disposables.add(loadShoppingListUseCase.loadShoppingList(DatabaseConstants.DEFAULT_SHOPPING_LIST_ID)
                .subscribeOn(schedulersFacade.io())
                .observeOn(schedulersFacade.ui())
                .firstElement()
                .subscribe(shoppingList -> deleteCompletedItems(shoppingList),
                        throwable -> Timber.e(throwable))
        );
    }

    private void deleteCompletedItems(ShoppingList shoppingList) {
        int itemCount = shoppingList.getItems().size();
        if (itemCount == 0 || !shoppingList.getItems().get(itemCount - 1).getCompleted()) {
            // no completed items
            return;
        }
        Timber.d("deleting completed items after shake");
        shoppingListDataHelper.removeCompletedItems(shoppingList);
        disposables.add(updateShoppingListUseCase.updateShoppingList(shoppingList)
                .subscribeOn(schedulersFacade.io())
                .observeOn(schedulersFacade.ui())
                .subscribe(updatedShoppingList -> {
                        // TODO use custom SingleLiveEvent instead of resetting the value
                        completedItemsDeleted.setValue(true);
                        completedItemsDeleted.setValue(false);
                    }, throwable -> Timber.e(throwable)
                )
        );
    }
}
