package com.savour.savourbackend.repository;

import org.springframework.data.repository.CrudRepository;

import com.savour.savourbackend.model.Users;
import java.util.List;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UsersRepo extends CrudRepository<Users, Integer> {
    //projection from users entity
    List<Users.ProfilePageData> findDataById(int id);

    List<Users> findByUsername(String password);
}
