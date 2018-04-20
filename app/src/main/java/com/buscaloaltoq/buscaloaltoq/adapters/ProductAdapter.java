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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.buscaloaltoq.buscaloaltoq.R;
import com.buscaloaltoq.buscaloaltoq.entities.Products;
import com.buscaloaltoq.buscaloaltoq.entities.Shops;
import com.buscaloaltoq.buscaloaltoq.others.DialogPersonalized;
import com.buscaloaltoq.buscaloaltoq.others.GlobalVariables;
import com.buscaloaltoq.buscaloaltoq.others.ToastPersonalized;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by rz on 15/04/2018.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolderProduct> {

    private ArrayList<Products> productosArrayList;
    private Context context;
    private Shops carritoCompras=null;
    private ArrayList<Shops> listaCarritoCompras;
    private ToastPersonalized toastPersonalizado = new ToastPersonalized();
    private DialogPersonalized dialogPersonalizado= new DialogPersonalized();

    public ProductAdapter(ArrayList<Products> productosArrayList, Context context) {
        this.productosArrayList = productosArrayList;
        this.context=context;
    }

    @Override
    public ProductAdapter.ViewHolderProduct onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        return  new ViewHolderProduct(view);
    }

    @Override
    public void onBindViewHolder(final ProductAdapter.ViewHolderProduct holder, final int position) {
        holder.txtItemNombreP.setText(productosArrayList.get(position).getName());
        holder.txtItemPrecioP.setText("s/. "+ productosArrayList.get(position).getPrice().toString());
        //<------Aqui mostramos las imagenes
        Glide.with(context)
                .load(GlobalVariables.getUrlimagen() + productosArrayList.get(position).getPhoto())
                .into(holder.imgItemProductoP);
        //------>Fin :v
        holder.imgItemOpcionP.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                MenuBuilder menuBuilder = new MenuBuilder(context);
                new SupportMenuInflater(context).inflate(R.menu.option_menu_product, menuBuilder);
                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @Override
                    public boolean onMenuItemSelected(MenuBuilder menu, MenuItem menuItem) {
                        // your "setOnMenuItemClickListener" code goes here
                        switch (menuItem.getItemId()) {
                            case R.id.mnu_item_agregar:
                                carritoCompras = new Shops();
                                if (GlobalVariables.getCarritoComprasArrayList() != null && !GlobalVariables.getCarritoComprasArrayList().isEmpty()) {
                                    listaCarritoCompras = GlobalVariables.getCarritoComprasArrayList();
                                }else{
                                    listaCarritoCompras =  new ArrayList<>();
                                }
                                if (holder.txtItemCantidadP.getText().toString().equals("0") || holder.txtItemCantidadP.getText().toString().equals("")){
                                    holder.txtItemCantidadP.setError("INGRESAR CANTIDAD");
                                }else {
                                    //<------Aqui agregamos los productos al carrito de compra
                                    carritoCompras.setProductid(productosArrayList.get(position).getProductid());
                                    carritoCompras.setName(productosArrayList.get(position).getName());
                                    carritoCompras.setPhoto(productosArrayList.get(position).getPhoto());
                                    carritoCompras.setPrice(productosArrayList.get(position).getPrice());
                                    carritoCompras.setDescription(productosArrayList.get(position).getDescription());
                                    carritoCompras.setQuantity(Integer.parseInt(holder.txtItemCantidadP.getText().toString()));
                                    DecimalFormat decimalFormat = new DecimalFormat();
                                    decimalFormat.setMaximumFractionDigits(2);
                                    carritoCompras.setSubtotal(Double.parseDouble(decimalFormat.format(Integer.parseInt(holder.txtItemCantidadP.getText().toString())*productosArrayList.get(position).getPrice())));
                                    GlobalVariables.setTotal(GlobalVariables.getTotal() + carritoCompras.getSubtotal());
                                    listaCarritoCompras.add(carritoCompras);
                                    GlobalVariables.setCarritoComprasArrayList(listaCarritoCompras);
                                    toastPersonalizado.mostrarMensaje(context,"PRODUCTO AGREGADO",R.drawable.ic_toast_very);
                                    //------>Fin :v
                                }
                                break;
                            case R.id.mnu_item_ver:
                                //<------Aqui mostramos la ventana de Dialog de Descripcion del Producto
                                dialogPersonalizado.mostrarDialog(context,productosArrayList.get(position).getDescription());
                                //------>Fin :v
                                break;
                        }
                        return false;
                    }
                    @Override
                    public void onMenuModeChange(MenuBuilder menu) {
                    }
                });
                MenuPopupHelper menuHelper = new MenuPopupHelper(context, menuBuilder, view);
                menuHelper.setForceShowIcon(true); // show icons!!!!!!!!
                menuHelper.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productosArrayList.size();
    }

    public class ViewHolderProduct extends RecyclerView.ViewHolder {
        TextView txtItemNombreP,txtItemPrecioP;
        ImageView imgItemProductoP,imgItemOpcionP;
        EditText txtItemCantidadP;
        public ViewHolderProduct(View itemView) {
            super(itemView);
            txtItemCantidadP = (EditText)itemView.findViewById(R.id.txtItemCantidadP);
            txtItemNombreP = (TextView) itemView.findViewById(R.id.txtItemNombreP);
            txtItemPrecioP = (TextView) itemView.findViewById(R.id.txtItemPrecioP);
            imgItemOpcionP = (ImageView) itemView.findViewById(R.id.imgItemOpcionP);
            imgItemProductoP = (ImageView) itemView.findViewById(R.id.imgItemProductoP);
        }
    }
}
