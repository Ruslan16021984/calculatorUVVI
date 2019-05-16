package kalkull.ruslanann.kalkulator24.base_fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.File;

import kalkull.ruslanann.kalkulator24.database.ToDoDatabase;

/**
 * Created by user on 07.10.2016.
 */

public class BaseFragment extends Fragment {
    private String title;
    protected ToDoDatabase mDbHelper;
    protected Context context;
    protected View view;
    protected static final String DIRECTORY_DOCS = "/documents";
    protected File  mPath;
    protected int mPos = 0;
    protected String oper = "";
    protected String mCurFileName;
    protected String c = "0";
    protected String massage;
    protected Spinner sPoloz;
    protected Spinner sFaza;
    protected String nomber ="";
    protected String fnomber ="";
    protected String snomber ="";
    protected ArrayAdapter<String> spAdapter;
    protected ArrayAdapter<String> fpAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        mPath = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS);

    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setContext(Context context) {
        this.context = context;
    }
}
