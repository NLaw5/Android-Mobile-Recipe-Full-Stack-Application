package com.example.assignment4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class SecondActivity extends AppCompatActivity implements RecipesAdapter.recipeCityListener,
NetworkingService.NetworkingListener{

    ArrayList<Recipe> recipes = new ArrayList<Recipe>();
    RecyclerView recyclerView;
    RecipesAdapter adapter;
    NetworkingService networkingManager;
    JsonService jsonService;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent intent_from_fa = getIntent();
        String querySearch = intent_from_fa.getStringExtra("queryMessage");

        //Initializing our objects
        networkingManager = ((myApp)getApplication()).getNetworkingService();

        jsonService = ((myApp)getApplication()).getJsonService();
        networkingManager.listener = this;
        recyclerView = findViewById(R.id.recipeslist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //For recycler view
        adapter = new RecipesAdapter(this, recipes);
        recyclerView.setAdapter(adapter);

        networkingManager.searchForRecipes(querySearch);
    }

    @Override
    public void dataListener(String jsonString) {
        recipes = jsonService.getRecipesFromJSON(jsonString);
        adapter = new RecipesAdapter(this, recipes);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext()
                , DividerItemDecoration.HORIZONTAL));
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void imageListener(Bitmap image) {

    }

    @Override
    public void recipeClicked(Recipe selectedRecipe) {
        //We want to send this to our Activity_recipe
        //NOTE: we are trying to send the specific recipe to be loaded in; therefore, only
        //selectedrecipe must be sent in

        Intent myIntent = new Intent(this, RecipeDetail.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("singleRecipe", selectedRecipe);
        myIntent.putExtra("bundle", bundle);
        myIntent.putExtra("showButton", 1); //show button is true
        startActivity(myIntent);
    }
}
