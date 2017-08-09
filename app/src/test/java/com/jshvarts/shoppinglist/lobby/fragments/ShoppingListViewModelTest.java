package com.jshvarts.shoppinglist.lobby.fragments;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.jshvarts.shoppinglist.common.domain.model.ShoppingList;
import com.jshvarts.shoppinglist.common.domain.model.ShoppingListDataHelper;
import com.jshvarts.shoppinglist.common.domain.model.ShoppingListItem;
import com.jshvarts.shoppinglist.rx.SchedulersFacade;
import com.jshvarts.shoppinglist.rx.SchedulersFacadeUtils;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.TestScheduler;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Unit tests for {@link ShoppingListViewModel}.
 */
public class ShoppingListViewModelTest {

    @InjectMocks
    private ShoppingListViewModel testSubject;

    @Mock
    private LoadShoppingListUseCase loadShoppingListUseCase;

    @Mock
    private UpdateShoppingListUseCase updateShoppingListUseCase;

    @Mock
    private SchedulersFacade schedulersFacade;

    @Mock
    private ShoppingListDataHelper shoppingListDataHelper;

    @Mock
    private CompositeDisposable disposables;

    private TestScheduler testScheduler;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        testScheduler = SchedulersFacadeUtils.setupSchedulersFacade(schedulersFacade);

        Whitebox.setInternalState(testSubject, "disposables", disposables);
    }

    @Test
    public void onCleared_clearsDisposables() throws Exception {
        // WHEN
        testSubject.onCleared();

        // THEN
        verify(disposables).clear();
    }

    @Test
    public void getShoppingList_returnsLiveShoppingList() throws Exception {
        // GIVEN
        LiveData<ShoppingList> expected = mock(MutableLiveData.class);
        Whitebox.setInternalState(testSubject, "liveShoppingList", expected);

        // WHEN
        LiveData<ShoppingList> result = testSubject.getShoppingList();

        // THEN
        assertThat(result, is(expected));
    }

    @Test
    public void getLoadingIndicatorStatus_returnsLoadingIndicatorStatus() throws Exception {
        // GIVEN
        LiveData<Boolean> expected = mock(MutableLiveData.class);
        Whitebox.setInternalState(testSubject, "loadingIndicatorStatus", expected);

        // WHEN
        LiveData<Boolean> result = testSubject.getLoadingIndicatorStatus();

        // THEN
        assertThat(result, is(expected));
    }

    @Test
    public void loadShoppingList() throws Exception {
        // TODO
    }

    @Test
    public void completeShoppingListItem_whenItemAlreadyCompleted_returnsShoppingList() throws Exception {
        // GIVEN
        int itemIndex = 0;
        ShoppingList shoppingList = given_shoppingListWithOneItem(true);
        MutableLiveData<ShoppingList> liveShoppingList = given_liveShoppingList(shoppingList);

        // WHEN
        testSubject.completeShoppingListItem(itemIndex);

        // THEN
        verify(liveShoppingList).setValue(shoppingList);
        assertThat(liveShoppingList.getValue(), is(shoppingList));
        verifyZeroInteractions(shoppingListDataHelper);
        verifyZeroInteractions(updateShoppingListUseCase);
    }

    @Test
    public void completeShoppingListItem_whenItemNotCompletedAndRepositoryEmitsSuccess_completesItem() throws Exception {
        // GIVEN
        int itemIndex = 0;
        ShoppingList shoppingList = given_shoppingListWithOneItem(false);
        MutableLiveData<ShoppingList> liveShoppingList = given_liveShoppingList(shoppingList);
        when(updateShoppingListUseCase.updateShoppingList(shoppingList)).thenReturn(Single.just(shoppingList));

        // WHEN
        testSubject.completeShoppingListItem(itemIndex);
        testScheduler.triggerActions();

        // THEN
        verify(shoppingListDataHelper).completeItem(shoppingList, itemIndex);
        verify(liveShoppingList).setValue(shoppingList);
    }


    @Test
    public void completeShoppingListItem_whenItemNotCompletedAndRepositoryEmitsError_completesItem() throws Exception {
        // GIVEN
        int itemIndex = 0;
        ShoppingList shoppingList = given_shoppingListWithOneItem(false);
        MutableLiveData<ShoppingList> liveShoppingList = given_liveShoppingList(shoppingList);
        when(updateShoppingListUseCase.updateShoppingList(shoppingList)).thenReturn(Single.error(new Exception()));

        // WHEN
        testSubject.completeShoppingListItem(itemIndex);
        testScheduler.triggerActions();

        // THEN
        verify(shoppingListDataHelper).completeItem(shoppingList, itemIndex);
        verify(liveShoppingList, never()).setValue(ArgumentMatchers.any(ShoppingList.class));
    }

    private ShoppingList given_shoppingListWithOneItem(boolean itemCompleted) {
        ShoppingList shoppingList = new ShoppingList();
        List<ShoppingListItem> items = new ArrayList<>();
        ShoppingListItem item = new ShoppingListItem();
        item.setCompleted(itemCompleted);
        items.add(item);
        shoppingList.setItems(items);
        return shoppingList;
    }

    private MutableLiveData<ShoppingList> given_liveShoppingList(ShoppingList shoppingList) {
        MutableLiveData<ShoppingList> liveShoppingList = mock(MutableLiveData.class);
        when(liveShoppingList.getValue()).thenReturn(shoppingList);
        Whitebox.setInternalState(testSubject, "liveShoppingList", liveShoppingList);
        return liveShoppingList;
    }
}