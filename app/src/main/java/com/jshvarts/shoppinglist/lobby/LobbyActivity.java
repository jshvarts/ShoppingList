package com.jshvarts.shoppinglist.lobby;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jshvarts.shoppinglist.R;
import com.jshvarts.shoppinglist.common.viewmodel.Response;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import timber.log.Timber;

public class LobbyActivity extends LifecycleActivity {

    @Inject
    LobbyViewModelFactory viewModelFactory;

    @BindView(R.id.create_shopping_list_success_textview)
    TextView createShoppingListSuccessTextView;

    @BindView(R.id.create_shopping_list_edittext)
    EditText createShoppingListEditText;

    @BindView(R.id.loading_indicator)
    ProgressBar loadingIndicator;

    private LobbyViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lobby_activity);

        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LobbyViewModel.class);

        observeLoadingStatus();

        observeDataValidationStatus();

        observeResponse();
    }

    @OnClick(R.id.create_shopping_list_button)
    void onCreateShoppingListButtonClicked() {
        viewModel.createShoppingList(createShoppingListEditText.getText().toString());
    }

    private void observeLoadingStatus() {
        viewModel.getLoadingStatus().observe(this, isLoading -> processLoadingStatus(isLoading));
    }

    private void observeDataValidationStatus() {
        viewModel.getDataValidationStatus().observe(this, isValid -> processDataValidationStatus(isValid));
    }

    private void observeResponse() {
        viewModel.getResponse().observe(this, response -> processResponse(response));
    }

    private void processLoadingStatus(boolean isLoading) {
        createShoppingListSuccessTextView.setVisibility(isLoading ? View.GONE : View.VISIBLE);
        loadingIndicator.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    private void processDataValidationStatus(boolean isValid) {
        if (isValid) {
            return;
        }
        Toast.makeText(this, R.string.create_shopping_list_validation_error, Toast.LENGTH_SHORT).show();
    }

    private void processResponse(Response<String> response) {
        switch (response.status) {
            case SUCCESS:
                createShoppingListSuccessTextView.setText(R.string.create_shopping_list_success);
                break;

            case ERROR:
                Timber.e(response.error);
                Toast.makeText(this, R.string.create_shopping_list_error, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
