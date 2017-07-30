package com.beehapps.mitraaalondri.main.handle_fragment.handle_main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.beehapps.mitraaalondri.adapter.OrderAdapter;
import com.beehapps.mitraaalondri.config.APIConfig;
import com.beehapps.mitraaalondri.database.DatabaseHandler;
import com.beehapps.mitraaalondri.pojo.Order;
import com.beehapps.mitraaalondri.pojo.Profil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderOnlineActivity extends AppCompatActivity {

    private ListView lv;
    private DatabaseHandler dataSource;
    public ArrayList<Profil> valuesProfil;

    private ProgressDialog loading;

    private String invoice, layanan, cek, token, selected;
    private LinearLayout linRiwayat;
    private OrderAdapter adapter;

    private List<Order> dataList = new ArrayList<Order>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_online);

        lv = (ListView) findViewById(R.id.listViewOrderOnline);
        dataSource = new DatabaseHandler(this);
        valuesProfil = (ArrayList<Profil>) dataSource.getAllProfils();

        adapter = new OrderAdapter(this, dataList);
        dataList.clear();
        lv.setAdapter(adapter);

        makeJsonObjectRequest();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(OrderOnlineActivity.this, OrderDetail.class);
                intent.putExtra("idOrder",dataList.get(position).getIdOrder());
                intent.putExtra("idUser",dataList.get(position).getUser_id());
                intent.putExtra("idKurir",dataList.get(position).getKurir_id());
                intent.putExtra("idMitra",dataList.get(position).getMitra_id());
                intent.putExtra("nama_jalan",dataList.get(position).getNama_jalan());
                intent.putExtra("nama_alias",dataList.get(position).getNama_alias());
                intent.putExtra("phone_alias",dataList.get(position).getPhone_alias());
                intent.putExtra("invoice_number",dataList.get(position).getInvoice_number());
                intent.putExtra("total_harga",dataList.get(position).getTotal_harga());
                intent.putExtra("detail_lokasi",dataList.get(position).getDetail_lokasi());
                intent.putExtra("catatan",dataList.get(position).getCatatan());
                intent.putExtra("alamat",dataList.get(position).getAlamat());
                intent.putExtra("is_ekspress",dataList.get(position).getIs_ekspress());
                intent.putExtra("tanggal_mulai",dataList.get(position).getTanggal_mulai());
                intent.putExtra("tanggal_akhir",dataList.get(position).getTanggal_akhir());
                intent.putExtra("waktu_mulai",dataList.get(position).getWaktu_mulai());
                intent.putExtra("waktu_akhir",dataList.get(position).getWaktu_akhir());
                intent.putExtra("is_byitem",dataList.get(position).getIs_byitem());
                startActivity(intent);
            }
        });

    }

    private void makeJsonObjectRequest() {


        for (Profil profil : valuesProfil) {
            token = profil.getToken();
        }
        loading = ProgressDialog.show(OrderOnlineActivity.this,"Mohon Tunggu","Sedang memuat...",false,false);

        RequestQueue queue = Volley.newRequestQueue(OrderOnlineActivity.this);
        StringRequest strReq = new StringRequest(Request.Method.GET, APIConfig.API_GET_ORDER_ONLINE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        JSONArray dataArray = jObj.getJSONArray("message");
                        try {
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject isi = dataArray.getJSONObject(i);
                                String id = isi.getString("id");
                                String user_id = isi.getString("user_id");
                                String kurir_id = isi.getString("kurir_id");
                                String mitra_id = isi.getString("mitra_id");
                                String nama_jalan = isi.getString("nama_jalan");
                                JSONObject users = isi.getJSONObject("users");
                                String nama_alias = users.getString("nama_alias");
                                String phone_alias = users.getString("phone_alias");
                                String invoice_number = users.getString("invoice_number");
                                String total_harga = users.getString("total_harga");
                                String detail_lokasi = users.getString("detail_lokasi");
                                String catatan = users.getString("catatan");
                                String alamat = users.getString("alamat");
                                String is_ekspress = users.getString("is_ekspress");
                                String tanggal_mulai = users.getString("tanggal_mulai");
                                String tanggal_akhir = users.getString("tanggal_akhir");
                                String waktu_mulai = users.getString("waktu_mulai");
                                String waktu_akhir = users.getString("waktu_akhir");
                                String is_byitem = users.getString("is_byitem");
                                dataList.add(new Order(i+"", id, user_id, kurir_id, mitra_id, nama_jalan, nama_alias, phone_alias, invoice_number,
                                        total_harga, detail_lokasi, catatan, alamat, is_ekspress, tanggal_mulai, tanggal_akhir, waktu_mulai, waktu_akhir, is_byitem));
                            }
                        } catch (JSONException e) {
                            Toast.makeText(OrderOnlineActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                            Log.d("ERROR 1",""+e);
                        }
                    }
                } catch (JSONException e) {
                    Toast.makeText(OrderOnlineActivity.this, "" + e, Toast.LENGTH_SHORT).show();
                    Log.d("ERROR 2",""+e);
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
                Toast.makeText(OrderOnlineActivity.this, "" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
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
}
