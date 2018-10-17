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


public class Edit2Activity extends AppCompatActivity {

   private TextView info;
   private TextView resolt;
   private TextView resolt2;
   private TextView resolt3;
   private TextView amper;
   private TextView volt;
   private TextView gradus;
   private TextView first;
    private Long mRowId;
    private ToDoDatabase mDbHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbHelper = new ToDoDatabase(this);
        setContentView(R.layout.activity_edit2);
        info = (TextView) findViewById(R.id.todo_edit_summary);
        resolt = (TextView) findViewById(R.id.tvResultSave);
        resolt2 = (TextView) findViewById(R.id.result2Save);
        resolt3 = (TextView) findViewById(R.id.result5);
        amper = (TextView) findViewById(R.id.tvAmper);
        volt = (TextView) findViewById(R.id.tvVolt);
        gradus = (TextView) findViewById(R.id.tvGradus);
        first = (TextView) findViewById(R.id.tvFirst);


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
                    Toast.makeText(Edit2Activity.this, "Данные не введены",
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
            Cursor totans = mDbHelper.getTrans(mRowId);

            startManagingCursor(totans);

            info.setText(totans.getString(totans
                    .getColumnIndexOrThrow(ToDoDatabase.COLUMN_SUMMARY)));
            resolt.setText(totans.getString(totans
                    .getColumnIndexOrThrow(ToDoDatabase.COLUMN_AMPER)));
            resolt2.setText(totans.getString(totans
                    .getColumnIndexOrThrow(ToDoDatabase.COLUMN_GRADUS)));
            resolt3.setText(totans.getString(totans
                    .getColumnIndexOrThrow(ToDoDatabase.COLUMN_PROCENT)));
            amper.setText(totans.getString(totans
                    .getColumnIndexOrThrow(ToDoDatabase.COLUMN_AMPERN)));
            volt.setText(totans.getString(totans
                    .getColumnIndexOrThrow(ToDoDatabase.COLUMN_VOLTN)));
            gradus.setText(totans.getString(totans
                    .getColumnIndexOrThrow(ToDoDatabase.COLUMN_GRADUSN)));
            first.setText(totans.getString(totans
                    .getColumnIndexOrThrow(ToDoDatabase.COLUMN_FIRST)));
            totans.close();


        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState();
        outState.putSerializable(ToDoDatabase.COLUMN_ID, mRowId);
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
        String ampern = amper.getText().toString();
        String voltn = volt.getText().toString();
        String gradusn = gradus.getText().toString();
        String firstn = first.getText().toString();

        if (description.length() == 0 && summary.length() == 0) {
            return;
        }

        if (mRowId == null) {
            long id = mDbHelper.createNewTrans(summary, description, descriptiona,
                    descriptionb, ampern, voltn, gradusn, firstn);
            if (id > 0) {
                mRowId = id;
            }
        } else {
            mDbHelper.updateTrans(mRowId, summary, description, descriptiona,
                    descriptionb, ampern, voltn, gradusn, firstn);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.open_main, R.anim.close_next);
    }
}
