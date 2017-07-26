package com.jshvarts.shoppinglist.di;

import android.content.Context;

import com.jshvarts.shoppinglist.App;
import com.jshvarts.shoppinglist.common.domain.model.ShoppingListRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * This is where you will inject application-wide dependencies.
 */
@Module
public class AppModule {

    @Provides
    Context provideContext(App application) {
        return application.getApplicationContext();
    }

    @Singleton
    @Provides
    ShoppingListRepository provideCircleRepository() {
        return new ShoppingListRepository();
    }
}
