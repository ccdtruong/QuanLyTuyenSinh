package com.example.myapplication;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button btnStudent, btnTarget, btnClass, btnBranch, btnExit;
    SQLHelper db=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get Instance.: csll, tạo bảng
        db = SQLHelper.getInstance(this);

        getWidge();

        //insert data
        if (db.getTotalWord(SQLHelper.TABLE_STUDENT)==0) fakeDataStudent();
        if (db.getTotalWord(SQLHelper.TABLE_TARGET)==0) fakeDataTarget();
        if (db.getTotalWord(SQLHelper.TABLE_CLASS)==0) fakeDataClass();
        if (db.getTotalWord(SQLHelper.TABLE_KHOA)==0) fakeDataKhoa();
        if (db.getTotalWord(SQLHelper.TABLE_NGANH)==0) fakeDataNganh();

        btnStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), StudentActivity.class);
                startActivity(intent);
            }
        });

        btnTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), TargetActivity.class);
                startActivity(intent);
            }
        });

        btnClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ClassActivity.class);
                startActivity(intent);
            }
        });

        btnBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), BranchActivity.class);
                startActivity(intent);
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Question?");
                builder.setMessage("Bạn có muốn thoát?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
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
        });
    }

    private void getWidge() {
        btnStudent = (Button)findViewById(R.id.btnStudent);
        btnTarget = (Button)findViewById(R.id.btnTarget);
        btnClass = (Button)findViewById(R.id.btnClass);
        btnBranch = (Button)findViewById(R.id.btnBranch);
        btnExit = (Button)findViewById(R.id.btnExit);
    }

    private void fakeDataStudent() {
        db.deleteAllData(SQLHelper.TABLE_STUDENT);
        db.insertStudent(new SinhVien(1, "Nguyễn Văn A", 8, "15/11/2020", "0982173912", "030200001234", "Hải Dương", "1", "Chưa đăng ký"));
        db.insertStudent(new SinhVien(2, "Nguyễn Văn B", 9, "16/11/2020", "0982173913", "030200001234", "Hải Dương", "1", "Chưa đăng ký"));
        db.insertStudent(new SinhVien(3, "Nguyễn Văn C", 10, "17/11/2020", "0982173914", "030200001234", "Hải Dương", "1", "Chưa đăng ký"));
    }

    private void fakeDataTarget() {
        db.deleteAllData(SQLHelper.TABLE_TARGET);
        db.insertTarget(new CHI_TIEU(1, "CNTT", "260", "2000"));
        db.insertTarget(new CHI_TIEU(2, "Kế toán", "250", "2025"));
        db.insertTarget(new CHI_TIEU(3, "CN ô tô", "240", "2021"));
    }

    private void fakeDataClass() {
        db.deleteAllData(SQLHelper.TABLE_CLASS);
        db.insertClass(new Lop(1, "KTPM01", "Hoa", "KTPM", "CNTT", "80"));
        db.insertClass(new Lop(2, "KETO01", "Tùng", "Kế toán", "Kế - Kiểm", "70"));
        db.insertClass(new Lop(3, "OTO01", "Trường", "CN ô tô", "Ô tô", "75"));
    }

    private void fakeDataNganh() {
        db.deleteAllData(SQLHelper.TABLE_NGANH);
        db.insertNGANH(new NGANH(20,"CNTT","CNTT","4 năm"));
        db.insertNGANH(new NGANH(24,"Kế toán","Kế-Kiểm","4 năm"));
        db.insertNGANH(new NGANH(25,"CN ô tô","Ô tô","5 năm"));
    }

    private void fakeDataKhoa() {
        db.deleteAllData(SQLHelper.TABLE_KHOA);
        db.insertKHOA(new KHOA("CNTT"));
        db.insertKHOA(new KHOA("Kế-Kiểm"));
        db.insertKHOA(new KHOA("Ô tô"));
    }
}
