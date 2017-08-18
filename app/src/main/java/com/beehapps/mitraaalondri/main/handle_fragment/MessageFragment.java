package com.beehapps.mitraaalondri.main.handle_fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.beehapps.mitraaalondri.adapter.MessageAdapter;
import com.beehapps.mitraaalondri.config.APIConfig;
import com.beehapps.mitraaalondri.database.DatabaseHandler;
import com.beehapps.mitraaalondri.main.handle_fragment.handle_message.MessageActivity;
import com.beehapps.mitraaalondri.main.handle_login.LoginActivity;
import com.beehapps.mitraaalondri.pojo.Message;
import com.beehapps.mitraaalondri.pojo.Profil;

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
public class MessageFragment extends Fragment {

    private ListView lv;
    private DatabaseHandler dataSource;
    public ArrayList<Profil> valuesProfil;

    private ProgressDialog loading;

    private String invoice, layanan, cek, token, selected, namamitra;
    private LinearLayout linRiwayat;
    private MessageAdapter adapter;

    private List<Message> dataList = new ArrayList<Message>();


    public MessageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.refresh, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                makeJsonObjectRequest();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_message, container, false);
        //((MainActivity) getActivity()).setTitle("Bantuan");


        lv = (ListView) rootView.findViewById(R.id.listViewMessage);
        linRiwayat = (LinearLayout) rootView.findViewById(R.id.linLayBelumRiwayat);
        dataSource = new DatabaseHandler(getActivity());
        valuesProfil = (ArrayList<Profil>) dataSource.getAllProfils();

        adapter = new MessageAdapter(getActivity(), dataList);
        dataList.clear();
        lv.setAdapter(adapter);

        makeJsonObjectRequest();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getActivity(), MessageActivity.class);
                intent.putExtra("idOrder",dataList.get(position).getIdOrder());
                intent.putExtra("idMitra",dataList.get(position).getIdMitra());
                intent.putExtra("idUser",dataList.get(position).getIdUser());
                intent.putExtra("invoice",dataList.get(position).getInvoice_number());
                startActivity(intent);
            }
        });

        return rootView;
    }

    private void makeJsonObjectRequest() {

        dataList.clear();

        for (Profil profil : valuesProfil) {
            token = profil.getToken();
            namamitra = profil.getUsername();
        }
        loading = ProgressDialog.show(getActivity(),"Mohon Tunggu","Sedang memuat...",false,false);

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest strReq = new StringRequest(Request.Method.GET, APIConfig.API_GET_ORDER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d("RESPON","message "+response);

                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        JSONArray message = jObj.getJSONArray("message");
                        for (int i = 0; i < message.length(); i++){
                            JSONObject object = message.getJSONObject(i);
                            String id = object.getString("id");
                            String user_id = object.getString("user_id");
                            String kurir_id = object.getString("kurir_id");
                            String mitra_id = object.getString("mitra_id");
                            JSONObject detail = object.getJSONObject("detail");
                            String nama_alias = detail.getString("nama_alias");
                            String invoice_number = detail.getString("invoice_number");
                            dataList.add(new Message(i+"",id,user_id,kurir_id,mitra_id,namamitra,nama_alias,invoice_number));
                        }
                    }
                } catch (JSONException e) {
                    Toast.makeText(getActivity(), "" + e, Toast.LENGTH_SHORT).show();
                }
                if(dataList.size()!=0){
                    linRiwayat.setVisibility(View.GONE);
                }else{
                    linRiwayat.setVisibility(View.VISIBLE);
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                hideDialog();
                Toast.makeText(getActivity(), "" + error, Toast.LENGTH_SHORT).show();
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

        int socketTimeout = 40000; // 40 seconds. You can change it
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
