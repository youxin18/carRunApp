package com.example.cardrunfast;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText phone_ev;
    private EditText register_pwd_ev;
    private TextView btn_register_tv;
    String register_name;
    String register_pwd;
    String find_name;
    String find_pwd;
    private CreateDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView userPwd=findViewById(R.id.userpwd);
        phone_ev=findViewById(R.id.phone);
        register_pwd_ev=findViewById(R.id.register_pwd);
        btn_register_tv=findViewById(R.id.btn_register);
        userPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,LoginUserPwdActivity.class);
                startActivity(intent);
            }
        });
        btn_register_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register_name=phone_ev.getText().toString();
                register_pwd=register_pwd_ev.getText().toString();
                find(register_name);
                if (register_name.equals(find_name)){
                    Toast.makeText(LoginActivity.this,"该用户名已注册！",Toast.LENGTH_SHORT).show();
                }else {
                    dbHelper=new CreateDbHelper(LoginActivity.this,"user.db",null,1);
                    SQLiteDatabase put=dbHelper.getWritableDatabase();
                    ContentValues values=new ContentValues();
                    values.put("username",register_name);
                    values.put("pwd",register_pwd);
                    put.insert("user1",null,values);
                    Toast.makeText(LoginActivity.this,"注册成功！",Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
    @SuppressLint("Range")
    public void find(String username) {
        dbHelper=new CreateDbHelper(this,"user.db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "Select*from user1 where username=?";
        Cursor cursor = db.rawQuery(sql, new String[]{username});
        if (cursor.moveToFirst()) {
            do {
               find_name = cursor.getString(cursor.getColumnIndex("username"));

            } while (cursor.moveToNext());
        }
        cursor.close();
    }
}