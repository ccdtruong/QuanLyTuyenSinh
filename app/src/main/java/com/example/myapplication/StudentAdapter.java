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

import java.util.ArrayList;

public class StudentAdapter extends ArrayAdapter implements Filterable {
    Activity context = null;
    int layoutID;
    ArrayList<SinhVien> list = null;
    ArrayList<SinhVien> filter_list = null;


    public StudentAdapter(Activity context, int layoutID, ArrayList<SinhVien> objects) {
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
            viewHolder.txtMaSV = (TextView) convertView.findViewById(R.id.txtMaSV);
            viewHolder.txtTenSV = (TextView) convertView.findViewById(R.id.txtHoTen);
            viewHolder.txtDiem = (TextView) convertView.findViewById(R.id.txtDiem);
            viewHolder.txtCMND = (TextView) convertView.findViewById(R.id.txtCMND);
            viewHolder.txtSDT = (TextView) convertView.findViewById(R.id.txtNganh);
            viewHolder.txtEmail = (TextView) convertView.findViewById(R.id.txtLop);
            viewHolder.txtNganh = (TextView) convertView.findViewById(R.id.txtNganhView);
            convertView.setTag(viewHolder);

        }else
            viewHolder = (ViewHolder)convertView.getTag();

        SinhVien sv = filter_list.get(position);
        viewHolder.txtMaSV.setText(sv.getMaSV()+"");
        viewHolder.txtTenSV.setText(sv.getHoTen()+"");
        viewHolder.txtDiem.setText(sv.getDiem() + "");
        viewHolder.txtCMND.setText(sv.getCmnd() +"");
        viewHolder.txtSDT.setText(sv.getSdt()+"");
        viewHolder.txtEmail.setText(sv.getLop()+"");
        viewHolder.txtNganh.setText(sv.getNganh());

        return convertView;
    }
    class ViewHolder{
        TextView txtTenSV, txtMaSV, txtDiem, txtCMND, txtSDT, txtEmail, txtNganh;
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
                    ArrayList<SinhVien> filterResultsData = new ArrayList<SinhVien>();
                    String searchStr = charSequence.toString().toLowerCase();
                    Log.i("MyAdapter", "searchStr: " + searchStr);
                    for(SinhVien data : list)
                    {
                        //In this loop, you'll filter through originalData and compare each item to charSequence.
                        //If you find a match, add it to your new ArrayList
                        String currentTenNganh = data.getHoTen().toLowerCase();
                        Log.i("MyAdapter", "currentHoTen: " + currentTenNganh);
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
                filter_list = (ArrayList<SinhVien>)filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
