package com.jshvarts.shoppinglist.common.domain.model;

import java.util.Set;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface Repository<T> {
    Observable<Set<T>> getItems(Specification specification);

    Observable<T> getItem(Specification specification);

    Completable add(T item);

    Completable update(T item);

    Completable remove(Specification specification);
}
