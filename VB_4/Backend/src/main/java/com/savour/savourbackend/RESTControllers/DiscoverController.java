package com.savour.savourbackend.RESTControllers;

import com.savour.savourbackend.model.Recipes;
import com.savour.savourbackend.repository.IngredientsRepo;
import com.savour.savourbackend.repository.RecipesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/discover" )
public class DiscoverController {

    @Autowired
    RecipesRepo recipesRepo;
    @Autowired
    IngredientsRepo ingredientsRepo;

    //Logger for debugging
    private static final Logger logger = Logger.getLogger(DiscoverController.class.getName());

    /**
     * this method will return trending recipies withing Savour
     * @return
     */
    @GetMapping()
    public Iterable<Recipes> getTrending() {
        return recipesRepo.findAll();
    }
}
