package com.vis.merna.udacitybakingapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.vis.merna.udacitybakingapp.model.Ingredient;
import com.vis.merna.udacitybakingapp.view.MainRecipesFragment;
import com.vis.merna.udacitybakingapp.view.RecipeActivity;

import org.junit.Rule;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
public class MainRecipeListScreenTest {
    private static final String INGREDIENT_TITLE ="Ingredient :" ;
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    public void clickListViewItem_RecipeDetailsActivity() {
        onData(anything()).inAdapterView(withId(R.id.recipes_recycler_view)).atPosition(1).perform(click());

        onView(withId(R.id.ingredient_title)).check(matches(withText(INGREDIENT_TITLE)));
    }
}
