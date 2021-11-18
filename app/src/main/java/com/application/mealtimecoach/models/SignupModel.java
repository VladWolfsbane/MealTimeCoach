package com.application.mealtimecoach.models;

public class SignupModel {

    private String name;
    private String diet;
    private String ageGroup;
    private String goal;

    public String getName() {
        return name;
    }

    public String getDiet() {
        return diet;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public String getGoal() {
        return goal;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }
}
