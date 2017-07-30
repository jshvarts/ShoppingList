package com.jshvarts.shoppinglist.lobby.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jshvarts.shoppinglist.R;
import com.jshvarts.shoppinglist.common.domain.model.ShoppingListItem;

import java.util.List;

import timber.log.Timber;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {

    private final List<ShoppingListItem> shoppingListItems;

    public ShoppingListAdapter(List<ShoppingListItem> shoppingListItems) {
        this.shoppingListItems = shoppingListItems;
    }

    @Override
    public ShoppingListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView itemName = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shopping_list_item, parent, false);
        return new ViewHolder(itemName);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ShoppingListItem shoppingListItem = shoppingListItems.get(position);
        holder.itemName.setText(shoppingListItem.getName());
    }

    @Override
    public int getItemCount() {
        return shoppingListItems == null ? 0 : shoppingListItems.size();
    }

    public void replaceItems(List<ShoppingListItem> shoppingListItems) {
        this.shoppingListItems.clear();
        this.shoppingListItems.addAll(shoppingListItems);
        notifyDataSetChanged();
    }

    /**
     * View holder for shopping list items of this adapter
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView itemName;

        public ViewHolder(final TextView itemName) {
            super(itemName);
            this.itemName = itemName;
        }
    }
}
