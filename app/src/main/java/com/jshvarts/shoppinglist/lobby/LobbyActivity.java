package com.jshvarts.shoppinglist.lobby;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jshvarts.shoppinglist.R;
import com.jshvarts.shoppinglist.common.viewmodel.Response;
import com.jshvarts.shoppinglist.lobby.fragments.AddShoppingListItemFragment;
import com.jshvarts.shoppinglist.lobby.fragments.AddShoppingListItemViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import timber.log.Timber;

public class LobbyActivity extends LifecycleActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Inject
    LobbyViewModelFactory viewModelFactory;

    private LobbyViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lobby_activity);

        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LobbyViewModel.class);

        observeLoadShoppingList();
    }

    @OnClick(R.id.create_shopping_list_item_button)
    void onCreateShoppingListButtonClicked() {
        Fragment addShoppingListItemFragment = new AddShoppingListItemFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.add_shopping_list_item_fragment_container,
                        addShoppingListItemFragment).commit();
    }

    private void observeLoadShoppingList() {
        // TODO display shopping list
        viewModel.createShoppingList();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
