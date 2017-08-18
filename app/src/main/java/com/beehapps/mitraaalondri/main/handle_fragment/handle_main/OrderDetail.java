package com.beehapps.mitraaalondri.main.handle_fragment.handle_main;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.beehapps.mitraaalondri.main.MainActivity;
import com.beehapps.mitraaalondri.main.handle_login.LoginActivity;
import com.beehapps.mitraaalondri.pojo.Order;
import com.beehapps.mitraaalondri.pojo.Profil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderDetail extends AppCompatActivity {

    private TextView tvNama, tvAlamat, tvJenis, tvJemput, tvAntar;
    private Button btTeirma, btTolak;

    private String idOrder, nama_jalan, nama_alias, phone_alias, total_harga, detail_lokasi, catatan, alamat,
            tanggal_mulai, tanggal_akhir, waktu_mulai, is_byitem, token;

    private DatabaseHandler dataSource;
    public ArrayList<Profil> valuesProfil;

    private ProgressDialog loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle(getString(R.string.activityOrderDetail));

        tvNama = (TextView) findViewById(R.id.textViewOrderNama);
        tvAlamat = (TextView) findViewById(R.id.textViewOrderAlamat);
        tvJenis = (TextView) findViewById(R.id.textViewOrderLayanan);
        tvJemput = (TextView) findViewById(R.id.textViewOrderJemput);
        tvAntar = (TextView) findViewById(R.id.textViewOrderAntar);
        btTeirma = (Button) findViewById(R.id.buttonTerima);
        btTolak = (Button) findViewById(R.id.buttonTolak);

        dataSource = new DatabaseHandler(OrderDetail.this);
        valuesProfil = (ArrayList<Profil>) dataSource.getAllProfils();

        Intent intent = getIntent();
        idOrder = intent.getStringExtra("idOrder");
        nama_jalan = intent.getStringExtra("nama_jalan");
        nama_alias = intent.getStringExtra("nama_alias");
        phone_alias = intent.getStringExtra("phone_alias");
        total_harga = intent.getStringExtra("total_harga");
        detail_lokasi = intent.getStringExtra("detail_lokasi");
        catatan = intent.getStringExtra("catatan");
        alamat = intent.getStringExtra("alamat");
        tanggal_mulai = intent.getStringExtra("tanggal_mulai");
        tanggal_akhir = intent.getStringExtra("tanggal_akhir");
        waktu_mulai = intent.getStringExtra("waktu_mulai");
        is_byitem = intent.getStringExtra("is_byitem");

        if (is_byitem.equals("0")){
            tvJenis.setText("Londri Kiloan\n"+catatan);
        }else{
            tvJenis.setText("Londri Satuan\n"+catatan);
        }

        tvNama.setText(nama_alias);
        tvAlamat.setText(nama_jalan+"\n"+alamat+"\n"+detail_lokasi);
        tvJemput.setText(tanggal_mulai+" "+waktu_mulai);
        tvAntar.setText(tanggal_akhir+" "+waktu_mulai);

        btTeirma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(OrderDetail.this);
                alert.setTitle("Peringatan");
                alert.setMessage("Menerima pesanan?");
                alert.setPositiveButton("Ya",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                request("",idOrder,"1","terima");
                            }
                        });
                alert.setNegativeButton("Tidak",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                            }
                        });
                alert.show();
            }
        });

        btTolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(OrderDetail.this);
                final EditText edittext = new EditText(OrderDetail.this);
                alert.setTitle("Peringatan");
                alert.setMessage("Berikan alasan menolak");
                alert.setView(edittext);
                alert.setPositiveButton("Ya",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                String alasan = edittext.getText().toString();
                                request(alasan,idOrder,"2","tolak");
                            }
                        });
                alert.setNegativeButton("Tidak",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                            }
                        });
                alert.show();
            }
        });

    }

    private void request(final String alasan, final String idOrder, final String status, final String kode) {


        for (Profil profil : valuesProfil) {
            token = profil.getToken();
        }
        loading = ProgressDialog.show(OrderDetail.this,"Mohon Tunggu","Sedang memuat...",false,false);

        RequestQueue queue = Volley.newRequestQueue(OrderDetail.this);
        StringRequest strReq = new StringRequest(Request.Method.POST, APIConfig.API_CHANGE_ORDER_STATUS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d("RESPON","Terima/Tolak "+response);

                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray dataArray = jObj.getJSONArray("message");
                    Toast.makeText(OrderDetail.this, ""+dataArray, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(OrderDetail.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    OrderDetail.this.startActivity(intent);
                    OrderDetail.this.finish();
                } catch (JSONException e) {
                    Toast.makeText(OrderDetail.this, "" + e, Toast.LENGTH_SHORT).show();
                    Log.d("ERROR 2",""+e);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
                Toast.makeText(OrderDetail.this, "" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id",idOrder);
                params.put("status",status);
                if(kode.equals("tolak")){
                    params.put("alasan",alasan);
                }
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String bearer = "Bearer " + token;
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", bearer);
                return headers;
            }
        };

        int socketTimeout = 15000;
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
}
