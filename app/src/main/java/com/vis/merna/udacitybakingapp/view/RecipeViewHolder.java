package com.vis.merna.udacitybakingapp.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vis.merna.udacitybakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

class RecipeViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.recipe_card_view)
    CardView recipeCardView;
    @BindView(R.id.recipe_image_view)
    ImageView recipeImageView;
    @BindView(R.id.recipe_name_text_view)
    TextView recipeNameTextView;
    @BindView(R.id.recipe_serving_text_view)
    TextView recipeServingTextView;

    public RecipeViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
