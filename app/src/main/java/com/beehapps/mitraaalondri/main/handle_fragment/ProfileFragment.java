package com.beehapps.mitraaalondri.main.handle_fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.beehapps.mitraaalondri.config.PromoData;
import com.beehapps.mitraaalondri.database.DatabaseHandler;
import com.beehapps.mitraaalondri.main.handle_login.LoginActivity;
import com.beehapps.mitraaalondri.pojo.Profil;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    public ArrayList<Profil> valuesProfil;
    public DatabaseHandler dataSource;

    private LinearLayout linearLayoutLogout;

    private TextView tvNama, tvEmail, tvOutlet, tvPemilik, tvAlamat;

    private String token,email,nama,outlet,pemilik,alamat;
    private ProgressDialog loading;

    public ProfileFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        linearLayoutLogout = (LinearLayout) rootView.findViewById(R.id.linearLayoutLogout);
        tvNama = (TextView) rootView.findViewById(R.id.textViewProfilNama);
        tvEmail = (TextView) rootView.findViewById(R.id.textViewProfilMail);
        tvOutlet = (TextView) rootView.findViewById(R.id.textViewProfilOutlet);
        tvPemilik = (TextView) rootView.findViewById(R.id.textViewProfilPemilik);
        tvAlamat = (TextView) rootView.findViewById(R.id.textViewProfilAlamat);

        dataSource = new DatabaseHandler(getActivity());
        valuesProfil = (ArrayList<Profil>) dataSource.getAllProfils();
        for (Profil profil : valuesProfil){
            token = profil.getToken();
            email = profil.getEmail();
            nama = profil.getUsername();
        }

        getDashboard();

        tvEmail.setText(email);
        tvNama.setText(nama);
        tvPemilik.setText(nama);

        linearLayoutLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataSource.hapusDbaseProfil();

                SharedPreferences sharedPreferences =  getActivity().getSharedPreferences("DATA",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("session",false);
                editor.commit();

                dataSource.hapusDbaseProfil();

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();
            }
        });


        return rootView;
    }

    private void getDashboard() {
        loading = ProgressDialog.show(getActivity(),"Mohon Tunggu","Sedang mengambil data...",false,false);


        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest strReq = new StringRequest(Request.Method.GET, APIConfig.API_DASHBOARD, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                hideDialog();
                Log.d("Hasilnya","Dashboard "+response);
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if(!error){
                        JSONObject message = jObj.getJSONObject("message");
                        String orderHarian = message.getString("orderHarian");
                        String totalKeuangan = message.getString("totalKeuangan");
                        String totalOrderOnline = message.getString("totalOrderOnline");
                        String totalOrderOffline = message.getString("totalOrderOffline");
                        JSONObject infoMitra = message.getJSONObject("infoMitra");
                        String nama_mitra = infoMitra.getString("nama_mitra");
                        String lokasi_mitra = infoMitra.getString("lokasi_mitra");
                        String alamat_mitra = infoMitra.getString("alamat_mitra");
                        tvOutlet.setText(nama_mitra);
                        tvAlamat.setText("Mitra "+lokasi_mitra+"\n"+alamat_mitra);
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
                Log.d("Hasilnya","error Dashboard "+error);
            }
        }){
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
