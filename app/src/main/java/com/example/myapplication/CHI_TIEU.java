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

public class CHI_TIEU {
    int id;
    String tenNganh, chiTieu, namHoc;
    public CHI_TIEU(){}
    public CHI_TIEU(int id, String tenNganh, String chiTieu, String namHoc){
        this.id = id;
        this.tenNganh = tenNganh;
        this.chiTieu = chiTieu;
        this.namHoc = namHoc;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getTenNganh(){
        return tenNganh;
    }
    public void setTenNganh(String tenNganh){
        this.tenNganh = tenNganh;
    }
    public String getchiTieu(){
        return chiTieu;
    }
    public void setchiTieu(String chiTieu){
        this.chiTieu = chiTieu;
    }
    public String getnamHoc(){
        return namHoc;
    }
    public void setnamHoc(String namHoc){
        this.namHoc = namHoc;
    }

    public static class BranchAdapter extends ArrayAdapter implements Filterable {
        Activity context = null;
        int layoutID;
        ArrayList<NGANH> list = null;
        ArrayList<NGANH> filter_list = null;


        public BranchAdapter(Activity context, int layoutID, ArrayList<NGANH> objects) {
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
                convertView =  LayoutInflater.from(context).inflate(layoutID,parent,false);
                viewHolder = new ViewHolder();
                viewHolder.txtMaNganh = (TextView) convertView.findViewById(R.id.txtMaNganh);
                viewHolder.txtTenNganh = (TextView) convertView.findViewById(R.id.txtTenNganh);
                viewHolder.txtDiemChuan = (TextView) convertView.findViewById(R.id.txtDiemChuan);
                viewHolder.txtKhoa = (TextView) convertView.findViewById(R.id.txtKhoa);
                viewHolder.txtTgDaoTao = (TextView) convertView.findViewById(R.id.txtTgDaoTao);
                convertView.setTag(viewHolder);

            }else
                viewHolder = (ViewHolder)convertView.getTag();

            NGANH nganh = filter_list.get(position);
            viewHolder.txtMaNganh.setText(nganh.getMaNganh()+"");
            viewHolder.txtTenNganh.setText(nganh.getTenNganh()+"");
            viewHolder.txtDiemChuan.setText(nganh.getDiemChuan() + "");
            viewHolder.txtKhoa.setText(nganh.getKhoa()+"");
            viewHolder.txtTgDaoTao.setText(nganh.getThoiGianDaoTao()+"");

            return convertView;
        }
        class ViewHolder{
            TextView txtTenNganh,txtMaNganh, txtDiemChuan, txtKhoa, txtTgDaoTao;
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
                        ArrayList<NGANH> filterResultsData = new ArrayList<NGANH>();
                        String searchStr = charSequence.toString().toLowerCase();
                        Log.i("MyAdapter", "searchStr: " + searchStr);
                        for(NGANH data : list)
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
                    filter_list = (ArrayList<NGANH>)filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }
    }
}
