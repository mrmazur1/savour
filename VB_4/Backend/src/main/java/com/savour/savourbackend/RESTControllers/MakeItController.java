package com.savour.savourbackend.RESTControllers;

import com.savour.savourbackend.SavourbackendApplication;
import com.savour.savourbackend.model.Recipes;
import com.savour.savourbackend.repository.IngredientsRepo;
import com.savour.savourbackend.repository.RecipesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/makeit" )
public class MakeItController {

    @Autowired
    RecipesRepo recipesRepo;
    @Autowired
    IngredientsRepo ingredientsRepo;

    //Logger for debugging
    private static Logger logger = SavourbackendApplication.getStaticInstance();

    /**
     * this gets any info requred when loading the panel
     * @return
     */
    @GetMapping(path="/init")
    public @ResponseBody Iterable<Recipes> init() {
        //TODO
        return null;
    }

    /**
     * this method will get the steps for the recipe
     * @param json recipe that will be used
     * @return steps
     */
    @PostMapping(path = "/getSteps")
    public String getSteps(@RequestBody LinkedHashMap json) {

        return null;
    }

}
