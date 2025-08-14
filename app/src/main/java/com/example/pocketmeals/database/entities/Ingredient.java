package com.example.pocketmeals.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.pocketmeals.database.typeConverters.PocketMealsDatabase;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getUnit() {
        return unit;
    }

    public void setUnit(@NonNull String unit) {
        this.unit = unit;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getCarbs() {
        return carbs;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    @NonNull
    public String getCategory() {
        return category;
    }

    public void setCategory(@NonNull String category) {
        this.category = category;
    }

    @NonNull
    public String toShoppingList() {
        return unit + " " + name;
    }
}

