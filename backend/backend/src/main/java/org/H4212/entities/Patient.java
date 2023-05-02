package org.H4212.entities;

import javax.persistence.Column;

public class Patient extends Person{

    @Column(name="Age", nullable = true)
    private int age;

    @Column(name="Weight", nullable = true)
    private int weight;

    @Column(name="Sex", nullable = true)
    private boolean sex;

    @Column(name="Height", nullable = true)
    private int height;

    public Patient(String lastName, String firstName, int age, int weight, boolean sex, int height) {
        super(lastName, firstName);
        this.age = age;
        this.weight = weight;
        this.sex = sex;
        this.height = height;
    }

    public Patient(String lastName, String firstName) {
        super(lastName, firstName);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "age=" + age +
                ", weight=" + weight +
                ", sex=" + sex +
                ", height=" + height +
                "} " + super.toString();
    }
}
