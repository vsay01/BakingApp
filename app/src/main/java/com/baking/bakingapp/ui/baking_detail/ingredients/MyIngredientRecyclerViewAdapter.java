package com.baking.bakingapp.ui.baking_detail.ingredients;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baking.bakingapp.R;
import com.baking.bakingapp.data.models.IngredientWS;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * specified {@link }.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyIngredientRecyclerViewAdapter extends RecyclerView.Adapter<MyIngredientRecyclerViewAdapter.ViewHolder> {

    private final List<IngredientWS> ingredientWSList;

    public MyIngredientRecyclerViewAdapter(List<IngredientWS> items) {
        ingredientWSList = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_ingredient, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = ingredientWSList.get(position);
        if (holder.mItem != null) {
            holder.ingredientDescription.setText(holder.mItem.ingredient);
            holder.ingredientMeasurement.setText(holder.mItem.measure);
            holder.ingredientQuantity.setText(String.format("%s", holder.mItem.quantity));
        }
    }

    @Override
    public int getItemCount() {
        return ingredientWSList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public IngredientWS mItem;

        @BindView(R.id.ingredient_description)
        AppCompatTextView ingredientDescription;

        @BindView(R.id.ingredient_measurement)
        AppCompatTextView ingredientMeasurement;

        @BindView(R.id.ingredient_quantity)
        AppCompatTextView ingredientQuantity;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
