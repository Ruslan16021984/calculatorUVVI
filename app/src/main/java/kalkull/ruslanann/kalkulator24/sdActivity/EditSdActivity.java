package kalkull.ruslanann.kalkulator24.sdActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;

import kalkull.ruslanann.kalkulator24.R;

public class EditSdActivity extends AppCompatActivity {
    // название папки на внешнем накопителе
    private static final String DIRECTORY_DOCS = "/documents";

    private EditText editSummary; // текстовое поле для ввода текста
    private EditText editor; // текстовое поле для ввода текста
    private String mCurFileName = ""; // имя текущего файла для работы
    private String mPath; // путь к файлу
    private int mPos = 0; // позиция при выборе имени файла в диалоговом окне
    private Long mRowId;
    private StringBuffer buffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sd);
        editor = (EditText)findViewById(R.id.editor);
        editSummary = (EditText)findViewById(R.id.todo_edit_summary);

        Bundle extras = getIntent().getExtras();
        mCurFileName = (String) extras.get("name");
        editSummary.setText(mCurFileName);

// сформируем путь для хранения файлов
        mPath = Environment.getExternalStorageDirectory().toString() + DIRECTORY_DOCS;

        File folder = new File(mPath);

// Если папки не существует, то создадим её
        if(!folder.exists()) {
            folder.mkdir();
        }
        openFile(mCurFileName);

    }
    private void openFile(String fileName) {
        try {
            File file = new File(mPath, fileName);
            FileInputStream fis = new FileInputStream(file);

            if (fis != null) {
                InputStreamReader inreader = new InputStreamReader(fis);
                BufferedReader reader = new BufferedReader(inreader);
                String str;
                buffer = new StringBuffer();

                while ((str = reader.readLine()) != null) {
                    buffer.append(str + "\n");
                }

                fis.close();
                editor.setText(buffer.toString());


                mCurFileName = fileName;

                // Выводим имя файла в заголовке
                setTitle("Результаты измерений");
            }
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
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

                long date = System.currentTimeMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("MMM MM dd, yyyy h:mm a");
                String dateString = sdf.format(date);


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
            Toast.makeText(EditSdActivity.this, e.toString(), Toast.LENGTH_LONG)
                    .show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(EditSdActivity.this, e.toString(), Toast.LENGTH_LONG)
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
        picMessageIntent.setType("plain/text");

        picMessageIntent.putExtra(Intent.EXTRA_TEXT, buffer.toString());

        startActivity(picMessageIntent);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.open_main, R.anim.close_next);
    }
}
