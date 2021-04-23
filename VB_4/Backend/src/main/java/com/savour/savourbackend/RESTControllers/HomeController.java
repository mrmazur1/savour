package com.savour.savourbackend.RESTControllers;

import com.savour.savourbackend.SavourbackendApplication;
import com.savour.savourbackend.model.Recipes;
import com.savour.savourbackend.repository.IngredientsRepo;
import com.savour.savourbackend.repository.RecipesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/home" )
public class HomeController {

    /**
     * we may not need this page and will instead replace
     */

    @Autowired
    RecipesRepo recipesRepo;
    @Autowired
    IngredientsRepo ingredientsRepo;

    //Logger for debugging
    private static Logger logger = SavourbackendApplication.getStaticInstance();

    /**
     * this gets any info requred when loading the panel
     *
     * @return
     */
    @GetMapping(path = "/init")
    public @ResponseBody
    Iterable<Recipes> init() {
        //TODO
        return null;
    }

    @GetMapping(path="/trends")
    public List<Recipes> getTrending() {
        //TODO
        return null;
    }
}
