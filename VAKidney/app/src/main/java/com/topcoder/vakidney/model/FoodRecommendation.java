package com.topcoder.vakidney.model;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Abinash Neupane on 2/7/2018.
 * This model class is used to store Food Recommendation
 */

public class FoodRecommendation extends SugarRecord<FoodRecommendation>
        implements Serializable {

    public static final int TYPE_UNSAFE = 0x00000001;
    public static final int TYPE_GOOD = 0x00000002;

    private String name;
    private String desc;
    private String ndbno;
    private double amount;
    private String unit;
    private int type;
    private String nutritionArray;

    public FoodRecommendation() {
    }

    public FoodRecommendation(
            String name,
            String desc,
            String ndbno,
            double amount,
            String unit,
            int type,
            String nutritionArray) {
        this.name = name;
        this.desc = desc;
        this.ndbno = ndbno;
        this.amount = amount;
        this.unit = unit;
        this.type = type;
        this.nutritionArray = nutritionArray;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getNdbno() {
        return ndbno;
    }

    public void setNdbno(String ndbno) {
        this.ndbno = ndbno;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNutritionArray() {
        return nutritionArray;
    }

    public void setNutritionArray(String nutritionArray) {
        this.nutritionArray = nutritionArray;
    }

    public static List<FoodRecommendation> getByName(String name) {
        return FoodRecommendation.find(FoodRecommendation.class, "name = ?", name);
    }

    public static List<FoodRecommendation> getUnsafe() {
        return FoodRecommendation.find(
                FoodRecommendation.class,
                "type = ?",
                String.valueOf(TYPE_UNSAFE));
    }

    public static List<FoodRecommendation> getGood() {
        return FoodRecommendation.find(
                FoodRecommendation.class,
                "type = ?",
                String.valueOf(TYPE_GOOD));
    }

    public static void removeUnlinkFoodRecommendation() {
        Iterator it = FoodRecommendation.findAll(FoodRecommendation.class);
        Iterator itMeal = Meal.findAll(Meal.class);
        List<Meal> mealList = new ArrayList<Meal>();

        while(itMeal.hasNext()) {
            mealList.add((Meal)itMeal.next());
        }
        while(it.hasNext()) {
            FoodRecommendation foodRecommenndation = (FoodRecommendation)it.next();
            String nameOfFoodRecommenndation = foodRecommenndation.getName();

            boolean isExistMeal = false;
            int len = mealList.size();
            for (int i = 0; i < len; i++) {
                Meal meal = mealList.get(i);
                for (int j = 0; j < meal.getMealDrugs().size(); j++) {
                    MealDrug mealDrug = meal.getMealDrugs().get(j);
                    String mealDrugName = "Reduce " + mealDrug.getName();
                    if (mealDrugName.equals(nameOfFoodRecommenndation)) {
                        isExistMeal = true;
                        break;
                    }
                }
                if (isExistMeal) {
                    break;
                }
            }
            if (!isExistMeal) {
                foodRecommenndation.delete();
            }
        }
    }
}
