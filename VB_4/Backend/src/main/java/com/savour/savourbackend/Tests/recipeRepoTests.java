package com.savour.savourbackend.Tests;

import com.savour.savourbackend.RESTControllers.RecipeController;
import com.savour.savourbackend.model.Recipes;
import com.savour.savourbackend.repository.RecipesRepo;
import com.savour.savourbackend.services.RecipeService;
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
public class recipeRepoTests {

    @TestConfiguration
    static class RecipeTestConfiguration {
        @Bean
        RecipesRepo getRepo(){
            return mock(RecipesRepo.class);
        }
    }


    @Autowired
    private RecipesRepo recipesRepo;

    private List<Recipes> list = new ArrayList<Recipes>();
    @Before
    public void  before(){
        when(recipesRepo.findAll()).thenReturn(list);
        when(recipesRepo.save((Recipes)any(Recipes.class))).thenAnswer(x -> {
            Recipes r = x.getArgument(0);
            list.add(r);
            return null;
        });

    }

    @Test
    public void repositorySaveTest() {
        List<Recipes> list = new ArrayList<Recipes>();
        when(recipesRepo.save((Recipes)any(Recipes.class))).thenAnswer(x -> {
            Recipes r = x.getArgument(0);
            list.add(r);
            return null;
        });
        Recipes r = new Recipes();
        recipesRepo.save(r);
        assertEquals("Recipe was not added to the repository", r, list.get(0));
    }

    @Test
    public void repositoryFindAllTest() {
        ArrayList<Recipes> listOfSavedRecipes = new ArrayList<Recipes>();
        for(int i = 0; i < 5; i++) {
            Recipes r = new Recipes();
            listOfSavedRecipes.add(r);
            recipesRepo.save(r);
        }
        assertEquals("Not all added recipes were returned", recipesRepo.findAll(), listOfSavedRecipes);
    }

    @Test
    public void findByRecipeNameContainsTest() {
        List<Recipes> l = new ArrayList<Recipes>();
        Recipes soup = new Recipes();
        soup.setName("Soup");
        l.add(soup);
        Recipes cheese = new Recipes();
        cheese.setName("Cheese");
        l.add(cheese);
        recipesRepo.save(soup);
        recipesRepo.save(cheese);
        when(recipesRepo.findByRecipeNameContains(soup.getName())).thenReturn(l);
        assertEquals("Recipes with specified names were returned", soup, recipesRepo.findByRecipeNameContains("Soup").get(0));
    }

    @Test
    public void verifyRecipeData() {
        Recipes r = mock(Recipes.class);
        r.setName("Soup");
        r.setCategory("Dinner");
        r.setCost(10.5);
        verify(r).setName("Soup");
        verify(r).setCategory("Dinner");
        verify(r).setCost(10.5);
    }

    @Test
    public void addRecipeTest() {
        List<Recipes> list = new ArrayList<Recipes>();
        when(recipesRepo.save((Recipes)any(Recipes.class))).thenAnswer(x -> {
            Recipes r = x.getArgument(0);
            list.add(r);
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
        recipe.put("recipeSteps", "steps here");
        JSONObject payload = new JSONObject();
        payload.put("recipe", recipe);
        Recipes r = new Recipes();
        LinkedHashMap recipeJSON = (LinkedHashMap) payload.get("recipe");
        RecipeController.JSONtoRecipe(recipeJSON, r);
        recipesRepo.save(r);
        assertEquals("Recipe was not added", r, list.get(0));
    }

}
