package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ClassAdapter extends ArrayAdapter implements Filterable {
    Activity context = null;
    int layoutID;
    ArrayList<Lop> list = null;
    ArrayList<Lop> filter_list = null;

    public ClassAdapter(Activity context, int layoutID, ArrayList<Lop> objects) {
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
            viewHolder.txtMaLop = (TextView) convertView.findViewById(R.id.txtMaLop);
            viewHolder.txtTenLop = (TextView) convertView.findViewById(R.id.txtTenLop);
            viewHolder.txtGV = (TextView) convertView.findViewById(R.id.txtGV) ;
            viewHolder.txtNganh = (TextView) convertView.findViewById(R.id.txtNganh);
            viewHolder.txtKhoa = (TextView) convertView.findViewById(R.id.txtKhoa);
            viewHolder.txtSiSo = (TextView) convertView.findViewById(R.id.txtSiSo);
            convertView.setTag(viewHolder);

        }else
            viewHolder = (ViewHolder)convertView.getTag();

        Lop Lop = filter_list.get(position);
        viewHolder.txtMaLop.setText(Lop.getMaLop()+"");
        viewHolder.txtTenLop.setText(Lop.getTenLop()+"");
        viewHolder.txtGV.setText(Lop.getGV() + "");
        viewHolder.txtNganh.setText(Lop.getNganh() +"");
        viewHolder.txtKhoa.setText(Lop.getKhoa()+"");
        viewHolder.txtSiSo.setText(Lop.getSiso()+"");

        return convertView;
    }
    class ViewHolder{
        TextView txtTenLop, txtMaLop, txtGV, txtNganh, txtKhoa, txtSiSo;
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
                    ArrayList<Lop> filterResultsData = new ArrayList<Lop>();
                    String searchStr = charSequence.toString().toLowerCase();
                    Log.i("MyAdapter", "searchStr: " + searchStr);
                    for(Lop data : list)
                    {
                        //In this loop, you'll filter through originalData and compare each item to charSequence.
                        //If you find a match, add it to your new ArrayList
                        String currentTenNganh = data.getTenLop().toLowerCase();
                        Log.i("MyAdapter", "currentTenLop: " + currentTenNganh);
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
                filter_list = (ArrayList<Lop>)filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}