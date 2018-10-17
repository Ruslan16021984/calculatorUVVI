package kalkull.ruslanann.kalkulator24.sdActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import kalkull.ruslanann.kalkulator24.R;
import kalkull.ruslanann.kalkulator24.database.EditActivity;

public class SdActivity extends AppCompatActivity {
    private static final String DIRECTORY_DOCS = "/documents";
    private static final int ACTIVITY_CREATE = 0;
    private static final int ACTIVITY_EDIT = 1;
    private static final int DELETE_ID = Menu.FIRST + 1;
    private ListView listView;
    private String mPath;
    private String mCurFileName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sd);
        listView = (ListView) findViewById(R.id.list);

        mPath = Environment.getExternalStorageDirectory().toString() + DIRECTORY_DOCS;

        File folder = new File(mPath);

// Если папки не существует, то создадим её
        if (!folder.exists()) {
            folder.mkdir();
        }
        fillData();
        registerForContextMenu(listView);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.mainb, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.insert:
                createNewTask();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createNewTask() {
        Intent intent = new Intent(this, EditActivity.class);
        startActivityForResult(intent, ACTIVITY_CREATE);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case DELETE_ID:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                        .getMenuInfo();

                getLoaderManager().getLoader(0).forceLoad();
                fillData();
                return true;
        }
        return super.onContextItemSelected(item);
    }
    private void fillData() {


        final String[] files = getFiles(mPath);

        // Теперь создадим адаптер массива и установим его для отображения наших
        // данных
        ArrayAdapter<String> notes = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, files);
        listView.setAdapter(notes);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SdActivity.this, EditSdActivity.class);
                mCurFileName = files[position];
                intent.putExtra("name", mCurFileName);
                // активити вернет результат если будет вызвано с помощью этого метода
                startActivityForResult(intent, ACTIVITY_EDIT);
                overridePendingTransition(R.anim.open_next, R.anim.close_main);

            }
        });





    }
    //    Получение списка файлов в выбранной папке
    private String[] getFiles(String dirPath) {
        ArrayList<String> items = new ArrayList<String>();
        try {
            File file = new File(dirPath);
            File[] files = file.listFiles();

            for (File afile : files) {
                if (!afile.isDirectory()) {
                    items.add(afile.getName());
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
        return items.toArray(new String[items.size()]);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE_ID, 0, R.string.menu_delete);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.open_main, R.anim.close_next);
    }

}
