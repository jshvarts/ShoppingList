package com.jshvarts.shoppinglist.di;

import android.content.Context;

import com.jshvarts.shoppinglist.App;
import com.jshvarts.shoppinglist.common.domain.model.ShoppingListDataHelper;
import com.jshvarts.shoppinglist.common.domain.model.firebase.FirebaseShoppingListRepository;

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
    FirebaseShoppingListRepository provideFirebaseShoppingListRepository() {
        return new FirebaseShoppingListRepository();
    }

    @Singleton
    @Provides
    ShoppingListDataHelper provideShoppingListDataHelper() {
        return new ShoppingListDataHelper();
    }
}
