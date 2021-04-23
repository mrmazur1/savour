package com.savour.savourbackend.RESTControllers;

import com.savour.savourbackend.SavourbackendApplication;
import com.savour.savourbackend.model.Amounts;
import com.savour.savourbackend.model.Ingredients;
import com.savour.savourbackend.model.Recipes;
import com.savour.savourbackend.repository.AmountsRepo;
import com.savour.savourbackend.repository.IngredientsRepo;
import com.savour.savourbackend.services.RecipeService;
import com.savour.savourbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path="/recipeUser", method = { RequestMethod.GET, RequestMethod.POST })
public class RecipeUserController {

    @Autowired
    private AmountsRepo amountsRepo;
    @Autowired
    private IngredientsRepo ingredientsRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private RecipeService recipeService;

    private static Logger logger = SavourbackendApplication.getStaticInstance();
    private int recipeId;
    private HTTPResponse httpResponse;

//    /**
//     * this method unfavorites a recipe
//     * @param recipeId
//     * @return
//     */
////    @PostMapping(path= "/unlinkRecipe")
////    public ResponseEntity<HTTPResponse> UnlinkRecipeTOUser(@RequestParam int recipeId, @RequestHeader(SavourbackendApplication.USER_ID_HEADER) int UID) {
////        //try{
//////           // int response = userService.checkRecipeInCalendar(recipeId, UID);
//////            if (response > 0) {
//////                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//////                Date date = new Date();
//////                formatter.format(date);
//////                java.util.Calendar calJava = java.util.Calendar.getInstance();
//////                calJava.setTime(date);
//////                String day = WeeklyPlanController.convertDaytoString(calJava.get(Calendar.DAY_OF_WEEK));
//////                userService.removeCalendar(recipeId, UID, day);
//////            }
//////            if(userService.unLinkRecipeToUser(recipeId, UID)) {
//////                httpResponse = new HTTPResponse("recipe unfavorited");
//////                return new ResponseEntity<>(httpResponse, HttpStatus.ACCEPTED);
//////            }
//////            httpResponse = new HTTPResponse("recipe not unfavorited succesfully");
//////            return new ResponseEntity<>(httpResponse, HttpStatus.NOT_ACCEPTABLE);
//////        } catch (Exception e) {
//////            httpResponse = new HTTPResponse("recipe not unfavorited succesfully");
//////            return new ResponseEntity<>(httpResponse, HttpStatus.NOT_ACCEPTABLE);
//////        }
////            return null;
////    }
////
////    /**
////     * this links a recipe already in the system to a user
////     * @param RecipeID id used to link recipe
////     * @return response
////     */
////
////    @PostMapping(path = "/linkRecipe/ID")
////    public ResponseEntity<HTTPResponse> linkRecipeToUser(@RequestParam int RecipeID, @RequestHeader(SavourbackendApplication.USER_ID_HEADER) int UID) {
////        try {
////            userService.linkRecipetoUser(RecipeID, UID);
////            httpResponse = new HTTPResponse("recipe added to user");
////            return new ResponseEntity<>(httpResponse, HttpStatus.ACCEPTED);
////        } catch (Exception e) {
////            httpResponse = new HTTPResponse("recipe added to user");
////            return new ResponseEntity<>(httpResponse, HttpStatus.NOT_ACCEPTABLE);
////        }
////    }
//
//    /**
//     * this method will save a json recipe and link it to user
//     * @param payload recipe to save
//     * @return response indicating status
//     * may not need this
//     */
//    @PostMapping(path = "/favoriteRecipe/json")
//    public ResponseEntity<Integer> favoriteRecipeJson(@RequestBody LinkedHashMap payload, @RequestHeader(SavourbackendApplication.USER_ID_HEADER) int UID){
//        try{
//            Recipes r = new Recipes();
//            LinkedHashMap recipeJson = (LinkedHashMap) payload.get("recipe");
//            JSONtoRecipe(recipeJson, r);
//
//            for(LinkedHashMap i : (List<LinkedHashMap>) payload.get("ingredients")) {
//                Ingredients ingr = new Ingredients();
//                ingredientsRepo.save(JSONtoIngredient(i, ingr));
//
//                Amounts amount = new Amounts();
//                amount.setIngredients(ingr);
//                amount.setRecipe(r);
//                amount.setAmount(Double.parseDouble(i.get("amount").toString()));
//                amountsRepo.save(amount);
//            }
//            userService.linkRecipetoUser(r.getId(), UID);
//            return ResponseEntity.accepted().header("saveJsontoUser").body(r.getId());
//        } catch (Exception e) {
//            logger.log(Level.WARNING, "problem with favoriting recipe with RID");
//            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
//        }
//    }
//
//    /**
//     * Helpers
//     */
//    public void JSONtoRecipe(LinkedHashMap json, Recipes recipe){
//        recipe.setName(json.get("name").toString());
//        recipe.setCost(Double.parseDouble(json.get("cost").toString()));
//        recipe.setTimeHours(Integer.parseInt(json.get("timeHours").toString()));
//        recipe.setTimeMinutes(Integer.parseInt(json.get("timeMinutes").toString()));
//        recipe.setTimeSeconds(Integer.parseInt(json.get("timeSeconds").toString()));
//        recipe.setCategory(json.get("category").toString());
//        recipe.setMediaUrl((json.get("mediaUrl").toString()));
//        recipeId = recipe.getId();
//    }
//
//    /**
//     * converts the ingredients for recipe from JSON to ingredients
//     * @param json file
//     * @param ingredient object/entity to add to
//     * @return ingredients read from json file
//     */
//    private Ingredients JSONtoIngredient(LinkedHashMap json, Ingredients ingredient){
//        ingredient.setName(json.get("name").toString());
//        ingredient.setUnit(json.get("unit").toString());
//        ingredient.setType(json.get("type").toString());
//        return ingredient;
//    }
}




