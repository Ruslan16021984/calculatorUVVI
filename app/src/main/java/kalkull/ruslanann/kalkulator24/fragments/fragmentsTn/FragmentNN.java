package kalkull.ruslanann.kalkulator24.fragments.fragmentsTn;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import kalkull.ruslanann.kalkulator24.R;
import kalkull.ruslanann.kalkulator24.base.BaseFragment;

/**
 * Created by CARD on 09.01.2016.
 */
public class FragmentNN extends BaseFragment implements View.OnClickListener {
    private EditText etNam1;
    private EditText etEtNam2;
    private EditText etNam3;
    private Button converter;
    private TextView tvResult;
    private TextView tvResult2;
    private String oper = "";

    public static FragmentNN getInstance(Context context) {
        FragmentNN fragment = new FragmentNN();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.setTitle(context.getString(R.string.item_tn));
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loan_calculator, container, false);
        etNam1 = (EditText)view.findViewById(R.id.etNam1);
        etEtNam2 = (EditText)view.findViewById(R.id.etNam2);
        etNam3 = (EditText)view.findViewById(R.id.etNam3);

        converter = (Button)view.findViewById(R.id.button);

        tvResult = (TextView)view.findViewById(R.id.tvResultSave);
        tvResult2 = (TextView)view.findViewById(R.id.result2Save);

        converter.setOnClickListener(this);

        return view;
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
    public void setContext(Context context) {
        this.context = context;
    }

}
