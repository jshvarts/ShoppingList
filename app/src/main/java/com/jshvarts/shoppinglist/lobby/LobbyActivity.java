package com.jshvarts.shoppinglist.lobby;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.jshvarts.shoppinglist.R;
import com.jshvarts.shoppinglist.lobby.fragments.AddShoppingListItemFragment;
import com.jshvarts.shoppinglist.lobby.fragments.ViewShoppingListFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class LobbyActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Inject
    ShoppingListViewModelFactory viewModelFactory;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lobby_activity);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // initialize ViewModel in Activity so that child Fragments can access it to get current shopping list
        ViewModelProviders.of(this, viewModelFactory).get(ShoppingListViewModel.class);

        fab.setOnClickListener(v -> displayAddShoppingListItemFragment());

        Fragment shoppingListFragment = new ViewShoppingListFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, shoppingListFragment)
                .commit();

    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }


    private void displayAddShoppingListItemFragment() {
        Fragment addShoppingListItemFragment = new AddShoppingListItemFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, addShoppingListItemFragment)
                .commit();
    }
}
