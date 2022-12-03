package com.example.recipeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.data_row.view.*

class RecyclerViewAdapter (private var recipesList: List<Recipes>):
    RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.data_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        var recipesItems = recipesList[position]

        holder.itemView.apply {
            recipeTitle.text = " Title: " + recipesItems[position].title
            recipeAuthor.text = " Author: " + recipesItems[position].author
            recipeIngredients.text = " Ingredients: " + recipesItems[position].ingredients
            recipeInstructions.text = " Instructions: " + recipesItems[position].instructions

        }
    }

    override fun getItemCount() = recipesList.size

    fun update(recipes: List<Recipes>){
        this.recipesList = recipes
        notifyDataSetChanged()
    }
}