package com.example.myapplication;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.CHI_TIEU;

import java.util.ArrayList;

public class TargetAdapter extends ArrayAdapter implements Filterable {
    Activity context = null;
    int layoutID;
    ArrayList<CHI_TIEU> list = null;
    ArrayList<CHI_TIEU> filter_list = null;


    public TargetAdapter(Activity context, int layoutID, ArrayList<CHI_TIEU> objects) {
        super(context, layoutID, objects);
        this.context = context;
        this.layoutID = layoutID;
        this.list = objects;
        this.filter_list = objects;
    }
    //For this helper method, return based on filteredData
    public int getCount()
    {
        return filter_list.size();
    }

    //This should return a data object, not an int
    public Object getItem(int position)
    {
        return filter_list.get(position);
    }

    public long getItemId(int position)
    {
        return position;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(layoutID,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.txtTenNganh = (TextView) convertView.findViewById(R.id.txtTenNganh);
            viewHolder.txtChiTieu = (TextView) convertView.findViewById(R.id.txtChiTieu);
            viewHolder.txtNamHoc = (TextView) convertView.findViewById(R.id.txtNamHoc);
            convertView.setTag(viewHolder);

        }else
            viewHolder = (ViewHolder)convertView.getTag();

        CHI_TIEU gv = filter_list.get(position);
        viewHolder.txtTenNganh.setText(gv.getTenNganh()+"");
        viewHolder.txtChiTieu.setText(gv.getchiTieu()+"");
        viewHolder.txtNamHoc.setText(gv.getnamHoc() + "");
        return convertView;
    }
    class ViewHolder{
        TextView txtTenNganh, txtChiTieu, txtNamHoc;
    }

    @Override
    public Filter getFilter()
    {
        return new Filter()
        {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence)
            {
                Log.i("MyAdapter", "start performFiltering");
                FilterResults results = new FilterResults();

                //If there's nothing to filter on, return the original data for your list
                if(charSequence == null || charSequence.length() == 0)
                {
                    results.values = list;
                    results.count = list.size();
                }
                else
                {
                    ArrayList<CHI_TIEU> filterResultsData = new ArrayList<CHI_TIEU>();
                    String searchStr = charSequence.toString().toLowerCase();
                    Log.i("MyAdapter", "searchStr: " + searchStr);
                    for(CHI_TIEU data : list)
                    {
                        //In this loop, you'll filter through originalData and compare each item to charSequence.
                        //If you find a match, add it to your new ArrayList
                        String currentTenNganh = data.getTenNganh().toLowerCase();
                        Log.i("MyAdapter", "currentTenNganh: " + currentTenNganh);
                        if(currentTenNganh.contains(searchStr))
                        {
                            Log.i("MyAdapter", currentTenNganh + " is matched");
                            filterResultsData.add(data);
                        }
                    }

                    results.values = filterResultsData;
                    results.count = filterResultsData.size();
                }
                Log.i("MyAdapter", "end performFiltering");
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults)
            {
                filter_list = (ArrayList<CHI_TIEU>)filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
