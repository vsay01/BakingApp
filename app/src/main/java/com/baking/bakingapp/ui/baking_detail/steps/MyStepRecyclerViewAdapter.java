package com.baking.bakingapp.ui.baking_detail.steps;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baking.bakingapp.R;
import com.baking.bakingapp.data.models.StepWS;
import com.baking.bakingapp.ui.baking_detail.steps.StepFragment.OnListFragmentInteractionListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyStepRecyclerViewAdapter extends RecyclerView.Adapter<MyStepRecyclerViewAdapter.ViewHolder> {

    private final List<StepWS> stepWSList;
    private StepItemClickListener stepItemClickListener;

    public MyStepRecyclerViewAdapter(List<StepWS> items, StepItemClickListener stepItemClickListener) {
        stepWSList = items;
        this.stepItemClickListener = stepItemClickListener;
    }

    @Override
    public MyStepRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_step, parent, false);
        return new MyStepRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyStepRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.mItem = stepWSList.get(position);
        if (holder.mItem != null) {
            holder.tvStepDescription.setText(holder.mItem.shortDescription);
            holder.view.setOnClickListener(v -> stepItemClickListener.onStepClicked(position));
        }
    }

    @Override
    public int getItemCount() {
        return stepWSList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public StepWS mItem;
        View view;

        @BindView(R.id.tv_step_description)
        AppCompatTextView tvStepDescription;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }
    }
}
