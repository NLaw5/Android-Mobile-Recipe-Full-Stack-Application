package com.example.assignment4;

import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button AmericanButton;
    Button ChineseButton;
    Button VietnameseButton;
    Button ThaiButton;
    String queryForRecipe;

    RecipeDataBaseClient dataBaseClient;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataBaseClient = new RecipeDataBaseClient(this);
        builder = new AlertDialog.Builder(this);

        AmericanButton = findViewById(R.id.AmericanButton);
        ChineseButton = findViewById(R.id.ChineseButton);
        VietnameseButton = findViewById(R.id.VietnameseButton);
        ThaiButton = findViewById(R.id.ThaiButton);

        AmericanButton.setOnClickListener(this);
        ChineseButton.setOnClickListener(this);
        VietnameseButton.setOnClickListener(this);
        ThaiButton.setOnClickListener(this);


    }

    //Button clicked
    public void onClick(View view){
        if(((Button)view).getText().toString().equals("American")){
            queryForRecipe = "American";
        }
        else if(((Button)view).getText().toString().equals("Chinese")){
            queryForRecipe = "Chinese";
        }
        else if(((Button)view).getText().toString().equals("Vietnamese")){
            queryForRecipe = "Vietnamese";
        }
        else
        {
            queryForRecipe = "Thai";
        }

        navigate_second(queryForRecipe);
    }

    public void navigate_second(String queryString)
    {
        Intent myIntent = new Intent(this, SecondActivity.class);
        myIntent.putExtra("queryMessage", queryForRecipe);

        startActivity(myIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.recipe_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.saved_recipes: {
                Intent myIntent = new Intent(this, SavedRecipes.class);
                startActivity(myIntent);
                break;
            }
            case R.id.delete_saved_recipes: {
                //NEED THIS so we can actually grab the data
                RecipeDataBaseClient.deleteAllRecipes();
                showAnAlert();
                break;
            }
            case R.id.exit:{
                break;
            }
        }
        return true;
    }

    private void showAnAlert() {
        builder.create();
        builder.setMessage("All Saved Recipes are Deleted!");
        builder.setTitle("Recipe Deletion!");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("Recipe App","in dialog ok button");
            }
        });
        builder.show();
    }
}