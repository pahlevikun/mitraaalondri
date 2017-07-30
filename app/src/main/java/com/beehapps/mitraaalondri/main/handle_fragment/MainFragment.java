package com.beehapps.mitraaalondri.main.handle_fragment;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.beehapps.mitraaalondri.main.handle_fragment.handle_main.OrderOfflineActivity;
import com.beehapps.mitraaalondri.main.handle_fragment.handle_main.OrderOnlineActivity;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    public ArrayList<Profil> valuesProfil;
    public DatabaseHandler dataSource;

    private LinearLayout orderOnline, orderOffline, orderHistory;
    private CardView cardViewOffline, cardViewOnline;
    private TextView tvNamaMitra, tvLokasiMitra, tvOrderHarian, tvTotalKeuangan, tvOrderOnline, tvOrderOffline;

    private String token,nama;
    private HashMap<String,String> url_maps;

    private ProgressDialog loading;

    private SliderLayout sliderShow;

    public MainFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);


            sliderShow = (SliderLayout) rootView.findViewById(R.id.slider);
            orderOffline = (LinearLayout) rootView.findViewById(R.id.linLayOrderOffline);
            orderOnline = (LinearLayout) rootView.findViewById(R.id.linLayOrderOffline);
            cardViewOnline = (CardView) rootView.findViewById(R.id.cardViewOrderOnline);
            cardViewOffline = (CardView) rootView.findViewById(R.id.cardViewOrderOffline);
            tvNamaMitra = (TextView) rootView.findViewById(R.id.textViewMainNama);
            tvLokasiMitra = (TextView) rootView.findViewById(R.id.textViewMainLokasi);
            tvOrderHarian = (TextView) rootView.findViewById(R.id.textViewMainOrder);
            tvTotalKeuangan = (TextView) rootView.findViewById(R.id.textViewMainPendapatan);
            tvOrderOnline = (TextView) rootView.findViewById(R.id.textViewMainOnline);
            tvOrderOffline = (TextView) rootView.findViewById(R.id.textViewMainOffline);

            dataSource = new DatabaseHandler(getActivity());
            valuesProfil = (ArrayList<Profil>) dataSource.getAllProfils();
            for (Profil profil : valuesProfil){
                token = profil.getToken();
            }
            Log.d("Hasilnya","token :"+token);

            getDashboard();
            getSlider();

        cardViewOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),OrderOfflineActivity.class);
                startActivity(intent);
            }
        });

        cardViewOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),OrderOnlineActivity.class);
                startActivity(intent);
            }
        });

            return rootView;
        }

    private void getSlider() {

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest strReq = new StringRequest(Request.Method.GET, APIConfig.API_PROMO, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("Hasilnya","Slider "+response);
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (error == false) {
                        String message = jObj.getString("data");
                        List<PromoData> data = new ArrayList<>();
                        JSONArray jArray = new JSONArray(message);
                        try {
                            url_maps = new HashMap<String, String>();
                            for(int i = 0; i < jArray.length();i++) {
                                JSONObject promo_data = jArray.getJSONObject(i);
                                PromoData promoData = new PromoData();
                                promoData.judul = promo_data.getString("judul");
                                promoData.konten = promo_data.getString("konten");
                                promoData.gambar = promo_data.getString("gambar");

                                url_maps.put(promoData.judul, promoData.gambar);
                            }
                            for(String name : url_maps.keySet()){
                                DefaultSliderView defaultSliderView = new DefaultSliderView(getActivity());
                                defaultSliderView
                                        .description(name)
                                        .image(url_maps.get(name))
                                        .setScaleType(BaseSliderView.ScaleType.Fit);
                                Log.d("URL GAMBAR",""+url_maps.get(name));
                                defaultSliderView.bundle(new Bundle());
                                defaultSliderView.getBundle()
                                        .putString("extra",name);

                                sliderShow.addSlider(defaultSliderView);
                            }
                            sliderShow.setPresetTransformer(SliderLayout.Transformer.Default);
                            sliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                            sliderShow.setCustomAnimation(new DescriptionAnimation());
                            sliderShow.setDuration(6000);
                        } catch (JSONException e) {
                            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                        }
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Hasilnya","error Slider "+error);
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
                        tvNamaMitra.setText(nama_mitra);
                        tvLokasiMitra.setText(lokasi_mitra);
                        tvOrderHarian.setText(orderHarian);
                        tvTotalKeuangan.setText(totalKeuangan);
                        tvOrderOffline.setText(totalOrderOffline);
                        tvOrderOnline.setText(totalOrderOnline);
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

    @Override
    public void onStop() {
        sliderShow.stopAutoCycle();
        super.onStop();
    }

    private void hideDialog() {
        if (loading.isShowing())
            loading.dismiss();
    }

}
