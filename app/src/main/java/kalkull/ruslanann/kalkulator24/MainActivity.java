package kalkull.ruslanann.kalkulator24;


import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import kalkull.ruslanann.kalkulator24.base_activity.BaseDrawerActivity;
import kalkull.ruslanann.kalkulator24.inner_fragmenrs.ScreenOne;

public class MainActivity extends BaseDrawerActivity {
    private static long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        createDrawer(toolbar);
        fragment = new ScreenOne();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
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
