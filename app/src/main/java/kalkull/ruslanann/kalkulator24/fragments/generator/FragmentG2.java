package kalkull.ruslanann.kalkulator24.fragments.generator;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import kalkull.ruslanann.kalkulator24.R;
import kalkull.ruslanann.kalkulator24.base_fragment.BaseFragment;

/**
 * Created by CARD on 12.01.2016.
 */
public class FragmentG2 extends BaseFragment implements View.OnClickListener {


    private SQLiteDatabase mDB;
    private CheckBox checkboxCosF;
    private SharedPreferences sPref;
    private EditText etNam;
    private EditText etNam1;
    private EditText etNam2;

    private EditText etNam20;
    private EditText etNam21;
    private EditText etNam22;
    private EditText etNam23;

    private EditText volt30;
   private EditText volt31;
    private EditText volt32;

    private Button converter;
    private TextView tvResult;
    private TextView tvResult2;
    private TextView tvResult3;
    private String oper = "";

    public static FragmentG2 getInstance(Context context) {
        FragmentG2 fragment = new FragmentG2();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.setTitle(context.getString(R.string.fragmentG2));
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_g2, container, false);
        etNam = (EditText) view.findViewById(R.id.etNam);
        etNam1 = (EditText) view.findViewById(R.id.etNam1);
        etNam2 = (EditText) view.findViewById(R.id.etNam2);

        etNam20 = (EditText) view.findViewById(R.id.etNam20);
        etNam21 = (EditText) view.findViewById(R.id.etNam21);
        etNam22 = (EditText) view.findViewById(R.id.etNam22);
        etNam23 = (EditText) view.findViewById(R.id.etNam23);

        volt30 = (EditText) view.findViewById(R.id.volt30);
        volt31 = (EditText) view.findViewById(R.id.volt31);
        volt32 = (EditText) view.findViewById(R.id.volt32);
        checkboxCosF = (CheckBox) view.findViewById(R.id.checkBox);
        converter = (Button) view.findViewById(R.id.button);

        tvResult = (TextView) view.findViewById(R.id.tvResultSave);
        tvResult2 = (TextView) view.findViewById(R.id.result2Save);
        tvResult3 = (TextView) view.findViewById(R.id.result3Save);


        converter.setOnClickListener(this);

        return view;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);





    }
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.save, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            case R.id.action_base:

                break;

         }

        return true;
    }

    @Override
    public void onClick(View v) {
        final Animation animAlpha = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha);
        v.startAnimation(animAlpha);
        float num1 = 0;
        float num2 = 0;
        float num3 = 0;

        float num30 = 0;
        float num31 = 0;
        float num32 = 0;
        float num33 = 0;

        float vol30 = 0;
        float vol31 = 0;
        float vol32 = 0;

        float result = 0;
        float result2 = 0;
        float result3 = 0;
        if (TextUtils.isEmpty(etNam.getText().toString()) || TextUtils.isEmpty(etNam1.getText().toString())) {
            return;
        } else if (TextUtils.isEmpty(etNam20.getText().toString()) || TextUtils.isEmpty(etNam21.getText().toString())) {
            return;

        } else if (TextUtils.isEmpty(etNam22.getText().toString()) || TextUtils.isEmpty(volt30.getText().toString())) {
            return;
        }else if (TextUtils.isEmpty(etNam23.getText().toString())) {
            return;
        } else if (TextUtils.isEmpty(volt31.getText().toString()) || TextUtils.isEmpty(volt32.getText().toString())) {
            return;
        }
        num1 = Float.parseFloat(etNam.getText().toString());
        num2 = Float.parseFloat(etNam1.getText().toString());
        num3 = Float.parseFloat(etNam2.getText().toString());

        num30 = Float.parseFloat(etNam20.getText().toString());
        num31 = Float.parseFloat(etNam21.getText().toString());
        num32 = Float.parseFloat(etNam22.getText().toString());
        num33 = Float.parseFloat(etNam23.getText().toString());

        vol30 = Float.parseFloat(volt30.getText().toString());
        vol31 = Float.parseFloat(volt31.getText().toString());
        vol32 = Float.parseFloat(volt32.getText().toString());
        switch (v.getId()) {
            case R.id.button:
                oper = "  ";
                result = (num1 / num2) * num3 * 240;
                tvResult.setText(oper + result+ " " + "А");
                if (checkboxCosF.isChecked())
                    result2 = (float) ((num30 / num31) * num32 * 0.1 * num33 * 7200);
                else
                    result2 = (num30 / num31) * num32 * num33 * 7200;

                tvResult2.setText(oper + result2 + " " + "Вт");
                result3 = (vol30 / vol31) * vol32 * 30;
                tvResult3.setText(oper + result3 + " " + "В");



                break;

        }
    }






}
