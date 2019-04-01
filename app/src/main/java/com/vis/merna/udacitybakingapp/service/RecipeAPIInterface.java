package com.vis.merna.udacitybakingapp.service;

import com.vis.merna.udacitybakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeAPIInterface {
    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getRecipes();
}
