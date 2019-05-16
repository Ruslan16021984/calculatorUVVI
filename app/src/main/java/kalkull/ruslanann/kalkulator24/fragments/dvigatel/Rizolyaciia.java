package kalkull.ruslanann.kalkulator24.fragments.dvigatel;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
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
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import kalkull.ruslanann.kalkulator24.R;
import kalkull.ruslanann.kalkulator24.base_fragment.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class Rizolyaciia extends BaseFragment implements View.OnClickListener{
    private EditText etNam1;
    private EditText etEtNam2;
    private EditText etNam3;
    TextView tvResult, tvResult2, tvResult3;
//    private TextView nomber;
    private Button converter;
    private Button mStart;
    private Button mLap;
    private Button mStop;
    private int second = 0;
    private boolean running;
    private boolean wasRunning;
    private String oper = "";
    private Chronometer mChronometer;
    private String mCurFileName = "";


    public static Rizolyaciia getInstance(Context context) {
        Rizolyaciia fragment = new Rizolyaciia();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.setTitle(context.getString(R.string.riz));
        return fragment;
    }


    public Rizolyaciia() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (savedInstanceState != null) {
            second = savedInstanceState.getInt("second");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasrunning");
            if (wasRunning) {
                running = true;
            }
        }
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rizolyaciia, container, false);
        mChronometer = (Chronometer) view.findViewById(R.id.tv_time);

        etNam1 = (EditText)view.findViewById(R.id.etNam1);
        etEtNam2 = (EditText)view.findViewById(R.id.etNam2);
        etNam3 = (EditText)view.findViewById(R.id.etNam3);
        tvResult = (TextView) view.findViewById(R.id.tvResultSave);
        tvResult = (TextView)view.findViewById(R.id.tvResultSave);
        tvResult2 = (TextView)view.findViewById(R.id.tvResultSave2);
        tvResult3 = (TextView)view.findViewById(R.id.tvResultSave3);

        mStart = (Button) view.findViewById(R.id.bt_start);
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickStart( v);
                final Animation animAlpha = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha);
                v.startAnimation(animAlpha);
            }
        });
        mLap = (Button) view.findViewById(R.id.bt_lap);
        mLap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReset(v);
                final Animation animAlpha = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha);
                v.startAnimation(animAlpha);
            }
        });
        mStop = (Button) view.findViewById(R.id.bt_stop);
        converter = (Button)view.findViewById(R.id.button);
        converter.setOnClickListener(this);
        mStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickStop( v);
                final Animation animAlpha = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha);
                v.startAnimation(animAlpha);
            }
        });

        mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long elapsedMillis = SystemClock.elapsedRealtime()
                        - mChronometer.getBase();

                if (elapsedMillis > 5000) {
                    String strElapsedMillis = "Прошло больше 5 секунд";

                }
            }
        });


        return view;

    }
    @Override
    public void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (wasRunning){
            running = true;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("second", second);
        outState.putBoolean("running", running);
        outState.putBoolean("wasrunning", wasRunning);
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
        float result3 = 0;
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
                result = num1 * num3;
                tvResult.setText(oper +  result + " " + "MОм");
                result2 = num2 * num3;
                tvResult2.setText(oper + result2 + " " + "MОм");
                result3 = result2 / result;
                tvResult3.setText(oper +  result3 + " " + "Кабс");
                if (result3 != 0) {
                    float i = result3;
                    if (i > -2 && i < 2) tvResult3.setTextColor(Color.GREEN);
                    else tvResult3.setTextColor(Color.RED);
                }


                break;
        }
    }

    public void onReset(View v) {
        mChronometer.setBase(SystemClock.elapsedRealtime());
    }

    public void onClickStop(View v) {
        mChronometer.stop();
    }

    public void onClickStart(View v) {

        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.start();
    }
//    private void showSaveFileDialog(){
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        View root = inflater.inflate(R.layout.save_dialog, null);
//
//        final EditText editFileName = (EditText)root.findViewById(R.id.editFileName);
//        editFileName.setText(mCurFileName);
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setView(root);
//        builder.setTitle("Дополнительные данные");
//
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                nomber.setText(editFileName.getText().toString());
//
//            }
//        });
//
//        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // TODO Auto-generated method stub
//                dialog.cancel();
//            }
//        });
//        builder.show();
//    }
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
                    +" R изоляция 15с= "
                    + tvResult.getText().toString() + " | "
                    +"R изоляция 60с= "
                    + tvResult2.getText().toString() + " | "
                    +"K абс= "
                    + tvResult3.getText().toString()+ "\n"
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
