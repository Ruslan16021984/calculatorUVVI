package kalkull.ruslanann.kalkulator24.database;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import kalkull.ruslanann.kalkulator24.R;


public class EditSqlGen extends AppCompatActivity {
    private TextView info;
    private TextView resolt;
    private TextView resolt2;
    private TextView resolt3;
    private TextView resolt4;
    private TextView resolt5;

    private Long mRowId;
    private ToDoDatabase mDbHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbHelper = new ToDoDatabase(this);
        setContentView(R.layout.activity_edit);

        info = (TextView) findViewById(R.id.todo_edit_summary);
        resolt = (TextView) findViewById(R.id.tvResultSave);
        resolt2 = (TextView) findViewById(R.id.result4);
        resolt3 = (TextView) findViewById(R.id.result2Save);
        resolt4 = (TextView) findViewById(R.id.result3Save);
        resolt5 = (TextView) findViewById(R.id.resulTime);

        Button confirmButton = (Button) findViewById(R.id.todo_edit_button);
        mRowId = null;
        Bundle extras = getIntent().getExtras();

        mRowId = (savedInstanceState == null) ? null
                : (Long) savedInstanceState
                .getSerializable(ToDoDatabase.COLUMN_ID);
        if (extras != null) {
            mRowId = extras.getLong(ToDoDatabase.COLUMN_ID);
        }

        populateFields();
        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (TextUtils.isEmpty(info.getText().toString())) {
                    Toast.makeText(EditSqlGen.this, "Данные не введены",
                            Toast.LENGTH_LONG).show();
                } else {
                    saveState();
                    setResult(RESULT_OK);
                    finish();
                    overridePendingTransition(R.anim.open_main, R.anim.close_next);
                }
            }
        });


    }
    private void populateFields() {
        if (mRowId != null) {
            Cursor todo = mDbHelper.getTodo(mRowId);
            startManagingCursor(todo);


            info.setText(todo.getString(todo
                    .getColumnIndexOrThrow(ToDoDatabase.COLUMN_SUMMARY)));
            resolt.setText(todo.getString(todo
                    .getColumnIndexOrThrow(ToDoDatabase.COLUMN_DESCRIPTION)));
            resolt2.setText(todo.getString(todo
                    .getColumnIndexOrThrow(ToDoDatabase.COLUMN_DESCRIPTIONA)));
            resolt3.setText(todo.getString(todo
                    .getColumnIndexOrThrow(ToDoDatabase.COLUMN_DESCRIPTIONB)));
            resolt4.setText(todo.getString(todo
                    .getColumnIndexOrThrow(ToDoDatabase.COLUMN_DESCRIPTIONC)));
            resolt5.setText(todo.getString(todo
                    .getColumnIndexOrThrow(ToDoDatabase.COLUMN_DESCRIPTIOND)));
            todo.close();


        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //saveState();
        //outState.putSerializable(ToDoDatabase.COLUMN_ID, mRowId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //saveState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateFields();
    }
    private void saveState() {

        String summary = info.getText().toString();
        String description = resolt.getText().toString();
        String descriptiona = resolt2.getText().toString();
        String descriptionb = resolt3.getText().toString();
        String descriptionc = resolt4.getText().toString();
        String descriptiond = resolt5.getText().toString();

        if (description.length() == 0 && summary.length() == 0) {
            return;
        }

        if (mRowId == null) {
            long id = mDbHelper.createNewTodo(summary, description, descriptiona,
                    descriptionb, descriptionc, descriptiond);
            if (id > 0) {
                mRowId = id;
            }
        } else {
            mDbHelper.updateTodo(mRowId, summary, description, descriptiona,
                    descriptionb, descriptionc, descriptiond);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.open_main, R.anim.close_next);
    }
}
