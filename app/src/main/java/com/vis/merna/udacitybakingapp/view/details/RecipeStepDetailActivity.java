package com.vis.merna.udacitybakingapp.view.details;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.vis.merna.udacitybakingapp.R;
import com.vis.merna.udacitybakingapp.model.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepDetailActivity extends AppCompatActivity {

    public static final String ARGS_RECIPE = "ARGS_RECIPE";
    public static final String ARGS_STEP_SELECTED = "ARGS_STEP_SELECTED";

    @BindView(R.id.recipe_step_tab_layout)
    TabLayout recipeStepTabLayout;
    @BindView(R.id.recipe_step_viewpager)
    ViewPager recipeStepViewPager;
    @BindView(android.R.id.content)
    View contentLayout;

    private Recipe recipe;
    private int selectedStepPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);

        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(ARGS_RECIPE) && bundle.containsKey(ARGS_STEP_SELECTED)) {
            recipe = bundle.getParcelable(ARGS_RECIPE);
            selectedStepPosition = bundle.getInt(ARGS_STEP_SELECTED);
        } else {
            // TODO: 4/1/2019 handle failer
        }


        // Show the Up button in the action bar.
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(recipe.getName());
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        StepsViewPagerAdapter adapter = new StepsViewPagerAdapter(getApplicationContext(), recipe.getSteps(), getSupportFragmentManager());
        recipeStepViewPager.setAdapter(adapter);
        recipeStepTabLayout.setupWithViewPager(recipeStepViewPager);
        recipeStepViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (actionBar != null) {
                    actionBar.setTitle(recipe.getSteps().get(position).getShortDescription());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        recipeStepViewPager.setCurrentItem(selectedStepPosition);
    }

}
