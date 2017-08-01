package com.beehapps.mitraaalondri.main.handle_fragment.handle_main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.beehapps.mitraaalondri.pojo.OrderFrag;
import com.beehapps.mitraaalondri.pojo.Profil;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class OrderOfflineActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private EditText etNama, etBerat, etTanggal, etCatatan;
    private Button btSetuju;

    public ArrayList<Profil> valuesProfil;
    public DatabaseHandler dataSource;

    private String token, nama, berat, catatan, tgl;
    private ProgressDialog loading;

    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    private int Year, Month, Day;
    private String outputTanggalSekarang, tanggal, jam, invoicetel, conference;
    private SimpleDateFormat sdfDate, sdfTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_offline);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle(getString(R.string.activityOrderOffline));

        etNama = (EditText) findViewById(R.id.textViewOfflineNama);
        etBerat = (EditText) findViewById(R.id.textViewOfflineBerat);
        etTanggal = (EditText) findViewById(R.id.textViewOfflineTanggal);
        etCatatan = (EditText) findViewById(R.id.textViewOfflineCatatan);
        btSetuju = (Button) findViewById(R.id.btPesan); 

        dataSource = new DatabaseHandler(OrderOfflineActivity.this);
        valuesProfil = (ArrayList<Profil>) dataSource.getAllProfils();
        for (Profil profil : valuesProfil){
            token = profil.getToken();
        }
        Log.d("Hasilnya","token :"+token);

        calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+7"));
        sdfDate = new SimpleDateFormat("yyyy-MM-dd");

        //Untuk set kalender ke hari ini
        Year = calendar.get(Calendar.YEAR) ;
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        
        etTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                calendar.set(year, monthOfYear, dayOfMonth);
                                Year = year;
                                Month = monthOfYear;
                                Day = dayOfMonth;
                                Log.d("Tanggal Pilih",""+Year+" "+Month+" "+Day);
                                outputTanggalSekarang = sdfDate.format(calendar.getTime());
                                try{
                                    Date date = new Date();
                                    String output = new SimpleDateFormat("dd-MM-yyyy").format(date);
                                    Date dateNow = new SimpleDateFormat("dd-MM-yyyy").parse(output);
                                    Date dateCalendar=new SimpleDateFormat("dd-MM-yyyy").parse(outputTanggalSekarang);
                                    Log.d("Tanggal",""+dateNow+", "+dateCalendar);
                                    etTanggal.setText(""+outputTanggalSekarang);
                                }catch (Exception e){

                                }
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );

                try {
                    final Activity activity = (Activity) OrderOfflineActivity.this;
                    dpd.show(activity.getFragmentManager(), "Datepickerdialog");
                }
                catch (Exception e) {
                    Toast.makeText(OrderOfflineActivity.this, ""+e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

        btSetuju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nama = etNama.getText().toString().trim();
                berat = etBerat.getText().toString().trim();
                catatan = etCatatan.getText().toString().trim();
                tgl = etTanggal.getText().toString().trim();
                if(nama.isEmpty()||berat.isEmpty()||catatan.isEmpty()||tgl.isEmpty()){
                    Toast.makeText(OrderOfflineActivity.this, "Isi dengan benar!", Toast.LENGTH_SHORT).show();
                }else{

                }
            }
        });
        
    }

    @Override
    public void onDateSet(DatePickerDialog view, int Years, int Months, int Days) {
        calendar.set(Years, Months, Days);
        Year = Years;
        Month = Months;
        Day = Days;
        Log.d("Tanggal Pilih",""+Year+" "+Month+" "+Day);
        outputTanggalSekarang = sdfDate.format(calendar.getTime());
        try{
            Date date = new Date();
            String output = new SimpleDateFormat("dd-MM-yyyy").format(date);
            Date dateNow = new SimpleDateFormat("dd-MM-yyyy").parse(output);
            Date dateCalendar=new SimpleDateFormat("dd-MM-yyyy").parse(outputTanggalSekarang);

            Log.d("Tanggal",""+dateNow+", "+dateCalendar);

            etTanggal.setText(""+outputTanggalSekarang);

        }catch (Exception e){

        }
    }

    private void pesan(final String nama, final String berat, final String catatan, final String tanggal) {

        loading = ProgressDialog.show(OrderOfflineActivity.this,"Mohon Tunggu","Sedang memuat...",false,false);

        RequestQueue queue = Volley.newRequestQueue(OrderOfflineActivity.this);
        StringRequest strReq = new StringRequest(Request.Method.POST, APIConfig.API_ORDER_OFFLINE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        JSONObject message = jObj.getJSONObject("message");
                        String invoice_number = message.getString("invoice_number");
                        final AlertDialog.Builder alert = new AlertDialog.Builder(OrderOfflineActivity.this);
                        alert.setTitle("Peringatan");
                        alert.setMessage("Berikut adalah invoice Anda\n"+invoice_number);
                        alert.setPositiveButton("Ya",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // TODO Auto-generated method stub
                                        finish();
                                    }
                                });
                        alert.show();
                        etTanggal.setText("");
                        etNama.setText("");
                        etBerat.setText("");
                        etCatatan.setText("");
                    }
                } catch (JSONException e) {
                    Toast.makeText(OrderOfflineActivity.this, "" + e, Toast.LENGTH_SHORT).show();
                    Log.d("ERROR 2",""+e);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
                Toast.makeText(OrderOfflineActivity.this, "" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("type", "0");
                params.put("berat", berat);
                params.put("nama", nama);
                params.put("catatan", catatan);
                params.put("tanggal_mulai", tanggal);
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
