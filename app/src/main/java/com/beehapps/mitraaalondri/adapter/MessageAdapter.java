package com.beehapps.mitraaalondri.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beehapps.mitraaalondri.R;
import com.beehapps.mitraaalondri.pojo.Message;

import java.util.List;

/**
 * Created by farhan on 3/9/17.
 */

public class MessageAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Message> categoryItems;

    public MessageAdapter(Activity activity, List<Message> categoryItems) {
        this.activity = activity;
        this.categoryItems = categoryItems;
    }

    @Override
    public int getCount() {
        return categoryItems.size();
    }

    @Override
    public Object getItem(int location) {
        return categoryItems.get(location);
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
            convertView = inflater.inflate(R.layout.adapter_listview_message, null);

        //TextView title = (TextView) convertView.findViewById(R.id.text);
        TextView tvCus = (TextView) convertView.findViewById(R.id.textViewCustomer);
        TextView tvInv = (TextView) convertView.findViewById(R.id.textViewInvoice);
        TextView tvMit = (TextView) convertView.findViewById(R.id.textViewMitra);

        // getting movie data for the row
        Message history = categoryItems.get(position);
        tvCus.setText(history.getNamaUser());
        tvInv.setText(history.getInvoice_number());
        tvMit.setText(history.getNamaMitra());

        return convertView;
    }
}
