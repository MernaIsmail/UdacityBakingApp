package util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.vis.merna.udacitybakingapp.R;
import com.vis.merna.udacitybakingapp.model.Recipe;

public class SharedPreferencesHelper {
    public static final String PREFS_NAME = "prefs";

    public static void saveRecipe(Context context, Recipe recipe) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(recipe);
        prefs.putString(context.getResources().getString(R.string.widget_recipe_key), json);
        prefs.apply();
    }

    public static Recipe loadRecipe(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(context.getResources().getString(R.string.widget_recipe_key), "");
        return gson.fromJson(json, Recipe.class);
    }
}
