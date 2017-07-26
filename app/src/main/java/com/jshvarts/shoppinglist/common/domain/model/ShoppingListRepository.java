package com.jshvarts.shoppinglist.common.domain.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class ShoppingListRepository implements Repository<ShoppingList> {
    private final FirebaseDatabase database;

    static final String NODE_SHOPPING_LIST = "shopping-lists";

    public ShoppingListRepository() {
        database = FirebaseDatabase.getInstance();
    }

    @Override
    public Observable<List<ShoppingList>> getAll() {
        // TODO implement
        List<ShoppingList> shoppingLists = new ArrayList<>();
        shoppingLists.add(new ShoppingList());
        return Observable.just(shoppingLists);
    }

    @Override
    public Single<ShoppingList> getById(String id) {
        // TODO implement
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setId(id);
        return Single.just(shoppingList);
    }

    @Override
    public Completable save(ShoppingList shoppingList) {
        DatabaseReference dbRef = database.getReference(NODE_SHOPPING_LIST);
        DatabaseReference singleListRef = dbRef.push();
        singleListRef.setValue(shoppingList);
        return Completable.complete();
        //return Completable.error(new IllegalArgumentException("Error saving shoppingList."));
    }

    @Override
    public Completable delete(String id) {
        // TODO implement
        return Completable.complete();
    }
}
