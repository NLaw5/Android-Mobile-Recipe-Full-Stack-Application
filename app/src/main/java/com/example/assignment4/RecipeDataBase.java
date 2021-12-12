package com.example.assignment4;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Recipe.class},version = 1)
public abstract class RecipeDataBase extends RoomDatabase{
    public abstract RecipeDao getRecipeDao();
}
