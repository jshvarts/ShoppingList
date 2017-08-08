package com.jshvarts.shoppinglist.lobby.fragments;

import com.jshvarts.shoppinglist.common.domain.model.ShoppingList;
import com.jshvarts.shoppinglist.common.domain.model.firebase.FirebaseShoppingListRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for {@link CreateShoppingListUseCase}.
 */
public class CreateShoppingListUseCaseTest {
    @InjectMocks
    CreateShoppingListUseCase testSubject;

    @Mock
    FirebaseShoppingListRepository repository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void execute_delegatesToRepository() throws Exception {
        // WHEN
        testSubject.execute();

        // THEN
        verify(repository).add(ArgumentMatchers.any(ShoppingList.class));
    }
}