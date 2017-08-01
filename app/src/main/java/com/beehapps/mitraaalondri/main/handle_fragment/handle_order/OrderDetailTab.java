package com.beehapps.mitraaalondri.main.handle_fragment.handle_order;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.beehapps.mitraaalondri.config.GPSTracker;
import com.beehapps.mitraaalondri.database.DatabaseHandler;
import com.beehapps.mitraaalondri.main.MainActivity;
import com.beehapps.mitraaalondri.main.handle_fragment.handle_main.OrderDetail;
import com.beehapps.mitraaalondri.pojo.Profil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderDetailTab extends AppCompatActivity implements OnMapReadyCallback {

    private String user_id, berat, total_bayar, order_id, nama,phone, namaJalan, invoice, status, startLat, startLng, token, kurir_id,mitra_id,detail_lokasi,alamat;
    private int idkurir,idmitra;
    private TextView tvAlamatDetail, tvAlamat, tvNama, tvStatuss, tvJemput, tvAntar;
    private Button btHubungi, btNavigasi, btPesanan, btbtAmbilPesanan, btUbahStatus;

    private GoogleMap mGoogleMap;

    private ProgressDialog loading;
    private GPSTracker gps;

    public ArrayList<Profil> valuesProfil;
    public DatabaseHandler dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_tab);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle(getString(R.string.activityOrderDetailTab));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map4);
        mapFragment.getMapAsync(OrderDetailTab.this);

        Intent ambil = getIntent();
        user_id = ambil.getStringExtra("user_id");
        order_id = ambil.getStringExtra("order_id");
        nama = ambil.getStringExtra("nama");
        namaJalan = ambil.getStringExtra("nama_jalan");
        alamat = ambil.getStringExtra("alamat");
        detail_lokasi = ambil.getStringExtra("detail_lokasi");
        invoice = ambil.getStringExtra("invoice");
        status = ambil.getStringExtra("status");
        phone = ambil.getStringExtra("phone");
        berat = ambil.getStringExtra("berat");
        total_bayar = ambil.getStringExtra("total_bayar");

        dataSource = new DatabaseHandler(this);
        valuesProfil = (ArrayList<Profil>) dataSource.getAllProfils();

        tvAlamat = (TextView) findViewById(R.id.textViewAlamat);
        tvNama = (TextView) findViewById(R.id.textViewNama);
        tvStatuss = (TextView) findViewById(R.id.textViewJenis);
        tvJemput = (TextView) findViewById(R.id.textViewDetailWaktuJemput);
        tvAntar = (TextView) findViewById(R.id.textViewDetailWaktuAntar);
        tvAlamatDetail = (TextView) findViewById(R.id.textViewAlamatDetail);
        btHubungi = (Button) findViewById(R.id.buttonHubungi);
        btNavigasi = (Button) findViewById(R.id.buttonNavigasi);
        btPesanan = (Button) findViewById(R.id.btPesanan);
        btbtAmbilPesanan = (Button) findViewById(R.id.btAmbilPesanan);
        btUbahStatus = (Button) findViewById(R.id.btUbahStatus);

        tvNama.setText(nama);
        tvAlamat.setText(namaJalan+"\n"+alamat);
        tvStatuss.setText(status);
        tvAlamatDetail.setText(detail_lokasi);

        gps = new GPSTracker(OrderDetailTab.this);

        btHubungi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
            }
        });

        btNavigasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderDetailTab.this, NavigasiActivity.class);
                intent.putExtra("currLat",gps.getLatitude());
                intent.putExtra("currLng",gps.getLongitude());
                intent.putExtra("endLat", startLat);
                intent.putExtra("endLng", startLng);
                intent.putExtra("namaUser",nama);
                intent.putExtra("order_id",order_id);
                startActivity(intent);
            }
        });

        btPesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderDetailTab.this, EditOrderActivity.class);
                intent.putExtra("nama",nama);
                intent.putExtra("alamat",alamat);
                intent.putExtra("berat",berat);
                intent.putExtra("order_id",order_id);
                intent.putExtra("total_bayar",total_bayar);
                startActivity(intent);
            }
        });

        btbtAmbilPesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ambilOrder();
            }
        });

        btUbahStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap=googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.getUiSettings().setAllGesturesEnabled(false);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.getUiSettings().setCompassEnabled(true);
        mGoogleMap.getUiSettings().setMapToolbarEnabled(false);

        Intent peta = getIntent();
        startLat = peta.getStringExtra("start_lat");
        startLng = peta.getStringExtra("start_lng");
        nama = peta.getStringExtra("nama");
        LatLng posisi = new LatLng(Double.parseDouble(startLat), Double.parseDouble(startLng));

        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posisi, 14));
        mGoogleMap.addMarker(new MarkerOptions().position(posisi).title(nama)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))).showInfoWindow();

    }

    private void ambilOrder() {
        loading = ProgressDialog.show(this,"Mohon Tunggu","Sedang memproses...",false,false);

        for (Profil profil : valuesProfil){
            token = profil.getToken();
        }

        RequestQueue queue = Volley.newRequestQueue(OrderDetailTab.this);
        StringRequest strReq = new StringRequest(Request.Method.POST, APIConfig.API_CHANGE_ORDER_STATUS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    String message = jObj.getString("message");
                    Toast.makeText(OrderDetailTab.this, ""+message, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    Toast.makeText(OrderDetailTab.this, e.toString(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
                Toast.makeText(OrderDetailTab.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("kurir_id", String.valueOf(kurir_id));
                params.put("mitra_id", String.valueOf(mitra_id));
                params.put("order_id", order_id);
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
        int socketTimeout = 15000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        strReq.setRetryPolicy(policy);
        queue.add(strReq);
    }

    private void changeStatus() {

        loading = ProgressDialog.show(this,"Mohon Tunggu","Sedang memuat...",false,false);

        for (Profil profil : valuesProfil){
            token = profil.getToken();
        }

        Intent ambilOrder = getIntent();
        order_id = ambilOrder.getStringExtra("order_id");
        kurir_id = String.valueOf(idkurir);
        mitra_id = String.valueOf(idmitra);

        RequestQueue queue = Volley.newRequestQueue(OrderDetailTab.this);
        StringRequest strReq = new StringRequest(Request.Method.POST, APIConfig.API_CHANGE_ORDER_STATUS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        String message = jObj.getString("message");
                        Toast.makeText(OrderDetailTab.this, ""+message, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(OrderDetailTab.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {
                    Toast.makeText(OrderDetailTab.this, ""+e, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
                Toast.makeText(OrderDetailTab.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }){
            //Untuk post data menggunakan volley
            //Data yang dikirim adalah email dan password
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("order_id",order_id);
                params.put("kurir_id",kurir_id);
                params.put("mitra_id", mitra_id);
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
