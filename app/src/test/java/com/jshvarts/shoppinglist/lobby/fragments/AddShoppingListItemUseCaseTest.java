package com.jshvarts.shoppinglist.lobby.fragments;

import com.jshvarts.shoppinglist.common.domain.model.ShoppingList;
import com.jshvarts.shoppinglist.common.domain.model.ShoppingListDataHelper;
import com.jshvarts.shoppinglist.common.domain.model.firebase.FirebaseShoppingListRepository;

import org.junit.Before;
import org.junit.Test;

import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.mock;

/**
 * Unit tests for {@link AddShoppingListItemUseCase}.
 */
public class AddShoppingListItemUseCaseTest {
    @InjectMocks
    AddShoppingListItemUseCase testSubject;

    @Mock
    FirebaseShoppingListRepository repository;

    @Mock
    ShoppingListDataHelper dataHelper;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void execute_addsItemViaDataHelper_andDelegatesToRepository() throws Exception {
        // GIVEN
        ShoppingList shoppingList = mock(ShoppingList.class);
        String itemName = "item name";

        // WHEN
        testSubject.execute(shoppingList, itemName);

        // THEN
        InOrder inOrder = Mockito.inOrder(dataHelper, repository);
        inOrder.verify(dataHelper).addItem(shoppingList, itemName);
        inOrder.verify(repository).update(shoppingList);
    }
}