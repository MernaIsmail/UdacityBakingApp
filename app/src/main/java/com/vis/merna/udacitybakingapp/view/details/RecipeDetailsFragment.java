package com.vis.merna.udacitybakingapp.view.details;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vis.merna.udacitybakingapp.R;
import com.vis.merna.udacitybakingapp.model.Ingredient;
import com.vis.merna.udacitybakingapp.model.Recipe;
import com.vis.merna.udacitybakingapp.view.RecipeActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecipeDetailsFragment extends Fragment implements IRecipeDetailsView {

    private static final String ARG_RECIPE = "ARG_RECIPE";
    OnStepClickListener callback;
    private Unbinder unbinder;
    private Recipe recipe;

    @BindView(R.id.ingredient_text_view)
    TextView ingredientTextView;
    @BindView(R.id.steps_recycler_view)
    RecyclerView stepsRecyclerView;

    public RecipeDetailsFragment() {
        Log.d("data**", "fragment");
    }

    public static RecipeDetailsFragment newInstance(Recipe recipe) {
        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_RECIPE, recipe);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("data**", "fragment created");
        if (getArguments() != null) {
            recipe = getArguments().getParcelable(ARG_RECIPE);
            Log.d("data**", recipe.getName());
        } else {
            recipe = ((RecipeActivity) getActivity()).getRecipeData();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        setContent();
        return rootView;
    }

    private void setContent() {
        setIngredientDetails();
        setupStepsRecyclerView();
    }

    private void setIngredientDetails() {
        String ingredientHTML = "";
        List<Ingredient> ingredients = recipe.getIngredients();
        for (Ingredient ingredient : ingredients) {
            if (!TextUtils.isEmpty(ingredient.getIngredient())) {
                String temp = String.format("- %s <b>(%s %s)</b><br>",
                        ingredient.getIngredient(),
                        ingredient.getQuantity(),
                        ingredient.getMeasure()
                );
                ingredientHTML += temp;
            }
        }
        ingredientTextView.setText(Html.fromHtml(ingredientHTML));
    }

    private void setupStepsRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(stepsRecyclerView.getContext(),
                linearLayoutManager.getOrientation());
        stepsRecyclerView.setLayoutManager(linearLayoutManager);
        stepsRecyclerView.addItemDecoration(dividerItemDecoration);
        stepsRecyclerView.setAdapter(new StepsAdapter(getContext(), recipe.getSteps(), position -> {
            callback.onStepSelected(position);
        }));
        stepsRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (OnStepClickListener) context;
        } catch (ClassCastException e) { 
            throw new ClassCastException(context.toString()
                    + " must implement OnStepClickListener");
        }
    }

    public interface OnStepClickListener {
        void onStepSelected(int position);
    }
}
