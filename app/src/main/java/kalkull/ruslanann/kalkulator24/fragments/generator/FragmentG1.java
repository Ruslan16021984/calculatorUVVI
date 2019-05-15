package kalkull.ruslanann.kalkulator24.fragments.generator;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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

import kalkull.ruslanann.kalkulator24.R;
import kalkull.ruslanann.kalkulator24.base.BaseFragment;
import kalkull.ruslanann.kalkulator24.database.ToDoDatabase;

/**
 * Created by CARD on 12.01.2016.
 */
public class FragmentG1 extends BaseFragment implements View.OnClickListener{



    private SharedPreferences sPref;

    private EditText etNam1;
    private EditText etEtNam2;
    private EditText etNam3;

    private Button converter;
    private RadioGroup mRadioOsGroup;
    private RadioButton mSelRadio;
    private String c = "0";

    private TextView tvTl;
    private TextView wTKg;
    private TextView amper;
    private TextView wt;
    private TextView tvResultTime;
    private TextView nomber;
    private TextView aMper;
    private TextView wat;
    private String oper = "";
    private String mCurFileName = "";
    private static final float X1 = 186642;
    private static final float X2 = 176388;
    private static final float X3 = 164000;
    private static final float SECH_X1 = 26559;
    private static final float SECH_X2 = 25100;
    private static final float SECH_X3 = 23400;


    public static FragmentG1 getInstance(Context context) {
        FragmentG1 fragment = new FragmentG1();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.setTitle(context.getString(R.string.fragmentG1));
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbHelper = new ToDoDatabase(getContext());
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_g, container, false);
        etNam1 = (EditText) view.findViewById(R.id.etNam1);
        etEtNam2 = (EditText) view.findViewById(R.id.etNam2);
        etNam3 = (EditText) view.findViewById(R.id.etNam3);

        converter = (Button) view.findViewById(R.id.button1);


        tvTl = (TextView) view.findViewById(R.id.tvResultSave);
        wTKg = (TextView) view.findViewById(R.id.result2Save);
        aMper = (TextView) view.findViewById(R.id.result3Save);
        wt = (TextView) view.findViewById(R.id.result4);
        tvResultTime = (TextView) view.findViewById(R.id.resulTime);
        nomber = (TextView) view.findViewById(R.id.nomber);
        amper = (TextView) view.findViewById(R.id.amher);
        wat = (TextView) view.findViewById(R.id.watt);
        mRadioOsGroup = (RadioGroup) view.findViewById(R.id.radio4);
        mRadioOsGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton:
                        c = "x1";
                        break;
                    case R.id.radioButton2:
                        c = "x2";
                        break;
                    case R.id.radioButton3:
                        c = "x3";
                        break;


                }
            }
        });


        converter.setOnClickListener(this);


        return view;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.load, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ToDoDatabase dbHelper = new ToDoDatabase(getContext());
        switch (item.getItemId()) {

            case R.id.action_base:

                saveState();
                break;
            default:
                return false;
        }
        dbHelper.close();
        return true;
    }

    @Override
    public void onClick(View v) {
        final Animation animAlpha = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha);
        v.startAnimation(animAlpha);

        switch (v.getId()) {
            case R.id.button1:

                oper = "";
                if (c.equals("x1")) {
                    float massa = X1;
                    float sech = SECH_X1;
                    makeCount(massa,sech);

                } else if (c.equals("x2")) {

                    float massa = X2;
                    float sech = SECH_X2;
                    makeCount(massa,sech);

                } else if (c.equals("x3")){

                    float massa = X3;
                    float sech = SECH_X3;
                    makeCount(massa,sech);
                }




                break;


        }
    }


    private void makeCount(float massa, float sech){
        float num1 = 0;
        float num2 = 0;
        float num3 = 0;
        float result0 = 0;
        float result = 0;
        float result1 = 0;
        float result2 = 0;
        float result3 = 0;
        float result4 = 0;
        float resultTime = 0;
        float pKab = 8;
        if (TextUtils.isEmpty(etNam1.getText().toString()) || TextUtils.isEmpty(etEtNam2.getText().toString())) {
            return;
        } else if (TextUtils.isEmpty(etNam3.getText().toString())) {
            return;

        }
        num1 = Float.parseFloat(etNam1.getText().toString());
        num2 = Float.parseFloat(etEtNam2.getText().toString());
        num3 = Float.parseFloat(etNam3.getText().toString());
        result = num3;
        result1 = (float) (result * 10000 / (4.44 * 50 * 1 * sech));
        tvTl.setText(String.format(oper + "%.4f", result1) + " " + "Тл");
        result0 = (float) ((float) (1.4 / result1) * (1.4 / result1));
        pKab = (float) (num1 * num1 * 0.0171);
        result2 = (num2 * 8 * result0 - pKab);
        result3 = result2 / (massa);

        wTKg.setText(String.format(oper + "%.3f", result3) + " " + "Вт.кг");
        result4 = 8 * num1;
        aMper.setText(String.format(oper + result4) + " " + "А");
        wt.setText(String.format(oper + "%.2f", result2) + " " + "Вт");
        resultTime = (float) (45 * ((1.4 / result1) * (1.4 / result1)));
        tvResultTime.setText(String.format(oper + resultTime) + " " + "мин");

    }



    private void saveState() {


        String summary = (nomber.getText().toString());
        String description = tvTl.getText().toString();
        String descriptionb = wTKg.getText().toString();
        String descriptionc = aMper.getText().toString();
        String descriptiona = wt.getText().toString();
        String descriptiond = tvResultTime.getText().toString();

        if (description.length() == 0 && summary.length() == 0) {
            return;
        }


        mDbHelper.createNewTodo(summary, description, descriptiona,
                descriptionb, descriptionc, descriptiond);
        Toast.makeText(getActivity(), "save", Toast.LENGTH_SHORT).show();


    }

    private void showSaveFileDialog() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View root = inflater.inflate(R.layout.save_dialog, null);

        final EditText editFileName = (EditText) root.findViewById(R.id.editFileName);
        editFileName.setText(mCurFileName);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(root);
        builder.setTitle("Сохранение файла");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                nomber.setText(editFileName.getText().toString());

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
}
