package com.jshvarts.shoppinglist.lobby.fragments;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jshvarts.shoppinglist.R;
import com.jshvarts.shoppinglist.common.domain.model.ShoppingListItem;

import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {

    private final List<ShoppingListItem> shoppingListItems;

    public ShoppingListAdapter(List<ShoppingListItem> shoppingListItems) {
        this.shoppingListItems = shoppingListItems;
    }

    @Override
    public ShoppingListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView itemContainer = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shopping_list_item, parent, false);
        return new ViewHolder(itemContainer);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ShoppingListItem shoppingListItem = shoppingListItems.get(position);
        if (shoppingListItem.getCompleted()) {
            // TODO define style for completed items
            holder.itemViewContainer.setCardBackgroundColor(Color.LTGRAY);
            holder.itemName.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
        holder.itemName.setText(shoppingListItem.getName());
    }

    @Override
    public int getItemCount() {
        return shoppingListItems == null ? 0 : shoppingListItems.size();
    }

    /**
     * View holder for shopping list items of this adapter
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CardView itemViewContainer;

        private TextView itemName;

        public ViewHolder(final CardView itemViewContainer) {
            super(itemViewContainer);
            this.itemViewContainer = itemViewContainer;
            this.itemName = (TextView) itemViewContainer.findViewById(R.id.shopping_list_item_name_textview);
        }
    }
}
