package com.jshvarts.shoppinglist.common.domain.model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
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
    public void completeItem_givenItemIsInRange_marksItemsCompleted() throws Exception {
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
        boolean result = testSubject.completeItem(shoppingList, 0);

        // THEN
        assertThat(shoppingList.getItems().get(0).getCompleted(), is(true));
        assertThat(shoppingList.getItems().get(1).getCompleted(), is(false));
        assertThat(result, is(true));
    }

    @Test
    public void completeItem_givenItemIsOutOfRange_doesNotMarkItemsComplete() throws Exception {
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
        boolean result = testSubject.completeItem(shoppingList, 2);

        // THEN
        assertThat(shoppingList.getItems().get(0).getCompleted(), is(false));
        assertThat(shoppingList.getItems().get(1).getCompleted(), is(false));
        assertThat(result, is(false));
    }

    @Test
    public void completeItem_givenListIsEmpty_doesNotMarkItemsComplete() throws Exception {
        // GIVEN
        List<ShoppingListItem> items = new ArrayList<>();
        shoppingList.setItems(items);

        // WHEN
        boolean result = testSubject.completeItem(shoppingList, 0);

        // THEN
        assertThat(result, is(false));
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
    public void addItem_appendsItem() throws Exception {
        // GIVEN
        final String itemName1 = "item name 1";
        final String itemName2 = "item name 2";
        ShoppingListItem shoppingListItem1 = new ShoppingListItem(itemName1);
        ShoppingListItem shoppingListItem2 = new ShoppingListItem(itemName2);
        List<ShoppingListItem> items = new ArrayList<>();
        items.add(shoppingListItem1);
        items.add(shoppingListItem2);
        shoppingList.setItems(items);

        String newItemName = "item name 3";

        // WHEN
        testSubject.addItem(shoppingList, newItemName);

        // GIVEN
        assertThat(shoppingList.getItems().size(), is(3));
        assertThat(shoppingList.getItems().get(0).getName(), is(itemName1));
        assertThat(shoppingList.getItems().get(1).getName(), is(itemName2));
        assertThat(shoppingList.getItems().get(2).getName(), is(newItemName));
    }

    @Test
    public void removeCompletedItems_whenShoppingListIsEmpty_doesNotRemoveItems() throws Exception {
        shoppingList = mock(ShoppingList.class);

        // WHEN
        boolean result = testSubject.removeCompletedItems(shoppingList);

        // THEN
        assertThat(result, is(false));
    }

    @Test
    public void removeCompletedItems_whenShoppingListHasCompletedItems_removesCompletedItems() throws Exception {
        shoppingList = Mockito.mock(ShoppingList.class);
        final String itemName1 = "item name 1";
        final String itemName2 = "item name 2";
        ShoppingListItem shoppingListItem1 = new ShoppingListItem(itemName1);
        shoppingListItem1.setCompleted(true);
        ShoppingListItem shoppingListItem2 = new ShoppingListItem(itemName2);
        shoppingListItem2.setCompleted(false);
        List<ShoppingListItem> items = new ArrayList<>();
        items.add(shoppingListItem1);
        items.add(shoppingListItem2);

        when(shoppingList.getItems()).thenReturn(items);

        // WHEN
        boolean result = testSubject.removeCompletedItems(shoppingList);

        // THEN
        assertThat(result, is(true));
        assertThat(shoppingList.getItems().size(), is(1));
        assertThat(shoppingList.getItems().get(0).getName(), is(itemName2));
    }
}