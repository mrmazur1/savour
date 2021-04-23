package com.savour.savourbackend.Tests.RestControllerTests;

import com.savour.savourbackend.RESTControllers.HTTPResponse;
import com.savour.savourbackend.RESTControllers.RecipeUserController;
import com.savour.savourbackend.RESTControllers.WeeklyPlanController;
import com.savour.savourbackend.model.Calendar;
import com.savour.savourbackend.model.Ingredients;
import com.savour.savourbackend.model.Recipes;
import com.savour.savourbackend.model.Users;
import com.savour.savourbackend.repository.*;
import com.savour.savourbackend.services.AmountsService;
import com.savour.savourbackend.services.CalendarService;
import com.savour.savourbackend.services.RecipeService;
import com.savour.savourbackend.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(WeeklyPlanController.class)
public class WeeklyPlanControllerTest {

    @MockBean
    UserService userService;

    @MockBean
    RecipeService recipeService;

    @MockBean
    CalendarService calendarService;

    @MockBean
    AmountsService amountsService;

    @MockBean
    RecipeUserController recipeUserController;

    @Mock
    @Autowired
    WeeklyPlanController weeklyPlanController;

    @MockBean
    private UsersRepo usersRepo;
    @MockBean
    private RecipesRepo recipesRepo;
    @MockBean
    private IngredientsRepo ingredientsRepo;
    @MockBean
    CalendarRepo calendarRepo;
    @MockBean
    AmountsRepo amountsRepo;

    @Autowired
    private MockMvc mockMvc;


    private Users users;
    private Recipes recipe;
    private Ingredients ingredients;
    private Calendar calendar;

    public void init(){

        users = new Users();
        users.setUsername("mrmazur");
        users.setPassword("password");
        users.setEmail("mrmazur@iastate.edu");
        users.setAdminStatus("true");

        recipe = new Recipes();
        recipe.setName("cheese cereal");
        recipe.setTimeSeconds(5);
        recipe.setTimeMinutes(10);
        recipe.setTimeHours(2);
        recipe.setCategory("DNNR");
        recipe.setCost(34.50);
        recipe.setMediaUrl("img.png");

        ingredients = new Ingredients();
        ingredients.setUnit("lbs");
        ingredients.setType("cheese");
        ingredients.setName("cheeseWheel");

        calendar = new Calendar();

    }

    @Test
    public void initTest() {
//        Users u = makeUser("mike", "12345", "mrmazur@iastate.edu", "true");
//        Recipes r = makeRecipe("apple pie", "dinner", 12.00, 3, 30, 45);
//        Ingredients i = makeInge("apple", "1", "whole apple");
//        assertEquals("mike", u.getUsername());
//        assertEquals("12345", u.getPassword());
//        assertEquals("mrmazur@iastate.edu", u.getEmail());
//        assertEquals("true", u.getAdminStatus());
//        List<Object> q = recipesRepo.getAllIngredients(r.getRecipeId());
//        List<Object> t = usersRepo.getAllRecipes(u.getUserId());
//        Recipes y = (Recipes)t.get(0);
//        assertEquals("apple pie", y.getName());
//        assertEquals("dinner", y.getCategory());
//        assertEquals(java.util.Optional.of(12.00), y.getCost());
//        assertEquals(3, y.getTimeHours());
//        assertEquals(30, y.getTimeMinutes());
//        assertEquals(45, y.getTimeSeconds());
        //hello

    }

    @Test
    public void addToPlannerTest() {

    }

    @Test
    public void addCalendar(){
        init();
        when(calendarService.saveTOCalendar(calendar, users.getUserId())).thenReturn(true);
        Optional<Recipes> r = Optional.of(recipe);
        Calendar cal = new Calendar();
        when(recipeService.getRecipeById(users.getUserId())).thenReturn(r);
        ArrayList<Recipes> rec = new ArrayList<>();
        rec.add(recipe);
        when(userService.getUserCalendarRecipes(users.getUserId())).thenReturn(rec);
        when(userService.linkRecipetoUser(cal.getCalendarID(), users.getUserId())).thenReturn(true);
        when(userService.addCalendar(cal.getCalendarID(), recipe.getId(), users.getUserId())).thenReturn(true);
        when(calendarService.spotIsAvailable(cal, users.getUserId())).thenReturn(true);
        HTTPResponse response = weeklyPlanController.addCalendar(recipe.getId(), "01/2/2045", users.getUserId()).getBody();
        assertEquals("calendar spot is not available", response.status);
    }


}