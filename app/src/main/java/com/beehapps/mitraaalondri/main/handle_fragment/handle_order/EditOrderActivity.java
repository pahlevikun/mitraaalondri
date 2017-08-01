package com.beehapps.mitraaalondri.main.handle_fragment.handle_order;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import com.beehapps.mitraaalondri.main.handle_login.LoginActivity;
import com.beehapps.mitraaalondri.pojo.Profil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditOrderActivity extends AppCompatActivity {

    private String nama, alamat, berat, order_id, berat_baru, token;
    private TextView tvNamaUser, tvAlamatUser, tvLayanan;
    private EditText editBerat;
    private Button btSetujuU;
    private LinearLayout linBerat;

    private ProgressDialog loading;

    public ArrayList<Profil> valuesProfil;
    public DatabaseHandler dataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_order);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle(getString(R.string.activityEditOrderTab));

        tvNamaUser = (TextView) findViewById(R.id.tvNama);
        tvAlamatUser = (TextView) findViewById(R.id.tvAlamat);
        tvLayanan = (TextView) findViewById(R.id.textViewWeightServiceKonfirmasi);
        linBerat = (LinearLayout) findViewById(R.id.linLayBerat);
        editBerat = (EditText) findViewById(R.id.etBerat);
        btSetujuU = (Button) findViewById(R.id.btSetuju);

        dataSource = new DatabaseHandler(this);
        valuesProfil = (ArrayList<Profil>) dataSource.getAllProfils();

        Intent ambil = getIntent();
        nama = ambil.getStringExtra("nama");
        alamat = ambil.getStringExtra("alamat");
        berat = ambil.getStringExtra("berat");
        order_id = ambil.getStringExtra("order_id");

        editBerat.setText(berat);
        tvNamaUser.setText(nama);
        tvAlamatUser.setText(alamat);
        if(berat.equals("0.00")){
            btSetujuU.setVisibility(View.INVISIBLE);
            linBerat.setVisibility(View.GONE);
            tvLayanan.setText("Londri Satuan");
        }else{
            btSetujuU.setVisibility(View.VISIBLE);
            linBerat.setVisibility(View.VISIBLE);
            tvLayanan.setText("Londri Kiloan");
        }

        btSetujuU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeJsonObjectRequest();
            }
        });



    }

    private void makeJsonObjectRequest() {
        loading = ProgressDialog.show(this,"Mohon Tunggu","Sedang mengubah data...",false,false);
        berat_baru = editBerat.getText().toString().trim();

        for (Profil profil : valuesProfil){
            token = profil.getToken();
        }
        RequestQueue queue = Volley.newRequestQueue(EditOrderActivity.this);
        StringRequest strReq  = new StringRequest(Request.Method.POST, APIConfig.API_EDIT_ORDER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    //Mulai parsing json untuk menampilkan order.
                    if (!error) {
                        String pesan = jObj.getString("message");
                        finish();
                    } else {
                        Toast.makeText(EditOrderActivity.this, "Gagal mengubah order!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(EditOrderActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
                Toast.makeText(EditOrderActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }){
            //Untuk post data menggunakan volley
            //Data yang dikirim adalah email dan password
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("order_id", order_id);
                params.put("berat", berat_baru);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String bearer = "Bearer " + token;
                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<String, String>();
                headersSys.remove("Authorization");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }
        };

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        strReq.setRetryPolicy(policy);
        queue.add(strReq);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void hideDialog() {
        if (loading.isShowing())
            loading.dismiss();
    }
}
