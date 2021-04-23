package com.savour.savourbackend.RESTControllers;

import com.savour.savourbackend.SavourbackendApplication;
import com.savour.savourbackend.model.Recipes;
import com.savour.savourbackend.repository.RecipesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/search" )
public class SearchController {

    @Autowired
    RecipesRepo recipesRepo;

    //Logger for debugging
    private static Logger logger = SavourbackendApplication.getStaticInstance();

    @GetMapping( path = "")
    public List<Recipes> searchBar(@RequestParam(name = "q") String query, @RequestParam(name = "category") String category) {
			if (query.isEmpty()) {
				// TODO - search by category
				return recipesRepo.findByRecipeNameContains(query);
			} else {
				return recipesRepo.findByRecipeNameContains(query);
			}
    }


    /**
     * Load all deserts for user
     * @return list of deserts in DB
     */
    @GetMapping(path = "/load/desert")
    public Optional<Recipes> getAllDeserts() {
        //TODO
        return null;
    }

    /**
     * Load all breakfast options for user
     * @return list of breakfast foods in DB
     */
    @GetMapping(path = "/load/breakfast")
    public Optional<Recipes> getAllBreakfast() {
        //TODO
        return null;
    }

    /**
     * Load all Dinner for user
     * @return list of dinner in DB
     */
    @GetMapping(path = "/load/dinner")
    public Optional<Recipes> getAllDinner() {
        //TODO
        return null;
    }

    /**
     * Load all snacks for user
     * @return list of snacks in DB
     */
    @GetMapping(path = "/load/snacks")
    public Optional<Recipes> getAllSnacks() {
        //TODO
        return null;
    }

    /**
     * Load all Lunch for user
     * @return list of Lunch in DB
     */
    @GetMapping(path = "/load/lunch")
    public Optional<Recipes> getAllLunch() {
        //TODO
        return null;
    }
}
