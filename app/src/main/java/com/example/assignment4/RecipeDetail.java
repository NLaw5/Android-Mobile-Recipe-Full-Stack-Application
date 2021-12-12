package com.example.assignment4;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class RecipeDetail extends AppCompatActivity implements NetworkingService.NetworkingListener {


    NetworkingService networkingManager;
    JsonService jsonService;
    TextView recipeTitle;
    TextView recipeDescription;
    ImageView recipeImage;
    Button saveRecipe;

    RecipeDataBaseClient dataBaseClient;
    AlertDialog.Builder builder;
    Recipe selectedRecipe;

    Integer flag = 1; //this will be used so we don't have multiple inserts of the same thing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipedetail);

        dataBaseClient = new RecipeDataBaseClient(this);
        builder = new AlertDialog.Builder(this);

        Bundle bundleFromSecondActivity = getIntent().getBundleExtra("bundle");
        selectedRecipe = bundleFromSecondActivity.getParcelable("singleRecipe");

        int buttonExist = getIntent().getIntExtra("showButton", 0);

        recipeTitle = findViewById(R.id.recipeTitle);
        recipeDescription = findViewById(R.id.recipeDesc);
        recipeImage = findViewById(R.id.recipeImage);
        saveRecipe = findViewById(R.id.saveRecipe);

        if(buttonExist != 1)
        {
            saveRecipe.setVisibility(View.GONE); //we are using this to load in our SavedRecipes
        }

        recipeTitle.setText(selectedRecipe.getTitle());

        networkingManager = ((myApp)getApplication()).getNetworkingService();
        jsonService = ((myApp)getApplication()).getJsonService();
        networkingManager.listener = this;

        //Set our Image data
        networkingManager.getImageData(selectedRecipe.getImage());

        //Call Netowrking Manager for description:
        int recipeId = selectedRecipe.getId();
        networkingManager.recipeDetails(String.valueOf(recipeId));
    }

    @Override
    public void dataListener(String jsonString) {
        //json data from recipe API
        // parse json
        String recipeSummary = jsonService.getRecipeDetailsFromJSON(jsonString);
        recipeDescription.setText(recipeSummary);
    }

    @Override
    public void imageListener(Bitmap image) {
        recipeImage.setImageBitmap(image);
    }

    public void click_add_recipe(View view){
        if(flag == 1)
        {
            RecipeDataBaseClient.insertNewRecipe(selectedRecipe);
            showAnAlert();
        }
        flag = 0;
    }

    private void showAnAlert() {
        builder.create();
        builder.setMessage("Your recipe " + selectedRecipe.getTitle() + " has been saved!");
        builder.setTitle("Recipe Saved!");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("Recipe App","in dialog ok button");
            }
        });
        builder.show();
    }
}
