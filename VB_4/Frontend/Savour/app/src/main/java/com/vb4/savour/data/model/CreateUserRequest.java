package com.vb4.savour.data.model;

import java.io.Serializable;

public class CreateUserRequest implements Serializable {
    public String username;
    public String password;
    public String canAddRecipe;
}
