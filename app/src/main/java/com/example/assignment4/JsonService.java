package com.example.assignment4;

import static android.text.TextUtils.indexOf;
import static android.text.TextUtils.substring;

import android.text.Html;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonService {
    public ArrayList<Recipe> getRecipesFromJSON(String json) {

        ArrayList<Recipe> arrayList = new ArrayList<>(0);
        try {
            //Initailize json string information as JsonArray
            JSONObject jsonObject = new JSONObject(json);
            JSONArray json_recipes = jsonObject.getJSONArray("results");
            //JSONArray json_recipes = new JSONArray(json);

            for(int i = 0; i<json_recipes.length(); i++)
            {
                JSONObject currentRecipe = json_recipes.getJSONObject(i);

                int id = currentRecipe.getInt("id");
                String title = currentRecipe.getString("title");
                String image = currentRecipe.getString("image");

                Recipe newRecipe = new Recipe(id, title, image);
                arrayList.add(newRecipe);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public String getRecipeDetailsFromJSON(String json){
        String recipeDescription = "";

        try{
            JSONObject recipeDetail = new JSONObject(json);
            recipeDescription = recipeDetail.getString("summary");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Html.fromHtml(recipeDescription, Html.FROM_HTML_MODE_LEGACY).toString();
    }
}
