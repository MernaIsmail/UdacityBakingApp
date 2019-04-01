package com.vis.merna.udacitybakingapp.view.details;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vis.merna.udacitybakingapp.R;
import com.vis.merna.udacitybakingapp.model.Step;

import java.util.List;

class StepsAdapter extends RecyclerView.Adapter<StepViewHolder> {

    private Context context;
    private List<Step> steps;
    private ItemClickListener itemClickListener;

    public StepsAdapter(Context context, List<Step> steps, ItemClickListener itemClickListener) {
        this.context = context;
        this.steps = steps;
        this.itemClickListener = itemClickListener;
    }


    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_step_item, parent, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        holder.stepNumberTextView.setText(position + " :");
        holder.stepDescTextView.setText(steps.get(position).getShortDescription());
        holder.itemView.setOnClickListener(v -> itemClickListener.onItemClick(position));
    }


    @Override
    public int getItemCount() {
        return steps.size();
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }
}
