package com.jshvarts.shoppinglist.lobby.fragments;

import com.jshvarts.shoppinglist.common.domain.model.ItemByIdSpecification;
import com.jshvarts.shoppinglist.common.domain.model.firebase.FirebaseShoppingListRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for {@link LoadShoppingListUseCase}.
 */
public class LoadShoppingListUseCaseTest {

    @InjectMocks
    LoadShoppingListUseCase testSubject;

    @Mock
    FirebaseShoppingListRepository repository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        testSubject = spy(testSubject);
    }

    @Test
    public void loadShoppingList_delegatesToRepository() throws Exception {
        // GIVEN
        final String testId = "1";
        ArgumentCaptor<ItemByIdSpecification> itemByIdSpecArgumentCaptor = ArgumentCaptor.forClass(ItemByIdSpecification.class);

        // WHEN
        testSubject.loadShoppingList(testId);

        // THEN
        verify(repository).getItem(itemByIdSpecArgumentCaptor.capture());
        assertThat(itemByIdSpecArgumentCaptor.getValue().getId(), is(testId));
    }
}