package com.example.myapplication;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin, btnClose;
    EditText editUser, editPassword;
    SQLHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = SQLHelper.getInstance(this);
        getWidget();

        db.DeleteTable();
        db.CreateTable();

        if (db.getTotalWord(SQLHelper.TABLE_USER)==0) fakeDataUser();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (db.login(editUser.getText()+"", editPassword.getText()+"") == 1)
                {
                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                    startActivity(intent);
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("Wanring?");
                    builder.setMessage("Sai tài khoản hoặc mật khẩu");

                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            editUser.setText("");
                            editPassword.setText("");
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
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
    public void getWidget(){
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnClose = (Button)findViewById(R.id.btnClose);
        editUser = (EditText)findViewById(R.id.editID);
        editPassword = (EditText)findViewById(R.id.editPass);
    }
    private void fakeDataUser() {
        db.deleteAllData(SQLHelper.TABLE_USER);
        db.insertUser(new User("admin", "admin"));
    }
}