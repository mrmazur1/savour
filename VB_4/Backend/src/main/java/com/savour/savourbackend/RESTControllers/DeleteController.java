package com.savour.savourbackend.RESTControllers;

import com.savour.savourbackend.model.Users;
import com.savour.savourbackend.repository.CalendarRepo;
import com.savour.savourbackend.repository.IngredientsRepo;
import com.savour.savourbackend.repository.RecipesRepo;
import com.savour.savourbackend.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping(path = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
public class DeleteController {

    @Autowired
    UsersRepo usersRepo;
    @Autowired
    RecipesRepo recipesRepo;
    @Autowired
    IngredientsRepo ingredientsRepo;
    @Autowired
    CalendarRepo calendarRepo;

    @RequestMapping(value = "users")
    public @ResponseBody String deleteAllUsers() {
        usersRepo.deleteAll();
        return "all users deleted";
    }

    @RequestMapping(value = "recipes")
    public @ResponseBody String deleteAllRecipes() {
        recipesRepo.deleteAll();
        return "all recipes deleted";
    }

    @RequestMapping(value = "ingredients")
    public @ResponseBody String deleteAllIngredients() {
        ingredientsRepo.deleteAll();
        return "all ingredeints deleted";
    }

    @RequestMapping(value = "calendars")
    public @ResponseBody String deleteAllCalendars() {
        calendarRepo.deleteAll();
        return "all calendars deleted";
    }



}
