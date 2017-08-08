package com.jshvarts.shoppinglist.lobby.fragments;

import com.jshvarts.shoppinglist.common.domain.model.ShoppingList;
import com.jshvarts.shoppinglist.common.domain.model.firebase.FirebaseShoppingListRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for {@link UpdateShoppingListUseCase}.
 */
public class UpdateShoppingListUseCaseTest {

    @InjectMocks
    UpdateShoppingListUseCase testSubject;

    @Mock
    FirebaseShoppingListRepository repository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void updateShoppingList_delegatesToRepository() throws Exception {
        // GIVEN
        ShoppingList shoppingList = mock(ShoppingList.class);

        // WHEN
        testSubject.updateShoppingList(shoppingList);

        // THEN
        verify(repository).update(shoppingList);
    }
}