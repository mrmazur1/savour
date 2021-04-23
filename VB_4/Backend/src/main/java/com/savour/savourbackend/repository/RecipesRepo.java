package com.savour.savourbackend.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.savour.savourbackend.model.Recipes;

import java.util.List;
import java.util.Optional;

// This will be AUTO IMPLEMENTED by Spring into a Bean called recipesRepository
// CRUD refers Create, Read, Update, Delete

public interface RecipesRepo extends CrudRepository<Recipes, Integer> {

    List<Recipes> findByRecipeNameContains(String recipeName);


}