package com.savour.savourbackend.model;


import javax.persistence.*;

@Entity
@Table(name = "calendar")
public class Calendar {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int calendarID;

    @Column(nullable = true)
    private int recipeID;

    @Column(nullable = true)
    private int userID;

    @Column(nullable = false, length = 100)
    private String Meal_Type;
    /**
     * example year format
     * ex: day, month, year
     */
    @Column(nullable = false, length = 5)
    private String Day;

    @Column(nullable = false, length = 20)
    private String Month;

    @Column(nullable = false, length = 5)
    private String Year;

    @Column(nullable = true, length = 4)
    private String dayWeek;

    @Column(nullable = true)
    private int weekOYear;

    @Column(nullable = false, length = 50)
    private String RecipeName;


    /**
     * SETS
     */
    public void setRecipeID(int rid){
        this.recipeID = rid;
    }
    public void setUserID(int uid){
        userID = uid;
    }
    public void setMeal_Type(String meal_Type){
        this.Meal_Type = meal_Type;
    }
    public void setDay(String day){
        this.Day = day;
    }
    public void setMonth(String month){
        this.Month = month;
    }
    public void setYear(String year){
        this.Year = year;
    }
    public void setRecipeName(String name){this.RecipeName = name;}
    public void setDayWeek(String dweek) { this.dayWeek = dweek;}
    public void setWeekOYear(int i){this.weekOYear = i;}

    /**
     * GETS
     */
    public int getCalendarID()  {return calendarID;}
    public int getRID() {
        return recipeID;
    }
    public int getUserID() {
        return userID;
    }
    public String getDay() {
        return Day;
    }
    public String getMonth() {
        return Month;
    }
    public String getYear() {
        return Year;
    }
    public String getMeal_Type() {
        return Meal_Type;
    }
    public String getRecipeName() {
        return RecipeName;
    }
    public String getDayWeek() {return dayWeek;}
    public int getWeekOYear() {return weekOYear;}
}
