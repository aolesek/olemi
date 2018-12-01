package pl.edu.agh.student.olemi.helpers;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.student.olemi.R;
import pl.edu.agh.student.olemi.model.ProductModel;

public class MealAdapter extends ArrayAdapter<ProductModel> {

    private Context mContext;
    private List<ProductModel> mealList = new ArrayList<>();

    public MealAdapter(@NonNull Context context, List<ProductModel> list) {
        super(context, 0, list);
        mContext = context;
        mealList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);

        ProductModel productModel = mealList.get(position);

        TextView list_meal_name = (TextView) listItem.findViewById(R.id.list_meal_name);
        list_meal_name.setText(productModel.getName());

        TextView list_meal_info_carbs = (TextView) listItem.findViewById(R.id.list_meal_info_carbs);
        list_meal_info_carbs.setText("C: "+productModel.getNutrients().getCarbs().toString());

        TextView list_meal_info_protein = (TextView) listItem.findViewById(R.id.list_meal_info_protein);
        list_meal_info_protein.setText("P: "+productModel.getNutrients().getProtein().toString());

        TextView list_meal_info_fat = (TextView) listItem.findViewById(R.id.list_meal_info_fat);
        list_meal_info_fat.setText("F: "+productModel.getNutrients().getFat().toString());

        TextView list_meal_info_calories = (TextView) listItem.findViewById(R.id.list_meal_info_calories);
        list_meal_info_calories.setText("Cal: "+productModel.getNutrients().getCalories().toString());
        return listItem;
    }
}
