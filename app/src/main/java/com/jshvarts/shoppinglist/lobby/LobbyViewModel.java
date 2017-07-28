package com.jshvarts.shoppinglist.lobby;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.jshvarts.shoppinglist.common.viewmodel.Response;
import com.jshvarts.shoppinglist.rx.SchedulersFacade;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

class LobbyViewModel extends ViewModel {

    private final LoadShoppingListUseCase loadShoppingListUseCase;

    private final CreateShoppingListUseCase createShoppingListUseCase;

    private final SchedulersFacade schedulersFacade;

    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<Response<String>> response = new MutableLiveData<>();

    private final MutableLiveData<Boolean> loadingStatus = new MutableLiveData<>();

    private final MutableLiveData<Boolean> dataValidationStatus = new MutableLiveData<>();

    LobbyViewModel(LoadShoppingListUseCase loadShoppingListUseCase,
                          CreateShoppingListUseCase createShoppingListUseCase,
                          SchedulersFacade schedulersFacade) {
        this.loadShoppingListUseCase = loadShoppingListUseCase;
        this.createShoppingListUseCase = createShoppingListUseCase;
        this.schedulersFacade = schedulersFacade;
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }

    void createShoppingList() {
        /*
        disposables.add(createShoppingListUseCase.execute()
                .subscribeOn(schedulersFacade.io())
                .observeOn(schedulersFacade.ui())
                .subscribe(() -> Timber.d("shopping list created"),
                        throwable -> Timber.e(throwable))
        );
        */
    }
}
