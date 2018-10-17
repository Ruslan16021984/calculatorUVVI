package kalkull.ruslanann.kalkulator24.fragments.dvigatel;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import kalkull.ruslanann.kalkulator24.R;
import kalkull.ruslanann.kalkulator24.base.BaseFragment;
import kalkull.ruslanann.kalkulator24.sdActivity.SdActivity;

/**
 * Created by CARD on 09.04.2016.
 */
public class OmCircleFrag extends BaseFragment implements View.OnClickListener {
    private EditText etNam1;
    private EditText etEtNam2;
    private EditText etNam3;
    private EditText etNam4;
    private Button converter;
    private TextView tvResult;
    private TextView tvResult2;
    private TextView tvResult3;
    private RadioGroup mRadioOsGroup;
    private RadioButton mSelRadio;
    private String c = "0";
    private static final float X1 = 1;
    private static final float X2 = 2;
    private static final float X3 = 3;

    private String oper = "";

    public static OmCircleFrag getInstance(Context context) {
        OmCircleFrag omCircleFrag = new OmCircleFrag();
        Bundle args = new Bundle();
        omCircleFrag.setArguments(args);
        omCircleFrag.setTitle(context.getString(R.string.omCircle));
        return omCircleFrag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.omcirclefrag, container, false);
        etNam1 = (EditText) view.findViewById(R.id.etNam1);
        etEtNam2 = (EditText) view.findViewById(R.id.etNam2);
        etNam3 = (EditText) view.findViewById(R.id.etNam3);
        etNam4 = (EditText) view.findViewById(R.id.etNam4);
        tvResult = (TextView) view.findViewById(R.id.tvResultSave);
        tvResult2 = (TextView) view.findViewById(R.id.result2Save);
        tvResult3 = (TextView) view.findViewById(R.id.result5);
        converter = (Button) view.findViewById(R.id.button);
        mRadioOsGroup = (RadioGroup) view.findViewById(R.id.radio3);
        converter.setOnClickListener(this);
        mRadioOsGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.x1:
                        c = "x1";
                        break;
                    case R.id.x2:
                        c = "x2";
                        break;
                    case R.id.x3:
                        c = "x3";
                        break;
                }
            }
        });

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
            case R.id.action_data1:
//                showSaveFileDialog();
                break;
            case R.id.action_save:
                showSaveSdDialog();
                break;
            case R.id.action_Shown:
                Intent intent = new Intent(getActivity(), SdActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.open_next, R.anim.close_main);
                break;

            default:
                return false;
        }
        return true;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        final Animation animAlpha = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha);
        v.startAnimation(animAlpha);


        //определяем нажатую кнопку и выполняем соответсвующую операцию
        //в опер пишем операцию, потом будем использовать в выводе
        switch (v.getId()) {
            case R.id.button:
                oper = " = ";
                if (c.equals("0")) {

                    Toast.makeText(getActivity(), "Выберите галочку", Toast.LENGTH_SHORT).show();
                    return;
                } else if (c.equals("x1")) {
                    float x = 1;
                    makeCount(x);
                } else if (c.equals("x2")) {
                    float x = 2;
                    makeCount(x);

                } else if (c.equals("x3")) {
                    float x = 3;
                    makeCount(x);
                }


                break;
        }
    }

    private void makeCount(float x) {
        float num1 = 0;
        float num2 = 0;
        float num3 = 0;
        float num4 = 0;
        float result = 0;
        float result2 = 0;
        float result3 = 0;
        //проаеряем поля на пустоту
        if (TextUtils.isEmpty(etNam1.getText().toString()) || TextUtils.isEmpty(etEtNam2.getText().toString())) {
            return;
        } else if (TextUtils.isEmpty(etNam3.getText().toString()) || TextUtils.isEmpty(etNam4.getText().toString())) {
            return;

        }
        //читаем эдиттекст и заполняем переменные числами
        num1 = Float.parseFloat(etNam1.getText().toString());
        num2 = Float.parseFloat(etEtNam2.getText().toString());
        num3 = Float.parseFloat(etNam3.getText().toString());
        num4 = Float.parseFloat(etNam4.getText().toString());
        result = (float) (num2 * x * 0.75 / 150 / num1);
        tvResult.setText(String.format(oper + "%.4f", result) + " " + "Ом");
        result = Float.parseFloat(String.format(oper + "%.4f", result));
        result2 = result * 255 / (235 + num3);
        tvResult2.setText(String.format(oper + "%.4f", result2) + " " + "Ом");
        result2 = Float.parseFloat(String.format(oper + "%.4f", result2));
        result3 = (result2 / num4) * 100 - 100;
        tvResult3.setText(oper + result3 + " " + "%");
        if (result3 != 0) {
            float i = result3;
            if (i > -1 && i < 1) tvResult3.setTextColor(Color.GREEN);
            else tvResult3.setTextColor(Color.RED);
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
                    +" результат при 9 (10)А = "
                    + tvResult.getText().toString() + " | "
                    +" приведенное к 20= "
                    + tvResult2.getText().toString() + " | "
                    +" прцент расхождения= "
                    + tvResult3.getText().toString() + "\n"
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
