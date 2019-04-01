package com.vis.merna.udacitybakingapp.service;

public interface RecipesApiCallback<T> {
    void onSuccess(T result);

    void onError();
}
