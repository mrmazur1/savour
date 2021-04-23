package com.savour.savourbackend.services;

import com.savour.savourbackend.SavourbackendApplication;
import com.savour.savourbackend.model.Amounts;
import com.savour.savourbackend.model.Calendar;
import com.savour.savourbackend.model.Users;
import com.savour.savourbackend.repository.CalendarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CalendarService {

    @Autowired
    UserService userService;
    @Autowired
    CalendarRepo calendarRepo;
    @Autowired
    RecipeService recipeService;

    private static Logger logger = SavourbackendApplication.getStaticInstance();

    /**
     * returns all calendars
     * @return
     */
    public Iterable<Calendar> getAll() {
        return calendarRepo.findAll();
    }

    /**
     * method returns the recipe ID stored in the calendar object
     * @param ID calendar ID
     * @return
     */
    public Optional<Integer> getRecipeID(int ID){
        Optional<Calendar> c = calendarRepo.findById(ID);
        Optional<Integer> rid;
        if(c.isPresent())
            rid = Optional.of(c.get().getRID());
        else
            rid = Optional.empty();
        return rid;
    }

    /**
     * method returns the user ID
     * @param ID calendar ID
     * @return
     */
    public Optional<Integer> getUserID(int ID){
        Optional<Calendar> c = calendarRepo.findById(ID);
        Optional<Integer> uid;
        if(c.isPresent())
            uid = Optional.of(c.get().getUserID());
        else
            uid = Optional.empty();
        return uid;
    }

    /*
    method saves a calender object to the repository
     */
    public boolean saveTOCalendar(Calendar calendar, int UID) {
        try{
            calendarRepo.save(calendar);
            Optional<Users> t = userService.findByID(UID);
            Users user = t.get();

            return true;
        } catch (Exception e){
            return false;
        }
    }

    /**
     * method taked calendar ID and removes the calender object from repo
     * @param CID
     * @return
     */
    public boolean removeFromCalendar(int CID) {
        try{
            Optional<Calendar> c = calendarRepo.findById(CID);
            calendarRepo.delete(c.get());
            return true;
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
            return false;
        }
    }

    public boolean spotIsAvailable(Calendar cal, int UID) {
        try {
            List<Calendar> calendars = userService.getUserCalendar(UID);
            if(calendars == null)
                return true;
            for(Calendar c : calendars){
                if (c.getMeal_Type().equals(cal.getMeal_Type()) && c.getWeekOYear() == cal.getWeekOYear() && c.getDayWeek().equals(cal.getDayWeek())){
                    return false; //calendar spot already filled up
                }
            }
            return true;
        } catch (Exception c) {
            c.printStackTrace();
            return false;
        }
    }

    public Calendar findByID(int ID){
        try{
            Optional<Calendar> opt = calendarRepo.findById(ID);
            return opt.get();
        } catch (Exception e) {
            return null;
        }
    }

    public int numAmountUsages(Amounts amount, Users user){
        List<Calendar> cals = user.userCalendars;
        int count = 0;
        for(Calendar c: cals){
            if(c.getRID() == amount.recipe.getId())
                //TODO
                count++;
        }
        return count;
    }


}
