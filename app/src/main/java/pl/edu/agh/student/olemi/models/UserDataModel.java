package pl.edu.agh.student.olemi.models;

import android.content.res.Resources;

import pl.edu.agh.student.olemi.OlemiApplication;
import pl.edu.agh.student.olemi.R;

public class UserDataModel {

    private final Integer caloriesGoal;

    private final Integer carbonhydrateGoal;

    private final Integer proteinGoal;

    private final Integer fatGoal;

    private final Integer weight;

    private final Integer age;

    private final String gender;

    private final Integer height;

    private final Integer activityLevel;

    private final Double weightLossRate;

    private UserDataModel(UserDataModelBuilder builder) {
        this.weight = builder.weight;
        this.age = builder.age;
        this.gender = builder.gender;
        this.height = builder.height;
        this.activityLevel = builder.activityLevel;
        this.weightLossRate = builder.weightLossRate;

        this.caloriesGoal = builder.caloriesGoal;
        this.carbonhydrateGoal = builder.carbonhydrateGoal;
        this.fatGoal = builder.fatGoal;
        this.proteinGoal = builder.proteinGoal;
    }

    public Integer getCaloriesGoal() {
        return caloriesGoal;
    }

    public Integer getCarbonhydrateGoal() {
        return carbonhydrateGoal;
    }

    public Integer getProteinGoal() {
        return proteinGoal;
    }

    public Integer getFatGoal() {
        return fatGoal;
    }

    public Integer getWeight() {
        return weight;
    }

    public Integer getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getActivityLevel() {
        return activityLevel;
    }

    public Double getWeightLossRate() {
        return weightLossRate;
    }

    public static UserDataModelBuilder builder() {
        return new UserDataModelBuilder();
    }

    public static final class UserDataModelBuilder {
        private Integer caloriesGoal;
        private Integer carbonhydrateGoal;
        private Integer proteinGoal;
        private Integer fatGoal;
        private Integer weight;
        private Integer age;
        private String gender;
        private Integer height;
        private Integer activityLevel; //1-4
        private Double weightLossRate;


        private UserDataModelBuilder() {
        }

        public static UserDataModelBuilder anUserDataModel() {
            return new UserDataModelBuilder();
        }

        public UserDataModelBuilder weight(Integer weight) {
            this.weight = weight;
            return this;
        }

        public UserDataModelBuilder age(Integer age) {
            this.age = age;
            return this;
        }

        public UserDataModelBuilder gender(String gender) {
            this.gender = gender;
            return this;
        }

        public UserDataModelBuilder height(Integer height) {
            this.height = height;
            return this;
        }

        public UserDataModelBuilder weightLossRate(Double weightLossRate) {
            this.weightLossRate = weightLossRate;
            return this;
        }

        public UserDataModelBuilder activityLevel(Integer activityLevel) {
            this.activityLevel = activityLevel;
            return this;
        }

        public UserDataModel build() {
            calculateGoals();
            return new UserDataModel(this);
        }

        public void calculateGoals() {
            calculateCalorieGoal();
            calculateMacros();
        }

        private void calculateCalorieGoal() {
            final String maleGender = "male";
            final double BMR = gender.toLowerCase().equals(maleGender)
                    ? ((this.height * 6.25) + (this.weight * 9.99) - (this.age * 4.92) + 5)
                    : ((this.height * 6.25) + (this.weight * 9.99) - (this.age * 4.92) - 161);
            final double TDEE = BMR * activityLevelToTDEEFactor();
            final double currentGoal = TDEE - weightLossRateToCaloriesDeficit();
            this.caloriesGoal = (int) currentGoal;
        }

        private double activityLevelToTDEEFactor() {
            switch (this.activityLevel) {
                case 0:
                    return 1.2;
                case 1:
                    return 1.375;
                case 2:
                    return 1.55;
                case 3:
                    return 1.725;
                case 4:
                    return 1.9;
                default:
                    throw new IllegalArgumentException("Invorrect activity level!");
            }
        }

        private double weightLossRateToCaloriesDeficit() {
            // this method should be replaced to more accurate. Weight loss rate should be between 0.0 - 1.0 (kilograms per week)
            return this.weightLossRate * 1000;
        }

        private void calculateMacros() {
            int remainingCalories = caloriesGoal;

            proteinGoal = 2 * weight; //2g per 1kg of bodyweight
            remainingCalories -= proteinGoal * 4; //1g of proteins is 4kcal

            this.fatGoal = (int) (0.37 * this.caloriesGoal / 9); // 37% of tdee, 1g of fat - 9 kcal
            remainingCalories -= fatGoal;

            this.carbonhydrateGoal = (int) (0.475 * this.caloriesGoal / 4); // 47.5% of tdee, 1g of carb - 4 kcal
            remainingCalories -= carbonhydrateGoal;

            // TODO: implement modifying fat/carbs goal to fit calorie goal exactly
        }
    }
}
