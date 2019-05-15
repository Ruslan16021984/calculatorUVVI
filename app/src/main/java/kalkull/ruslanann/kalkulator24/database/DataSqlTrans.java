package kalkull.ruslanann.kalkulator24.database;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import kalkull.ruslanann.kalkulator24.R;


public class DataSqlTrans extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    private ToDoDatabase dbHelper;
    private static final int ACTIVITY_CREATE = 0;
    private static final int ACTIVITY_EDIT = 1;
    private static final int DELETE_ID = Menu.FIRST + 1;
    private ListView listView;
    private SimpleCursorAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data2);
        ;
        dbHelper = new ToDoDatabase(this);
        listView = (ListView) findViewById(R.id.list);
        fillData();
        registerForContextMenu(listView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainb, menu);
        return true;
    }
    // Реакция на выбор меню


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.insert:
                createNewTask();
                break;
            case android.R.id.home:
                overridePendingTransition(R.anim.open_main, R.anim.close_next);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case DELETE_ID:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                        .getMenuInfo();
                dbHelper.deleteTans(info.id);
                getLoaderManager().getLoader(0).forceLoad();
                fillData();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void createNewTask() {
        Intent intent = new Intent(this, EditSqlTrans.class);
        startActivityForResult(intent, ACTIVITY_CREATE);
        overridePendingTransition(R.anim.open_next, R.anim.close_main);
    }

    private void fillData() {


        String[] from = new String[]{ToDoDatabase.COLUMN_SUMMARY};
        int[] to = new int[]{R.id.label};

        // Теперь создадим адаптер массива и установим его для отображения наших
        // данных
        adapter = new SimpleCursorAdapter(this,
                R.layout.list_row, null, from, to, 0);
        listView.setAdapter(adapter);
        getLoaderManager().initLoader(0, null, this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DataSqlTrans.this, EditSqlTrans.class);
                intent.putExtra(ToDoDatabase.TRANS_ID, id);
                // активити вернет результат если будет вызвано с помощью этого метода
                startActivityForResult(intent, ACTIVITY_EDIT);
                overridePendingTransition(R.anim.open_next, R.anim.close_main);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (resultCode == RESULT_OK) {
            fillData();
        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE_ID, 0, R.string.menu_delete);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.open_main, R.anim.close_next);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new MyCursorLoader(this, dbHelper);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLoaderManager().getLoader(0).forceLoad();
    }

    static class MyCursorLoader extends CursorLoader {
        ToDoDatabase db;

        public MyCursorLoader(Context context, ToDoDatabase db) {
            super(context);
            this.db = db;
        }

        @Override
        public Cursor loadInBackground() {
            Cursor cursor = db.getAllTrans();

            return cursor;
        }
    }
}
