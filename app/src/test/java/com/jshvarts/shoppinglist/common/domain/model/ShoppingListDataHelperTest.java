package com.jshvarts.shoppinglist.common.domain.model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link ShoppingListDataHelper}.
 */
public class ShoppingListDataHelperTest {

    ShoppingListDataHelper testSubject;

    ShoppingList shoppingList;

    @Before
    public void setUp() throws Exception {
        testSubject = new ShoppingListDataHelper();
        shoppingList = new ShoppingList();
    }

    @Test
    public void completeItem_removesItemAndAppendsItAsCompleted() throws Exception {
        // GIVEN
        final String itemName1 = "item name 1";
        final String itemName2 = "item name 2";
        ShoppingListItem shoppingListItem1 = new ShoppingListItem(itemName1);
        ShoppingListItem shoppingListItem2 = new ShoppingListItem(itemName2);
        List<ShoppingListItem> items = new ArrayList<>();
        items.add(shoppingListItem1);
        items.add(shoppingListItem2);
        shoppingList.setItems(items);

        // WHEN
        testSubject.completeItem(shoppingList, 0);

        // THEN
        assertThat(shoppingList.getItems().size(), is(2));

        assertThat(shoppingList.getItems().get(0).getName(), is(itemName2));
        assertThat(shoppingList.getItems().get(0).getCompleted(), is(false));

        assertThat(shoppingList.getItems().get(1).getName(), is(itemName1));
        assertThat(shoppingList.getItems().get(1).getCompleted(), is(true));
    }

    @Test
    public void addItem_whenItemCountIsZero_appendsItem() throws Exception {
        // GIVEN
        String itemName = "item name 1";

        // WHEN
        testSubject.addItem(shoppingList, itemName);

        // GIVEN
        assertThat(shoppingList.getItems().size(), is(1));
        assertThat(shoppingList.getItems().get(0).getName(), is(itemName));
    }

    @Test
    public void addItem_whenLastItemIsNotCompleted_appendsItemAtTheEnd() throws Exception {
        // GIVEN
        final String itemName1 = "item name 1";
        final String itemName2 = "item name 2";
        ShoppingListItem shoppingListItem1 = new ShoppingListItem(itemName1);
        ShoppingListItem shoppingListItem2 = new ShoppingListItem(itemName2);
        List<ShoppingListItem> items = new ArrayList<>();
        shoppingListItem2.setCompleted(true);
        items.add(shoppingListItem1);
        items.add(shoppingListItem2);
        shoppingList.setItems(items);

        String newItemName = "item name 3";

        // WHEN
        testSubject.addItem(shoppingList, newItemName);

        // GIVEN
        assertThat(shoppingList.getItems().size(), is(3));
        assertThat(shoppingList.getItems().get(0).getName(), is(itemName1));
        assertThat(shoppingList.getItems().get(1).getName(), is(newItemName));
        assertThat(shoppingList.getItems().get(2).getName(), is(itemName2));
    }

    @Test
    public void addItem_whenThereAreCompletedItems_appendsItemAtTheEnd() throws Exception {
        // GIVEN
        final String itemName1 = "item name 1";
        final String itemName2 = "item name 2";
        final String itemName3 = "item name 3";
        ShoppingListItem shoppingListItem1 = new ShoppingListItem(itemName1);
        ShoppingListItem shoppingListItem2 = new ShoppingListItem(itemName2);
        ShoppingListItem shoppingListItem3 = new ShoppingListItem(itemName3);
        List<ShoppingListItem> items = new ArrayList<>();
        items.add(shoppingListItem1);
        shoppingListItem2.setCompleted(true);
        items.add(shoppingListItem2);
        shoppingListItem3.setCompleted(true);
        items.add(shoppingListItem3);
        shoppingList.setItems(items);

        String newItemName = "item name 4";

        // WHEN
        testSubject.addItem(shoppingList, newItemName);

        // GIVEN
        assertThat(shoppingList.getItems().size(), is(4));
        assertThat(shoppingList.getItems().get(0).getName(), is(itemName1));
        assertThat(shoppingList.getItems().get(1).getName(), is(newItemName));
        assertThat(shoppingList.getItems().get(2).getName(), is(itemName2));
        assertThat(shoppingList.getItems().get(3).getName(), is(itemName3));
    }

    @Test
    public void removeCompletedItems_whenShoppingListIsEmpty_doesNothing() throws Exception {
        shoppingList = mock(ShoppingList.class);

        // WHEN
        testSubject.removeCompletedItems(shoppingList);

        // THEN
        verify(shoppingList).getItems();
        verifyNoMoreInteractions(shoppingList);
    }

    @Test
    public void removeCompletedItems_whenShoppingListHasSingleCompletedItem_removesCompletedItem() throws Exception {
        shoppingList = Mockito.mock(ShoppingList.class);
        final String itemName1 = "item name 1";
        final String itemName2 = "item name 2";
        ShoppingListItem shoppingListItem1 = new ShoppingListItem(itemName1);
        ShoppingListItem shoppingListItem2 = new ShoppingListItem(itemName2);
        shoppingListItem2.setCompleted(true);
        List<ShoppingListItem> items = new ArrayList<>();
        items.add(shoppingListItem1);
        items.add(shoppingListItem2);

        when(shoppingList.getItems()).thenReturn(items);

        // WHEN
        testSubject.removeCompletedItems(shoppingList);

        // THEN
        assertThat(shoppingList.getItems().size(), is(1));
        assertThat(shoppingList.getItems().get(0).getName(), is(itemName1));
    }

    @Test
    public void removeCompletedItems_whenShoppingListHasMultipleCompletedItems_removesCompletedItems() throws Exception {
        shoppingList = Mockito.mock(ShoppingList.class);
        final String itemName1 = "item name 1";
        final String itemName2 = "item name 2";
        ShoppingListItem shoppingListItem1 = new ShoppingListItem(itemName1);
        shoppingListItem1.setCompleted(true);
        ShoppingListItem shoppingListItem2 = new ShoppingListItem(itemName2);
        shoppingListItem2.setCompleted(true);
        List<ShoppingListItem> items = new ArrayList<>();
        items.add(shoppingListItem1);
        items.add(shoppingListItem2);

        when(shoppingList.getItems()).thenReturn(items);

        // WHEN
        testSubject.removeCompletedItems(shoppingList);

        // THEN
        assertThat(shoppingList.getItems().size(), is(0));
    }
}