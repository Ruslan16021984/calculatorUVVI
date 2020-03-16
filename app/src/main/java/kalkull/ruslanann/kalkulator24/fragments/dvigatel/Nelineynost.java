package kalkull.ruslanann.kalkulator24.fragments.dvigatel;


import android.content.Context;
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
import kalkull.ruslanann.kalkulator24.base_fragment.BaseFragment;

/**
 */
public class Nelineynost extends BaseFragment implements View.OnClickListener{

    private EditText etNam;
    private EditText etNam1;
    private EditText etNam2;
    private Button converter;

    private EditText etNam20;
    private EditText etNam21;
    private EditText etNam22;
    private TextView tvResult;
    private String oper = "";
    public static Nelineynost getInstance(Context context) {
        Nelineynost fragment = new Nelineynost();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.setTitle(context.getString(R.string.n));
        return fragment;
    }


    public Nelineynost() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nelineynost, container, false);
        etNam = (EditText) view.findViewById(R.id.etNam1);
        etNam1 = (EditText) view.findViewById(R.id.etNam2);
        etNam2 = (EditText) view.findViewById(R.id.etNam3);

        etNam20 = (EditText) view.findViewById(R.id.etNam4);
        etNam21 = (EditText) view.findViewById(R.id.etNam5);
        etNam22 = (EditText) view.findViewById(R.id.etNam6);
        tvResult = (TextView) view.findViewById(R.id.tvResultSave);
        converter = (Button) view.findViewById(R.id.button);
        converter.setOnClickListener(this);
        // Inflate the layout for this fragment
        return view;
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

        float result = 0;
        if (TextUtils.isEmpty(etNam.getText().toString()) || TextUtils.isEmpty(etNam1.getText().toString())) {
            return;
        } else if (TextUtils.isEmpty(etNam20.getText().toString()) || TextUtils.isEmpty(etNam21.getText().toString())) {
            return;

        } else if (TextUtils.isEmpty(etNam22.getText().toString())) {
            return;
        }
        num1 = Float.parseFloat(etNam.getText().toString());
        num2 = Float.parseFloat(etNam1.getText().toString());
        num3 = Float.parseFloat(etNam2.getText().toString());

        num30 = Float.parseFloat(etNam20.getText().toString());
        num31 = Float.parseFloat(etNam21.getText().toString());
        num32 = Float.parseFloat(etNam22.getText().toString());
        switch (v.getId()) {
            case R.id.button:
                oper = "  ";
                result = (num32 * 3)/  (num1 * 18);
                tvResult.setText(oper + result);
                break;

        }
    }
}
