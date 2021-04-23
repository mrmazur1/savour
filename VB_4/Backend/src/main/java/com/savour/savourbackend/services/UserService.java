package com.savour.savourbackend.services;

import com.savour.savourbackend.SavourbackendApplication;
import com.savour.savourbackend.model.Amounts;
import com.savour.savourbackend.model.Calendar;
import com.savour.savourbackend.model.Recipes;
import com.savour.savourbackend.model.Users;
import com.savour.savourbackend.repository.AmountsRepo;
import com.savour.savourbackend.repository.CalendarRepo;
import com.savour.savourbackend.repository.RecipesRepo;
import com.savour.savourbackend.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


@Service
public class UserService {
    @Autowired
    UsersRepo usersRepo;
    @Autowired
    RecipesRepo recipesRepo;
    @Autowired
    CalendarRepo calendarRepo;
    @Autowired
    AmountsRepo amountsRepo;
    @Autowired
    CalendarService calendarService;

    private static Logger logger = SavourbackendApplication.getStaticInstance();

    /**
     * saves a user to the system
     * @param user user object
     */
    public void registerUser(Users user){
        usersRepo.save(user);
    }

    /**
     * gets all the users
     * @return
     */
    public Iterable<Users> getAllUsers(){
        return usersRepo.findAll();
    }

    /**
     * returns a user by their ID
     * @param id
     * @return
     */
    public Optional<Users> findByID(int id) {return usersRepo.findById(id);}

    /**
     * this will favorite a recipe to a user
     * @param RID recipe ID
     * @param UID user ID
     */
    public boolean linkRecipetoUser(int RID, int UID){
        Optional<Users> u = usersRepo.findById(UID);
        if(u.isPresent()){
            Users user = u.get();
            Optional<Recipes> r = recipesRepo.findById(RID);
            Recipes rec = r.get();
            List<Amounts> amounts = amountsRepo.findByRecipe_Id(RID);
            for (Amounts i : amounts) {
                if (i != null) {
                   // if (!user.userCalendarAmounts.contains(i))
                        user.userCalendarAmounts.add(i);
                }
            }
            usersRepo.save(user);
            return true;

        }
        return false;
    }

    /**
     * method returns a boolean value indicating if the recipe was unfavored successfully
     * @param RID recipe ID
     * @param UID User ID
     * @return true if removed, false if failed removal
     */
    public boolean unLinkRecipeToUser(int RID, int UID) {
        try {
            Users user = new Users();
            Optional<Users> u = usersRepo.findById(UID);
            user = u.get();
            Optional<Recipes> r = recipesRepo.findById(RID);

            List<Amounts> amounts = amountsRepo.findByRecipe_Id(RID);
            for( Amounts i : amounts) {
                List<Amounts> userCalsAmounts = user.userCalendarAmounts;
                List<Amounts> tmp = new ArrayList<>();
                for(Amounts a: userCalsAmounts){
                    tmp.add(a);
                }

                for(Amounts j : tmp) {
                    if(j.equals(i)) {
                        user.userCalendarAmounts.remove(i);
                        break;
                    }
                }
            }
            usersRepo.save(user);
            return true;
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * adds a calendar to the relational table between the User and Calendar repos
     * @param CID calendar ID
     * @param UID user ID
     */
    public boolean addCalendar(int CID, int RID, int UID){
        try{
            Optional<Users> u = usersRepo.findById(UID);
            if(u.isPresent()){
                Users user = u.get();
                Optional<Calendar> c = calendarRepo.findById(CID);
                Calendar cal = c.get();

                //check if calendar object exists for that recipe/user
                for(Calendar i : user.userCalendars) {
                    if(i.getUserID() == UID) {
                        if(cal.getDay().equals(i.getDay()) && cal.getMonth().equals(i.getMonth()) && cal.getYear().equals(i.getYear()) && cal.getRecipeName().equals(i.getRecipeName())) {
                            // this means that they tried to add the same recipe at the same time twice
                            //do nothing
                            return false;
                        }
                    }
                }
                user.userCalendars.add(cal);
                usersRepo.save(user);
                return true;
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
            return false;
        }
        return false;
    }

    /**
     * method removes a calendar from the relational table between
     * @param RID calendar ID
     * @param UID user ID
     * @return true if removed, false if there was a problem removing
     */
    public boolean removeCalendar(int RID, int UID, String day) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            formatter.format(date);
            java.util.Calendar calJava = java.util.Calendar.getInstance();
            //year, month, day
            calJava.setTime(date);
            int weekOyear = calJava.get(java.util.Calendar.WEEK_OF_YEAR);

            Optional<Users> u = usersRepo.findById(UID);
            Users user = u.get();
            //get user calendars
            List<Calendar> userCalenders = user.userCalendars;
            Calendar tmp = new Calendar();
            for (Calendar c: userCalenders) {
                //if it is this calendar object
                if (c.getDayWeek().equals(day) && c.getWeekOYear() == weekOyear && RID == c.getRID()){
                    user.userCalendars.remove(c);
                    usersRepo.save(user);
                    calendarRepo.delete(c);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
            return false;
        }
    }

    /**
     * this method checks if a recipe is stored in one of the calendar objects
     * @param RID recipe ID to check
     * @param UID user ID to check
     * @return 0 if not in repo, otherwise it returns the ID of the calendar
     */
    public int checkRecipeInCalendar(int RID, int UID){
        try{
            Optional<Users> u = usersRepo.findById(UID);
            Users user = u.get();
            List<Calendar> userCal = user.userCalendars;
            for(Calendar c: userCal) {
                if (c.getRID() == RID) {
                    return c.getCalendarID();
                }
            }
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }

    public List<Calendar> getCalendars(int RID, int UID){
        try{
            Optional<Users> u = usersRepo.findById(UID);
            Users user = u.get();
            List<Calendar> userCal = user.userCalendars;
            List<Calendar> ret = new ArrayList<>();
            for(Calendar c: userCal) {
                if (c.getRID() == RID) {
                    //return c.getCalendarID();
                    ret.add(c);
                }
            }
            return ret;
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * this method will get all the recipes linked to a user
     * @param UID the user ID
     * @return list of recipes linked to user
     */
    public List<Recipes> getUserCalendarRecipes(int UID) {
        try{
            Optional<Users> u = usersRepo.findById(UID);
            Users user = u.get();
            if(user == null) {
                logger.log(Level.WARNING, "user not in system");
                return null;
            }
           // List<Recipes> rec = user.getUserCalendarRecipes();
           // return rec;
            return null;
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
            return null;
        }
    }

    public List<Calendar> getUserCalendar(int userID) {
        try {
            Optional<Users> u = usersRepo.findById(userID);
            Users user = u.get();
            List<Calendar> list = user.userCalendars;
            return list;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Amounts> getLinkedAmounts(int UID) {
        try {
            Optional<Users> u = usersRepo.findById(UID);
            Users user = u.get();
            List<Amounts> list = user.userCalendarAmounts;
            return list;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
