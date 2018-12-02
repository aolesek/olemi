package pl.edu.agh.student.olemi.models;

public class UserDataModel {

    private Integer caloriesGoal;

    private Integer carbonhydrateGoal;

    private Integer proteinGoal;

    private Integer fatGoal;

    private Integer weight;

    private Integer age;

    private String gender;

    private Integer height;

    private Integer activityLevel; //1-4

    public Integer getCaloriesGoal() {
        return caloriesGoal;
    }

    public void setCaloriesGoal(Integer caloriesGoal) {
        this.caloriesGoal = caloriesGoal;
    }

    public Integer getCarbonhydrateGoal() {
        return carbonhydrateGoal;
    }

    public void setCarbonhydrateGoal(Integer carbonhydrateGoal) {
        this.carbonhydrateGoal = carbonhydrateGoal;
    }

    public Integer getProteinGoal() {
        return proteinGoal;
    }

    public void setProteinGoal(Integer proteinGoal) {
        this.proteinGoal = proteinGoal;
    }

    public Integer getFatGoal() {
        return fatGoal;
    }

    public void setFatGoal(Integer fatGoal) {
        this.fatGoal = fatGoal;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(Integer activityLevel) {
        this.activityLevel = activityLevel;
    }

}
