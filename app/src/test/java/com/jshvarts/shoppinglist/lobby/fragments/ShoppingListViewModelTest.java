package com.jshvarts.shoppinglist.lobby.fragments;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.jshvarts.shoppinglist.common.domain.model.ShoppingList;
import com.jshvarts.shoppinglist.common.domain.model.ShoppingListDataHelper;
import com.jshvarts.shoppinglist.common.domain.model.ShoppingListItem;
import com.jshvarts.shoppinglist.rx.SchedulersFacade;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
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

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void onCleared_clearsDisposables() throws Exception {
        // GIVEN
        CompositeDisposable disposables = mock(CompositeDisposable.class);
        Whitebox.setInternalState(testSubject, "disposables", disposables);

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
        ShoppingList shoppingList = new ShoppingList();
        List<ShoppingListItem> items = new ArrayList<>();
        ShoppingListItem item = new ShoppingListItem();
        item.setCompleted(true);
        items.add(item);
        shoppingList.setItems(items);

        MutableLiveData<ShoppingList> liveShoppingList = mock(MutableLiveData.class);
        Whitebox.setInternalState(testSubject, "liveShoppingList", liveShoppingList);
        when(liveShoppingList.getValue()).thenReturn(shoppingList);

        // WHEN
        testSubject.completeShoppingListItem(itemIndex);

        // THEN
        verify(liveShoppingList).setValue(shoppingList);
        assertThat(liveShoppingList.getValue(), is(shoppingList));
        verifyZeroInteractions(shoppingListDataHelper);
        verifyZeroInteractions(updateShoppingListUseCase);
    }
}