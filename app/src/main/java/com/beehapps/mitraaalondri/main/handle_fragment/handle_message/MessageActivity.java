package com.beehapps.mitraaalondri.main.handle_fragment.handle_message;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.beehapps.mitraaalondri.adapter.ChatAdapter;
import com.beehapps.mitraaalondri.config.APIConfig;
import com.beehapps.mitraaalondri.database.DatabaseHandler;
import com.beehapps.mitraaalondri.pojo.Chats;
import com.beehapps.mitraaalondri.pojo.Profil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MessageActivity extends AppCompatActivity {

    private Button btn_send_msg;
    private EditText input_msg;
    private DatabaseHandler dataSource;
    private ArrayList<Profil> valuesProfil;
    private ArrayList<Chats> valuesChat = new ArrayList<>();
    private ListView listView;
    private ChatAdapter adapter;
    private String token, idOrder,idMitra,idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle(getString(R.string.activityMessage));

        listView = (ListView) findViewById(R.id.listViewChat);
        btn_send_msg = (Button)findViewById(R.id.button);
        input_msg = (EditText)findViewById(R.id.editText);

        dataSource = new DatabaseHandler(MessageActivity.this);
        valuesProfil = (ArrayList<Profil>) dataSource.getAllProfils();

        for (Profil profil : valuesProfil) {
            token = profil.getToken();
            idMitra = String.valueOf(profil.getUserID());
        }

        Intent intent = getIntent();
        idOrder = intent.getStringExtra("idOrder");
        idMitra = intent.getStringExtra("idMitra");
        idUser = intent.getStringExtra("idUser");
        requestChat(idOrder);

        adapter = new ChatAdapter(MessageActivity.this, valuesChat);
        listView.setAdapter(adapter);
        listView.setDividerHeight(0);
        adapter.notifyDataSetChanged();

        btn_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String chat = input_msg.getText().toString().trim();
                if(chat.isEmpty()){
                    Toast.makeText(MessageActivity.this, "Silahkan isi dengan benar!", Toast.LENGTH_SHORT).show();
                }else {
                    sendChat(idOrder,idUser,chat);
                }
            }
        });

    }

    public void requestChat(final String order_id){
        RequestQueue queue = Volley.newRequestQueue(MessageActivity.this);
        StringRequest strReq = new StringRequest(Request.Method.POST, APIConfig.API_CHAT_READ, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                input_msg.setText("");
                Log.d("RESPON","chat "+response);

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if(!error){
                        valuesChat.clear();
                        JSONArray jArray = jObj.getJSONArray("data");
                        for (int i=0; i<jArray.length();i++){
                            JSONObject data = jArray.getJSONObject(i);
                            String id = data.getString("id");
                            String sender_id = data.getString("sender_id");
                            String receiver_id = data.getString("receiver_id");
                            String sender_name = data.getString("sender_name");
                            String receiver_name = data.getString("receiver_name");
                            String content = data.getString("content");
                            valuesChat.add(new Chats(id,sender_id,receiver_id,sender_name,receiver_name,content));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "JSON Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
                adapter.notifyDataSetChanged();
                new Handler().postDelayed(new Thread() {
                    @Override
                    public void run() {
                        requestChat(order_id);
                    }
                }, 1000);
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Gagal Terhubung ke Server", Toast.LENGTH_LONG).show();
                finish();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("order_id",order_id);
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
        int socketTimeout = 50000; // 40 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        strReq.setRetryPolicy(policy);
        queue.add(strReq);
    }

    public void sendChat(final String order_id, final String user_id, final String content){
        RequestQueue queue = Volley.newRequestQueue(MessageActivity.this);
        StringRequest strReq = new StringRequest(Request.Method.POST, APIConfig.API_CHAT_SEND, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObj = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "JSON Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Gagal Terhubung ke Server", Toast.LENGTH_LONG).show();
                finish();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("order_id",order_id);
                params.put("receiver_id",user_id);
                params.put("content",content);
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
        int socketTimeout = 50000; // 40 seconds. You can change it
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
}
