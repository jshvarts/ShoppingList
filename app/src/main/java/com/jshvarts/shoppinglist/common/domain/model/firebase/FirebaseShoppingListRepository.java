package com.jshvarts.shoppinglist.common.domain.model.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jshvarts.shoppinglist.common.domain.model.Repository;
import com.jshvarts.shoppinglist.common.domain.model.ShoppingList;
import com.jshvarts.shoppinglist.common.domain.model.ItemByIdSpecification;
import com.jshvarts.shoppinglist.common.domain.model.Specification;

import java.util.Set;

import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;
import timber.log.Timber;

@Singleton
public class FirebaseShoppingListRepository implements Repository<ShoppingList> {
    private final FirebaseDatabase database;

    static final String NODE_SHOPPING_LIST = "shopping-lists";

    public FirebaseShoppingListRepository() {
        database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);
    }

    @Override
    public Observable<Set<ShoppingList>> getItems(Specification specification) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Observable<ShoppingList> getItem(Specification specification) {
        ItemByIdSpecification byIdSpecification = (ItemByIdSpecification) specification;
        return Observable.create(emitter -> {
            ValueEventListener shoppingListListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        ShoppingList shoppingList = dataSnapshot.getValue(ShoppingList.class);
                        shoppingList.setId(dataSnapshot.getKey());
                        emitter.onNext(shoppingList);
                        emitter.onComplete();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Timber.e(databaseError.toException(), "getItem:onCancelled.");
                    emitter.onError(databaseError.toException());
                }
            };

            DatabaseReference shoppingListRef = database.getReference(NODE_SHOPPING_LIST).child(byIdSpecification.getId());
            shoppingListRef.addValueEventListener(shoppingListListener);

            emitter.setCancellable(() -> shoppingListRef.removeEventListener(shoppingListListener));
        });
    }

    @Override
    public Completable add(ShoppingList shoppingList) {
        DatabaseReference shoppingListsRef = database.getReference(NODE_SHOPPING_LIST);
        DatabaseReference singleListRef = shoppingListsRef.push();
        singleListRef.setValue(shoppingList);
        shoppingList.setId(shoppingListsRef.getKey());
        return Completable.complete();
    }

    @Override
    public Completable update(ShoppingList shoppingList) {
        DatabaseReference shoppingListsRef = database.getReference(NODE_SHOPPING_LIST);
        DatabaseReference singleListRef = shoppingListsRef.child(shoppingList.getId());
        singleListRef.setValue(shoppingList);
        return Completable.complete();
    }

    @Override
    public Completable remove(Specification specification) {
        throw new RuntimeException("Not implemented");
    }
}
