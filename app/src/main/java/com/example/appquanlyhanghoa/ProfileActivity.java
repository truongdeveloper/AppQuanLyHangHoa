package com.example.appquanlyhanghoa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.huawei.hmf.tasks.OnCompleteListener;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.support.hwid.service.HuaweiIdAuthAPIService;
import com.huawei.hms.support.hwid.service.HuaweiIdAuthAPIServiceImpl;
import com.huawei.hms.support.hwid.service.HuaweiIdAuthServiceImpl;

public class ProfileActivity extends AppCompatActivity {

    HuaweiIdAuthServiceImpl mAuthService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        showUserInformation();
        findViewById(R.id.dangxuat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        BottomNavigationView mNavView = findViewById(R.id.botom_nav);



        mNavView.setSelectedItemId(R.id.logout);

        mNavView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.list:
                        startActivity(new Intent(getApplicationContext(),ListActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.scan:
                        startActivity(new Intent(getApplicationContext(),ScanKit.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.info:
                        startActivity(new Intent(getApplicationContext(),InfoActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.logout:
                        return true;
                }
                return false;
            }
        });

    }
    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, DangNhap.class);
        startActivity(intent);
        finish();
    }

    private void showUserInformation(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        TextView tvEmail = findViewById(R.id.textviewemail);
        if(user == null){
            return;
        }
        String email = user.getEmail();
        tvEmail.setText(email);
    }
}