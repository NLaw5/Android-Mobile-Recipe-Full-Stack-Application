package com.example.assignment4;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.room.Room;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RecipeDataBaseClient {

    static RecipeDataBase dbClient;
    static Context db_context;

    public interface DatabaseActionListener {
        public void databaseReturnWithList(List<Recipe> recipeList);
    }

    static DatabaseActionListener listener;

    public static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(4);
    static Handler handler = new Handler(Looper.getMainLooper());

    //Constructor
    RecipeDataBaseClient(Context context){
        db_context = context;
        dbClient = Room.databaseBuilder(context,
                RecipeDataBase.class, "database-recipe").build();
    }

    public static RecipeDataBase getDbClient() {
        if(dbClient == null){
            dbClient = new RecipeDataBaseClient(db_context).dbClient;
        }
        return dbClient;
    }

    public static void insertNewRecipe(Recipe newRecipe){
        //background thread
        databaseExecutor.execute(new Runnable() {
            @Override
            public void run(){dbClient.getRecipeDao().insert(newRecipe);}
        });
    }

    public static void deleteAllRecipes(){
        databaseExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dbClient.getRecipeDao().deleteAllRecipes();
            }
        });
    }
    public static void getAllRecipes()
    {
        databaseExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<Recipe> listFromDB = dbClient.getRecipeDao().getAllRecipes();
                //run some code in main thread
                //return from background thread

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // return something to main thread, in this case our List<Donation>
                        listener.databaseReturnWithList(listFromDB);
                    }
                });
            }
        });
    }
}
