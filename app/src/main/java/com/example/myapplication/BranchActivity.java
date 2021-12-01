package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class BranchActivity extends AppCompatActivity implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    //    Button btnThem, btnSua, btnDong;
    EditText txtDiemChuan, txtTimKiem;
    AutoCompleteTextView txtTenNganh;
    Spinner spinKhoa, spinTgDaoTao;
    ListView lv;


    //    String arrKhoa[] = null;
    //String arrKhoa[] = {"CNTT","Kế-kiểm","Ô tô"};
    String arrTgDaoTao[] = {"3 năm","3,5 năm","4 năm","4,5 năm","5 năm"};
    //String arrTenNganh[] = {"CNTT","Kế toán","CN ô tô"};

    ArrayList<String> arrTenNganh = new ArrayList<String>();
    ArrayList<String> arrKhoa = new ArrayList<String>();
    ArrayList<NGANH> arrNganh = new ArrayList<NGANH>();
    CHI_TIEU.BranchAdapter adapter = null;

    SQLHelper db=null;
    NGANH nganhSelected=new NGANH();

    //khai báo cặp đối tượng dùng cho Spineer khoa, tgDaoTao
    ArrayAdapter<String> adapterSpinnerKhoa =null;
    ArrayAdapter<String> adapterSpinnertgDaoTao =null;
    ArrayAdapter<String> adapterTenNganh =null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch);
        db = SQLHelper.getInstance(this);

        getWidge();

        //chuẩn bị dữ liệu
        //gán dl cho listview
        refreshDataForListView();
        Toast.makeText(BranchActivity.this, "so nganh:"+arrNganh.size(),
                Toast.LENGTH_LONG).show();


        // xu ly chon 1 phan tu trong listView
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                nganhSelected = (NGANH) adapter.getItem(i);
                txtTenNganh.setText(nganhSelected.getTenNganh() +"");
                txtDiemChuan.setText(nganhSelected.getDiemChuan() +"");
                spinKhoa.setSelection(adapterSpinnerKhoa.getPosition(nganhSelected.getKhoa()));
                spinTgDaoTao.setSelection(adapterSpinnertgDaoTao.getPosition(nganhSelected.getThoiGianDaoTao()));

            }
        });
//
        // xu ly long click
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                NGANH nganhSelectedNew = (NGANH) adapter.getItem(i);
                AlertDialog.Builder b=new AlertDialog.Builder(BranchActivity.this);
                b.setTitle("Question");
                b.setMessage("Are you sure you want to delete?");
                b.setPositiveButton("Yes", new DialogInterface. OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteNGANH(nganhSelectedNew);
                        refreshDataForListView();
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
                    BranchActivity.this.adapter.getFilter().filter(charSequence);}
                catch (Exception ex){
                    Toast toast = Toast.makeText(BranchActivity.this, "Danh sách trống",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void refreshDataForListView() {
        try {
            arrNganh=db.getAllNGANH();
            adapter=new CHI_TIEU.BranchAdapter(BranchActivity.this,R.layout.branchitemlist,arrNganh);
            lv.setAdapter(adapter);
            lv.setTextFilterEnabled(true);
            adapter.notifyDataSetChanged();
        }catch (Exception e){
            System.out.println(" có lỗi: "+e.toString());
        }
    }

    private void getWidge() {
        txtTimKiem = (EditText) findViewById(R.id.txtTimKiem);
//        btnThem = (Button)findViewById(R.id.btnThem);
//        btnSua = (Button)findViewById(R.id.btnSua);
//        btnDong = (Button)findViewById(R.id.btnDong);
        txtDiemChuan = (EditText)findViewById(R.id.txtDiemChuan);
        txtTenNganh = (AutoCompleteTextView) findViewById(R.id.txtTenNganh);
        spinKhoa = (Spinner)findViewById(R.id.spinKhoa);
        spinTgDaoTao = (Spinner)findViewById(R.id.spinTgDaoTao);
        lv = (ListView)findViewById(R.id.lv);

        arrKhoa = db.getAllKHOA();
        //cau hinh cho spinner khoa
        adapterSpinnerKhoa=new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                arrKhoa
        );
        adapterSpinnerKhoa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinKhoa.setAdapter(adapterSpinnerKhoa);

        //cau hinh cho spinner tgDaoTao
        adapterSpinnertgDaoTao=new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                arrTgDaoTao
        );
        adapterSpinnertgDaoTao.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinTgDaoTao.setAdapter(adapterSpinnertgDaoTao);

        //cau hinh auto ten nganh
        arrTenNganh = db.getTenNGANH();
        adapterTenNganh = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrTenNganh);
        txtTenNganh.setAdapter(adapterTenNganh);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //gắn menu XML Resource vào ứng dụng
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return true;
    }

    //thực hiện xử lý các lựa chọn ứng với menu option
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        doSomething(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.mymenu,menu);
    }

    //các xử lý với menu context
    public boolean onContextItemSelected(MenuItem item){
        doSomething(item);
        return  super.onContextItemSelected(item);
    }

    private void doSomething(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnuAdd:
                try{
                    NGANH nganh = new NGANH();
                    nganh.setTenNganh(txtTenNganh.getText()+"");
                    nganh.setDiemChuan(Float.parseFloat(txtDiemChuan.getText()+""));
                    nganh.setKhoa(spinKhoa.getSelectedItem()+"");
                    nganh.setThoiGianDaoTao(spinTgDaoTao.getSelectedItem()+"");

                    db.insertNGANH(nganh);

                    // lấy lại trạng thái mới nhất từ csdl
                    refreshDataForListView();

//                    arrNganh.add(nganh);
//                    adapter.notifyDataSetChanged();
                    if(Arrays.asList(arrTenNganh).contains(txtTenNganh.getText()+"") == false)
                        adapterTenNganh.add(txtTenNganh.getText()+"");


                    txtTenNganh.setText("");
                    txtDiemChuan.setText("");
                    txtTenNganh.requestFocus();

                }catch (Exception ex){
                    Toast toast = Toast.makeText(BranchActivity.this, "Chưa nhập đẩy đủ thông tin!",
                            Toast.LENGTH_SHORT);
                    toast.show();

                }

                Toast.makeText(this, "Thêm ngành", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mnuView:
                try {
                    NGANH nganh = nganhSelected;
                    nganh.setTenNganh(txtTenNganh.getText()+"");
                    nganh.setDiemChuan(Float.parseFloat(txtDiemChuan.getText()+""));
                    nganh.setKhoa(spinKhoa.getSelectedItem()+"");
                    nganh.setThoiGianDaoTao(spinTgDaoTao.getSelectedItem()+"");

                    db.updateNGANH(nganh);
                    refreshDataForListView();

//                    adapter.notifyDataSetChanged();
                }catch (Exception ex){
                    Toast toast = Toast.makeText(BranchActivity.this, "Chưa chọn item cần chỉnh sửa",
                            Toast.LENGTH_SHORT);
                    toast.show();

                }

                Toast.makeText(BranchActivity.this,"Sửa ngành",Toast.LENGTH_LONG).show();
                break;
            case R.id.mnuExit:
                AlertDialog.Builder b=new AlertDialog.Builder(BranchActivity.this);
                b.setTitle("Question");
                b.setMessage("Are you sure you want to exit?");
                b.setPositiveButton("Yes", new DialogInterface. OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                b.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        dialog.cancel();
                    }
                });
                b.create().show();

                Toast.makeText(BranchActivity.this,"Đóng",Toast.LENGTH_LONG).show();
                break;
        }
    }
}
