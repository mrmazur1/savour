package com.savour.savourbackend.services;

import com.savour.savourbackend.model.Recipes;
import com.savour.savourbackend.repository.RecipesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    @Autowired
    RecipesRepo recipesRepo;


    /**
     * saves a recipe
     * @param recipe
     */
    public boolean registerRecipe(Recipes recipe) {
        try{
            recipesRepo.save(recipe);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * method deletes recipe from repo
     * @param RID
     * @return
     */
    public boolean deleteRecipe(int RID){
        try{
            Optional<Recipes> r = recipesRepo.findById(RID);
            recipesRepo.delete(r.get());
            r = recipesRepo.findById(RID);
            Recipes t = r.get();
            return true;
        } catch (Exception c) {
            c.printStackTrace();
            return false;
        }
    }

    /**
     * returns all recipes in repo
     * @return
     */
    public Iterable<Recipes> getAllRecipes() {return recipesRepo.findAll();}

    /**
     * returns recipe by ID
     * @param id used to search for recipe
     * @return
     */
    public Optional<Recipes> getRecipeById(int id) { return recipesRepo.findById(id); }


    /**
     * this returns all of the recipes linked to the user
     * @param UserRecipes
     * @return list of user recipies
     */
    public List<Recipes> getAllUserRecipes(List<Integer> UserRecipes) {
        List<Recipes> returnList = new ArrayList<Recipes>();
        for(int i =0; i< UserRecipes.size(); i++) {
            Optional<Recipes> tmp = recipesRepo.findById(UserRecipes.get(i));
            if(tmp.isPresent()){
                returnList.add(tmp.get());
            }
        }
        return returnList;
    }

}
