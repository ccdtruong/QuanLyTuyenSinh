package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class ClassActivity extends AppCompatActivity implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    EditText editTenLop, editSiSo, txtTimKiem;
    Spinner spnTenGV, spnNganh, spnKhoa;
    ListView lv;
    int MaLop = 10;
    int pos = -1;
    String arrGV[] = {"Hoa", "Đức Anh", "Quang", "Trường", "Tùng"};
//    String arrKhoa[] = {"CNTT", "Điện tử", "Du lịch", "Kế - Kiểm", "Ô tô"};
//    String arrNganh[] = {"KTPM", "Tự động hóa", "Lữ hành", "Kế toán", "CN ô tô"};
    ArrayList<String> arrNganh = new ArrayList<String>();
    ArrayList<String> arrKhoa = new ArrayList<String>();
    ArrayList<Lop> arrLop = new ArrayList<Lop>();
    ClassAdapter adapter = null;
    SQLHelper sqlHelper=null;
    Lop classSelected = new Lop();

    //Khai bao doi tuong dung cho spinner
    ArrayAdapter<String> adapterSpnGV = null;
    ArrayAdapter<String> adapterSpnNganh = null;
    ArrayAdapter<String> adapterSpnKhoa = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        sqlHelper = SQLHelper.getInstance(this);

        getWidget();

        reloadList();
        // xu ly chon 1 phan tu trong listView
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                classSelected = (Lop) adapter.getItem(i);
                editTenLop.setText(classSelected.getTenLop() +"");
                editSiSo.setText(classSelected.getSiso() +"");
                spnTenGV.setSelection(adapterSpnGV.getPosition(classSelected.getGV()));
                spnNganh.setSelection(adapterSpnNganh.getPosition(classSelected.getNganh()));
                spnKhoa.setSelection(adapterSpnKhoa.getPosition(classSelected.getKhoa()));
                pos = i;
            }
        });
        // xu ly long click
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Lop classSelectedNew = (Lop) adapter.getItem(i);
                AlertDialog.Builder b=new AlertDialog.Builder(ClassActivity.this);
                b.setTitle("Question");
                b.setMessage("Bạn có chắc chắn muốn xóa?");
                b.setPositiveButton("Yes", new DialogInterface. OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sqlHelper.deleteClass(classSelectedNew.getMaLop());
                        reloadList();
                    }
                });
                b.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        dialog.cancel();
                    }
                });
                b.create().show();
                return false;
            }
        });

        // xu ly su kien tim kiem
        txtTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try{
                    ClassActivity.this.adapter.getFilter().filter(charSequence);}
                catch (Exception ex){
                    Toast toast = Toast.makeText(ClassActivity.this, "Danh sách trống",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    private void getWidget() {
        txtTimKiem = (EditText) findViewById(R.id.txtTimKiem);
        editTenLop = (EditText)findViewById(R.id.editName);
        editSiSo = (EditText)findViewById(R.id.editNumber);
        spnTenGV = (Spinner)findViewById(R.id.spnTeacher);
        spnNganh = (Spinner) findViewById(R.id.spnbranch);
        spnKhoa = (Spinner)findViewById(R.id.spnFaculty);
        lv = (ListView)findViewById(R.id.lv);

        //cau hinh cho spinner Giao vien
        adapterSpnGV=new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                arrGV
        );
        adapterSpnGV.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTenGV.setAdapter(adapterSpnGV);

        //cau hinh cho spinner Nganh
        arrNganh = sqlHelper.getTenNGANH();
        adapterSpnNganh=new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                arrNganh
        );
        adapterSpnNganh.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnNganh.setAdapter(adapterSpnNganh);

        //cau hinh cho spinner khoa
        arrKhoa = sqlHelper.getAllKHOA();
        adapterSpnKhoa=new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                arrKhoa
        );
        adapterSpnKhoa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnKhoa.setAdapter(adapterSpnKhoa);

    }
    public void reloadList()
    {
        try{
//          arrLop = new ArrayList<Lop>();
            arrLop = sqlHelper.getListClass();
            adapter = new ClassAdapter(ClassActivity.this,R.layout.classitemlist,arrLop);
            lv.setAdapter(adapter);
            lv.setTextFilterEnabled(true);
            adapter.notifyDataSetChanged();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void addClass(){
        Lop lop = new Lop();

        lop.setTenLop(editTenLop.getText()+"");
        lop.setGV(spnTenGV.getSelectedItem()+"");
        lop.setNganh(spnNganh.getSelectedItem()+"");
        lop.setKhoa(spnKhoa.getSelectedItem()+"");
        lop.setSiso(editSiSo.getText()+"");

        sqlHelper.insertClass(lop);
        reloadList();

        editTenLop.setText("");
        editSiSo.setText("");
    }
    public void updateData(){
        classSelected.setTenLop(editTenLop.getText()+"");
        classSelected.setSiso(editSiSo.getText()+"");
        classSelected.setGV(spnTenGV.getSelectedItem()+"");
        classSelected.setNganh(spnNganh.getSelectedItem()+"");
        classSelected.setKhoa(spnKhoa.getSelectedItem()+"");

        sqlHelper.updateClass(classSelected);
        reloadList();
    }
    public void exitApp(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ClassActivity.this);
        builder.setTitle("Question?");
        builder.setMessage("Bạn có muốn thoát?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ClassActivity.this.finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        doSomething(item);
        return super.onOptionsItemSelected(item);
    }
    public void doSomething(MenuItem item){
        switch (item.getItemId()){
            case R.id.mnuAdd:
                addClass();
                break;
            case R.id.mnuView:
                updateData();
                break;
            case R.id.mnuExit:
                exitApp();
                break;


        }
    }
}
