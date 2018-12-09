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
}
