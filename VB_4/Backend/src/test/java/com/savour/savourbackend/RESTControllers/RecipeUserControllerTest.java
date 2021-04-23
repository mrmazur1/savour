package com.savour.savourbackend.RESTControllers;

import com.savour.savourbackend.model.Calendar;
import com.savour.savourbackend.model.Ingredients;
import com.savour.savourbackend.model.Recipes;
import com.savour.savourbackend.model.Users;
import com.savour.savourbackend.repository.*;
import com.savour.savourbackend.services.RecipeService;
import com.savour.savourbackend.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;



import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(RecipeUserController.class)
public class RecipeUserControllerTest {

    @MockBean
    UserService userService;

    @MockBean
    RecipeService recipeService;

    @Mock
    @Autowired
    RecipeUserController recipeUserController;

    @MockBean
    private UsersRepo usersRepo;
    @MockBean
    private RecipesRepo recipesRepo;
    @MockBean
    private IngredientsRepo ingredientsRepo;
    @MockBean
    private CalendarRepo calendarRepo;
    @MockBean
    private AmountsRepo amountsRepo;

    private Users users;
    private Recipes recipe;
    private Ingredients ingredients;


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

    }

    @Test
    public void unfavoriteRecipeTest() {
//        init();
//        when(userService.checkRecipeInCalendar(recipe.getId(), users.getUserId())).thenReturn(null);
//        when(userService.unLinkRecipeToUser(recipe.getId(), users.getUserId())).thenReturn(true);
//        String response = recipeUserController.UnlinkRecipeTOUser(recipe.getId(), users.getUserId()).getBody().status;
//        assertEquals("recipe unfavorited", response);
    }

    @Test
    public void saveRecipeIDToUserTest() {
//        init();
//        when(userService.linkRecipetoUser(recipe.getId(), users.getUserId())).thenReturn(true);
//        String response = recipeUserController.linkRecipeToUser(recipe.getId(), users.getUserId()).getBody().status;
//        assertEquals("recipe added to user", response);
    }

}