package com.savour.savourbackend.RESTControllers;

import com.savour.savourbackend.SavourbackendApplication;
import com.savour.savourbackend.model.Calendar;
import com.savour.savourbackend.model.Recipes;
import com.savour.savourbackend.model.Users;
import com.savour.savourbackend.services.AmountsService;
import com.savour.savourbackend.services.CalendarService;
import com.savour.savourbackend.services.RecipeService;
import com.savour.savourbackend.services.UserService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/planner" )
public class WeeklyPlanController {

    @Autowired
    RecipeService recipeService;
    @Autowired
    UserService userService;
    @Autowired
    RecipeUserController recipeUserController;
    @Autowired
    CalendarService calendarService;
    @Autowired
    AmountsService amountsService;

    private static Logger logger = SavourbackendApplication.getStaticInstance();
    private HTTPResponse addToWeeklyPlanResponse;

    /**
     * this gets any info required when loading the panel and initializes data
     * @return return json array
     */
    @SuppressWarnings("unchecked")
    @GetMapping(path="/init")
    public JSONArray init(@RequestHeader(SavourbackendApplication.USER_ID_HEADER) int UID) {
        try {

            int weekNum = getWeekNum();

            JSONObject sunday = new JSONObject();
            sunday.put("day", "SUN");
            Recipes breakfast;
            Recipes lunch;
            Recipes dinner;
            JSONArray jsonArray = new JSONArray();

            //Sunday
            List<Recipes> tmp = sortWeekDay(weekNum, 1, UID);
            breakfast = sortByCategory(tmp, "BKFT");
            lunch = sortByCategory(tmp, "LNCH");
            dinner = sortByCategory(tmp, "DNNR");
            jsonArray.add(breakfast);
            jsonArray.add(lunch);
            jsonArray.add(dinner);
            sunday.put("recipes", jsonArray);


            //Monday
            JSONObject monday = new JSONObject();
            monday.put("day", "MON");
            List<Recipes> tmp1 = sortWeekDay(weekNum, 2, UID);
            breakfast = sortByCategory(tmp1, "BKFT");
            lunch = sortByCategory(tmp1, "LNCH");
            dinner = sortByCategory(tmp1, "DNNR");
            JSONArray jsonArray2 = new JSONArray();
            jsonArray2.add(breakfast);
            jsonArray2.add(lunch);
            jsonArray2.add(dinner);
            monday.put("recipes", jsonArray2);

            //tuesday
            JSONObject tuesday = new JSONObject();
            tuesday.put("day", "TUE");
            List<Recipes> tmp2 = sortWeekDay(weekNum, 3, UID);
            breakfast = sortByCategory(tmp2, "BKFT");
            lunch = sortByCategory(tmp2, "LNCH");
            dinner = sortByCategory(tmp2, "DNNR");
            JSONArray jsonArray3 = new JSONArray();
            jsonArray3.add(breakfast);
            jsonArray3.add(lunch);
            jsonArray3.add(dinner);
            tuesday.put("recipes", jsonArray3);

            //Wednesday
            JSONObject wed = new JSONObject();
            wed.put("day", "WED");

            List<Recipes> tmp3 = sortWeekDay(weekNum, 4, UID);
            breakfast = sortByCategory(tmp3, "BKFT");
            lunch = sortByCategory(tmp3, "LNCH");
            dinner = sortByCategory(tmp3, "DNNR");
            JSONArray jsonArray4 = new JSONArray();
            jsonArray4.add(breakfast);
            jsonArray4.add(lunch);
            jsonArray4.add(dinner);
            wed.put("recipes", jsonArray4);

            //Thursday
            JSONObject thursday = new JSONObject();
            thursday.put("day", "THU");
            List<Recipes> tmp4 = sortWeekDay(weekNum, 5, UID);
            breakfast = sortByCategory(tmp4, "BKFT");
            lunch = sortByCategory(tmp4, "LNCH");
            dinner = sortByCategory(tmp4, "DNNR");
            JSONArray jsonArray5 = new JSONArray();
            jsonArray5.add(breakfast);
            jsonArray5.add(lunch);
            jsonArray5.add(dinner);
            thursday.put("recipes", jsonArray5);

            //Friday
            JSONObject friday = new JSONObject();
            friday.put("day", "FRI");

            List<Recipes> tmp5 = sortWeekDay(weekNum, 6, UID);
            breakfast = sortByCategory(tmp5, "BKFT");
            lunch = sortByCategory(tmp5, "LNCH");
            dinner = sortByCategory(tmp5, "DNNR");
            JSONArray jsonArray6 = new JSONArray();
            jsonArray6.add(breakfast);
            jsonArray6.add(lunch);
            jsonArray6.add(dinner);
            friday.put("recipes", jsonArray6);

            //Saturday
            JSONObject saturday = new JSONObject();
            saturday.put("day", "SAT");

            List<Recipes> tmp6 = sortWeekDay(weekNum, 7, UID);
            breakfast = sortByCategory(tmp6, "BKFT");
            lunch = sortByCategory(tmp6, "LNCH");
            dinner = sortByCategory(tmp6, "DNNR");
            JSONArray jsonArray7 = new JSONArray();
            jsonArray7.add(breakfast);
            jsonArray7.add(lunch);
            jsonArray7.add(dinner);
            saturday.put("recipes", jsonArray7);

            JSONArray week = new JSONArray();
            week.add(sunday);
            week.add(monday);
            week.add(tuesday);
            week.add(wed);
            week.add(thursday);
            week.add(friday);
            week.add(saturday);

            return week;
        } catch (Exception ignored) {
            return null;
        }
    }


    /**
     * This method will add a recipe to the calendar using a recipe in system
     * @param RID recipe ID
     * @param date date in format below
     *             !!!WE CAN CHANGE THIS THIS IS NOW SET IN STONE!!!!!
     *             format: "day,month,year"
     *             example: "13/03/2027"
     * @return response to client on status
     */
    @PostMapping(path="/addtoPlannerRID")
    public ResponseEntity<HTTPResponse> addCalendar(@RequestParam int RID, @RequestParam String date, @RequestHeader(SavourbackendApplication.USER_ID_HEADER) int UID) {
        try{

            Calendar cal = makeCalendar(RID, date, UID); //make a calendar object for RID and Date
            if(calendarService.spotIsAvailable(cal, UID)) //check if a spot is open for the recipe
                calendarService.saveTOCalendar(cal, UID); //if open save
            else{
                addToWeeklyPlanResponse = new HTTPResponse("calendar spot is not available");
                return new ResponseEntity<>(addToWeeklyPlanResponse, HttpStatus.NOT_ACCEPTABLE);
            }

            Optional<Recipes> r = recipeService.getRecipeById(RID);
            Recipes recipe;
            if(r.isPresent()) //check if recipe is null
               recipe = r.get();
            else{
                addToWeeklyPlanResponse = new HTTPResponse("adding recipe to calendar failed, recipe could not be retrieved, cause recipe ID did not retrieve any recipe");
                return new ResponseEntity<>(addToWeeklyPlanResponse, HttpStatus.NOT_ACCEPTABLE);
            }
                userService.linkRecipetoUser(RID, UID);
         //  }
            userService.addCalendar(cal.getCalendarID(), RID, UID);
            addToWeeklyPlanResponse = new HTTPResponse("recipe saved to calendar");
            ResponseEntity<HTTPResponse> val =  new ResponseEntity<>(addToWeeklyPlanResponse, HttpStatus.ACCEPTED);
            return val;
        } catch (Exception e) {
            HTTPResponse response = new HTTPResponse("adding recipe to calendar failed");
            return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
        }
    }


    /**
     * this method removes a calendar
     * @param RID recipe ID
     * @return response to frontend
     */
    @PostMapping(path="/removeCalendar")
    public ResponseEntity<HTTPResponse> removeCalendar(@RequestParam int RID, @RequestHeader(SavourbackendApplication.USER_ID_HEADER) int UID, @RequestParam String date){
        try{
            if(userService.removeCalendar(RID, UID, date)){
                if(!userService.unLinkRecipeToUser(RID, UID)) {
                    addToWeeklyPlanResponse = new HTTPResponse("calendar not removed properly");
                    return new ResponseEntity<>(addToWeeklyPlanResponse, HttpStatus.NOT_ACCEPTABLE);
                }
                addToWeeklyPlanResponse = new HTTPResponse("calendar removed");
                return new ResponseEntity<>(addToWeeklyPlanResponse, HttpStatus.ACCEPTED);
            }
            addToWeeklyPlanResponse = new HTTPResponse("removing calendar did not work");
            return new ResponseEntity<>(addToWeeklyPlanResponse, HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            addToWeeklyPlanResponse = new HTTPResponse("removing calendar did not work");
            return new ResponseEntity<>(addToWeeklyPlanResponse, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    /**
     * returns all calendar objects for the user
     * @return list of calendar objects
     */
    @GetMapping(path="/getCalendarAll")
    public List<Calendar> getCalendarALL(@RequestParam int UID){
        try{
            Optional<Users> u = userService.findByID(UID);
            Users user = u.get();
            return user.getUserCalendars();
        } catch (Exception e) {
            logger.log(Level.INFO, "could not get all calendar objects");
            return null;
        }
    }


     // helper methods

    /**
     * this method refreshes all the lists sorted by weeknum and daynum type
     * param: the week number of the year
     */
    public List<Recipes> sortWeekDay(int weekOyear, int dayOweek, int UID) {
        try {
            List<Calendar> recipes = userService.getUserCalendar(UID);
            ArrayList<Recipes> all = new ArrayList<>();
            for (int i =0; i<recipes.size(); i++) { //go through user calendars
                Optional<Recipes> r = recipeService.getRecipeById(recipes.get(i).getRID());
                Recipes tmpR = r.get();
                String weekDay = convertDaytoString(dayOweek);
                //if the recipe has the proper week and year
                if(recipes.get(i).getWeekOYear() == weekOyear && recipes.get(i).getDayWeek().equals(weekDay)){
                    all.add(tmpR); //add to list
                }
            }
            return all;
        } catch (Exception e) {
            logger.log(Level.INFO, "refreshPlanner method failed");
            return null;
        }
    }

    /**
     * This method makes and returns a calender object
     * @param RID recipe ID
     * @param dateInput date in dd,mm,yyyy
     * @return calendar created
     */
    private Calendar makeCalendar(int RID, String dateInput, int UID){
        try{
            Calendar calendar = new Calendar();
            Optional<Recipes> r = recipeService.getRecipeById(RID);
            if(r.isPresent()) {
                calendar.setMeal_Type(r.get().getCategory());
                calendar.setMeal_Type(r.get().getCategory());
                calendar.setRecipeID(RID);
                calendar.setUserID(UID);
            }
            else {
                throw new NullPointerException("recipe not present in repo");
            }
            //this gets the data and splits it by day, month,year
            String[] tmp = dateInput.split("/");
            calendar.setDay(tmp[0]);
            calendar.setMonth(tmp[1]);
            calendar.setYear(tmp[2]);
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date date = format.parse(dateInput);
            //get week of the year and day of week
            java.util.Calendar calJava = java.util.Calendar.getInstance();
            //year, month, day
            calJava.setTime(date);
            int day = calJava.get(java.util.Calendar.DAY_OF_WEEK);
            int weekOyear = calJava.get(java.util.Calendar.WEEK_OF_YEAR);
            //get the weekday
            String dayWeek = convertDaytoString(day);

            //set the weekday
            calendar.setDayWeek(dayWeek);
            calendar.setWeekOYear(weekOyear);
            calendar.setRecipeName(r.get().getName());
            return calendar;
        } catch (Exception e){
            return null;
        }
    }

    /**
     * this method searches all the saved recipies and sorts them by mael type
     * @param list list of all saved recipes
     * @param category type of meal Ex: lunch, dinner
     * @return recipies fitting the category description
     */
    public Recipes sortByCategory(List<Recipes> list, String category) {
        Recipes RecReturn = new Recipes();
        for (Recipes tmpRecipe : list) {
            if (tmpRecipe.getCategory().equals(category)) {
                RecReturn = tmpRecipe;
                return RecReturn;
            }
        }
        return null;
    }

    /**
     * converts that day from int to string.
     * @param day
     * @return
     */
    public static String convertDaytoString(int day) {
        String dayWeek = null;
        switch (day) {
            case 1:
                dayWeek = "SUN";
                break;
            case 2:
                dayWeek = "MON";
                break;
            case 3:
                dayWeek = "TUE";
                break;
            case 4:
                dayWeek = "WED";
                break;
            case 5:
                dayWeek = "THR";
                break;
            case 6:
                dayWeek = "FRI";
                break;
            case 7:
                dayWeek = "SAT";
                break;
            default:
                return null;
        }
        return dayWeek;
    }

    /**
     * gets the week number of the year. returns as an int
     * @return week number of year
     * @throws ParseException
     */
    public int getWeekNum() throws ParseException {
        //get the current date
        String dateStr = java.time.LocalDateTime.now().toString();
        String[] tmp = dateStr.split("-");  //this gets the data and splits it by day, month,year
        tmp[2] = tmp[2].substring(0,2);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String dateInput = tmp[2] + "/" + tmp[1] + "/" + tmp[0];
        Date date = format.parse(dateInput);
        //get week of the year and day of week
        java.util.Calendar calJava = java.util.Calendar.getInstance();
        //year, month, day
        calJava.setTime(date);
        return calJava.get(java.util.Calendar.WEEK_OF_YEAR);
    }

    /**
     * gets the day of the week. returns as an int. 1-7, SUN-Sat
     * day of week, for example monday is 2
     * @throws ParseException
     */
    public int getDayNum() throws ParseException {
        //get the current date
        String dateStr = java.time.LocalDateTime.now().toString();
        String[] tmp = dateStr.split("-");  //this gets the data and splits it by day, month,year
        tmp[2] = tmp[2].substring(0,2);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String dateInput = tmp[2] + "/" + tmp[1] + "/" + tmp[0];
        Date date = format.parse(dateInput);
        //get week of the year and day of week
        java.util.Calendar calJava = java.util.Calendar.getInstance();
        //year, month, day
        calJava.setTime(date);
        return calJava.get(java.util.Calendar.DAY_OF_WEEK);
    }

}
