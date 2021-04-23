package com.savour.savourbackend.RESTControllers;

import com.savour.savourbackend.SavourbackendApplication;
import com.savour.savourbackend.model.Recipes;
import com.savour.savourbackend.model.Users;
import com.savour.savourbackend.repository.RecipesRepo;
import com.savour.savourbackend.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Optional;


@RestController
@RequestMapping(path = "/profile", method = { RequestMethod.GET, RequestMethod.POST })
public class ProfilePageController {

    @Autowired
    UsersRepo usersRepo;

    @Autowired
    RecipesRepo recipesRepo;

    Users user;
    @GetMapping()
    public Users.ProfilePageData userDetails(@RequestHeader(SavourbackendApplication.USER_ID_HEADER) int userId) {
        return usersRepo.findDataById(userId).get(0);
    }
    

    @RequestMapping(value = "/views/{recipeId}")
    public @ResponseBody Users userViewsRecipe(@RequestHeader(SavourbackendApplication.USER_ID_HEADER)
                                                           int userId, @PathVariable int recipeId) {
        Optional<Users> optionalUser = usersRepo.findById(userId);
        user = optionalUser.get();
        Optional<Recipes> optionalRecipe = recipesRepo.findById(recipeId);
        Recipes recipe = optionalRecipe.get();
        for(Recipes r : user.getRecentlyViewed())
            if(r.getId() == recipe.getId())
                return user;
        user.addRecentlyViewed(recipe);
        usersRepo.save(user);
        return user;
    }

    @RequestMapping(value = "/favorite/{recipeId}")
    public @ResponseBody Users userFavoritesRecipe(@RequestHeader(SavourbackendApplication.USER_ID_HEADER)
                                                               int userId, @PathVariable int recipeId) {
        Optional<Users> optionalUser = usersRepo.findById(userId);
        user = optionalUser.get();
        Optional<Recipes> optionalRecipe = recipesRepo.findById(recipeId);
        Recipes recipe = optionalRecipe.get();
        for(Recipes r : user.getFavoriteRecipes()) {
            if (r.getId() == recipe.getId()) {
                user.removeRecipeFromFavorites(recipe);
                usersRepo.save(user);
                return user;
            }
        }       
        user.addFavoriteRecipes(recipe);
        usersRepo.save(user);
        return user;
    }

}
