package com.beehapps.mitraaalondri.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beehapps.mitraaalondri.R;
import com.beehapps.mitraaalondri.database.DatabaseHandler;
import com.beehapps.mitraaalondri.pojo.Chats;
import com.beehapps.mitraaalondri.pojo.Profil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by farhan on 2/13/17.
 */

public class ChatAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Chats> orderItems;
    private ArrayList<Profil> valuesProfil;
    private DatabaseHandler dataSource;
    private String id;


    public ChatAdapter(Activity activity, List<Chats> orderItems) {
        this.activity = activity;
        this.orderItems = orderItems;
    }

    @Override
    public int getCount() {
        return orderItems.size();
    }

    @Override
    public Object getItem(int location) {
        return orderItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.adapter_chat, null);

        TextView title = (TextView) convertView.findViewById(R.id.textName);
        TextView chat = (TextView) convertView.findViewById(R.id.textChat);
        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.linLayAdapter);
        View viewLeft = (View) convertView.findViewById(R.id.viewLeft);
        View viewRight = (View) convertView.findViewById(R.id.viewRight);

        // getting movie data for the row
        Chats m = orderItems.get(position);
        title.setText(m.getSender_name());
        chat.setText(m.getContent());

        dataSource = new DatabaseHandler(activity);
        valuesProfil = (ArrayList<Profil>) dataSource.getAllProfils();
        for (Profil profil : valuesProfil){
            id = profil.getUsername();
        }

        if(m.getSender_name().equals(id)){
            viewLeft.setVisibility(View.GONE);
            viewRight.setVisibility(View.VISIBLE);
            linearLayout.setBackgroundResource(R.drawable.bg_chats_left);
        }else{
            viewLeft.setVisibility(View.VISIBLE);
            viewRight.setVisibility(View.GONE);
            linearLayout.setBackgroundResource(R.drawable.bg_chats_right);
        }

        return convertView;
    }

}