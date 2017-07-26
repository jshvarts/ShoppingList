package com.jshvarts.shoppinglist.lobby;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.jshvarts.shoppinglist.common.viewmodel.Response;
import com.jshvarts.shoppinglist.rx.SchedulersFacade;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

class LobbyViewModel extends ViewModel {

    private final CreateShoppingListUseCase createShoppingListUseCase;

    private final SchedulersFacade schedulersFacade;

    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<Response<String>> response = new MutableLiveData<>();

    private final MutableLiveData<Boolean> loadingStatus = new MutableLiveData<>();

    private final MutableLiveData<Boolean> dataValidationStatus = new MutableLiveData<>();

    LobbyViewModel(CreateShoppingListUseCase createShoppingListUseCase,
                          SchedulersFacade schedulersFacade) {
        this.createShoppingListUseCase = createShoppingListUseCase;
        this.schedulersFacade = schedulersFacade;
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }

    boolean validateShoppingListName(String shoppingListName) {
        if (shoppingListName != null && !shoppingListName.trim().isEmpty()) {
            Timber.d("james setting data validation value to true");
            dataValidationStatus.setValue(true);
        } else {
            Timber.d("james setting data validation value to false");
            dataValidationStatus.setValue(false);
        }
        return dataValidationStatus.getValue();
    }

    void createShoppingList(String shoppingListName) {
        if (!validateShoppingListName(shoppingListName)) {
            return;
        }
        disposables.add(createShoppingListUseCase.execute(shoppingListName)
                .subscribeOn(schedulersFacade.io())
                .observeOn(schedulersFacade.ui())
                .doOnSubscribe(s -> loadingStatus.setValue(true))
                .doAfterTerminate(() -> loadingStatus.setValue(false))
                .subscribe(() -> response.setValue(Response.success(null)),
                        throwable -> response.setValue(Response.error(throwable))
                )
        );
    }

    MutableLiveData<Response<String>> getResponse() {
        return response;
    }

    MutableLiveData<Boolean> getLoadingStatus() {
        return loadingStatus;
    }

    MutableLiveData<Boolean> getDataValidationStatus() {
        return dataValidationStatus;
    }
}
