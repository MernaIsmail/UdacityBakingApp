package com.vis.merna.udacitybakingapp.service;

import com.vis.merna.udacitybakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeApiService {
    private static volatile RecipeApiService sharedRecipeInstance = new RecipeApiService();
    private Retrofit retrofit = null;
    private RecipeAPIInterface recipeAPIInterface;

    private RecipeApiService() {
        String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";
        if (retrofit == null) {
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        recipeAPIInterface = retrofit.create(RecipeAPIInterface.class);
    }

    public static RecipeApiService getInstance() {
        if (sharedRecipeInstance == null) {
            synchronized (RecipeApiService.class) {
                if (sharedRecipeInstance == null) sharedRecipeInstance = new RecipeApiService();
            }
        }

        return sharedRecipeInstance;
    }

    public void getRecipes(final RecipesApiCallback<List<Recipe>> recipesApiCallback) {
        recipeAPIInterface.getRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                recipesApiCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                    recipesApiCallback.onError();
            }
        });
    }
}
