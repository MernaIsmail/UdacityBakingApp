package com.vis.merna.udacitybakingapp.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vis.merna.udacitybakingapp.R;
import com.vis.merna.udacitybakingapp.model.Recipe;
import com.vis.merna.udacitybakingapp.presenter.IMainRecipesPresenter;
import com.vis.merna.udacitybakingapp.presenter.MainRecipesPresenter;
import com.vis.merna.udacitybakingapp.view.details.RecipeDetailsFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainRecipesFragment extends Fragment implements IMainView, RecipesAdapter.ItemClickListener {

    @BindView(R.id.recipes_recycler_view)
    RecyclerView recipesRecyclerView;


    private Unbinder unbinder;
    private List<Recipe> recipes;

    private IMainRecipesPresenter mainRecipesPresenter;

    public MainRecipesFragment() {
        mainRecipesPresenter = new MainRecipesPresenter(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main_recipes, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        mainRecipesPresenter.loadRecipesData();
        return rootView;
    }

    private void setMoviesRecyclerView(List<Recipe> recipes) {

        boolean twoPaneMode = getResources().getBoolean(R.bool.twoPaneMode);
        if (twoPaneMode) {
            recipesRecyclerView.setLayoutManager(new GridLayoutManager(getActivity().
                    getApplicationContext(), 3));
        } else {
            recipesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().
                    getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        }
        recipesRecyclerView.setAdapter(new RecipesAdapter(getContext(), recipes, this));
        recipesRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateView(List<Recipe> recipes) {
        this.recipes = recipes;
        setMoviesRecyclerView(recipes);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getContext(), RecipeActivity.class);
        intent.putExtra(RecipeActivity.ARG_RECIPE, recipes.get(position));
        startActivity(intent);

//        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().
//                beginTransaction();
//        fragmentTransaction.replace(R.id.recipes_list_fragment,
//                RecipeDetailsFragment.newInstance(recipes.get(position))).
//                addToBackStack(null).commit();
    }
}
