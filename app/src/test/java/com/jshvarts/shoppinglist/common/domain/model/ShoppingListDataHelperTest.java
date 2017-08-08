package com.jshvarts.shoppinglist.common.domain.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

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
    public void addItem() throws Exception {
    }

    @Test
    public void removeCompletedItems() throws Exception {
    }

}