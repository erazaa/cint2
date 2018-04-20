package com.buscaloaltoq.buscaloaltoq.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.buscaloaltoq.buscaloaltoq.adapters.CategoryAdapter;
import com.buscaloaltoq.buscaloaltoq.entities.Categories;
import com.buscaloaltoq.buscaloaltoq.others.GlobalVariables;
import com.buscaloaltoq.buscaloaltoq.others.ToastPersonalized;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {

    RecyclerView recyclerViewCategoria;
    ArrayList<Categories> listaCategorias;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    ToastPersonalized toastPersonalizado = new ToastPersonalized();

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View viewCategoria = inflater.inflate(R.layout.fragment_category,container,false);
        listaCategorias= new ArrayList<>();
        recyclerViewCategoria = (RecyclerView) viewCategoria.findViewById(R.id.rvCategoria);
        recyclerViewCategoria.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerViewCategoria.setHasFixedSize(true);
        requestQueue = Volley.newRequestQueue(getContext());
        cargarWebService();

        return  viewCategoria;
    }

    private void cargarWebService() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Cargando....");
        progressDialog.show();
        String url= GlobalVariables.getUrl() + "wsJSONConsultarCategoria.php";
        StringRequest stringRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.hide();
                Categories categorias = null;

                try{
                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONArray jsonArray = jsonObject.optJSONArray("Categorias");

                    for (int i=0;i<jsonArray.length();i++){
                        categorias = new Categories();
                        JSONObject jsonRow = null;
                        jsonRow = jsonArray.getJSONObject(i);
                        categorias.setCategoryid(jsonRow.optInt("CategoriaID"));
                        categorias.setName(jsonRow.optString("Nombre"));
                        listaCategorias.add(categorias);
                    }
                    if (categorias.getCategoryid().equals(0)){
                        toastPersonalizado.mostrarMensaje(getContext(),"NO HAY CATEGORIAS",R.drawable.ic_toast_bad);
                    }else {
                        CategoryAdapter categoriaAdapter = new CategoryAdapter(listaCategorias);
                        categoriaAdapter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //Redireccionamos a otro Fragment
                                FragmentManager fragmentManager = getFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                ProductFragment fragmentProducto = new ProductFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("CategoriaID", listaCategorias.get(recyclerViewCategoria.getChildAdapterPosition(view)).getCategoryid().toString());
                                fragmentProducto.setArguments(bundle);
                                fragmentTransaction.replace(R.id.contenedor, fragmentProducto);
                                fragmentTransaction.commit();
                            }
                        });
                        recyclerViewCategoria.setAdapter(categoriaAdapter);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                toastPersonalizado.mostrarMensaje(getContext(),"PROBLEMAS EN LA CONEXION",R.drawable.ic_toast_bad);
                progressDialog.hide();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        requestQueue.add(stringRequest);
    }

}
