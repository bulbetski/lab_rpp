package com.example.laba_8;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AddressFromAutoCompleteAdapter extends BaseAdapter implements Filterable {

    private final Context context;
    private List<String> addresses;

    public AddressFromAutoCompleteAdapter(Context context) {
        this.context = context;
        addresses = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return addresses.size();
    }

    @Override
    public Object getItem(int position) {
        return addresses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.simple_dropdown, parent, false);
        }
        String name = addresses.get(position);
        ((TextView) convertView.findViewById(R.id.txt2)).setText(name);
        return convertView;
    }


    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(final CharSequence constraint) {
                FilterResults results = new FilterResults();
                if(constraint != null) {
                    final List<String> names = findResults(constraint.toString());
                    results.values = names;
                    results.count = names.size();
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if(results != null && results.count > 0) {
                    addresses = (List<String>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }

    private List<String> findResults(String name) {
        AddressService service = new AddressService();
        return service.findResults(name);
    }
}
