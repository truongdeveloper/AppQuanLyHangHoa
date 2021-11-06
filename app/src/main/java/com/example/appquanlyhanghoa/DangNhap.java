package com.example.appquanlyhanghoa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appquanlyhanghoa.Fragment.ListFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DangNhap extends AppCompatActivity {

    EditText edttk, edtmk;
    Button btdangnhap, btdangky, btthoat;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangnhap);
        Anhxa();
        DieukhienNut();
        mAuth = FirebaseAuth.getInstance();
    }

    private void Anhxa() {
        edttk = (EditText) findViewById(R.id.edittexttaikhoan);
        edtmk = (EditText) findViewById(R.id.edittextmatkhau);
        btdangnhap =(Button) findViewById(R.id.buttondangnhap);
        btdangky = (Button) findViewById((R.id.buttondangky));
        btthoat = (Button) findViewById((R.id.buttonthoat));
    }

    private void DieukhienNut() {
        btthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DangNhap.this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
                builder.setTitle("Bạn muốn thoát ứng dụng?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onBackPressed();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });
        btdangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(DangNhap.this);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_dangky);
                EditText edtnhaptk = (EditText) dialog.findViewById(R.id.edittextnhaptk);
                EditText edtnhapmk = (EditText) dialog.findViewById(R.id.edittextnhapmk);
                Button bthuy = (Button) dialog.findViewById(R.id.buttonhuy);
                Button btok = (Button) dialog.findViewById(R.id.buttonok);
                btok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String  taikhoan = edtnhaptk.getText().toString().trim();
                        String matkhau = edtnhapmk.getText().toString().trim();

                        mAuth.createUserWithEmailAndPassword(taikhoan, matkhau)
                                .addOnCompleteListener(DangNhap.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(DangNhap.this, "ĐĂNG KÝ THÀNH CÔNG", Toast.LENGTH_SHORT).show();
                                            edttk.setText(taikhoan);
                                            edtmk.setText(matkhau);
                                        } else {
                                            Toast.makeText(DangNhap.this, "          ĐĂNG KÝ THẤT BẠI\n-Tài khoản đúng định dạng mail\n      -Mật khẩu 6 kí tự trở lên", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                        dialog.cancel();
                    }
                });
                bthuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });
        btdangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taikhoan = edttk.getText().toString().trim();
                String matkhau = edtmk.getText().toString().trim();
                if(edttk.getText().length() != 0 && edtmk.getText().length() != 0) {
                    mAuth.signInWithEmailAndPassword(taikhoan, matkhau)
                            .addOnCompleteListener(DangNhap.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(DangNhap.this, "ĐĂNG NHẬP THÀNH CÔNG", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(DangNhap.this, Menu.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(DangNhap.this, "ĐĂNG NHẬP THẤT BẠI", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }else {
                    Toast.makeText(DangNhap.this, "NHẬP ĐẦY ĐỦ THÔNG TIN", Toast.LENGTH_SHORT).show();
                 }
            }
        });
    }


}