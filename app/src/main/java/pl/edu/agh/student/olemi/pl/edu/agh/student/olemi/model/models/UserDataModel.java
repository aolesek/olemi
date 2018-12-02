package pl.edu.agh.student.olemi.pl.edu.agh.student.olemi.model.models;

public class UserDataModel {

    private Integer caloriesGoal;

    private Integer carbonhydrateGoal;

    private Integer proteinGoal;

    private Integer fatGoal;

    private Double weight;

    private Double age;

    private String gender;

    private Double height;

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

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getAge() {
        return age;
    }

    public void setAge(Double age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Integer getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(Integer activityLevel) {
        this.activityLevel = activityLevel;
    }
}
