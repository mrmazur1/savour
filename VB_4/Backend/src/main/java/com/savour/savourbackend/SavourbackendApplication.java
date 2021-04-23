package com.savour.savourbackend;

import com.savour.savourbackend.model.Recipes;
import com.savour.savourbackend.model.Users;
import com.savour.savourbackend.repository.AmountsRepo;
import com.savour.savourbackend.repository.IngredientsRepo;
import com.savour.savourbackend.repository.RecipesRepo;
import com.savour.savourbackend.repository.UsersRepo;
import com.savour.savourbackend.services.RecipeService;
import com.savour.savourbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


@SpringBootApplication
@RestController
public class SavourbackendApplication {
    
    public static final String USER_ID_HEADER = "x-savour-user-id";

    @Autowired
    UserService userService;

    //delete both later
    @Autowired
    RecipeService recipeService;
    @Autowired
    AmountsRepo amountsRepo;
    @Autowired
    UsersRepo usersRepo;
    @Autowired
    RecipesRepo recipesRepo;
    @Autowired
    IngredientsRepo ingredientsRepo;

    private static int UserID;

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(SavourbackendApplication.class, args);
    }

    private static Logger logger;
    public static Logger getStaticInstance() {
        if(logger==null){
            logger = Logger.getLogger(SavourbackendApplication.class.getName());
        }
        return logger;
    }


    //hello world
    @GetMapping("/hello")
    public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    public static int getUserID() {
        return UserID;
    }
    public static void setUserData(int id) { UserID = id;}

    /**
     * debug purposes
     * will be deleted later
     */
    @PostMapping(path = "/user/clear")
    public ResponseEntity<String> clearUsers() {
        try{
            usersRepo.deleteAll();
            return new ResponseEntity<>("table cleared", HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, e.getMessage());
            return new ResponseEntity<>("table not cleared properly", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * debug purposes
     * will be deleted later
     */
    @PostMapping(path = "/recipe/clear")
    public ResponseEntity<String> clearRecipes() {
        try{
            amountsRepo.deleteAll();
            ingredientsRepo.deleteAll();
            recipesRepo.deleteAll();
            return new ResponseEntity<>("table cleared", HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, e.getMessage());
            return new ResponseEntity<>("table not cleared properly", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * debug purposes
     * will be deleted later
     */
    @GetMapping(path="/getU")
    public Iterable<Users> getUser() {
        Iterable<Users> val = userService.getAllUsers();
        return userService.getAllUsers();
    }

    /**
     * debug purposes
     * will be deleted later
     */
    @GetMapping(path = "/getRecipes")
    public Iterable<Recipes> getR() {
        return recipeService.getAllRecipes();
    }



}
