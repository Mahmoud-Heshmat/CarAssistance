package com.h2m.carassistance;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.h2m.carassistance.Utitls.*;
import com.h2m.carassistance.api.*;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.email_address) EditText email_edit;
    @BindView(R.id.password) EditText password_edit;
    @BindView(R.id.user_name) EditText name_edit;
    @BindView(R.id.phone) EditText phone_edit;
    @BindView(R.id.signup_btn) Button signup_btn;
    @BindView(R.id.already_member) TextView already_member;

    String user_name, user_email, user_password, phone;
    AwesomeValidation awesomeValidation;
    Context context;

    sharedPreferences preferences;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            user_name = bundle.getString(Constant.username);
            name_edit.setText(user_name);
            user_email = bundle.getString(Constant.email);
            email_edit.setText(user_email);
            user_password = bundle.getString(Constant.password);
            password_edit.setText(user_password);
            password_edit.setVisibility(View.INVISIBLE);
        }

        context = this;
        preferences = new sharedPreferences(context);

        // Validation for user data entry
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.user_name, RegularExpression.NAME_PATTERN, R.string.name_error);
        awesomeValidation.addValidation(this, R.id.email_address, RegularExpression.EMAIL_PATTERN, R.string.email_error);
        awesomeValidation.addValidation(this, R.id.password, RegularExpression.PASSWORD_PATTERN, R.string.password_error);

        signup_btn.setOnClickListener(this);
        already_member.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.signup_btn:
                if(!InternetConnection.internetConnectionAvailable(1000)){
                    Utility.snackBar(v, context.getString(R.string.check_network_connection));
                }else{
                    user_name = name_edit.getText().toString();
                    user_email = email_edit.getText().toString();
                    user_password = password_edit.getText().toString();
                    phone = phone_edit.getText().toString();

                    if(awesomeValidation.validate()){
                        loading = ProgressDialog.show(context, "Please wait...", "Loading", false, false);
                        Signup();
                    }
                }

                break;
            case R.id.already_member:
                startActivity(new Intent(context, LoginActivity.class));
                finish();
                break;
        }
    }

    public void Signup(){
        final String token = FirebaseInstanceId.getInstance().getToken();
        StringRequest jsObjRequest = new StringRequest
                (Request.Method.POST, DatabaseURL.sign_up, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        Log.d("response", response);
                        if(response.equals("found")){
                            AlertDialog();
                        }else if(response.equals("Failed")){
                            AlertDialogFailed();
                        }else if(response.equals("nofound")){
                            Toast.makeText(context, "Almost done", Toast.LENGTH_LONG).show();
                        }else{
                            preferences.user_data(user_name, user_email, phone);
                            startActivity(new Intent(context, MainActivity.class));
                            finish();
                        }
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("response", error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", user_name);
                params.put("email", user_email);
                params.put("password", user_password);
                params.put("phone", phone);
                params.put("user_token", token);
                return params;
            }
        };
        Singleton.getInstance(context).addToRequestQueue(jsObjRequest);
    }

    public void AlertDialog(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle(R.string.warning_alert);
        builder1.setMessage( getString(R.string.alert_login));
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void AlertDialogFailed(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle(R.string.warning_alert);
        builder1.setMessage(getString(R.string.wrong_alert));
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
