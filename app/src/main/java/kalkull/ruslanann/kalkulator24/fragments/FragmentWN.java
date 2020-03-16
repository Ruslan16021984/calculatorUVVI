package kalkull.ruslanann.kalkulator24.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import kalkull.ruslanann.kalkulator24.R;
import kalkull.ruslanann.kalkulator24.base_fragment.BaseFragment;
import kalkull.ruslanann.kalkulator24.database.ToDoDatabase;
import kalkull.ruslanann.kalkulator24.mathCount.MathCount;

import static kalkull.ruslanann.kalkulator24.R.id.spinner;


/**
 * Created by CARD on 08.01.2016.
 */
public class FragmentWN extends BaseFragment
        implements View.OnClickListener{
    private static final int ACTIVITY_CREATE = 0;
    private static final int ACTIVITY_EDIT = 1;
    private static final int DELETE_ID = Menu.FIRST + 1;
    private static final int WRITE_REQUEST_CODE = 43;
//    private static final String DIRECTORY_DOCS = "/documents";

    private String mCurFileName = ""; // имя текущего файла для работы
    private String mPath; // путь к файлу
    private int mPos = 0; // позиция при выборе имени файла в диалоговом окне

    private ToDoDatabase mDbHelper;
    @BindView(R.id.etNam1)
    EditText etNam1;
    @BindView(R.id.etNam3)
    EditText etNam3;
    @BindView(R.id.etNam2)
    EditText etEtNam2;
    @BindView(R.id.etNam4)
    EditText etNam4;
    @BindView(R.id.button)
    Button converter;
    @BindView(R.id.tvResultSave)
    TextView tvResult;
    @BindView(R.id.tvResultSave2)
    TextView tvResult2;
    @BindView(R.id.result5)
    TextView tvResult5;
    @BindView(R.id.data2)
    TextView tv2;

    private RadioGroup mRadioOsGroup;
    String[] faza = {"AB", "BC", "AC"};
    String[] polozenie = {"2-3", "3-4", "5-6", "4-5", "2-4"};


    public static FragmentWN getInstance(Context context) {
        FragmentWN fragment = new FragmentWN();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.item_SN));
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_sn, container, false);
        unbinder = ButterKnife.bind(this, view);
        mPath = Environment.getExternalStorageDirectory().toString() + DIRECTORY_DOCS;
        File folder = new File(mPath);

// Если папки не существует, то создадим её
        if(!folder.exists()) {
            folder.mkdir();
        }
        mDbHelper = new ToDoDatabase(getActivity());
//        showSd = (TextView) view.findViewById(R.id.showSd);

        sPoloz = (Spinner) view.findViewById(R.id.spinner2);
        sFaza = (Spinner) view.findViewById(spinner);

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
        fpAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, faza);
        spAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, polozenie);
        sFaza.setAdapter(spAdapter);
        sPoloz.setAdapter(fpAdapter);

        converter.setOnClickListener(this);

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
                showDialog();
                saveState();
                break;
            case R.id.save_cd:
                showSaveFileDialog();
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
        initSppiner();
        switch (v.getId()) {
            case R.id.button:
                oper = "  ";
                if (perlaseMnozitel.equals("0")) {
                    Toast.makeText(getActivity(), "выберите флажок x1,x2 или x3 ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (perlaseMnozitel.equals("x1")) {
                    float x = 1;
                    makeCount(x);
                } else if (perlaseMnozitel.equals("x2")) {
                    float x = 2;
                    makeCount(x);
                } else if (perlaseMnozitel.equals("x3")) {
                    float x = 3;
                    makeCount(x);

                }

                break;

        }


    }

    private void initSppiner() {
        fnomber = sFaza.getSelectedItem().toString();
        snomber = sPoloz.getSelectedItem().toString();
        fpAdapter.notifyDataSetChanged();
        spAdapter.notifyDataSetChanged();
        nomber = snomber + " " +fnomber;
    }

    private void makeCount(float x) {

        float result = 0;
        float result2 = 0;
        float result3 = 0;
        //проаеряем поля на пустоту
        if (TextUtils.isEmpty(etNam1.getText().toString()) || TextUtils.isEmpty(etEtNam2.getText().toString())) {
            return;
        } else if (TextUtils.isEmpty(etNam3.getText().toString()) || TextUtils.isEmpty(etNam4.getText().toString())) {
            return;

        }
        result = (float) MathCount.makeCount10Amper(etNam1,etEtNam2, x);
        tvResult.setText(result + " " + "Ом");

        result2 = MathCount.ROUND_HALF_UP(etNam3);
        tvResult2.setText(result2 + " " + "Ом");

        result3 = MathCount.makeCount20degrees(etNam4,result2);
        tvResult5.setText(result3 + " "+"%");

        if (result3 != 0) {
            float i = result3;
            if (i > -1 && i < 1) tvResult5.setTextColor(Color.GREEN);
            else tvResult5.setTextColor(Color.RED);
        }
    }

    private void saveState() {
        initSppiner();

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
    private void saveFile(String fileName) {
        try {

            File file = new File(mPath, fileName);

            BufferedWriter bW;

            bW = new BufferedWriter(new FileWriter(file,true));
            massage = nomber +"\n"
                    +" изм А= "
                    + tvResult.getText().toString() + " | "
                    +"прив 20 С= "
                    + tvResult2.getText().toString() + " | "
                    +"расх= "
                    + tvResult5.getText().toString()+ "\n"
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
    //--------------------------------------------------------------
    private void showDialog(){
    }
    private void showOpenFileDialog(){
        try{
            final String[] files = getFiles(mPath);

            // Если в папке есть файлы, то создаем диалоговое окно
            if(files.length > 0){
                mPos = 0;
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Открыть файл");

                // Отображаем список файлов
                builder.setSingleChoiceItems(files, 0, (dialog, which) -> {
                    mPos = which;
                });

                builder.setPositiveButton("OK", (dialog, which) -> {
                    mCurFileName = files[mPos];
                    openFile(mCurFileName);
                });

                builder.setNegativeButton("Отмена", (dialog, which) -> {
                    dialog.cancel();
                });
                builder.setCancelable(false);
                builder.show();
            }
        }
        catch(Exception e){
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    private String[] getFiles(String dirPath) {
        ArrayList<String> items = new ArrayList<String>();
        try {
            File file = new File(dirPath);
            File[] files = file.listFiles();

            for (File afile : files) {
                if (!afile.isDirectory()) {
                    items.add(afile.getName());
                }
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
        }
        return items.toArray(new String[items.size()]);
    }
    private void openFile(String fileName) {
        try {
            File file = new File(mPath, fileName);
            FileInputStream fis = new FileInputStream(file);

            if (fis != null) {
                InputStreamReader inreader = new InputStreamReader(fis);
                BufferedReader reader = new BufferedReader(inreader);
                String str;
                StringBuffer buffer = new StringBuffer();

                while ((str = reader.readLine()) != null) {
                    buffer.append(str + "\n");
                }

                fis.close();
//                editor.setText(buffer.toString());

                mCurFileName = fileName;

                // Выводим имя файла в заголовке
                setTitle(mCurFileName + ": Блокнот");
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    private void showSaveFileDialog(){
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View root = inflater.inflate(R.layout.save_dialog, null);

        final EditText editFileName = (EditText)root.findViewById(R.id.editFileName);
        editFileName.setText(mCurFileName);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(root);
        builder.setTitle("Сохранение файла");

        builder.setPositiveButton("OK", (dialog, which) -> {
            saveFile(editFileName.getText().toString());
        });

        builder.setNegativeButton("Отмена", (dialog, which) -> {
            dialog.cancel();
        });
        builder.show();
    }


}
