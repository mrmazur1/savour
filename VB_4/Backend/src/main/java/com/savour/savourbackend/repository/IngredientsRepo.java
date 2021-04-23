package com.savour.savourbackend.repository;

import org.springframework.data.repository.CrudRepository;
import com.savour.savourbackend.model.Ingredients;


// This will be AUTO IMPLEMENTED by Spring into a Bean called ingredientsRepository
// CRUD refers Create, Read, Update, Delete

public interface IngredientsRepo extends CrudRepository<Ingredients, Integer> {

}