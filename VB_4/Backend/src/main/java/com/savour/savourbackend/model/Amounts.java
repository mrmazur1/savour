package com.savour.savourbackend.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "amounts")
public class Amounts implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int amountId;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "recipe_id")
    public Recipes recipe;
    public Recipes getRecipeId() {
        return recipe;
    }

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ingredient_id")
    Ingredients ingredient;
    public Ingredients getIngredientsId() {
        return ingredient;
    }

    @Column(nullable = true)
    private Double amount;


    /**
     * SETS
     */
    public void setAmount(Double amount){
        this.amount = amount;
    }

    public void setIngredients(Ingredients ingredient) {
        this.ingredient = ingredient;
    }

    public void setRecipe(Recipes recipe) {
        this.recipe = recipe;
    }

    /**
     * GETS
     */
    public int getID() {return amountId;}
    public Double getAmount()
    {
        return amount;
    }
}