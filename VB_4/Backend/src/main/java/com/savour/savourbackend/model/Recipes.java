package com.savour.savourbackend.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "recipes")
public class Recipes implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column(nullable = true, length = 100)
    private String recipeName;

    @Column(nullable = true, length = 100)
    private String recipeCategory;

    @Column(nullable = true)
    private Double recipeCost;

    @Column(nullable = true)
    private int recipeTimeHours;

    @Column(nullable = true)
    private int recipeTimeMinutes;

    @Column(nullable = true)
    private int recipeTimeSeconds;

    @Column(nullable = true)
    private String recipeMediaUrl;

    @Column(nullable = false, length = 1000)
    private String recipeSteps;


    /**
     * SETS
     */
    public void setName(String name){
        this.recipeName = name;
    }

    public void setCategory(String category){
        this.recipeCategory = category;
    }

    public void setCost(double cost) {
        this.recipeCost = cost;
    }

    public void setTimeHours(int timeHours){
        this.recipeTimeHours = timeHours;
    }

    public void setTimeMinutes(int timeMinutes){
        this.recipeTimeMinutes = timeMinutes;
    }

    public void setTimeSeconds(int timeSeconds){
        this.recipeTimeSeconds = timeSeconds;
    }

    public void setMediaUrl(String mediaUrl) {
        this.recipeMediaUrl = mediaUrl;
    }

    public void setRecipeSteps(String recipeSteps) {
        this.recipeSteps = recipeSteps;
    }


    /**
     * GETS
     */
    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.recipeName;
    }

    public String getCategory(){
        return this.recipeCategory;
    }

    public Double getCost(){
        return this.recipeCost;
    }

    public int getTimeHours(){
        return this.recipeTimeHours;
    }

    public int getTimeMinutes(){
        return this.recipeTimeMinutes;
    }

    public int getTimeSeconds(){
        return this.recipeTimeSeconds;
    }

    public String getMediaUrl() {
        return this.recipeMediaUrl;
    }

    public String getRecipeSteps() {
        return this.recipeSteps;
    }

}

