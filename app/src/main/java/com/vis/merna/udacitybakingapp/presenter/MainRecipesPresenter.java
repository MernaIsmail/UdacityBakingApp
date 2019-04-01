package com.vis.merna.udacitybakingapp.presenter;

import com.vis.merna.udacitybakingapp.model.Recipe;
import com.vis.merna.udacitybakingapp.service.RecipeApiService;
import com.vis.merna.udacitybakingapp.service.RecipesApiCallback;
import com.vis.merna.udacitybakingapp.view.IMainView;

import java.util.List;

public class MainRecipesPresenter implements IMainRecipesPresenter {
    private RecipeApiService recipeApiService;
    private IMainView mainView;

    public MainRecipesPresenter(IMainView mainView) {
        this.mainView = mainView;
        recipeApiService = RecipeApiService.getInstance();
    }

    @Override
    public void loadRecipesData() {
        recipeApiService.getRecipes(new RecipesApiCallback<List<Recipe>>() {
            @Override
            public void onSuccess(List<Recipe> result) {
                mainView.updateView(result);
            }

            @Override
            public void onError() {

            }
        });
    }
}
