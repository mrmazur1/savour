package com.savour.savourbackend.Tests;

import com.savour.savourbackend.RESTControllers.RecipeController;
import com.savour.savourbackend.model.Amounts;
import com.savour.savourbackend.model.Ingredients;
import com.savour.savourbackend.model.Recipes;
import com.savour.savourbackend.repository.AmountsRepo;
import com.savour.savourbackend.repository.IngredientsRepo;
import com.savour.savourbackend.repository.RecipesRepo;
import com.savour.savourbackend.services.RecipeService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class ingredientsRepoTests {

    @TestConfiguration
    static class IngredientTestConfiguration {

        @Bean
        IngredientsRepo getRepo() {
            return mock(IngredientsRepo.class);
        }

    }



    @Autowired
    private IngredientsRepo ingredientsRepo;


    @Before
    public void before() {

    }

    @Test
    public void addIngredientsTest() {
        List<Ingredients> list = new ArrayList<Ingredients>();
        when(ingredientsRepo.save((Ingredients)any(Ingredients.class))).thenAnswer(x -> {
            Ingredients i = x.getArgument(0);
            list.add(i);
            return null;
        });


        JSONArray ingredientsJSONArray = new JSONArray();
        LinkedHashMap<String, String> noodle = new LinkedHashMap<String, String>();
        noodle.put("name", "Noodle");
        noodle.put("unit", "CUPS");
        noodle.put("type", "GRAIN");
        LinkedHashMap<String, String> chicken = new LinkedHashMap<String, String>();
        chicken.put("name", "Chicken");
        chicken.put("unit", "CUPS");
        chicken.put("type", "MEAT");
        ingredientsJSONArray.add(noodle);
        ingredientsJSONArray.add(chicken);
        JSONObject payload = new JSONObject();
        payload.put("ingredients", ingredientsJSONArray);

        ArrayList<Ingredients> ingredients = new ArrayList<Ingredients>();
        for(LinkedHashMap i : (List<LinkedHashMap>) payload.get("ingredients")) {
            Ingredients ingr = new Ingredients();
            ingredientsRepo.save(RecipeController.JSONtoIngredient(i, ingr));
            ingredients.add(ingr);
        }
        assertEquals("All ingredients were added", ingredients, list);
    }

}