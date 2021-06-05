package com.example.app.database.pojo;

public class Patient {
    private String firstName;
    private String lastName;
    private int age;
    private String city;

    public Patient(String firstName, String lastName, int age, String city){
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.city = city;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        city = city;
    }

    @Override
    public String toString(){
        return firstName + " " + lastName + " " + age + " " + city;
    }
}
