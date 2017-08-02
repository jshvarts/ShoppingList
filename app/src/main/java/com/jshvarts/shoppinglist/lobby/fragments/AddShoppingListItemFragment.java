package com.jshvarts.shoppinglist.lobby.fragments;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.jshvarts.shoppinglist.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;

public class AddShoppingListItemFragment extends LifecycleFragment {

    public static final String TAG = AddShoppingListItemFragment.class.getSimpleName();

    @Inject
    AddShoppingListItemViewModelFactory viewModelFactory;

    @BindView(R.id.shopping_list_item_name_edittext)
    EditText addShoppingListItemButtonEditText;

    private AddShoppingListItemViewModel viewModel;

    private Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_shopping_list_item_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AddShoppingListItemViewModel.class);

        viewModel.isItemAdded().observe(this, success -> {
            hideKeyboard();
            detachFragment();
        });
    }

    @OnClick(R.id.save_shopping_list_item_button)
    void onSaveShoppingListItemButtonClicked() {
        viewModel.addShoppingListItem(addShoppingListItemButtonEditText.getText().toString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    private void detachFragment() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }
}
