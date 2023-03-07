package com.example.myapplicationtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class User extends AppCompatActivity {
    EditText etId, etName, etPhone;
    Button btnOk, btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        etId = findViewById(R.id.editTextTextPersonName14);
        etName = findViewById(R.id.editTextTextPersonName15);
        etPhone = findViewById(R.id.editTextTextPersonName16);
        btnOk = findViewById(R.id.button11);
        btnCancel = findViewById(R.id.button12);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            int id = bundle.getInt("Id");
            String name = bundle.getString("Name");
            String phone = bundle.getString("Phone");
            etId.setText(String.valueOf(id));
            etName.setText(name);
            etPhone.setText(phone);
            btnOk.setText("Edit");
        }
        btnCancel.setOnClickListener(view -> {
            Intent intent1 = new Intent(this, MainActivity.class);
            startActivityForResult(intent1,0);
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check()){

                    //Tạo intent để trở về MainActivity
                    Intent intent = new Intent();
                    //Tạo bundle là đối tượng để chứa dữ liệu
                    Bundle bundle = new Bundle();
                    //bundle hoạt động như một Java Map các phần tử phân biệt theo key
                    //bundle có các hàm put... trong đó ... là kiểu dữ liệu tương ứng
                    bundle.putInt("Id", Integer.parseInt(etId.getText().toString()));
                    bundle.putString("Name", etName.getText().toString());
                    bundle.putString("Phone", etPhone.getText().toString());
                    //có thể đặt cả đối tượng lên bundle bằng hàm putSerilizable
                    //đặt bundle lên intent

                    intent.putExtras(bundle);


                    //trả về bằng hàm setResult
                    //tham số thứ nhất là resultCode để quản lý phiên
                    //tham số thứ hai  là intent chứa dữ liệu gửi về
                    setResult(200, intent);
                    if(btnOk.getText()=="Edit")
                        setResult(201, intent);
                    //Kết thúc: đóng activity hiện thời.
                    finish();
                }

            }
        });
        }
    boolean check(){
        if(etId.getText().toString().isEmpty()){
            Toast.makeText(this, "Vui lòng nhập ID!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(etName.getText().toString().isEmpty()){
            Toast.makeText(this, "Vui lòng nhập tên!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(etPhone.getText().toString().isEmpty()){
            Toast.makeText(this, "Vui lòng nhập số điện thoại!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}