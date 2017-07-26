package com.jshvarts.shoppinglist.common.domain.model;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface Repository<T> {
    Observable<List<T>> getAll();

    Single<T> getById(String id);

    Completable save(T item);

    Completable delete(String id);
}
