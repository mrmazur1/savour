package com.vb4.savour.data.model;

import java.io.Serializable;

public class Recipe implements Serializable {
    public int id;
    public String name;
    public String mediaUrl;
    public int timeHours;
    public int timeMinutes;
    public int timeSeconds;
    public String category;
    public double cost;
    public String recipeSteps;
}
