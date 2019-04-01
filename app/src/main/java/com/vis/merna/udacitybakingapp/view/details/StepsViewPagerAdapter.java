package com.vis.merna.udacitybakingapp.view.details;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vis.merna.udacitybakingapp.model.Step;

import java.util.List;

class StepsViewPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private List<Step> steps;

    public StepsViewPagerAdapter(Context context,
                                 List<Step> steps,
                                 FragmentManager fragmentManager) {
        super(fragmentManager);
        this.context = context;
        this.steps = steps;
    }

    @Override
    public int getCount() {
        return steps.size();
    }

    @Override
    public Fragment getItem(int position) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(RecipeStepDetailFragment.STEP_KEY, steps.get(position));
        RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
        fragment.setArguments(arguments);

        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return String.format("Step %d", position);
    }
}
