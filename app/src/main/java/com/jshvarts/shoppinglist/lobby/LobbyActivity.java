package com.jshvarts.shoppinglist.lobby;

import android.arch.lifecycle.LifecycleActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Button;

import com.jshvarts.shoppinglist.R;
import com.jshvarts.shoppinglist.lobby.fragments.AddShoppingListItemFragment;
import com.jshvarts.shoppinglist.lobby.fragments.ShoppingListFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class LobbyActivity extends LifecycleActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @BindView(R.id.create_shopping_list_item_button)
    Button addShoppingListItemButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lobby_activity);

        ButterKnife.bind(this);

        Fragment shoppingListFragment = new ShoppingListFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.lobby_root_view, shoppingListFragment).commit();
    }

    @OnClick(R.id.create_shopping_list_item_button)
    void onAddShoppingListItemButtonClicked() {
        Fragment addShoppingListItemFragment = new AddShoppingListItemFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.lobby_root_view, addShoppingListItemFragment).commit();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
