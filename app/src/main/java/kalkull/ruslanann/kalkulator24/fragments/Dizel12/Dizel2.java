package kalkull.ruslanann.kalkulator24.fragments.Dizel12;

import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import kalkull.ruslanann.kalkulator24.R;
import kalkull.ruslanann.kalkulator24.base_fragment.BaseFragment;
import kalkull.ruslanann.kalkulator24.database.ToDoDatabase;

/**
 * Created by CARD on 04.02.2016.
 */
public class Dizel2 extends BaseFragment implements View.OnClickListener {
    private EditText etNam1;
    private EditText etEtNam2;
    private EditText etNam3;

    private Button converter;
    private CheckBox checkbox1;
    private CheckBox checkbox2;
    private TextView nomber;
    private String mCurFileName = "";
    private TextView tvResult;
    private TextView tvResult2;
    private TextView tvResult3;
    private TextView tvResult4;
    private TextView tvResultTime;
    private RadioGroup mRadioOsGroup;
    private RadioButton mSelRadio;
    private String c = "0";

    private String oper = "";

    public static Dizel2 getInstance(Context context) {
        Dizel2 fragment = new Dizel2();
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
        View view = inflater.inflate(R.layout.fragment_dizel2, container, false);
        etNam1 = (EditText) view.findViewById(R.id.etNam1);
        etEtNam2 = (EditText) view.findViewById(R.id.etNam2);
        etNam3 = (EditText) view.findViewById(R.id.etNam3);


        converter = (Button) view.findViewById(R.id.button1);

        nomber = (TextView) view.findViewById(R.id.nomber);
        tvResult = (TextView) view.findViewById(R.id.tvResultSave);
        tvResult2 = (TextView) view.findViewById(R.id.result2Save);
        tvResult3 = (TextView) view.findViewById(R.id.result3Save);
        tvResult4 = (TextView) view.findViewById(R.id.result4);
        tvResultTime = (TextView) view.findViewById(R.id.resulTime);
        mRadioOsGroup = (RadioGroup) view.findViewById(R.id.radio2);
        mRadioOsGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.v1:
                        c = "v1";
                        break;
                    case R.id.v2:
                        c = "v2";
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
    public void onClick(View v) {
        final Animation animAlpha = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha);
        v.startAnimation(animAlpha);
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
        switch (v.getId()) {
            case R.id.button1:
                oper = "  ";
                result = num3;
                if (c.equals("0")) {

                    Toast.makeText(getActivity(), "Выберите одну галочку", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (c.equals("v1")) {
                    result1 = (result * 45 / (1 * 1403));
                } else if (c.equals("v2")) {
                    result1 = (result * 45 / (2 * 1403));
                }
                tvResult.setText(String.format(oper + "%.4f", result1) + " " + "Тл");
                result0 = (1 / result1) * (1 / result1);

                result2 = num2 * result0;
                result3 = result2 / (5397);
                tvResult2.setText(String.format(oper + "%.3f", result3)+ " " + "Вт/кг");
                result4 = num1;
                tvResult3.setText(String.format(oper + result4) + " " + "А");
                tvResult4.setText(String.format(oper + "%.2f", result2) + " " + "Вт");
                resultTime = (90 * ((1 / result1) * (1 / result1)));
                tvResultTime.setText(String.format(oper + resultTime)+ " " + "мин");

                break;

        }

    }

    private void saveState() {
        Toast.makeText(getActivity(), "save", Toast.LENGTH_SHORT).show();

        String summary = ("дизель-генератор");
        String description = tvResult.getText().toString();
        String descriptiona = tvResult4.getText().toString();
        String descriptionb = tvResult2.getText().toString();
        String descriptionc = tvResult3.getText().toString();
        String descriptiond = tvResultTime.getText().toString();

        if (description.length() == 0 && summary.length() == 0) {
            return;
        }


        mDbHelper.createNewTodo(summary, description, descriptiona, descriptionb, descriptionc, descriptiond);

    }
    private void showSaveFileDialog(){
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View root = inflater.inflate(R.layout.save_dialog, null);

        final EditText editFileName = (EditText)root.findViewById(R.id.editFileName);
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
