package kalkull.ruslanann.kalkulator24.fragments.Dizel12;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import java.math.BigDecimal;

import kalkull.ruslanann.kalkulator24.R;
import kalkull.ruslanann.kalkulator24.base_fragment.BaseFragment;
import kalkull.ruslanann.kalkulator24.database.ToDoDatabase;

public class Dizel3 extends BaseFragment implements View.OnClickListener {
    private static final int ACTIVITY_CREATE = 0;
    private static final int ACTIVITY_EDIT = 1;
    private static final int DELETE_ID = Menu.FIRST + 1;

    private ToDoDatabase mDbHelper;
    private EditText etNam1;
    private EditText etEtNam2;
    private EditText etNam3;
    private EditText etNam4;

    private Button converter;

    private TextView tvResult;
    private TextView tvResult2;
    private TextView tvResult5;
    private TextView tv2;

    private RadioGroup mRadioOsGroup;
    private RadioGroup mRadioPredelGroup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public static Dizel3 getInstance(Context context) {
        Dizel3 fragment = new Dizel3();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.setTitle("Омы");
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dizel_om, container, false);
        mDbHelper = new ToDoDatabase(getActivity());

        // находим элементы
        etNam1 = (EditText) view.findViewById(R.id.etNam1);
        etEtNam2 = (EditText) view.findViewById(R.id.etNam2);
        etNam3 = (EditText) view.findViewById(R.id.etNam3);
        etNam4 = (EditText) view.findViewById(R.id.etNam4);
        converter = (Button) view.findViewById(R.id.button);

        tvResult = (TextView) view.findViewById(R.id.tvResultSave);
        tvResult2 = (TextView) view.findViewById(R.id.result2Save);
        tvResult5 = (TextView) view.findViewById(R.id.result5);
        tv2 = (TextView) view.findViewById(R.id.data2);

        mRadioOsGroup = (RadioGroup) view.findViewById(R.id.radio1);
        mRadioOsGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.x1:
                    perlaseMnozitel = "x1";
                    break;
                case R.id.x2:
                    perlaseMnozitel = "x2";
                    break;
                case R.id.x3:
                    perlaseMnozitel = "x3";
                    break;
            }
        });
        mRadioPredelGroup = (RadioGroup) view.findViewById(R.id.radioGroup1);
        mRadioPredelGroup.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            switch (checkedId){
                case R.id.x11:
                    perlasePredel = (float) 7.5;
                    break;
                case R.id.x22:
                    perlasePredel = (float) 0.75;
                    break;
            }
        });


        converter.setOnClickListener(this);
        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.save_basa, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_base:

                saveState();
                break;

            default:
                return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        final Animation animAlpha = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha);
        view.startAnimation(animAlpha);

        //определяем нажатую кнопку и выполняем соответсвующую операцию
        //в опер пишем операцию, потом будем использовать в выводе
        switch (view.getId()) {
            case R.id.button:
                oper = "  ";
                if (perlaseMnozitel.equals("0")|| perlasePredel==0) {
                    Toast.makeText(getActivity(), "выберите флажок x1, x2 или x3 ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (perlaseMnozitel.equals("x1")) {
                    float x = 1;
                    makeCount(x, perlasePredel);
                } else if (perlaseMnozitel.equals("x2")) {
                    float x = 2;
                    makeCount(x, perlasePredel);
                } else if (perlaseMnozitel.equals("x3")) {
                    float x = 3;
                    makeCount(x, perlasePredel);
                }
                break;
        }
    }


    private void makeCount(float x, float y) {
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
        result = (float) (num2 * y * x / 150 / num1);
        tvResult.setText(String.format(oper + "%.4f", result) + " " + "Ом");
        result = new BigDecimal(result).setScale(4, BigDecimal.ROUND_HALF_UP).floatValue();
        result2 = result * 250 / (235 + num3);
        tvResult2.setText(String.format(oper + "%.4f", result2) + " " + "Ом");
        result2 = new BigDecimal(result2).setScale(4, BigDecimal.ROUND_HALF_UP).floatValue();
        result3 = (result2 / num4) * 100 - 100;
        tvResult5.setText(String.format(oper + "%.4f", result3) + " " + "%");
        if (result3 != 0) {
            float i = result3;
            if (i > -1 && i < 1) tvResult5.setTextColor(Color.GREEN);
            else tvResult5.setTextColor(Color.RED);
        }
    }

    private void saveState() {
        String summary = (nomber);
        String description = tvResult.getText().toString();
        String descriptiona = tvResult2.getText().toString();
        String descriptionb = tvResult5.getText().toString();
        String amper = etNam1.getText().toString();
        String volt = etEtNam2.getText().toString();
        String gradus = etNam3.getText().toString();
        String first = etNam4.getText().toString();

        if (description.length() == 0 && summary.length() == 0) {
            return;
        }
        mDbHelper.createNewTrans(summary, description, descriptiona, descriptionb,
                amper, volt, gradus, first);
        Toast.makeText(getActivity(), "save", Toast.LENGTH_SHORT).show();
    }


    private void showSaveSdDialog() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View root = inflater.inflate(R.layout.save_dialog, null);

        final EditText editFileName = (EditText) root.findViewById(R.id.editFileName);

        editFileName.setText(mCurFileName);

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        builder.setView(root);
        builder.setTitle("Сохранение файла");

        builder.setPositiveButton("OK", (dialog, which) -> {
            // TODO Auto-generated method stub
            if (TextUtils.isEmpty(nomber.toString())) {
                Toast.makeText(getActivity(), "Данные ОБМОТКА И ПОЛОЖЕНИЕ не введены",
                        Toast.LENGTH_LONG).show();
            } else {
                saveFile(editFileName.getText().toString());
                mCurFileName = editFileName.getText().toString();
            }
        });

        builder.setNegativeButton("Отмена", (dialog, which) -> {
            // TODO Auto-generated method stub
            dialog.cancel();
        });
        builder.show();
    }

    private void saveFile(String fileName) {
        try {

            File file = new File(mPath, fileName);

            BufferedWriter bW;

            bW = new BufferedWriter(new FileWriter(file, true));
            massage = nomber + "\n"
                    + " изм А= "
                    + tvResult.getText().toString() + " | "
                    + "прив 20 С= "
                    + tvResult2.getText().toString() + " | "
                    + "расх= "
                    + tvResult5.getText().toString() + "\n"
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
