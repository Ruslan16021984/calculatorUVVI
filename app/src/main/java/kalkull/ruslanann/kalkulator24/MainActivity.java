package kalkull.ruslanann.kalkulator24;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;

import kalkull.ruslanann.kalkulator24.adapter.inner_adapter.ScreenOne;
import kalkull.ruslanann.kalkulator24.base_activity.BaseDrawerActivity;

public class MainActivity extends BaseDrawerActivity {
    private static long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int ACCESS_FINE_LOCATION = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int ACCESS_COARSE_LOCATION = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        Log.d("TAG", + ACCESS_COARSE_LOCATION + "" + ACCESS_COARSE_LOCATION);
        if (ACCESS_FINE_LOCATION == PackageManager.PERMISSION_GRANTED && ACCESS_COARSE_LOCATION ==PackageManager.PERMISSION_GRANTED) {
//            mapFragment.getMapAsync(this);
        } else {
            Log.d("TAG", "" + "DONT " + android.Manifest.permission.ACCESS_COARSE_LOCATION);
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSION_READ_CONTACTS);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        createDrawer(toolbar);
        fragment = new ScreenOne();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//                mapFragment.getMapAsync(this);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (item.getItemId()) {


            case R.id.action_data:
                tv2 = (TextView) findViewById(R.id.data41);
                long date = System.currentTimeMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("MMM MM dd, yyyy h:mm a");
                String dateString = sdf.format(date);
                tv2.setText(dateString);
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else
            Toast.makeText(getBaseContext(), "хотите выйти нажмите ещё раз!",
                    Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }


}
