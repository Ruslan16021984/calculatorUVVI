package kalkull.ruslanann.kalkulator24;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import kalkull.ruslanann.kalkulator24.screenfragments.ScreenOne;

public class MainActivity extends BaseDrawerActivity {
    private static long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPath = Environment.getExternalStorageDirectory().toString() + SCREEN_SHOTS_LOCATION;
        File folder = new File(mPath);

        // Если папки не существует, то создадим её
        if (!folder.exists()) {
            folder.mkdir();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.action_search:
                captureScreen();
                break;

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

    private void captureScreen() {
        View v = getWindow().getDecorView().getRootView();
        v.setDrawingCacheEnabled(true);
        Bitmap bmp = Bitmap.createBitmap(v.getDrawingCache());
        v.setDrawingCacheEnabled(false);
        File picDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File picFile = new File(picDir + "/" + "card.png");

        try {
            picFile.createNewFile();
            FileOutputStream picOut = new FileOutputStream(picFile);
            boolean worked = bmp.compress(Bitmap.CompressFormat.PNG, 100,
                    picOut);
            if (worked) {
                Toast.makeText(
                        getApplicationContext(),
                        "Image saved to your device Pictures "
                                + "directory!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Whoops! File not saved.", Toast.LENGTH_SHORT)
                        .show();
            }
            picOut.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG)
                    .show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG)
                    .show();
        }
        Intent picMessageIntent = new Intent(Intent.ACTION_SEND);
        picMessageIntent.setType("image/jpeg");
        File downloadedPic = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "card.png");

        picMessageIntent.putExtra(Intent.EXTRA_STREAM,
                Uri.fromFile(downloadedPic));

        startActivity(picMessageIntent);

    }
    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis())

            super.onBackPressed();

        else
            Toast.makeText(getBaseContext(), "хотите выйти нажмите ещё раз!",
                    Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }
}
