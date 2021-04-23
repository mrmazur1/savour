package com.savour.savourbackend.repository;


import org.springframework.data.repository.CrudRepository;
import com.savour.savourbackend.model.Amounts;

import java.util.List;


// This will be AUTO IMPLEMENTED by Spring into a Bean called amountsRepository
// CRUD refers Create, Read, Update, Delete

public interface AmountsRepo extends CrudRepository<Amounts, Integer> {
    List<Amounts> findByRecipe_Id(int id);
}

