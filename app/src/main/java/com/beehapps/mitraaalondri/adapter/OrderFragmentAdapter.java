package com.beehapps.mitraaalondri.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beehapps.mitraaalondri.R;
import com.beehapps.mitraaalondri.pojo.Order;
import com.beehapps.mitraaalondri.pojo.OrderFrag;

import java.util.List;

/**
 * Created by farhan on 3/9/17.
 */

public class OrderFragmentAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<OrderFrag> categoryItems;
    private String layanan;

    public OrderFragmentAdapter(Activity activity, List<OrderFrag> categoryItems) {
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
            convertView = inflater.inflate(R.layout.adapter_jemput, null);

        TextView tvInvoice = (TextView) convertView.findViewById(R.id.textViewJemputNo);
        TextView tvName = (TextView) convertView.findViewById(R.id.textViewJemputNama);
        TextView tvType = (TextView) convertView.findViewById(R.id.textViewJemputTipe);
        TextView tvJemput = (TextView) convertView.findViewById(R.id.textViewJemputJemput);
        TextView tvAntar = (TextView) convertView.findViewById(R.id.textViewJemputAntar);
        TextView tvBayar = (TextView) convertView.findViewById(R.id.textViewJemputBayar);
        TextView tvStatus = (TextView) convertView.findViewById(R.id.textViewJemputStatus);

        OrderFrag order = categoryItems.get(position);
        if (order.getIs_ekspress().equals("1")){
            layanan = "Reguler";
        }else if(order.getIs_ekspress().equals("2")){
            layanan = "Ekspress";
        }else if(order.getIs_ekspress().equals("3")){
            layanan = "Kilat";
        }

        tvInvoice.setText(order.getInvoice_number());
        tvName.setText(order.getNama_alias());
        tvType.setText(layanan+"\n"+order.getAlamat()+", "+order.getDetail_lokasi()+"\n"+order.getPhone_alias());
        tvJemput.setText(order.getTanggal_mulai()+" "+order.getWaktu_mulai());
        tvAntar.setText(order.getTanggal_mulai()+" "+order.getWaktu_mulai());
        tvBayar.setText("IDR "+order.getTotal_harga());
        if(order.getStatus().equals("2")){
            tvStatus.setText("Order Ditolak");
        }else{
            tvStatus.setText("Order Diterima");
        }

        return convertView;
    }
}
