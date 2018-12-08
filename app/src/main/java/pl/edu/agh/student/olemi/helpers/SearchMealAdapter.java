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
import pl.edu.agh.student.olemi.models.ProductModel;

public class SearchMealAdapter extends ArrayAdapter<ProductModel> implements Filterable {

    private ArrayList<ProductModel> fullList;
    private ArrayList<ProductModel> mOriginalValues;
    private ArrayFilter mFilter;

    public SearchMealAdapter(Context context, int resource, List<ProductModel> objects) {
        super(context, resource, objects);
        fullList = (ArrayList<ProductModel>) objects;
        mOriginalValues = new ArrayList<ProductModel>(fullList);

    }

    @Override
    public void add(ProductModel object) {
        super.add(object);
        fullList.add(object);
        mOriginalValues.add(object);
    }

    @Override
    public void clear() {
        super.clear();
        fullList.clear();
        mOriginalValues.clear();
    }

    @Override
    public int getCount() {
        return fullList.size();
    }

    @Override
    public ProductModel getItem(int position) {
        return fullList.get(position);
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.search_list_item,parent,false);

        ProductModel productModel = getItem(position);

        TextView list_search_name = (TextView) listItem.findViewById(R.id.list_search_name);
        list_search_name.setText(productModel.getName());

        return listItem;
    }


    private class ArrayFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            ArrayList<ProductModel> list =  new ArrayList<ProductModel>();

            if (mOriginalValues == null) {
                mOriginalValues = new ArrayList<ProductModel>(fullList);
            }

            for (ProductModel item : mOriginalValues) {
                if (item.getName().toLowerCase().contains(prefix.toString().toLowerCase())) {
                    list.add(item);
                }
            }

            results.values = list;
            results.count = list.size();
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            if (results.values != null) {
                fullList = (ArrayList<ProductModel>) results.values;
            } else {
                fullList = new ArrayList<ProductModel>();
            }
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }

 /*   private Context mContext;
    private List<ProductModel> mealList;
    private List<ProductModel> origList;
    //private MealFilter mealFilter;
    private List<ProductModel> filteredList;

    public SearchMealAdapter(@NonNull Context context, List<ProductModel> list) {
        super(context, 0, list);
        this.mContext = context;
        this.mealList = list;
        this.origList = new ArrayList<>(list);
        this.filteredList = list;

    }

    *//**
     * Get size of user list
     * @return userList size
     *//*
    @Override
    public void clear() {
        super.clear();
        filteredList.clear();

    }

    @Override
    public int getCount() {
            return filteredList.size();
    }

    *//**
     * Get specific item from user list
     * @param i item index
     * @return list item
     *//*
    @Override
    public ProductModel getItem(int i) {
        return filteredList.get(i);
    }

    *//**
     * Get user list item id
     * @param i item index
     * @return current item id
     *//*
    @Override
    public long getItemId(int i) {
        return i;
    }




    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();

                if(mealList == null){

                }

                if (constraint!=null && constraint.length()>0) {
                    ArrayList<ProductModel> tempList = new ArrayList<>();

                    // search content in friend list
                    for (ProductModel productModel : mealList) {
                        if (productModel.getName().toLowerCase().startsWith(constraint.toString())) {
                            tempList.add(productModel);
                            System.out.println(productModel.getName());
                        }
                    }

                    filterResults.values = tempList;
                    filterResults.count = tempList.size();
                } else {
                    filterResults.values = mealList;
                    filterResults.count = mealList.size();
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList = (ArrayList<ProductModel>) results.values;
                if (results.count > 0) {
                    System.out.println(mealList.size() + " " + origList.size());

                    clear();
                    System.out.println(mealList.size() + " " + origList.size());
                    addAll(filteredList);
                    SearchMealAdapter.this.notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
    }*/

//    private class MealFilter extends Filter {
//
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//
//        }
//
//        /**
//         * Notify about filtered list to ui
//         * @param constraint text
//         * @param results filtered result
//         */
//        @SuppressWarnings("unchecked")
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//
//        }
//    }
}
