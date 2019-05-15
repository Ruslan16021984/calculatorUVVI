package kalkull.ruslanann.kalkulator24.fragments.dvigatel;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import kalkull.ruslanann.kalkulator24.R;
import kalkull.ruslanann.kalkulator24.base.BaseFragment;
import kalkull.ruslanann.kalkulator24.sdActivity.SdActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class OmFrag extends BaseFragment implements View.OnClickListener{
    private EditText etNam1;
    private EditText etEtNam2;
    private EditText etNam3;
    private Button converter;
    private TextView tvResult;
    private TextView tvResult2;
    private String oper = "";



    public OmFrag() {
        // Required empty public constructor
    }
    public static OmFrag getInstance(Context context) {
        OmFrag fragment = new OmFrag();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.setTitle(context.getString(R.string.om));
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_om, container, false);
        etNam1 = (EditText)view.findViewById(R.id.etNam1);
        etEtNam2 = (EditText)view.findViewById(R.id.etNam2);
        etNam3 = (EditText)view.findViewById(R.id.etNam3);

        converter = (Button)view.findViewById(R.id.button);

        tvResult = (TextView)view.findViewById(R.id.tvResultSave);
        tvResult2 = (TextView)view.findViewById(R.id.result2Save);
        converter.setOnClickListener(this);
        // Inflate the layout for this fragment
        return view;
    }
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.save_basa, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_base:

//                saveState();
                break;

            default:
                return false;
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
        float result = 0;
        float result2 = 0;
        //проаеряем поля на пустоту
        if (TextUtils.isEmpty(etNam1.getText().toString()) || TextUtils.isEmpty(etEtNam2.getText().toString())) {
            return;
        }else if (TextUtils.isEmpty(etNam3.getText().toString())) {
            return;

        }
        //читаем эдиттекст и заполняем переменные числами
        num1 = Float.parseFloat(etNam1.getText().toString());
        num2 = Float.parseFloat(etEtNam2.getText().toString());
        num3 = Float.parseFloat(etNam3.getText().toString());
        //определяем нажатую кнопку и выполняем соответсвующую операцию
        //в опер пишем операцию, потом будем использовать в выводе
        switch (v.getId()) {
            case R.id.button:
                oper = " = ";
                result = num1 * 255/(235 + num2);
                tvResult.setText(String.format(oper + "%.4f", result) + " " + "Ом");
                result = Float.parseFloat(String.format(oper + "%.4f", result));
                result2 =(result/num3) * 100-100;
                tvResult2.setText(String.format(oper + "%.4f", result2) + " " + "%");
                if (result2 != 0) {
                    float i = result2;
                    if (i > -2 && i < 2) tvResult2.setTextColor(Color.GREEN);
                    else tvResult2.setTextColor(Color.RED);
                }

                break;

        }
    }
    private void showSaveSdDialog() {
//        initSppiner();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View root = inflater.inflate(R.layout.save_dialog, null);

        final EditText editFileName = (EditText) root.findViewById(R.id.editFileName);

        editFileName.setText(mCurFileName);

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        builder.setView(root);
        builder.setTitle("Сохранение файла");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
//                if (TextUtils.isEmpty(nomber.toString())) {
//                    Toast.makeText(getActivity(), "Данные ОБМОТКА И ПОЛОЖЕНИЕ не введены",
//                            Toast.LENGTH_LONG).show();
//                }else {
                saveFile(editFileName.getText().toString());
                mCurFileName = editFileName.getText().toString();
//                }
            }
        });

        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void saveFile(String fileName) {
        try {

            File file = new File(mPath, fileName);

            BufferedWriter bW;

            bW = new BufferedWriter(new FileWriter(file,true));
            massage = nomber.toString()+"\n"
                    +" изм = "
                    + etNam1.getText().toString() + " | "
                    +" приведенное к 20= "
                    + tvResult.getText().toString() + " | "
                    +" прцент расхождения= "
                    + tvResult2.getText().toString() + "\n"
                    + "****************************";
            bW.write(massage);
            bW.newLine();
            bW.flush();
            bW.close();
            Toast.makeText(getActivity(), "Успешно сохранено на карту памяти", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

}
