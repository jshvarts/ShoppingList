package com.jshvarts.shoppinglist.lobby.fragments;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.jshvarts.shoppinglist.R;
import com.jshvarts.shoppinglist.common.domain.model.ShoppingList;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;
import timber.log.Timber;

public class ViewShoppingListFragment extends LifecycleFragment {

    public static final String TAG = ViewShoppingListFragment.class.getSimpleName();

    @Inject
    ShoppingListViewModelFactory shoppingListViewModelFactory;

    @BindView(R.id.shopping_list_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.shopping_list_data_container)
    FrameLayout shoppingListDataContainer;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private ShoppingListAdapter recyclerViewAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;

    private Unbinder unbinder;

    private ShoppingListViewModel viewModel;

    private ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();
            viewModel.completeShoppingListItem(position);
        }
    };

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shopping_list_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        fab.setOnClickListener(v -> {
            hideShoppingListDataContainer();
            attachAddShoppingListItemFragment();
        });

        recyclerView.setHasFixedSize(true);

        recyclerViewLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(recyclerViewLayoutManager);

        recyclerViewAdapter = new ShoppingListAdapter(new ArrayList<>());
        recyclerView.setAdapter(recyclerViewAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        Timber.d("shopping list fragment onCreateView");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity(), shoppingListViewModelFactory).get(ShoppingListViewModel.class);

        viewModel.loadShoppingList();

        observeShoppingList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void observeShoppingList() {
        viewModel.getShoppingList().observe(this, shoppingList -> displayShoppingList(shoppingList));
    }

    private void displayShoppingList(ShoppingList shoppingList) {
        if (!shoppingList.getItems().isEmpty()) {
            recyclerViewAdapter.replaceItems(shoppingList.getItems());
        }
    }

    private void attachAddShoppingListItemFragment() {
        Fragment addShoppingListItemFragment = new AddShoppingListItemFragment();
        getChildFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null)
                .add(R.id.shopping_list_fragment_root_view, addShoppingListItemFragment)
                .commit();
    }

    private void hideShoppingListDataContainer() {
        shoppingListDataContainer.setVisibility(View.GONE);
    }
}
