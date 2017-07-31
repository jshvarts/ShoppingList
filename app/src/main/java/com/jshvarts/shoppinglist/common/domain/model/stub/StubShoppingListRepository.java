package com.jshvarts.shoppinglist.common.domain.model.stub;

import com.jshvarts.shoppinglist.common.domain.model.Repository;
import com.jshvarts.shoppinglist.common.domain.model.ShoppingList;
import com.jshvarts.shoppinglist.common.domain.model.ItemByIdSpecification;
import com.jshvarts.shoppinglist.common.domain.model.ShoppingListItem;
import com.jshvarts.shoppinglist.common.domain.model.Specification;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import timber.log.Timber;

@Singleton
public class StubShoppingListRepository implements Repository<ShoppingList> {

    private final Set<ShoppingList> shoppingLists;

    public StubShoppingListRepository() {
        this.shoppingLists = new HashSet<>();
        initializeShoppingLists();
    }

    @Override
    public Observable<List<ShoppingList>> getItems(Specification specification) {
        //ItemsSpecification itemsSpecification = (ItemsSpecification) specification;
        return Observable.just(new ArrayList<>(shoppingLists));
    }

    @Override
    public Observable<ShoppingList> getItem(Specification specification) {
        ItemByIdSpecification byIdSpecification = (ItemByIdSpecification) specification;
        for (ShoppingList shoppingList : shoppingLists) {
            if (shoppingList.getId().equals(byIdSpecification.getId())) {
                Timber.d("found shopping list that matches " + byIdSpecification.getId());
                return Observable.just(shoppingList);
            }
        }
        return Observable.error(new IllegalArgumentException("No items match Specification provided."));
    }

    @Override
    public Single<ShoppingList> add(ShoppingList item) {
        shoppingLists.add(item); // add or update given shopping list
        return Single.just(item);
    }

    @Override
    public Single<ShoppingList> update(ShoppingList item) {
        shoppingLists.add(item); // add or update given shopping list
        return Single.just(item);
    }

    @Override
    public Completable removeItem(Specification specification) {
        ItemByIdSpecification byIdSpecification = (ItemByIdSpecification) specification;
        for (ShoppingList shoppingList : shoppingLists) {
            if (shoppingList.getId().equals(byIdSpecification.getId())) {
                shoppingLists.remove(shoppingList);
                return Completable.complete();
            }
        }
        return Completable.error(new IllegalArgumentException("No items match Specification provided."));
    }

    private void initializeShoppingLists() {
        List<ShoppingListItem> shoppingList1Items = new ArrayList<>();
        shoppingList1Items.add(new ShoppingListItem("bread"));
        shoppingList1Items.add(new ShoppingListItem("milk"));
        ShoppingList shoppingList1 = new ShoppingList(shoppingList1Items);
        shoppingList1.setId("1");
        shoppingLists.add(shoppingList1);

        List<ShoppingListItem> shoppingList2Items = new ArrayList<>();
        shoppingList2Items.add(new ShoppingListItem("cereal"));
        shoppingList2Items.add(new ShoppingListItem("cookies"));
        ShoppingList shoppingList2 = new ShoppingList(shoppingList2Items);
        shoppingList2.setId("2");
        shoppingLists.add(shoppingList2);
    }
}
