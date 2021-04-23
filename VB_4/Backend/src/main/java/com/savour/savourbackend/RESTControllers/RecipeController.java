package com.savour.savourbackend.RESTControllers;


import com.savour.savourbackend.model.Amounts;
import com.savour.savourbackend.model.Ingredients;
import com.savour.savourbackend.model.Recipes;

import com.savour.savourbackend.repository.AmountsRepo;
import com.savour.savourbackend.repository.IngredientsRepo;
import com.savour.savourbackend.repository.RecipesRepo;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;


@RestController
@RequestMapping(path="/recipe", method = { RequestMethod.GET, RequestMethod.POST })
public class RecipeController {
    @Autowired
    private RecipesRepo recipesRepo;
    @Autowired
    private IngredientsRepo ingredientsRepo;
    @Autowired
    private AmountsRepo amountsRepo;




    @GetMapping( path = "/{recipeId}")
    public List<Amounts> searchBar(@PathVariable int recipeId) {
        return amountsRepo.findByRecipe_Id(recipeId);
    }

    @PostMapping("/addRecipe")
    public @ResponseBody Recipes addNewRecipe(@RequestBody JSONObject payload) {
        Recipes r = new Recipes();
        LinkedHashMap recipeJSON = (LinkedHashMap) payload.get("recipe");
        JSONtoRecipe(recipeJSON, r);
        recipesRepo.save(r);

        for(LinkedHashMap i : (List<LinkedHashMap>) payload.get("ingredients")) {
            Ingredients ingr = new Ingredients();
            ingredientsRepo.save(JSONtoIngredient(i, ingr));

            Amounts amount = new Amounts();
            amount.setIngredients(ingr);
            amount.setRecipe(r);
            amount.setAmount(Double.parseDouble(i.get("amount").toString()));
            amountsRepo.save(amount);
        }
        return recipesRepo.save(r);
    }

    /**
     * Helpers
     */
    public static Recipes JSONtoRecipe(LinkedHashMap json, Recipes recipe){
        recipe.setName(json.get("name").toString());
        recipe.setCost(Double.parseDouble(json.get("cost").toString()));
        recipe.setTimeHours(Integer.parseInt(json.get("timeHours").toString()));
        recipe.setTimeMinutes(Integer.parseInt(json.get("timeMinutes").toString()));
        recipe.setTimeSeconds(Integer.parseInt(json.get("timeSeconds").toString()));
        recipe.setCategory(json.get("category").toString());
        recipe.setMediaUrl((json.get("mediaUrl").toString()));
        recipe.setRecipeSteps(((json.get("recipeSteps").toString())));
        return recipe;
    }

    public static Ingredients JSONtoIngredient(LinkedHashMap json, Ingredients ingredient){
        ingredient.setName(json.get("name").toString());
        ingredient.setUnit(json.get("unit").toString());
        ingredient.setType(json.get("type").toString());
        return ingredient;
    }

}
