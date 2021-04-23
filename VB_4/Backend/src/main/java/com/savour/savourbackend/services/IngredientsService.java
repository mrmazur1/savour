package com.savour.savourbackend.services;

import com.savour.savourbackend.model.Ingredients;
import com.savour.savourbackend.repository.IngredientsRepo;
import com.savour.savourbackend.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class IngredientsService {

    @Autowired
    IngredientsRepo ingredientsRepo;
    @Autowired
    UsersRepo usersRepo;


    public boolean addtoRepo(Ingredients ingredients){
        try{
            ingredientsRepo.save(ingredients);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
