package com.example.assignment4;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.TasksViewHolder> {

    interface recipeCityListener {
        public void recipeClicked(Recipe selectedRecipe);
    }

    private Context mCtx;
    public List<Recipe> recipeList;
    recipeCityListener listener;

    public RecipesAdapter(Context mCtx, List<Recipe> recipeList)
    {
        this.mCtx = mCtx;
        this.recipeList = recipeList;
        listener = (recipeCityListener)mCtx;
    }

    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_recipes, parent, false);
        return new TasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TasksViewHolder holder, int position) {
        Recipe recipeInList = recipeList.get(position);
        holder.cityTextView.setText(recipeInList.getTitle());
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    class TasksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView cityTextView, countryTextView;

        public TasksViewHolder(View itemView) {
            super(itemView);

            cityTextView = itemView.findViewById(R.id.recipeInfo);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Recipe recipes = recipeList.get(getAdapterPosition());
            listener.recipeClicked(recipes);
        }
    }
}
