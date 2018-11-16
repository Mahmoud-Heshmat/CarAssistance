package com.h2m.carassistance;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.h2m.carassistance.Utitls.Constant;
import com.h2m.carassistance.Utitls.InternetConnection;
import com.h2m.carassistance.Utitls.Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddCarItemActivity extends AppCompatActivity implements View.OnClickListener , DatePickerDialog.OnDateSetListener {

    @BindView(R.id.itemName) EditText nameField;
    @BindView(R.id.dateReminder) EditText dateReminderField;
    @BindView(R.id.carKilo) EditText carKiloField;
    @BindView(R.id.addCarItemBtn) Button addCarItemBtn;

    private AppDatabase appDatabase;
    private Context context;

    String itemName, itemKM, itemDate;
    int carId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car_item);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        mTitle.setText(R.string.add_item);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            carId = bundle.getInt(Constant.cardId);
        }

        context = this;
        appDatabase = AppDatabase.getsInstance(getApplicationContext());
        addCarItemBtn.setOnClickListener(this);
        dateReminderField.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addCarItemBtn:
                if(!InternetConnection.internetConnectionAvailable(1000)){
                    Utility.snackBar(v, getString(R.string.check_network_connection));
                }else{
                    addCarItem(v);
                }
                break;
            case R.id.dateReminder:
                dateDaiolg();
                break;
        }
    }

    private void addCarItem(View v){
        itemName = nameField.getText().toString();
        itemKM = carKiloField.getText().toString();

        if (itemName.isEmpty()){
            Utility.snackBar(v, getString(R.string.fill_item_name));
            return;
        }

        if (itemKM.isEmpty()) {
            Utility.snackBar(v, getString(R.string.fill_car_km));
            return;
        }

        if (itemDate.isEmpty()) {
            Utility.snackBar(v, getString(R.string.fill_item_date));
            return;
        }

        if(carId == -1){
            Utility.snackBar(v, getString(R.string.wrong_alert));
            return;
        }

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                CarItemsEntry carEntry = new CarItemsEntry(carId, itemName, itemDate, itemKM);
                appDatabase.carItemsDAO().insertCarItem(carEntry);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Added Successfully", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void dateDaiolg(){
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        DatePickerDialog dialog = new DatePickerDialog(context, this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        itemDate = dayOfMonth + "/" + month + "/" + year;
        dateReminderField.setText(itemDate);
        Log.d("responseDate", year + "  " + month + "  " + getDateFromString(itemDate));
    }

    private Date getDateFromString(String mDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(mDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return convertedDate;
    }
}
