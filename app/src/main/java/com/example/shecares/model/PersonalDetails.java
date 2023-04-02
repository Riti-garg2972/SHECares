package com.example.shecares.model;

//TODO: To be implemented , take these details from user once they open app
public class PersonalDetails {
    private int age;
    private int Maristatus;
    private float height;
    private float weight;
    private int Numberpreg;
    private int BMI;

    public int getBMI() {
        return BMI;
    }

    public void setBMI(int BMI) {
        this.BMI = BMI;
    }

    public PersonalDetails() {
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getMaristatus() {
        return Maristatus;
    }

    public void setMaristatus(int maristatus) {
        Maristatus = maristatus;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getNumberpreg() {
        return Numberpreg;
    }

    public void setNumberpreg(int numberpreg) {
        Numberpreg = numberpreg;
    }
}
