package com.vis.merna.udacitybakingapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.vis.merna.udacitybakingapp.R;
import com.vis.merna.udacitybakingapp.model.Recipe;
import com.vis.merna.udacitybakingapp.view.details.RecipeDetailsFragment;
import com.vis.merna.udacitybakingapp.view.details.RecipeStepDetailActivity;
import com.vis.merna.udacitybakingapp.view.details.RecipeStepDetailFragment;
import com.vis.merna.udacitybakingapp.widget.RecipeWidgetService;

public class RecipeActivity extends AppCompatActivity implements RecipeDetailsFragment.OnStepClickListener {

    public static final String ARG_RECIPE = "ARG_RECIPE";
    private Recipe recipe;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.recipe_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_widget) {
            RecipeWidgetService.updateWidget(this, recipe);
            Toast.makeText(getApplicationContext(),
                    String.format("Recipe %s added to widget", recipe.getName()), Toast.LENGTH_LONG).
                    show();
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("data**", "activity");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(ARG_RECIPE)) {
            recipe = bundle.getParcelable(ARG_RECIPE);
        }
        setContentView(R.layout.activity_recipe);

        if (getResources().getBoolean(R.bool.twoPaneMode)) {
            if (savedInstanceState == null && !recipe.getSteps().isEmpty()) {
                Bundle arguments = new Bundle();
                arguments.putParcelable(RecipeStepDetailFragment.STEP_KEY, recipe.getSteps().get(0));
                RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.recipe_step_detail_container, fragment)
                        .commit();
            }
        }
    }

    private void handleShowingStepsDetails(int position) {
        if (getResources().getBoolean(R.bool.twoPaneMode)) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(RecipeStepDetailFragment.STEP_KEY, recipe.getSteps().get(position));
            RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_step_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, RecipeStepDetailActivity.class);
            intent.putExtra(RecipeStepDetailActivity.ARGS_RECIPE, recipe);
            intent.putExtra(RecipeStepDetailActivity.ARGS_STEP_SELECTED, position);
            startActivity(intent);
        }
    }

    public Recipe getRecipeData() {
        return recipe;
    }

    @Override
    public void onStepSelected(int position) {
        handleShowingStepsDetails(position);
    }
}
