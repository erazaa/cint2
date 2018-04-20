package com.buscaloaltoq.buscaloaltoq.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.buscaloaltoq.buscaloaltoq.R;
import com.buscaloaltoq.buscaloaltoq.entities.OrderDetails;
import com.buscaloaltoq.buscaloaltoq.others.GlobalVariables;

import java.util.ArrayList;

/**
 * Created by rz on 15/04/2018.
 */

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.ViewHolderOrderDetails> implements View.OnClickListener {

    ArrayList<OrderDetails> detallePedidosArrayList;
    private View.OnClickListener listener;
    private Context context;

    public OrderDetailsAdapter(ArrayList<OrderDetails> detallePedidosArrayList, Context context) {
        this.detallePedidosArrayList = detallePedidosArrayList;
        this.context=context;
    }

    @Override
    public OrderDetailsAdapter.ViewHolderOrderDetails onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orderdetail,parent,false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        view.setOnClickListener(this);
        return  new ViewHolderOrderDetails(view);
    }

    @Override
    public void onBindViewHolder(OrderDetailsAdapter.ViewHolderOrderDetails holder, int position) {
        holder.txtItemNombreDP.setText(detallePedidosArrayList.get(position).getName());
        holder.txtItemCantidadDP.setText(detallePedidosArrayList.get(position).getQuantity().toString());
        holder.txtItemTotalDP.setText(detallePedidosArrayList.get(position).getSubtotal().toString());
        //<------Aqui mostramos las imagenes
        Glide.with(context)
                .load(GlobalVariables.getUrlimagen() + detallePedidosArrayList.get(position).getPhoto())
                .into(holder.imgItemProductoDP);
        //------>Fin :v
    }

    @Override
    public int getItemCount() {
        return detallePedidosArrayList.size();
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

    public class ViewHolderOrderDetails extends RecyclerView.ViewHolder {
        TextView txtItemNombreDP,txtItemCantidadDP,txtItemTotalDP;

        ImageView imgItemProductoDP;
        public ViewHolderOrderDetails(View itemView) {
            super(itemView);
            txtItemNombreDP = (TextView) itemView.findViewById(R.id.txtItemNombreDP);
            txtItemCantidadDP = (TextView) itemView.findViewById(R.id.txtItemCantidadDP);
            txtItemTotalDP = (TextView) itemView.findViewById(R.id.txtItemTotalDP);
            imgItemProductoDP = (ImageView) itemView.findViewById(R.id.imgItemProductoDP);
        }
    }
}
