package com.jshvarts.shoppinglist.common.domain.model.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jshvarts.shoppinglist.common.domain.model.ItemsSpecification;
import com.jshvarts.shoppinglist.common.domain.model.Repository;
import com.jshvarts.shoppinglist.common.domain.model.ShoppingList;
import com.jshvarts.shoppinglist.common.domain.model.ItemByIdSpecification;
import com.jshvarts.shoppinglist.common.domain.model.ShoppingListItemNameComparator;
import com.jshvarts.shoppinglist.common.domain.model.Specification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import timber.log.Timber;

@Singleton
public class FirebaseShoppingListRepository implements Repository<ShoppingList> {
    private final FirebaseDatabase database;

    public FirebaseShoppingListRepository() {
        database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);
    }

    @Override
    public Single<List<ShoppingList>> getItems(Specification specification) {
        ItemsSpecification itemsSpecification = (ItemsSpecification) specification;
        List<ShoppingList> shoppingLists = new ArrayList<>();
        return Single.create(emitter -> {
            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            ShoppingList shoppingList = childSnapshot.getValue(ShoppingList.class);
                            shoppingList.setId(childSnapshot.getKey());
                            shoppingLists.add(shoppingList);
                            emitter.onSuccess(shoppingLists);
                        }
                    } else {
                        emitter.onError(new IllegalArgumentException("Unable to find any shopping lists"));
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Timber.e(databaseError.toException(), "getItems:onCancelled.");
                    emitter.onError(databaseError.toException());
                }
            };

            Query shoppingListQuery = database.getReference().orderByKey().limitToLast(itemsSpecification.getMaxCount());
            shoppingListQuery.addValueEventListener(valueEventListener);
            emitter.setCancellable(() -> shoppingListQuery.removeEventListener(valueEventListener));
        });
    }

    @Override
    public Observable<ShoppingList> getItem(Specification specification) {
        ItemByIdSpecification byIdSpecification = (ItemByIdSpecification) specification;
        return Observable.create(emitter -> {
            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        ShoppingList shoppingList = dataSnapshot.getValue(ShoppingList.class);
                        shoppingList.setId(dataSnapshot.getKey());
                        // TODO ideally left Firebase API do the sorting
                        Collections.sort(shoppingList.getItems(), new ShoppingListItemNameComparator());
                        emitter.onNext(shoppingList);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Timber.e(databaseError.toException(), "getItem:onCancelled.");
                    emitter.onError(databaseError.toException());
                }
            };

            DatabaseReference shoppingListRef = database.getReference().child(byIdSpecification.getId());
            shoppingListRef.addValueEventListener(valueEventListener);
            emitter.setCancellable(() -> shoppingListRef.removeEventListener(valueEventListener));
        });
    }

    @Override
    public Single<ShoppingList> add(ShoppingList shoppingList) {
        DatabaseReference shoppingListRef = database.getReference().push();
        shoppingListRef.setValue(shoppingList, (databaseError, databaseReference) -> {
            if (databaseError != null) {
                Timber.e(databaseError.toException(), "add:databaseError.");
                Single.error(databaseError.toException());
            }
        });
        shoppingList.setId(shoppingListRef.getKey());
        return Single.just(shoppingList);
    }

    @Override
    public Single<ShoppingList> update(ShoppingList shoppingList) {
        DatabaseReference shoppingListRef = database.getReference().child(shoppingList.getId());
        shoppingListRef.setValue(shoppingList, (databaseError, databaseReference) -> {
            if (databaseError != null) {
                Timber.e(databaseError.toException(), "update:databaseError.");
                Single.error(databaseError.toException());
            }
        });
        return Single.just(shoppingList);
    }

    @Override
    public Completable removeItem(Specification specification) {
        throw new RuntimeException("Not implemented");
    }
}
