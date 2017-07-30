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
import com.beehapps.mitraaalondri.pojo.Order;

import java.util.List;

/**
 * Created by farhan on 3/9/17.
 */

public class OrderAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Order> categoryItems;
    private String layanan;

    public OrderAdapter(Activity activity, List<Order> categoryItems) {
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
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.adapter_order_online, null);

        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvType = (TextView) convertView.findViewById(R.id.tvType);
        TextView tvSchedule = (TextView) convertView.findViewById(R.id.tvSchedule);
        TextView tvPhone = (TextView) convertView.findViewById(R.id.tvPhone);
        TextView tvAddress = (TextView) convertView.findViewById(R.id.tvAddress);

        Order order = categoryItems.get(position);
        if (order.getIs_ekspress().equals("1")){
            layanan = "Reguler";
        }else if(order.getIs_ekspress().equals("2")){
            layanan = "Ekspress";
        }else if(order.getIs_ekspress().equals("3")){
            layanan = "Kilat";
        }

        tvName.setText(""+order.getNama_alias());
        tvType.setText(layanan);
        tvSchedule.setText(order.getTanggal_mulai()+" "+order.getWaktu_mulai());
        tvPhone.setText(""+order.getPhone_alias());
        tvAddress.setText(order.getAlamat()+"\n"+order.getDetail_lokasi());

        return convertView;
    }
}
