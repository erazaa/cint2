package com.buscaloaltoq.buscaloaltoq.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.buscaloaltoq.buscaloaltoq.R;
import com.buscaloaltoq.buscaloaltoq.entities.Orders;

import java.util.ArrayList;

/**
 * Created by rz on 15/04/2018.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolderOrder> implements View.OnClickListener{

    ArrayList<Orders> pedidosArrayList;
    private View.OnClickListener listener;

    public OrderAdapter(ArrayList<Orders> pedidosArrayList) {
        this.pedidosArrayList = pedidosArrayList;
    }

    @Override
    public OrderAdapter.ViewHolderOrder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order,parent,false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        view.setOnClickListener(this);
        return  new ViewHolderOrder(view);
    }

    @Override
    public void onBindViewHolder(OrderAdapter.ViewHolderOrder holder, int position) {
        holder.txtItemPedidoF.setText(pedidosArrayList.get(position).getOrderid().toString());
        holder.txtItemTotalF.setText(pedidosArrayList.get(position).getTotal().toString());
        holder.txtItemFechaF.setText(pedidosArrayList.get(position).getCreated_at());
    }

    @Override
    public int getItemCount() {
        return pedidosArrayList.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolderOrder extends RecyclerView.ViewHolder {
        TextView txtItemPedidoF,txtItemTotalF,txtItemFechaF;
        public ViewHolderOrder(View itemView) {
            super(itemView);
            txtItemPedidoF = (TextView) itemView.findViewById(R.id.txtItemPedidoPe);
            txtItemTotalF = (TextView) itemView.findViewById(R.id.txtItemTotalPe);
            txtItemFechaF = (TextView) itemView.findViewById(R.id.txtItemFechaPe);
        }
    }
}
