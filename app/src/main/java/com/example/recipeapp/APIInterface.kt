package com.example.recipeapp

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface APIInterface {

    @GET("recipes/")
    fun getRecipes():retrofit2.Call<Recipes>

    @POST("recipes/")
    fun addNewRecipes(@Body recipeData: RecipesItem):retrofit2.Call<Recipes>

}