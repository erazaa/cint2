package com.buscaloaltoq.buscaloaltoq.fragments;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.buscaloaltoq.buscaloaltoq.R;
import com.buscaloaltoq.buscaloaltoq.adapters.ShopAdapter;
import com.buscaloaltoq.buscaloaltoq.entities.Shops;
import com.buscaloaltoq.buscaloaltoq.others.GlobalVariables;
import com.buscaloaltoq.buscaloaltoq.others.ToastPersonalized;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends Fragment {

    RecyclerView recyclerViewCarritoCompra;
    ArrayList<Shops> listaCarritoCompras;
    TextView txtTotalCC;
    ShopAdapter carritoCompraAdapter;
    Button btnComprar;
    String fechaActual;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    JSONArray detallePedidoItems;
    ToastPersonalized toastPersonalizado = new ToastPersonalized();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop,container,false);
        txtTotalCC = (TextView)view.findViewById(R.id.txtTotalCC);
        btnComprar = (Button)view.findViewById(R.id.btnComprar);
        recyclerViewCarritoCompra = (RecyclerView) view.findViewById(R.id.rvCarritoCompra);
        recyclerViewCarritoCompra.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerViewCarritoCompra.setHasFixedSize(true);

        requestQueue = Volley.newRequestQueue(getContext());

        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(GlobalVariables.isCookieUsuario()==false){
                    toastPersonalizado.mostrarMensaje(getContext(),"DEBE LOGUEARSE",R.drawable.ic_toast_bad);
                }else {
                  //  pantallPago();
                }
            }
        });

        cargarLista();
        return view;
    }

    private void cargarLista() {
        if (GlobalVariables.getCarritoComprasArrayList() != null && !GlobalVariables.getCarritoComprasArrayList().isEmpty()) {
            btnComprar.setEnabled(true);
            listaCarritoCompras = GlobalVariables.getCarritoComprasArrayList();

        }else{
            listaCarritoCompras =  new ArrayList<>();
            btnComprar.setEnabled(false);
        }

        txtTotalCC.setText(GlobalVariables.getTotal().toString());
        carritoCompraAdapter = new ShopAdapter(listaCarritoCompras,getContext(),txtTotalCC,btnComprar);
        recyclerViewCarritoCompra.setAdapter(carritoCompraAdapter);
    }

    private void cargarWebServicePedido(){
        fechaActual = new SimpleDateFormat("yyyy-MM-dd").format(new Date().getTime());
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Cargando....");
        progressDialog.show();
        String url = GlobalVariables.getUrl() + "wsJSONRegistrarPedido.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.hide();
                int pedidoID;

                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONArray jsonArray = jsonObject.optJSONArray("Pedidos");
                    JSONObject jsonRow =jsonArray.getJSONObject(0);
                    pedidoID = jsonRow.optInt("PedidoID");

                    if (pedidoID == 0) {
                        toastPersonalizado.mostrarMensaje(getContext(),"LA COMPRA FALLO",R.drawable.ic_toast_bad);
                    }
                    else{
                        toastPersonalizado.mostrarMensaje(getContext(),"COMPRA EXITOSA",R.drawable.ic_toast_very);
                        cargarWebServiceDetallePedido(pedidoID);
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                toastPersonalizado.mostrarMensaje(getContext(),"PROBLEMAS EN LA CONEXION",R.drawable.ic_toast_bad);
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parameters = new HashMap<>();
                parameters.put("ClienteID",GlobalVariables.getCodigoUsuario().toString());
                parameters.put("Total",GlobalVariables.getTotal().toString());
                parameters.put("FechaCreacion",fechaActual);
                return parameters;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void cargarWebServiceDetallePedido(int pedidoID){
        //<------Recuperamos los datos del arraylist y lo insertamos en un JSONarray para enviarlo al php
        JSONObject jsonObject;
        detallePedidoItems = new JSONArray();
        for (int i=0; i<GlobalVariables.getCarritoComprasArrayList().size();i++){
            jsonObject = new JSONObject();
            try {
                jsonObject.put("PedidoID",pedidoID);
                jsonObject.put("ProductoID",GlobalVariables.getCarritoComprasArrayList().get(i).getProductid());
                jsonObject.put("Cantidad",GlobalVariables.getCarritoComprasArrayList().get(i).getQuantity());
                detallePedidoItems.put(jsonObject);

            }catch (JSONException e){
                throw  new RuntimeException(e);
            }
        }
        //------>Fin :v
        String url = GlobalVariables.getUrl() + "wsJSONRegistrarDetallePedido.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listaCarritoCompras =  new ArrayList<>();
                GlobalVariables.setTotal(0.0);
                GlobalVariables.setCarritoComprasArrayList(listaCarritoCompras);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                toastPersonalizado.mostrarMensaje(getContext(),"PROBLEMAS EN LA CONEXION",R.drawable.ic_toast_bad);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parameters = new HashMap<>();
                parameters.put("DetallePedidoItems",detallePedidoItems.toString());
                return parameters;
            }
        };
        requestQueue.add(stringRequest);
    }

  /*  private void pantallPago(){
        final Dialog dialog;
        Button btnCancelarPago,btnComprarPago;
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.fragment_pago);

        btnComprarPago = (Button)dialog.findViewById(R.id.btnComprarPago);
        btnComprarPago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cargarWebServicePedido();
                dialog.dismiss();

            }
        });
        btnCancelarPago = (Button)dialog.findViewById(R.id.btnCancelarPago);
        btnCancelarPago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }*/

}
