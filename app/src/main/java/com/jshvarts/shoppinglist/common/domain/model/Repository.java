package com.jshvarts.shoppinglist.common.domain.model;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface Repository<T> {
    Observable<List<T>> getItems(Specification specification);

    Observable<T> getItem(Specification specification);

    Observable<T> add(T item);

    Completable update(T item);

    Completable remove(Specification specification);
}
