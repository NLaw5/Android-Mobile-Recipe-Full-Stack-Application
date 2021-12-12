package com.example.assignment4;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SavedRecipes extends AppCompatActivity
    implements RecipeDataBaseClient.DatabaseActionListener, RecipesAdapter.recipeCityListener{

    RecipeDataBaseClient dataBaseClient;
    RecyclerView recyclerView;
    RecipesAdapter adapter;
    ArrayList<Recipe> recipes = new ArrayList<Recipe>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        dataBaseClient = new RecipeDataBaseClient(this);

        recyclerView = findViewById(R.id.recipeslist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //For recycler view
        adapter = new RecipesAdapter(this, recipes);
        recyclerView.setAdapter(adapter);



        //NEED THIS so we can actually grab the data
        RecipeDataBaseClient.listener = this;
        RecipeDataBaseClient.getAllRecipes();
    }


    @Override
    public void databaseReturnWithList(List<Recipe> recipeList) {
        recipes = new ArrayList<Recipe>(recipeList);
        adapter = new RecipesAdapter(this, recipes);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void recipeClicked(Recipe selectedRecipe) {

        Intent myIntent = new Intent(this, RecipeDetail.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("singleRecipe", selectedRecipe);
        myIntent.putExtra("bundle", bundle);
        myIntent.putExtra("showButton", 0); //show button is true
        startActivity(myIntent);
    }
}
