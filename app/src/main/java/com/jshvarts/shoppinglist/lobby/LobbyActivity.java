package com.jshvarts.shoppinglist.lobby;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;
import android.widget.Toolbar;

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

public class LobbyActivity extends LifecycleActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Inject
    ShoppingListViewModelFactory viewModelFactory;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;

    private ShoppingListViewModel shoppingListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lobby_activity);

        ButterKnife.bind(this);

        setActionBar(toolbar);

        // initialize ViewModel in Activity so that child Fragments can access it to get current shopping list
        shoppingListViewModel = ViewModelProviders.of(this, viewModelFactory).get(ShoppingListViewModel.class);
        shoppingListViewModel.getCurrentShoppingList().observe(this, shoppingList -> attachViewShoppingListFragment());

        fab.setOnClickListener(v -> attachAddShoppingListItemFragment());

        attachViewShoppingListFragment();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        attachViewShoppingListFragment();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    private void attachViewShoppingListFragment() {
        if (fragmentContainer.getChildCount() == 0) {
            Fragment shoppingListFragment = new ViewShoppingListFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, shoppingListFragment)
                    .commit();
        }
    }

    private void attachAddShoppingListItemFragment() {
        Fragment addShoppingListItemFragment = new AddShoppingListItemFragment();
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(AddShoppingListItemFragment.class.getSimpleName())
                .replace(R.id.fragment_container, addShoppingListItemFragment)
                .commit();
    }
}
