package com.savour.savourbackend.RESTControllers;

import com.savour.savourbackend.SavourbackendApplication;
import com.savour.savourbackend.model.*;
import com.savour.savourbackend.model.Calendar;
import com.savour.savourbackend.repository.AmountsRepo;
import com.savour.savourbackend.repository.IngredientsRepo;
import com.savour.savourbackend.services.AmountsService;
import com.savour.savourbackend.services.CalendarService;
import com.savour.savourbackend.services.IngredientsService;
import com.savour.savourbackend.services.UserService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/grocery" )
public class GroceryController {

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
    @Autowired
    CalendarService calendarService;

    //Logger for debugging
    private static Logger logger = SavourbackendApplication.getStaticInstance();
    private HTTPResponse groceryResponse;

    /**
     * this gets any info required when loading the panel
     * @return
     */
    @GetMapping(path="/init")
    @SuppressWarnings("unchecked")
    public JSONArray init(@RequestHeader(SavourbackendApplication.USER_ID_HEADER) int UID) {
        try {

            int weekNum = getWeekNum();

            JSONObject sunday = new JSONObject();
            sunday.put("day", "SUN");
            List<Amounts> breakfast;
            List<Amounts> lunch;
            List<Amounts> dinner;
            JSONArray jsonArray = new JSONArray();

            //Sunday
            List<Amounts> tmp = sortWeekDay(weekNum, 1, UID);
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
            List<Amounts> tmp1 = sortWeekDay(weekNum, 2, UID);
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
            List<Amounts> tmp2 = sortWeekDay(weekNum, 3, UID);
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

            List<Amounts> tmp3 = sortWeekDay(weekNum, 4, UID);
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
            List<Amounts> tmp4 = sortWeekDay(weekNum, 5, UID);
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

            List<Amounts> tmp5 = sortWeekDay(weekNum, 6, UID);
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

            List<Amounts> tmp6 = sortWeekDay(weekNum, 7, UID);
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
     * this method searches all the saved recipies and sorts them by mael type
     * @param list list of all saved recipes
     * @param category type of meal Ex: lunch, dinner
     * @return recipies fitting the category description
     */
    public List<Amounts> sortByCategory(List<Amounts> list, String category) {
        try{
            List<Amounts> listRet = new ArrayList<>();
            for (Amounts tmp : list) {
                if (tmp.recipe.getCategory().equals(category)) {
                    listRet.add(tmp);
                }
            }
            return listRet;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * this method refreshes all the lists sorted by weeknum and daynum type
     * param: the week number of the year
     */
    public List<Amounts> sortWeekDay(int weekOyear, int dayOweek, int UID) {
        try {
            Optional<Users> opt = userService.findByID(UID);
            Users user = opt.get();
            List<Amounts> amounts = userService.getLinkedAmounts(UID);
            ArrayList<Amounts> all = new ArrayList<>();
            for (int i =0; i<amounts.size(); i++) { //go through user calendars
                Recipes tmpR = amounts.get(i).recipe;
                if(tmpR != null){
                    List<Calendar> calList = userService.getCalendars(tmpR.getId(), UID);
                    for(Calendar cal: calList) {
                        //if the proper amounts
                        if(cal.getWeekOYear() == weekOyear && cal.getDayWeek().equals(convertDaytoString(dayOweek)) && tmpR.getId() == cal.getRID()){
                            if(!all.contains(amounts.get(i)))
                                all.add(amounts.get(i));
                        }
                    }
                }
            }
            return all;
        } catch (Exception e) {
            logger.log(Level.INFO, "refreshPlanner method failed");
            return null;
        }
    }

    /**
     * converts that day from int to string.
     * @param day
     * @return
     */
    public String convertDaytoString(int day) {
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

}
