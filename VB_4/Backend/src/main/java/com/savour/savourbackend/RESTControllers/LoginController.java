package com.savour.savourbackend.RESTControllers;

import com.savour.savourbackend.model.Users;

import com.savour.savourbackend.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.LinkedHashMap;
import java.util.List;

//login failed
import static org.springframework.http.HttpStatus.I_AM_A_TEAPOT;

//signup failed
import static org.springframework.http.HttpStatus.FORBIDDEN;


@RestController
@RequestMapping(path = "/login" )
public class LoginController {

    @Autowired
    UsersRepo usersRepo;

    @RequestMapping(value = "")
    public @ResponseBody Users login(@RequestBody LinkedHashMap json) {
        List<Users> users = usersRepo.findByUsername(json.get("username").toString());

            for (Users user : users) {
                if (user.getPassword().equals(json.get("password"))) {
                    return user;
                }
            }
        throw new ResponseStatusException(I_AM_A_TEAPOT, "Incorrect username or password");
    }

    //IF USER EXSISTS THROW ERROR and make USERNAME Unique
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public @ResponseBody Users addNewUser(@RequestBody LinkedHashMap json) {
        Users user = new Users();
        user.setCanAddRecipe(Boolean.parseBoolean(json.get("canAddRecipe").toString()));
        user.setUsername(json.get("username").toString());
        user.setPassword(json.get("password").toString());
        try {
            usersRepo.save(user);
        }
        catch(Exception e){
            throw new ResponseStatusException(FORBIDDEN, "Don't use other peoples usernames >:(");
        }
        return user;
    }

    //@RequestHeader



}
