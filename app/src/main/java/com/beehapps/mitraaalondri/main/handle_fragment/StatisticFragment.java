package com.beehapps.mitraaalondri.main.handle_fragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.beehapps.mitraaalondri.pojo.Profil;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    public ArrayList<Profil> valuesProfil;
    public DatabaseHandler dataSource;

    private TextView tvTotalKeuangan, tvOrderHarian, tvKiloan, tvSatuan;
    private EditText etTanggal;
    private Button btCari;

    private String token;

    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    private int Year, Month, Day;
    private String outputTanggalSekarang, tanggal, jam, invoicetel, conference;
    private SimpleDateFormat sdfDate, sdfTime;


    private ProgressDialog loading;

    public StatisticFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_statistic, container, false);

        tvOrderHarian = (TextView) rootView.findViewById(R.id.textViewStatOrder);
        tvTotalKeuangan = (TextView) rootView.findViewById(R.id.textViewStatPendapatan);
        tvKiloan = (TextView) rootView.findViewById(R.id.textViewStatKiloan);
        tvSatuan = (TextView) rootView.findViewById(R.id.textViewStatSatuan);
        btCari = (Button) rootView.findViewById(R.id.buttonCari);
        etTanggal = (EditText) rootView.findViewById(R.id.editTextTanggal);

        dataSource = new DatabaseHandler(getActivity());
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

        final FragmentManager fragmentManager = getActivity().getFragmentManager();

        etTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*datePickerDialog = DatePickerDialog.newInstance(getActivity().onDa, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#DA322A"));
                datePickerDialog.show(fragmentManager, "DatePickerDialog");*/
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
                    final Activity activity = (Activity) getContext();
                    dpd.show(activity.getFragmentManager(), "Datepickerdialog");
                }
                catch (Exception e) {
                    Toast.makeText(getActivity(), ""+e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

        btCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etTanggal.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Pilih tanggal terlebih dahulu!", Toast.LENGTH_SHORT).show();
                }else{
                    getData(etTanggal.getText().toString().trim());
                }
            }
        });


        return rootView;
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


    private void getData(final String tanggal) {
        loading = ProgressDialog.show(getActivity(),"Mohon Tunggu","Sedang mengambil data...",false,false);


        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest strReq = new StringRequest(Request.Method.POST, APIConfig.API_STAT_DASHBOARD, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                hideDialog();
                Log.d("Hasilnya","Stat "+response);
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if(!error){
                        JSONObject message = jObj.getJSONObject("message");
                        String keuanganOnline = message.getString("keuanganOnline");
                        String keuanganOffline = message.getString("keuanganOffline");
                        String totalKiloan = message.getString("totalKiloan");
                        String totalSatuan = message.getString("totalSatuan");

                        if(keuanganOnline.equals("null")||keuanganOnline.equals(null)){
                            keuanganOnline = "0";
                        }
                        if(keuanganOffline.equals("null")||keuanganOffline.equals(null)){
                            keuanganOffline = "0";
                        }
                        if(totalKiloan.equals("null")||totalKiloan.equals(null)){
                            totalKiloan = "0";
                        }
                        if(totalSatuan.equals("null")||totalSatuan.equals(null)){
                            totalSatuan = "0";
                        }

                        tvTotalKeuangan.setText("IDR "+keuanganOnline);
                        tvOrderHarian.setText("IDR "+keuanganOffline);
                        tvKiloan.setText(totalKiloan+" Kg");
                        tvSatuan.setText(totalSatuan+" piece");

                    }else{
                        String message = jObj.getString("message");
                        Toast.makeText(getActivity(), ""+message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
                Log.d("Hasilnya","error Stat "+error);
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("startDate", tanggal);
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

}
