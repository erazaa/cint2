package com.buscaloaltoq.buscaloaltoq.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.buscaloaltoq.buscaloaltoq.R;
import com.buscaloaltoq.buscaloaltoq.entities.Shops;
import com.buscaloaltoq.buscaloaltoq.others.DialogPersonalized;
import com.buscaloaltoq.buscaloaltoq.others.GlobalVariables;
import com.buscaloaltoq.buscaloaltoq.others.ToastPersonalized;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by rz on 15/04/2018.
 */

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolderShop> {

    private ArrayList<Shops> carritoComprasArrayList;
    private Context context;
    private TextView txtTotalCC;
    private Button btnComprar;
    ToastPersonalized toastPersonalizado = new ToastPersonalized();
    private DialogPersonalized dialogPersonalizado= new DialogPersonalized();

    public ShopAdapter(ArrayList<Shops> carritoComprasArrayList, Context context,TextView textView,Button button) {
        this.carritoComprasArrayList = carritoComprasArrayList;
        this.context=context;
        this.txtTotalCC=textView;
        this.btnComprar=button;
    }

    @Override
    public ShopAdapter.ViewHolderShop onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop,parent,false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        return  new ViewHolderShop(view);
    }

    private  void generarTotal(){
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(2);

        GlobalVariables.setTotal(0.0);
        for (Shops carritoCompras: GlobalVariables.getCarritoComprasArrayList()){
            GlobalVariables.setTotal(GlobalVariables.getTotal() + carritoCompras.getSubtotal());
        }
        txtTotalCC.setText(decimalFormat.format(GlobalVariables.getTotal()));
    }

    @Override
    public void onBindViewHolder(final ShopAdapter.ViewHolderShop holder, final int position) {
        holder.txtItemNombreCC.setText(carritoComprasArrayList.get(position).getName());
        holder.txtItemCantidadCC.setText(carritoComprasArrayList.get(position).getQuantity().toString());
        holder.txtItemSubTotalCC.setText("s/. "+ carritoComprasArrayList.get(position).getSubtotal().toString());
        //<------Aqui mostramos las imagenes
        Glide.with(context)
                .load(GlobalVariables.getUrlimagen() + carritoComprasArrayList.get(position).getPhoto())
                .into(holder.imgItemProductoCC);
        //------>Fin :v
        holder.imgItemOpcionCC.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                MenuBuilder menuBuilder = new MenuBuilder(context);
                if (GlobalVariables.isCookieMenuEditar()==false) {
                    new SupportMenuInflater(context).inflate(R.menu.option_menu_shop, menuBuilder);
                    menuBuilder.setCallback(new MenuBuilder.Callback() {
                        @Override
                        public boolean onMenuItemSelected(MenuBuilder menu, MenuItem menuItem) {
                            // your "setOnMenuItemClickListener" code goes here
                            switch (menuItem.getItemId()) {
                                case R.id.mnu_item_update:
                                    btnComprar.setEnabled(false);
                                    GlobalVariables.setCookieMenuEditar(true);
                                    holder.txtItemCantidadCC.setEnabled(true);
                                    holder.txtItemCantidadCC.requestFocus();
                                    break;
                                case R.id.mnu_item_delete:

                                    GlobalVariables.getCarritoComprasArrayList().remove(position);
                                    notifyDataSetChanged();
                                    generarTotal();
                                    if (GlobalVariables.getCarritoComprasArrayList() != null && !GlobalVariables.getCarritoComprasArrayList().isEmpty()) {
                                        btnComprar.setEnabled(true);
                                    }else{
                                        btnComprar.setEnabled(false);
                                    }
                                    break;
                                case R.id.mnu_item_look:
                                    dialogPersonalizado.mostrarDialog(context, carritoComprasArrayList.get(position).getDescription());
                                    break;
                            }
                            return false;
                        }
                        @Override
                        public void onMenuModeChange(MenuBuilder menu) {
                        }
                    });
                }else
                {
                    new SupportMenuInflater(context).inflate(R.menu.option_menu_edit_shop, menuBuilder);
                    menuBuilder.setCallback(new MenuBuilder.Callback() {
                        @Override
                        public boolean onMenuItemSelected(MenuBuilder menu, MenuItem menuItem) {
                            // your "setOnMenuItemClickListener" code goes here
                            switch (menuItem.getItemId()) {
                                case R.id.mnu_item_save:
                                    if (holder.txtItemCantidadCC.getText().toString().equals("0") || holder.txtItemCantidadCC.getText().toString().equals("")){
                                        holder.txtItemCantidadCC.setError("INGRESAR CANTIDAD");
                                    }else {
                                        GlobalVariables.getCarritoComprasArrayList().get(position).setQuantity(Integer.parseInt(holder.txtItemCantidadCC.getText().toString()));
                                        DecimalFormat decimalFormat= new DecimalFormat();
                                        decimalFormat.setMaximumFractionDigits(2);
                                        GlobalVariables.getCarritoComprasArrayList().get(position).setSubtotal(Double.parseDouble(decimalFormat.format(Integer.parseInt(holder.txtItemCantidadCC.getText().toString())*GlobalVariables.getCarritoComprasArrayList().get(position).getPrice())));
                                        generarTotal();
                                        GlobalVariables.setCookieMenuEditar(false);
                                        holder.txtItemCantidadCC.setEnabled(false);
                                        btnComprar.setEnabled(true);
                                        notifyDataSetChanged();
                                    }
                                    break;
                            }
                            return false;
                        }
                        @Override
                        public void onMenuModeChange(MenuBuilder menu) {
                        }
                    });
                }
                MenuPopupHelper menuHelper = new MenuPopupHelper(context, menuBuilder, view);
                menuHelper.setForceShowIcon(true); // show icons!!!!!!!!
                menuHelper.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return carritoComprasArrayList.size();
    }

    public class ViewHolderShop extends RecyclerView.ViewHolder {
        TextView txtItemNombreCC,txtItemSubTotalCC;
        EditText txtItemCantidadCC;
        ImageView imgItemOpcionCC,imgItemProductoCC;

        public ViewHolderShop(View itemView) {
            super(itemView);
            txtItemCantidadCC = (EditText) itemView.findViewById(R.id.txtItemCantidadCC);
            txtItemNombreCC = (TextView) itemView.findViewById(R.id.txtItemNombreCC);
            txtItemSubTotalCC = (TextView) itemView.findViewById(R.id.txtItemSubTotalCC);
            imgItemOpcionCC = (ImageView)itemView.findViewById(R.id.imgItemOpcionCC);
            imgItemProductoCC = (ImageView)itemView.findViewById(R.id.imgItemProductoCC);
        }
    }

}
