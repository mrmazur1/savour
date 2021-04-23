package com.savour.savourbackend.RESTControllers;

import com.savour.savourbackend.model.Amounts;
import com.savour.savourbackend.model.Ingredients;
import com.savour.savourbackend.repository.AmountsRepo;
import com.savour.savourbackend.repository.IngredientsRepo;
import com.savour.savourbackend.services.AmountsService;
import com.savour.savourbackend.services.IngredientsService;
import com.savour.savourbackend.services.UserService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping(path = "/stock")
public class IngredientsStockController {
    @Autowired
    UserService userService;
    @Autowired
    AmountsRepo amountsRepo;
    @Autowired
    IngredientsRepo ingredientsRepo;
    @Autowired
    IngredientsService ingredientsService;
    @Autowired
    AmountsService amountsService;


    @GetMapping(path = "/getStock")
    public List<Amounts> getStock(@RequestParam int UID) {
        //TODO
        return null;
    }

    @PostMapping(path = "/addIngredientsToStock")
    public ResponseEntity<String> addIngredintsToUser(@RequestParam int UID, @RequestBody JSONObject payload){
        try{
            for(LinkedHashMap i : (List<LinkedHashMap>) payload.get("ingredients")) {
                Ingredients ingr = new Ingredients();
                ingredientsService.addtoRepo(JSONtoIngredient(i, ingr));


                Amounts amount = new Amounts();
                amount.setIngredients(ingr);
                amount.setRecipe(null);
                amount.setAmount(Double.parseDouble(i.get("amount").toString()));
                amountsRepo.save(amount);
                amountsService.addIngredientsToUserStock(UID, amount.getID());
            }
            return new ResponseEntity<>("added ingredients to user", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("added not ingredients to user: \n\n" + e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    //this is for debugging
    //will remove later
    @GetMapping(path="/getAmount")
    public Iterable<Amounts> getAmounts(){
        return amountsRepo.findAll();
    }

    //this is for debugging
    //will remove later
    @GetMapping(path="/getI")
    public Iterable<Ingredients> getIngr(){
        return ingredientsRepo.findAll();
    }

    //helper method
    public static Ingredients JSONtoIngredient(LinkedHashMap json, Ingredients ingredient){
        ingredient.setName(json.get("name").toString());
        ingredient.setUnit(json.get("unit").toString());
        ingredient.setType(json.get("type").toString());
        return ingredient;
    }
}
