package kalkull.ruslanann.kalkulator24.database;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

import kalkull.ruslanann.kalkulator24.R;


public class DataSqlGen extends AppCompatActivity implements
        LoaderCallbacks<Cursor> {
    private ToDoDatabase dbHelper;
    private static final int ACTIVITY_CREATE = 0;
    private static final int ACTIVITY_EDIT = 1;
    private static final int DELETE_ID = Menu.FIRST + 1;
    // private Cursor cursor;
    private ListView listView;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        dbHelper = new ToDoDatabase(this);
        listView = (ListView) findViewById(R.id.list);
        fillData();
        registerForContextMenu(listView);


    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        getLoaderManager().getLoader(0).forceLoad();
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
                dbHelper.deleteTodo(info.id);
                getLoaderManager().getLoader(0).forceLoad();
                //fillData();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void createNewTask() {
        Intent intent = new Intent(this, EditSqlGen.class);
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
                Intent intent = new Intent(DataSqlGen.this, EditSqlGen.class);
                intent.putExtra(ToDoDatabase.COLUMN_ID, id);
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
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // TODO Auto-generated method stub
        return new MyCursorLoader(this, dbHelper);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // TODO Auto-generated method stub
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO Auto-generated method stub
        adapter.swapCursor(null);
    }

    static class MyCursorLoader extends CursorLoader {
        ToDoDatabase db;

        public MyCursorLoader(Context context, ToDoDatabase db) {
            super(context);
            this.db = db;
        }

        @Override
        public Cursor loadInBackground() {
            Cursor cursor = db.getAllTodos();

            return cursor;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.open_main, R.anim.close_next);
    }
}
