package com.buscaloaltoq.buscaloaltoq.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.buscaloaltoq.buscaloaltoq.R;
import com.buscaloaltoq.buscaloaltoq.adapters.OrderDetailsAdapter;
import com.buscaloaltoq.buscaloaltoq.entities.OrderDetails;
import com.buscaloaltoq.buscaloaltoq.others.DialogPersonalized;
import com.buscaloaltoq.buscaloaltoq.others.GlobalVariables;
import com.buscaloaltoq.buscaloaltoq.others.ToastPersonalized;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderDetailsFragment extends Fragment {
    RecyclerView recyclerViewDetallePedido;
    ArrayList<OrderDetails> listaDetallePedidos;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String valorRecuperado;
    ToastPersonalized toastPersonalizado = new ToastPersonalized();
    private DialogPersonalized dialogPersonalizado= new DialogPersonalized();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewDetallePedido = inflater.inflate(R.layout.fragment_order_details, container, false);
        listaDetallePedidos= new ArrayList<>();
        recyclerViewDetallePedido = (RecyclerView) viewDetallePedido.findViewById(R.id.rvDetallePedido);
        recyclerViewDetallePedido.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerViewDetallePedido.setHasFixedSize(true);
        requestQueue = Volley.newRequestQueue(getContext());
        cargarWebService();
        return  viewDetallePedido;
    }

    private void cargarWebService() {
        //<------Aqui Recuperamos el Valor del Fragment enviado de otro Fragment
        Bundle bundle = getArguments();
        valorRecuperado = bundle.getString("PedidoID");
        //------>Fin :v
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Cargando....");
        progressDialog.show();
        String url= GlobalVariables.getUrl() + "wsJSONConsultarDetallePedido.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.hide();
                Double precio;
                OrderDetails detallePedidos= null;
                try{
                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONArray jsonArray = jsonObject.optJSONArray("DetallePedido");
                    for (int i=0;i<jsonArray.length();i++){
                        detallePedidos = new OrderDetails();
                        JSONObject jsonRow = null;
                        jsonRow = jsonArray.getJSONObject(i);

                        precio = jsonRow.optDouble("Precio");
                        detallePedidos.setQuantity(jsonRow.optInt("Cantidad"));
                        detallePedidos.setName(jsonRow.optString("Nombre"));
                        detallePedidos.setPhoto(jsonRow.optString("Foto"));
                        detallePedidos.setDescription(jsonRow.optString("Descripcion"));
                        DecimalFormat decimalFormat = new DecimalFormat();
                        decimalFormat.setMaximumFractionDigits(2);
                        detallePedidos.setSubtotal(Double.parseDouble(decimalFormat.format(precio*detallePedidos.getQuantity())));
                        listaDetallePedidos.add(detallePedidos);
                    }

                    OrderDetailsAdapter detallePedidoAdapter = new OrderDetailsAdapter(listaDetallePedidos,getContext());
                    detallePedidoAdapter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialogPersonalizado.mostrarDialog(getContext(),listaDetallePedidos.get(recyclerViewDetallePedido.getChildAdapterPosition(view)).getDescription().toString());
                        }
                    });
                    recyclerViewDetallePedido.setAdapter(detallePedidoAdapter);
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
                Map<String,String> parameters = new HashMap<String, String>();
                parameters.put("PedidoID",valorRecuperado);
                return parameters;
            }
        };
        requestQueue.add(stringRequest);
    }

}
