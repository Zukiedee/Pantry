package com.rosegold.pantry.Model;

/**
 * Model class for to do tasks
 */
public class PantryModel {
    private int ID, Price;
    private String name;

    /**
     * Empty constructor method
     */
    public PantryModel (){
        this.Price = 0;
        this.name = "no name";
    }

    /**
     * Constructor method
     * Initializes all class variables
     */
    public PantryModel(int id, String name, int price){
        this.ID = id;
        this.name = name;
        this.Price = price;
    }

    /**
     * Sets task ID variable
     * @param ID id from database
     */
    public void setId(int ID) {
        this.ID = ID;
    }

    /**
     * Sets checked status variable
     * @param price checked/done status
     */
    public void setPrice(int price) {
        this.Price = price;
    }

    /**
     * Sets the task  variable
     * @param name task
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns task ID
     * @return task ID in DB
     */
    public int getId() {
        return ID;
    }

    /**
     * Returns task status
     * @return checked status
     */
    public int getPrice() {
        return Price;
    }

    /**
     * Returns task
     * @return task
     */
    public String getName() {
        return name;
    }
}
