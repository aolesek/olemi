package pl.edu.agh.student.olemi.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import pl.edu.agh.student.olemi.R;
import pl.edu.agh.student.olemi.model_maciek.ProductModel;

public class SearchMealAdapter extends ArrayAdapter<ProductModel> implements Filterable {

    private Context mContext;
    private List<ProductModel> mealList;
    private MealFilter mealFilter;
    private List<ProductModel> filteredList;

    public SearchMealAdapter(@NonNull Context context, List<ProductModel> list) {
        super(context, 0, list);
        this.mContext = context;
        this.mealList = list;
        this.filteredList = list;

        getFilter();
    }

    /**
     * Get size of user list
     * @return userList size
     */
    @Override
    public int getCount() {
        if(filteredList != null)
            return filteredList.size();
        else
            return 0;
    }

    /**
     * Get specific item from user list
     * @param i item index
     * @return list item
     */
    @Override
    public ProductModel getItem(int i) {
        return filteredList.get(i);
    }

    /**
     * Get user list item id
     * @param i item index
     * @return current item id
     */
    @Override
    public long getItemId(int i) {
        return i;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.search_list_item,parent,false);

        ProductModel productModel = mealList.get(position);

        TextView list_search_name = (TextView) listItem.findViewById(R.id.list_search_name);
        list_search_name.setText(productModel.getName());

        return listItem;
    }

    @Override
    public Filter getFilter() {
        if (mealFilter == null) {
            mealFilter = new MealFilter();
        }
        return mealFilter;
    }

    private class MealFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>0) {
                ArrayList<ProductModel> tempList = new ArrayList<ProductModel>();

                // search content in friend list
                for (ProductModel productModel : mealList) {
                    if (productModel.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(productModel);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = mealList.size();
                filterResults.values = mealList;
            }

            return filterResults;
        }

        /**
         * Notify about filtered list to ui
         * @param constraint text
         * @param results filtered result
         */
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (List<ProductModel>) results.values;
            clear();
            addAll(filteredList);
            notifyDataSetChanged();
        }
    }
}
