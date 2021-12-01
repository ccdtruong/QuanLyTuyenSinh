package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class TargetActivity extends AppCompatActivity implements TextWatcher{
    EditText txtChiTieu, txtNamHoc, txtTimKiem;
    Spinner spinTenNganh;
    ListView lv;
    int vitri;
    SQLHelper sqlHelper;
    ArrayList<String> arrTenNganh = new ArrayList<String>();

    ArrayList<CHI_TIEU> arrCt = null;
    TargetAdapter adapter = null;
    CHI_TIEU targerSelected = new CHI_TIEU();

    //khai báo cặp đối tượng dùng cho Spineer khoa, tgDaoTao
    ArrayAdapter<String> adapterSpinnerTenNganh =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);
        sqlHelper = SQLHelper.getInstance(this);
        getWidge();
        reloadList();

        // xu ly chon 1 phan tu trong listView
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                targerSelected = (CHI_TIEU) adapter.getItem(i);
                txtChiTieu.setText(targerSelected.getchiTieu() +"");
                txtNamHoc.setText(targerSelected.getnamHoc() +"");
                spinTenNganh.setSelection(adapterSpinnerTenNganh.getPosition(targerSelected.getTenNganh()));
                vitri = i;
            }
        });

        // xu ly long click
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                CHI_TIEU targerSelectedNew = (CHI_TIEU) adapter.getItem(i);
                AlertDialog.Builder b=new AlertDialog.Builder(TargetActivity.this);
                b.setTitle("Question");
                b.setMessage("Bạn có muốn xóa chỉ tiêu này không?");
                b.setPositiveButton("Có", new DialogInterface. OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sqlHelper.deleteTarget(targerSelectedNew.getId());
                        arrCt.remove(i);
                        adapter.notifyDataSetChanged();
                    }
                });
                b.setNegativeButton("Không", new DialogInterface.OnClickListener() {
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
                    TargetActivity.this.adapter.getFilter().filter(charSequence);}
                catch (Exception ex){
                    Toast toast = Toast.makeText(TargetActivity.this, "Danh sách trống",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    public void reloadList()
    {
        try{
            arrCt = new ArrayList<>();
            arrCt = sqlHelper.getListTarget();
            adapter = new TargetAdapter(TargetActivity.this,R.layout.targetitemlist,arrCt);
            lv.setAdapter(adapter);
            lv.setTextFilterEnabled(true);
            adapter.notifyDataSetChanged();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void getWidge() {
        txtChiTieu = (EditText) findViewById(R.id.txtChiTieu);
        txtTimKiem = (EditText) findViewById(R.id.txtTimKiem);
        txtNamHoc = (EditText) findViewById(R.id.txtNamHoc);
        spinTenNganh = (Spinner)findViewById(R.id.spinTenNganh);
        lv = (ListView)findViewById(R.id.lv);
        arrTenNganh = sqlHelper.getTenNGANH();
        //cau hinh cho spinner
        adapterSpinnerTenNganh=new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                arrTenNganh
        );
        adapterSpinnerTenNganh.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinTenNganh.setAdapter(adapterSpinnerTenNganh);

    }
    public void exitApp(){
        AlertDialog.Builder builder = new AlertDialog.Builder(TargetActivity.this);
        builder.setTitle("Question?");
        builder.setMessage("Bạn có muốn thoát?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TargetActivity.this.finish();
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
    public void alertMesage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(TargetActivity.this);
        builder.setTitle("Warning");
        builder.setMessage("Không thể sửa dữ liệu từ trước năm 2021");
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
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
                CHI_TIEU ct = new CHI_TIEU();
                ct.setchiTieu(txtChiTieu.getText()+"");
                ct.setnamHoc(txtNamHoc.getText()+"");
                ct.setTenNganh(spinTenNganh.getSelectedItem()+"");

                sqlHelper.insertTarget(ct);
                reloadList();
                break;
            case R.id.mnuView:
                if(Integer.parseInt(txtNamHoc.getText()+"")>2020){
                    targerSelected.setchiTieu(txtChiTieu.getText()+"");
                    targerSelected.setnamHoc(txtNamHoc.getText()+"");
                    targerSelected.setTenNganh(spinTenNganh.getSelectedItem()+"");
                    sqlHelper.updateTarget(targerSelected);
                    reloadList();
                }
                else{
                    alertMesage();
                }
                break;
            case R.id.mnuExit:
                AlertDialog.Builder builder = new AlertDialog.Builder(TargetActivity.this);
                builder.setTitle("Question?");
                builder.setMessage("Bạn có muốn thoát?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TargetActivity.this.finish();
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
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}