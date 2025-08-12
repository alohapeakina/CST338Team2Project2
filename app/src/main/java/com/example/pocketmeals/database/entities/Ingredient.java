package com.example.pocketmeals.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.pocketmeals.database.PocketMealsDatabase;

@Entity(tableName = PocketMealsDatabase.INGREDIENT_TABLE)
public class Ingredient {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String name;

    @NonNull
    public String unit;

    public double calories;
    public double protein;
    public double fat;
    public double carbs;

    @NonNull
    public String category;

    public Ingredient(@NonNull String name, @NonNull String unit,
                      double calories, double protein, double fat, double carbs,
                      @NonNull String category) {
        this.name = name;
        this.unit = unit;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.carbs = carbs;
        this.category = category;
    }
}

