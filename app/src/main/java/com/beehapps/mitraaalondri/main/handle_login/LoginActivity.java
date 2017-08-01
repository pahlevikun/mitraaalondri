package com.beehapps.mitraaalondri.main.handle_login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.beehapps.mitraaalondri.R;
import com.beehapps.mitraaalondri.config.APIConfig;
import com.beehapps.mitraaalondri.database.DatabaseHandler;
import com.beehapps.mitraaalondri.main.MainActivity;
import com.beehapps.mitraaalondri.pojo.FCM;
import com.beehapps.mitraaalondri.pojo.Profil;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private TextView tvForgot;
    private EditText etEmail, etPassword;
    private Button btLogin, btDaftar;
    private String editemail, editpassword;
    private ProgressDialog loading;

    private DatabaseHandler dataSource;
    private ArrayList<FCM> valuesFCM;
    private String token, username, mail, created_at, token_fcm;
    private int user_id;

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        boolean session = getSharedPreferences("DATA",MODE_PRIVATE).getBoolean("session",false);
        if(session==true){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        
        dataSource = new DatabaseHandler(this);
        valuesFCM = (ArrayList<FCM>) dataSource.getAllFCMs();
        for(FCM fcm : valuesFCM){
            token_fcm = fcm.getTokenFCM();
        }

        etEmail = (EditText) findViewById(R.id.editTextEmail);
        etPassword = (EditText) findViewById(R.id.editTextPassword);
        btLogin = (Button) findViewById(R.id.buttonMasuk);


        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editemail = etEmail.getText().toString().trim();
                editpassword = etPassword.getText().toString().trim();

                if (editemail.isEmpty() || editpassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Email / Password masih kosong!", Toast.LENGTH_LONG).show();
                } else {
                    checkLogin(editemail,editpassword);
                }/*
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                LoginActivity.this.startActivity(intent);
                LoginActivity.this.finish();*/
            }
        });
        //Log.d("TOKEN", "Refreshed token: " + FirebaseInstanceId.getInstance().getToken());
    }

    private void checkLogin(final String email, final String password) {
        loading = ProgressDialog.show(this,"Mohon Tunggu","Sedang login...",false,false);

        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        StringRequest strReq = new StringRequest(Request.Method.POST, APIConfig.API_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                hideDialog();
                Log.d("Hasilnya",""+response);
                try {
                    JSONObject jObj = new JSONObject(response);
                    String error = jObj.getString("error");
                    if (error.equals("false")) {
                        JSONObject created = jObj.getJSONObject("created");
                        token = jObj.getString("token");
                        user_id = jObj.getInt("userid");
                        username = jObj.getString("username");
                        mail = jObj.getString("email");
                        created_at = created.getString("date");

                        dataSource.tambahProfil(new Profil(user_id, username,mail,token,"INI TOKEN FCM",created_at));
                        SharedPreferences sharedPreferences =  getSharedPreferences("DATA",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("session",true);
                        editor.commit();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        LoginActivity.this.startActivity(intent);
                        LoginActivity.this.finish();
                    } else if (error.equals("incorect")) {
                        String gagal = jObj.getString("message");
                        Toast.makeText(getApplicationContext(), gagal, Toast.LENGTH_LONG).show();
                    } else {
                        String gagal = jObj.getString("message");
                        Toast.makeText(getApplicationContext(), gagal, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.d("Hasilnya",""+error);
                Toast.makeText(getApplicationContext(), "Gagal Terhubung ke Server", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                params.put("fcm_token", "TOKEN");
                return params;
            }
        };

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        strReq.setRetryPolicy(policy);
        queue.add(strReq);
    }

    private void hideDialog() {
        if (loading.isShowing())
            loading.dismiss();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tekan lagi untuk keluar!", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
