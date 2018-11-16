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
import com.h2m.carassistance.api.*;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.h2m.carassistance.Utitls.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.email_address) EditText email_edit;
    @BindView(R.id.password) EditText password_edit;
    @BindView(R.id.loginbtn) Button login_btn;
    @BindView(R.id.not_user_yet) TextView not_user_yet;

    Context context;
    ProgressDialog loading;
    AwesomeValidation awesomeValidation;
    sharedPreferences preferences;
    String user_email, user_password;

    JSONObject jsonObject = null;
    JSONArray jsonArray = null;
    JSONObject json = null;

    //Google login
    GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        ButterKnife.bind(this);

        context = this;
        preferences = new sharedPreferences(context);

        // Validation for user data entry
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.email_address, RegularExpression.EMAIL_PATTERN, R.string.email_error);
        awesomeValidation.addValidation(this, R.id.password, RegularExpression.PASSWORD_PATTERN, R.string.password_error);

        login_btn.setOnClickListener(this);
        not_user_yet.setOnClickListener(this);
        //Google login
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!InternetConnection.internetConnectionAvailable(1000)){
                    Utility.snackBar(v, context.getString(R.string.check_network_connection));
                }else{
                    GooglesignIn();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.loginbtn:
                if(!InternetConnection.internetConnectionAvailable(1000)){
                    Utility.snackBar(v, context.getString(R.string.check_network_connection));
                }else{
                    if(awesomeValidation.validate()) {
                        user_email = email_edit.getText().toString();
                        user_password = password_edit.getText().toString();
                        loading = ProgressDialog.show(context, "Please wait...", "Loading", false, false);
                        login();
                    }
                }

                break;
            case R.id.not_user_yet:
                startActivity(new Intent(context, SignupActivity.class));
                finish();
                break;
        }
    }

    // Normal login
    public void login(){
        StringRequest jsObjRequest = new StringRequest
                (Request.Method.POST, DatabaseURL.login, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        Log.d("response", response);

                        if(response.equals("nofound")){
                            AlertDialog();
                        }else{
                            parse_json(response);
                        }
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", user_email);
                params.put("password", user_password);
                return params;
            }
        };
        Singleton.getInstance(context).addToRequestQueue(jsObjRequest);
    }

    private void parse_json(String response){
        try {
            jsonObject = new JSONObject(response);
            jsonArray = jsonObject.getJSONArray("result");
            json = jsonArray.getJSONObject(0);

            String user_id = json.getString("id");
            String user_username = json.getString("user_name");
            String user_email = json.getString("email");
            String user_phone = json.getString("phone");

            preferences.user_data(user_id, user_username, user_email, user_phone);

            startActivity(new Intent(context, MainActivity.class));
            finish();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void AlertDialog(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle(R.string.warning_alert);
        builder1.setMessage( getString(R.string.login_error));
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


    //Google Login
    private void GooglesignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();

            assert acct != null;
            String google_email = acct.getEmail();
            String google_name = acct.getDisplayName();
            String google_id = acct.getId();

            Log.d("responseEmail", google_email);

            makeLogin(google_email,google_id, google_name);

        } else {
            Toast.makeText(this, "Please follow instruction", Toast.LENGTH_LONG).show();
        }
    }

    //Google login volley
    public void makeLogin(final String email,final String id, final String name){
        StringRequest jsObjRequest = new StringRequest
                (Request.Method.POST, DatabaseURL.login, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);
                        if(response.equals("nofound")){
                            // First time make login with google
                            Intent intent = new Intent(context, SignupActivity.class);
                            intent.putExtra(Constant.username, name);
                            intent.putExtra(Constant.email, email);
                            intent.putExtra(Constant.password, id);
                            startActivity(intent);
                        }else{
                            parse_json(response);
                        }
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", id);
                return params;
            }
        };
        Singleton.getInstance(context).addToRequestQueue(jsObjRequest);
    }
}
