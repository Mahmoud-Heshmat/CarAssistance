package com.h2m.carassistance;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.h2m.carassistance.Utitls.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    @BindView(R.id.empty_item) TextView emptyTxt;
    @BindView(R.id.carsSpinner) Spinner spinner;
    @BindView(R.id.add_fab) FloatingActionButton addFab;

    Context context;
    List<CarEntry> cars = new ArrayList<>();
    List<CarItemsEntry> carItems = new ArrayList<>();
    List<String> carNames = new ArrayList();
    List<Integer> carIds = new ArrayList();
    static int carId = 0;

    //RecycleView
    RecyclerView recyclerView;
    private CarItemsAdapter adapter;
    LinearLayoutManager linearLayoutManager;

    sharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        mTitle.setText(getString(R.string.app_name));
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        context = this;
        preferences = new sharedPreferences(context);
        addFab.setOnClickListener(this);

        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new CarItemsAdapter(context, carItems);
        recyclerView.setAdapter(adapter);

        getCars();
    }

    public void getCars(){
        CarsViewModel viewModel = ViewModelProviders.of(this).get(CarsViewModel.class);
        viewModel.getCars().observe(this, new Observer<List<CarEntry>>() {
            @Override
            public void onChanged(@Nullable List<CarEntry> carsEntries) {
                if (!carsEntries.isEmpty()) {
                    emptyTxt.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    cars = carsEntries;
                    for(CarEntry item : carsEntries) {
                        carNames.add(item.getName());
                        carIds.add(item.getId());
                    }
                    addCarSpinners(carNames);
                }else{
                    emptyTxt.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    public void getCarItems(){
        CarItemsViewModel viewModel = ViewModelProviders.of(this).get(CarItemsViewModel.class);
        viewModel.getCarItems(sharedPreferences.get_car_id(context)).observe(this, new Observer<List<CarItemsEntry>>() {
            @Override
            public void onChanged(@Nullable List<CarItemsEntry> carsEntries) {
                if (!carsEntries.isEmpty()) {
                    emptyTxt.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    carItems = carsEntries;
                    adapter.update_data(carItems);
                    AppWidgetService.updateWidget(context, carItems);
                    Log.d("responseItemsss", carsEntries.get(0).itemName);
                }else{
                    emptyTxt.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void addCarSpinners(List<String> list){
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.add_fab:
                showAlertDialogButtonClicked(v);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String car = parent.getItemAtPosition(position).toString();
        carId = carIds.get(position);
        preferences.setSelectedCar(car, carId);
        carItems.clear();
        getCarItems();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public void showAlertDialogButtonClicked(View view) {

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.choose_adding);

        // add a list
        String[] options = {getString(R.string.add_car), getString(R.string.add_item)};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: // add car
                        startActivity(new Intent(context, AddCarActivity.class));
                    case 1: // add item
                        if (carId == 0){
                            Toast.makeText(context, "Please add car first", Toast.LENGTH_LONG).show();
                            return;
                        }
                        Intent intent = new Intent(context, AddCarItemActivity.class);
                        intent.putExtra(Constant.cardId, carId);
                        startActivity(intent);
                }
            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
