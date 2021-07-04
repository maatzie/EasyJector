package com.example.app.database.pojo;

public class Bottle {
    private int ID;
    private String bottleID;
    private String name;
    private int volume;
    private int quantity;
    private int isDeleted;

    public Bottle(int ID, String bottleID, String name, int volume, int quantity){
        this.ID = ID;
        this.bottleID = bottleID;
        this.name = name;
        this.volume = volume;
        this.quantity = quantity;
        this.isDeleted = 0;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getBottleID() {
        return bottleID;
    }

    public void setBottleID(String bottleID) {
        this.bottleID = bottleID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString(){
        return bottleID + " " + name;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }
}
