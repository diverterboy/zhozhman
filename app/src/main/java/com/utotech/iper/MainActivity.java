package com.utotech.iper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;


import com.google.android.material.navigation.NavigationView;
import com.utotech.iper.mvp.ContractMainActivity;
import com.utotech.iper.mvp.Model;
import com.utotech.iper.mvp.PresenterMainActivity;



public class MainActivity extends AppCompatActivity implements ContractMainActivity.View {
    public static Toolbar toolbar;
    NavigationView navigationView;
    ImageView btnNavMenu, btn_setting, btnHelp;
    DrawerLayout drawerLayout;
    NavController controller;
    ContractMainActivity.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initial();
        setupNotif();
        btnsOnClick(btnNavMenu, btn_setting, btnHelp);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                navigationSwitch(item.getItemId());
                return false;
            }
        });
    }

    @Override
    public void initial() {
        controller = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment);
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);
        btnNavMenu = findViewById(R.id.btnNavMenu);
        btn_setting = findViewById(R.id.btn_setting);
        navigationView = findViewById(R.id.navigationView);
        btnHelp = findViewById(R.id.btnHelp);
        presenter = new PresenterMainActivity(new Model(getApplicationContext()), getApplicationContext(), controller, drawerLayout);
    }

    @Override
    public void setupNotif() {
        presenter.setupNotif();
    }

    @Override
    public void navigationSwitch(int id) {
        presenter.switchCaseNavView(id);

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void btnsOnClick(ImageView btnNavMenu, ImageView btn_setting, ImageView btnHelp) {
        presenter.btnsOnClick(btnNavMenu, btn_setting, btnHelp);
    }


}