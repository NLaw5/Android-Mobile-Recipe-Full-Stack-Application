package com.example.assignment4;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RecipeDao {

    @Insert
    void insert(Recipe recipe);

    @Delete
    void delete(Recipe recipe);

    @Query("Select * from Recipe")
    List<Recipe> getAllRecipes();

    @Update
    void updateDonation(Recipe updatedDonation);

    @Query("Delete From Recipe")
    void deleteAllRecipes();
}
