package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
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

public class StudentActivity extends AppCompatActivity {
    EditText txtDiem, txtCMND, txtTenSinhVien, txtTimKiem, txtNgaySinh, txtSDT;
    Spinner spinNganh, spinLop, spinNoiSinh;
    ListView lv;
    int maSinhVien = 1;
    int vitri = -1;
    SinhVien studentSelected = new SinhVien();

    String arrNganh[] = {"CNTT","Điện tử","Du lịch","Kế - kiểm","Ô tô"};
    String arrNoiSinh[] = {"Hà Nội", "Hải Dương", "Bắc Ninh", "Bắc Giang", "Quảng Ninh", "Hưng Yên", "Thái Bình"};
    String arrLop[] = {"1","2","3","4","5"};

    ArrayList<SinhVien> arrSinhVien = null;
    StudentAdapter adapter = null;
    ArrayList<String> arrTenNganh = new ArrayList<String>();

    //khai báo cặp đối tượng dùng cho Spineer khoa, tgDaoTao
    ArrayAdapter<String> adapterSpinnerNoiSinh =null;
    ArrayAdapter<String> adapterSpinnerNganh =null;
    ArrayAdapter<String> adapterSpinnerLop =null;

    // Khai báo class thao tác xử lý với csdl
    SQLHelper sqlHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        sqlHelper = SQLHelper.getInstance(this);

        getWidge();

        reloadList();
        //tao ArrayList object
        //arrSinhVien = new ArrayList<SinhVien>();
        //adapter = new MyAdapter(MainActivity.this,R.layout.myitemlist,arrSinhVien);
        //lv.setAdapter(adapter);


        // xu ly chon 1 phan tu trong listView
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                studentSelected = (SinhVien) adapter.getItem(i);
                txtTenSinhVien.setText(studentSelected.getHoTen() +"");
                txtDiem.setText(studentSelected.getDiem() +"");
                txtCMND.setText(studentSelected.getCmnd() +"");
                txtNgaySinh.setText(studentSelected.getNgaySinh() + "");
                spinNganh.setSelection(adapterSpinnerNganh.getPosition(studentSelected.getNganh()));
                spinLop.setSelection(adapterSpinnerLop.getPosition(studentSelected.getLop()));
                txtSDT.setText(studentSelected.getSdt() +"");
                vitri = i;
            }
        });

        // xu ly long click
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                // arrSinhVien.remove(i);
                SinhVien a = (SinhVien) adapter.getItem(i);
                //int a = arrSinhVien.get(i).getMaSV();
                setBtnXoa(a.getMaSV());
                adapter.notifyDataSetChanged();
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
                    StudentActivity.this.adapter.getFilter().filter(charSequence);}
                catch (Exception ex){
                    Toast toast = Toast.makeText(StudentActivity.this, "Danh sách trống",
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
            arrSinhVien = new ArrayList<>();
            arrSinhVien = sqlHelper.getListStudent();
            adapter = new StudentAdapter(StudentActivity.this,R.layout.studentitemlist,arrSinhVien);
            lv.setAdapter(adapter);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setBtnXoa(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chú ý");
        builder.setMessage("Bạn có chắc muốn xóa không?");
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // arrSinhVien.remove(id);
                sqlHelper.deleteStudent(id);
                reloadList();
            }
        });
        builder.show();
    }

    private void getWidge() {
        txtDiem = (EditText)findViewById(R.id.editDiem);
        txtTimKiem = (EditText)findViewById(R.id.txtTimKiem);
        txtCMND = (EditText)findViewById(R.id.editCMND);
        txtTenSinhVien = (EditText)findViewById(R.id.editName);
        txtNgaySinh = (EditText) findViewById(R.id.editNgaySinh);
        spinNganh = (Spinner)findViewById(R.id.spinnerNganh);
        spinLop = (Spinner)findViewById(R.id.spinnerLop);
        spinNoiSinh = (Spinner) findViewById(R.id.spinnerNoiSinh);
        txtSDT = (EditText) (EditText)findViewById(R.id.editPhoneNumber);
        lv = (ListView)findViewById(R.id.lstSV);

        //cau hinh cho spinner noi sinh
        adapterSpinnerNoiSinh=new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                arrNoiSinh
        );
        adapterSpinnerNoiSinh.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinNoiSinh.setAdapter(adapterSpinnerNoiSinh);

        //cau hinh cho spinner khoa
        //cau hinh auto ten nganh
        arrTenNganh = sqlHelper.getTenNGANH();
        // adapterSpinnerNganh.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterSpinnerNganh = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrTenNganh);
        spinNganh.setAdapter(adapterSpinnerNganh);

        //cau hinh cho spinner tgDaoTao
        adapterSpinnerLop=new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                arrLop
        );
        adapterSpinnerLop.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinLop.setAdapter(adapterSpinnerLop);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.studentmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        doSomething(item);
        return super.onOptionsItemSelected(item);
    }
//xử lý các sự kiện của menu option
    public void doSomething(MenuItem item){
        switch (item.getItemId()){
            case R.id.mnuAddST:
                SinhVien SinhVien = new SinhVien();

                SinhVien.setHoTen(txtTenSinhVien.getText()+"");
                SinhVien.setNgaySinh(txtNgaySinh.getText() + "");
                SinhVien.setDiem(Float.parseFloat(txtDiem.getText()+""));
                SinhVien.setCmnd(txtCMND.getText()+"");
                SinhVien.setSdt(txtSDT.getText()+"");
                SinhVien.setLop(spinLop.getSelectedItem()+"");
                SinhVien.setNoiSinh(spinNoiSinh.getSelectedItem()+"");
                SinhVien.setNganh("Chưa đăng ký");

                //arrSinhVien.add(SinhVien);
                //adapter.notifyDataSetChanged();

                txtTenSinhVien.setText("");
                txtCMND.setText("");
                txtDiem.setText("");
                txtTenSinhVien.requestFocus();

                sqlHelper.insertStudent(SinhVien);
                reloadList();
                break;
            case R.id.mnuEditST:
                studentSelected.setHoTen(txtTenSinhVien.getText()+"");
                studentSelected.setDiem(Float.parseFloat(txtDiem.getText()+""));
                studentSelected.setCmnd(txtCMND.getText()+"");
                studentSelected.setSdt(txtSDT.getText()+"");
                studentSelected.setEmail(spinLop.getSelectedItem()+"");

                sqlHelper.updateStudent(studentSelected);
                reloadList();
                break;
            case R.id.mnuRegister:
                studentSelected.setNganh(spinNganh.getSelectedItem()+"");
                sqlHelper.updateStudentBranch(studentSelected);
                reloadList();
                break;
            case R.id.mnuExitST:
                StudentActivity.this.finish();
                break;


            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
    }


}