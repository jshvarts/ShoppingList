package com.jshvarts.shoppinglist.common.domain.model;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface Repository<T> {
    Observable<List<T>> getItems(Specification specification);

    Observable<T> getItem(Specification specification);

    Single<T> add(T item);

    Single<T> update(T item);

    Completable removeItem(Specification specification);
}
