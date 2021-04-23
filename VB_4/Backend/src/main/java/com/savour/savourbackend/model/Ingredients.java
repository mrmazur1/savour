package com.savour.savourbackend.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ingredients")
public class Ingredients implements Serializable {

    @Id
    //increments id value
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column(nullable = true, length = 100)
    private String ingredientName;

    @Column(nullable = true, length = 100)
    private String ingredientUnit;

    @Column(nullable = true, length = 100)
    private String ingredientType;



    /**
     * GETS
     */
    public void setName(String name){
        this.ingredientName = name;
    }

    public void setUnit(String unit){
        this.ingredientUnit = unit;
    }

    public void setType(String ingredientType) {
        this.ingredientType = ingredientType;
    }

    /**
     * SETS
     * these determine the JSON key
     */
    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.ingredientName;
    }

    public String getUnit(){
        return this.ingredientUnit;
    }

    public String getType(){
        return this.ingredientType;
    }
}

