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
import org.apache.tomcat.util.json.JSONParser;
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
public class amountsRepoTests {

    @TestConfiguration
    static class AmountsTestConfiguration {

        @Bean
        AmountsRepo getRepo() {
            return mock(AmountsRepo.class);
        }
    }

    @Autowired
    private AmountsRepo amountsRepo;

    @Before
    public void before() {

    }

    @Test
    public void addIngredientsTest() {
        List<Amounts> list = new ArrayList<Amounts>();
        when(amountsRepo.save((Amounts)any(Amounts.class))).thenAnswer(x -> {
            Amounts i = x.getArgument(0);
            list.add(i);
            return null;
        });

        LinkedHashMap<String, String> recipe = new LinkedHashMap<String, String>();
        recipe.put("name", "Soup");
        recipe.put("cost", "10.5");
        recipe.put("timeHours", "1");
        recipe.put("timeMinutes", "2");
        recipe.put("timeSeconds", "50");
        recipe.put("category", "Lunch");
        recipe.put("mediaUrl", "SampleImage");
        JSONObject payload = new JSONObject();
        payload.put("recipe", recipe);

        JSONArray ingredientsJSONArray = new JSONArray();
        LinkedHashMap<String, String> noodle = new LinkedHashMap<String, String>();
        noodle.put("name", "Noodle");
        noodle.put("unit", "CUPS");
        noodle.put("type", "GRAIN");
        noodle.put("amount", "2");
        LinkedHashMap<String, String> chicken = new LinkedHashMap<String, String>();
        chicken.put("name", "Chicken");
        chicken.put("unit", "CUPS");
        chicken.put("type", "MEAT");
        chicken.put("amount", "5");
        ingredientsJSONArray.add(noodle);
        ingredientsJSONArray.add(chicken);
        payload.put("ingredients", ingredientsJSONArray);
        ArrayList<Amounts> amounts = new ArrayList<Amounts>();

        for(LinkedHashMap i : (List<LinkedHashMap>) payload.get("ingredients")) {
            Ingredients ingr = new Ingredients();
            Recipes r = new Recipes();
            Amounts amount = new Amounts();
            amount.setIngredients(ingr);
            amount.setRecipe(r);
            amount.setAmount(Double.parseDouble(i.get("amount").toString()));
            amountsRepo.save(amount);

            amounts.add(amount);
        }
        assertEquals("All ingredients were added", amounts, list);
    }

}