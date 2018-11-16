package com.h2m.carassistance;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.h2m.carassistance.Utitls.CarDataList;
import com.h2m.carassistance.Utitls.InternetConnection;
import com.h2m.carassistance.Utitls.Utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddCarActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    @BindView(R.id.cNameSpinner) Spinner spinner;
    @BindView(R.id.carModel) EditText modelField;
    @BindView(R.id.carYear) EditText yearField;
    @BindView(R.id.carKilo) EditText KMField;
    @BindView(R.id.addCarBbtn) Button addCar;

    String carName, carModel, carYear, carKM;

    private AppDatabase appDatabase;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        mTitle.setText(getString(R.string.add_car));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        context = this;
        addCar.setOnClickListener(this);

        appDatabase = AppDatabase.getsInstance(getApplicationContext());
        setCarNameSpinner();

    }

    private void setCarNameSpinner(){

        spinner.setOnItemSelectedListener(this);
        List<String> categories = CarDataList.getCarsName();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        carName = parent.getItemAtPosition(position).toString();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addCarBbtn:
                if(!InternetConnection.internetConnectionAvailable(1000)){
                    Utility.snackBar(v, getString(R.string.check_network_connection));
                }else{
                    addCar(v);
                }
                break;
        }
    }

    private void addCar(View v){
        carModel = modelField.getText().toString();
        carYear = yearField.getText().toString();
        carKM = KMField.getText().toString();

        if (carModel.isEmpty()){
            Utility.snackBar(v, getString(R.string.fill_car_model));
            return;
        }

        if (carYear.isEmpty()) {
            Utility.snackBar(v, getString(R.string.fill_car_year));
            return;
        }

        if (carKM.isEmpty()) {
            Utility.snackBar(v, getString(R.string.fill_car_km));
            return;
        }

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Date date = new Date();
                CarEntry carEntry = new CarEntry(carName, carModel, carYear, date, carKM);
                appDatabase.carDAO().insertCar(carEntry);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Added Successfully", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
