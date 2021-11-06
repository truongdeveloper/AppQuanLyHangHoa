package com.example.appquanlyhanghoa;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.appquanlyhanghoa.Fragment.InformationFragment;
import com.example.appquanlyhanghoa.Fragment.ListFragment;
import com.example.appquanlyhanghoa.Fragment.ProfileFragment;
import com.example.appquanlyhanghoa.Fragment.ScanFragment;
import com.example.appquanlyhanghoa.Fragment.StatisticalFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Menu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private static final int Fragment_List = 0;
    private static final int Fragment_Statistical = 1;
    private static final int Fragment_Scan = 2;
    private static final int Fragment_Profile = 3;
    private static final int Fragment_Information = 4;

    private int mCurrentFragment = Fragment_List;
    private NavigationView mNavigationView;
    private ImageView imgAvatar;
    private TextView tvName,tvEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initUi();

        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        mNavigationView.setNavigationItemSelectedListener(this);

        replacefragment(new ListFragment());
        mNavigationView.getMenu().findItem(R.id.nav_list).setChecked(true);

        showUserInformation();

    }

    private void initUi() {
        mNavigationView = findViewById(R.id.navigation_view);
        imgAvatar = mNavigationView.getHeaderView(0 ).findViewById(R.id.imageviewavatar);
        tvName = mNavigationView.getHeaderView(0 ).findViewById(R.id.tv_name);
        tvEmail = mNavigationView.getHeaderView(0 ).findViewById(R.id.tv_email);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_list) {
            if (mCurrentFragment != Fragment_List) {
                replacefragment(new ListFragment());
                mCurrentFragment = Fragment_List;
            }
        } else if (id == R.id.nav_statistical) {
            if (mCurrentFragment != Fragment_Statistical) {
                replacefragment(new StatisticalFragment());
                mCurrentFragment = Fragment_Statistical;
            }
        } else if (id == R.id.nav_scan) {
            if (mCurrentFragment != Fragment_Scan) {
                Intent intent = new Intent(Menu.this,ScanKit.class);startActivity(intent);
                mCurrentFragment = Fragment_Scan;
            }
        } else if (id == R.id.nav_profile) {
            if (mCurrentFragment != Fragment_Profile) {
                replacefragment(new ProfileFragment());
                mCurrentFragment = Fragment_Profile;
            }
        } else if (id == R.id.nav_information) {
            if (mCurrentFragment != Fragment_Information) {
                replacefragment(new InformationFragment());
                mCurrentFragment = Fragment_Information;
            }
        } else if (id == R.id.nav_sign_out) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, DangNhap.class);
            startActivity(intent);
            finish();
        }


        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void replacefragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }
    private void showUserInformation(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            return;
        }

        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();

        if (name == null){
            tvName.setVisibility(View.GONE);
        }else{
            tvName.setVisibility(View.VISIBLE);
            tvName.setText(name);
        }

        tvName.setText(name);
        tvEmail.setText(email);
        Glide.with(this).load(photoUrl).error(R.drawable.img).into(imgAvatar);
    }

}

