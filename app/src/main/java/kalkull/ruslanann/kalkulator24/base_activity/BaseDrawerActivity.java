package kalkull.ruslanann.kalkulator24.base_activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

import kalkull.ruslanann.kalkulator24.R;
import kalkull.ruslanann.kalkulator24.adapter.inner_adapter.ScreenDV;
import kalkull.ruslanann.kalkulator24.adapter.inner_adapter.ScreenDizel;
import kalkull.ruslanann.kalkulator24.adapter.inner_adapter.ScreenOne;
import kalkull.ruslanann.kalkulator24.adapter.inner_adapter.ScreenThree;
import kalkull.ruslanann.kalkulator24.adapter.inner_adapter.ScreenTwo;
import kalkull.ruslanann.kalkulator24.database.DataSqlGen;
import kalkull.ruslanann.kalkulator24.database.DataSqlTrans;

/**
 * Created by user on 27.10.2016.
 */

public abstract class BaseDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    protected static final String SCREEN_SHOTS_LOCATION = "/screenshots";
    protected static final int REQUEST_CODE_PERMISSION_READ_CONTACTS = 123;

    protected String mPath;
    protected TextView tv2;;
    protected Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    protected void createDrawer(Toolbar toolbar) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        fragment = null;
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            fragment = new ScreenOne();
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            fragment = new ScreenTwo();

        } else if (id == R.id.nav_slideshow) {
            fragment = new ScreenThree();

        } else if (id == R.id.nav_manage) {
            fragment = new ScreenDizel();

        } else if (id == R.id.nav_dv) {
            fragment = new ScreenDV();

        } else if (id == R.id.nav_share) {
            Intent intent = new Intent(this, DataSqlTrans.class);
            startActivity(intent);
            overridePendingTransition(R.anim.open_next, R.anim.close_main);
        } else if (id == R.id.nav_send){
            Intent intent2 = new Intent(this, DataSqlGen.class);
            startActivity(intent2);
            overridePendingTransition(R.anim.open_next, R.anim.close_main);
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment).commit();
        }
        setTitle(item.getTitle());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    // хранит значение, как отработал метод onBackPressed, чтобы понимать что должны делать кнопка back - либо закрывать drawer, либо другое действие, указанное в переопределенном дочернем методе
    protected boolean drawerClosed;


     //закрывает drawer кнопкой back, если он активен
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            drawerClosed = true;

        } else{
            drawerClosed = false;
        }

    }
}
