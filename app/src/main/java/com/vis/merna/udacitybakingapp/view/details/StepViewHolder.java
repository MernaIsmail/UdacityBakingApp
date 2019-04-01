package com.vis.merna.udacitybakingapp.view.details;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.vis.merna.udacitybakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

class StepViewHolder  extends RecyclerView.ViewHolder {

    @BindView(R.id.step_number_text_view)
    TextView stepNumberTextView;
    @BindView(R.id.step_desc_text_view)
    TextView stepDescTextView;


    public StepViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
