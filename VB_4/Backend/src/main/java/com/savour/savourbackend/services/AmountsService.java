package com.savour.savourbackend.services;

import com.savour.savourbackend.model.Amounts;
import com.savour.savourbackend.model.Users;
import com.savour.savourbackend.repository.AmountsRepo;
import com.savour.savourbackend.repository.IngredientsRepo;
import com.savour.savourbackend.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AmountsService {
    @Autowired
    IngredientsRepo ingredientsRepo;
    @Autowired
    UsersRepo usersRepo;
    @Autowired
    AmountsRepo amountsRepo;

    public boolean addIngredientsToUserStock(int UID, int AID){
        try{
            Optional<Users> opt = usersRepo.findById(UID);
            Users users = opt.get();
            Optional<Amounts> optionalAmounts = amountsRepo.findById(AID);
            Amounts amounts = optionalAmounts.get();
            users.userStockAmounts.add(amounts);
            usersRepo.save(users);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean addIngredientstoFavorites(int UID, int AID){
        try{
            Optional<Users> opt = usersRepo.findById(UID);
            Users users = opt.get();
            Optional<Amounts> optionalAmounts = amountsRepo.findById(AID);
            Amounts amounts = optionalAmounts.get();
            users.userCalendarAmounts.add(amounts);
            usersRepo.save(users);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean removeIngredientsFromUserStock(int UID, int AID) {
        try{
            Optional<Users> opt = usersRepo.findById(UID);
            Users users = opt.get();
            Optional<Amounts> optional = amountsRepo.findById(AID);
            Amounts amounts = optional.get();
            users.userStockAmounts.remove(amounts);
            usersRepo.save(users);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean removeIngredientsFromFavorites(int UID, int AID) {
        try{
            Optional<Users> opt = usersRepo.findById(UID);
            Users users = opt.get();
            Optional<Amounts> optional = amountsRepo.findById(AID);
            Amounts amounts = optional.get();
            users.userCalendarAmounts.remove(amounts);
            usersRepo.save(users);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Amounts> getUserStock(int UID){
        try{
            Optional<Users> opt = usersRepo.findById(UID);
            Users users = opt.get();
            return users.userStockAmounts;
        } catch (Exception e) {
            return null;
        }
    }

    boolean addtoRepo(Amounts amount){
        try{
            amountsRepo.save(amount);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
