package com.savour.savourbackend.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "Users")
public class Users implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column(length = 100)
    private String email;

    @Column(nullable = false, length = 100, unique = true)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(length = 100)
    private String adminStatus;

    //This is the favorited recipes
    @ManyToMany
    @Column
    private List<Recipes> recentlyViewed;

    @ManyToMany
    @Column
    private List<Recipes> favoriteRecipes;

    @Column
    private boolean canAddRecipe;

    //these are all the calendar objects related to the user
    @ManyToMany
    @Column(nullable = true)
    public List<Calendar> userCalendars;

    @ManyToMany
    @Column(nullable = true)
    public List<Amounts> userCalendarAmounts;

    @ManyToMany
    @Column(nullable = true)
    public List<Amounts> userStockAmounts;



    /**
     * SETS
     */
    public void setEmail(String email){
        this.email = email;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setAdminStatus(String adminStatus){
        this.adminStatus = adminStatus;
    }

    public void addRecentlyViewed(Recipes recentlyViewed) {
        this.recentlyViewed.add(recentlyViewed);
    }

    public void addFavoriteRecipes(Recipes favoriteRecipes) {
        this.favoriteRecipes.add(favoriteRecipes);
    }

    public void removeRecipeFromFavorites(Recipes recipe){
        this.favoriteRecipes.remove(recipe);
    }

    public void setCanAddRecipe(boolean canAddRecipe) {
        this.canAddRecipe = canAddRecipe;
    }


    /**
     * GETS
     */

    public int getUserId() {return id;}

    public String getEmail(){
        return email;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String getAdminStatus(){
        return adminStatus;
    }

    public List<Calendar> getUserCalendars() {return userCalendars;}

    public List<Amounts> getUserStockAmounts() {return userStockAmounts; }

    public List<Amounts> getUserCalendarAmounts() {return userCalendarAmounts;}

    public List<Recipes> getRecentlyViewed() {
        return recentlyViewed;
    }

    public List<Recipes> getFavoriteRecipes() {
        return favoriteRecipes;
    }

    public boolean getCanAddRecipe() {
        return canAddRecipe;
    }

    //projection
    public interface ProfilePageData {
        boolean getCanAddRecipe();
        List<Recipes> getRecentlyViewed();
        List<Recipes> getFavoriteRecipes();
    }

 }
