package com.vis.merna.udacitybakingapp.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.vis.merna.udacitybakingapp.R;
import com.vis.merna.udacitybakingapp.model.Recipe;

import java.util.List;


public class RecipesAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

    private Context context;
    private List<Recipe> recipes;
    private ItemClickListener itemClickListener;

    public RecipesAdapter(Context context, List<Recipe> recipes, ItemClickListener itemClickListener) {
        this.context = context;
        this.recipes = recipes;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_item, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.recipeCardView.setOnClickListener(v -> itemClickListener.onItemClick(position));
        holder.recipeNameTextView.setText(recipes.get(position).getName());
        holder.recipeServingTextView.setText(String.format("Serving : %s",
                recipes.get(position).getServings()));
        if(recipes.get(position).getImage()!=null){
            Picasso.get().load(recipes.get(position).getImage()).into(holder.recipeImageView);
        }
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }
}
