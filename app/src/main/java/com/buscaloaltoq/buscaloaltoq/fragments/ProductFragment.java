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
import com.buscaloaltoq.buscaloaltoq.adapters.ProductAdapter;
import com.buscaloaltoq.buscaloaltoq.entities.Products;
import com.buscaloaltoq.buscaloaltoq.others.GlobalVariables;
import com.buscaloaltoq.buscaloaltoq.others.ToastPersonalized;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment {

    RecyclerView recyclerViewProducto;
    ArrayList<Products> listaProductos;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String valorRecuperado;
    ToastPersonalized toastPersonalizado = new ToastPersonalized();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewProducto = inflater.inflate(R.layout.fragment_product,container,false);
        listaProductos = new ArrayList<>();

        recyclerViewProducto = (RecyclerView) viewProducto.findViewById(R.id.rvProducto);
        recyclerViewProducto.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerViewProducto.setHasFixedSize(true);
        requestQueue = Volley.newRequestQueue(getContext());
        cargarWebService();
        return viewProducto;
    }

    private void cargarWebService() {
        //<------Aqui Recuperamos el Valor del Fragment enviado de otro Fragment
        Bundle bundle = getArguments();
        valorRecuperado = bundle.getString("CategoriaID");
        //------>Fin :v
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Cargando....");
        progressDialog.show();
        String url= GlobalVariables.getUrl() + "wsJSONConsultarProducto.php";
        StringRequest stringRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.hide();
                Products productos= null;

                try{
                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONArray jsonArray = jsonObject.optJSONArray("Productos");
                    for (int i=0;i<jsonArray.length();i++){
                        productos= new Products();
                        JSONObject jsonRow = null;
                        jsonRow = jsonArray.getJSONObject(i);
                        productos.setProductid(jsonRow.optInt("ProductoID"));
                        productos.setName(jsonRow.optString("Nombre"));
                        productos.setPhoto(jsonRow.optString("Foto"));
                        productos.setDescription(jsonRow.optString("Descripcion"));
                        productos.setPrice(jsonRow.optDouble("Precio"));
                        listaProductos.add(productos);
                    }

                    ProductAdapter productoAdapter = new ProductAdapter(listaProductos, getContext());
                    recyclerViewProducto.setAdapter(productoAdapter);

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
                parameters.put("CategoriaID",valorRecuperado);
                return parameters;
            }
        };
        requestQueue.add(stringRequest);
    }

}
