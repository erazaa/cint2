package com.buscaloaltoq.buscaloaltoq.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.buscaloaltoq.buscaloaltoq.R;
import com.buscaloaltoq.buscaloaltoq.entities.Customers;
import com.buscaloaltoq.buscaloaltoq.others.GlobalVariables;
import com.buscaloaltoq.buscaloaltoq.others.ToastPersonalized;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements Validator.ValidationListener {


    @NotEmpty(message = "Debe Ingresar Email")
    @Email(message = "Email Incorreco")
    EditText txtEmailL;
    @NotEmpty(message = "Debe Ingresar Clave")
    EditText txtClaveL;
    Button btnIngresar,btnRegistrar;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    Validator validator;
    ToastPersonalized toastPersonalizado = new ToastPersonalized();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_login, container, false);
        txtEmailL= (EditText) vista.findViewById(R.id.txtEmailL);
        txtClaveL = (EditText) vista.findViewById(R.id.txtClaveL);
        btnIngresar = (Button) vista.findViewById(R.id.btnIngresar);
        btnRegistrar = (Button) vista.findViewById(R.id.btnRegistrar);
        requestQueue = Volley.newRequestQueue(getContext());
        //<------Aqui llamamos a la clase que validara nuestros campos
        validator = new Validator(this);
        validator.setValidationListener(this);
        //------>Fin :v

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                RegisterFragment fragmentRegistrar = new RegisterFragment();
                fragmentTransaction.replace(R.id.contenedor,fragmentRegistrar);
                fragmentTransaction.commit();
            }
        });
        return vista;
    }

    private void cargarWebService(){
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Cargando....");
        progressDialog.show();
        String url = GlobalVariables.getUrl() + "wsJSONConsultarLogin.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.hide();
                Customers miClientes = new Customers();

                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONArray jsonArray = jsonObject.optJSONArray("Clientes");
                    JSONObject jsonRow =jsonArray.getJSONObject(0);
                    miClientes.setCustomerid(jsonRow.optInt("ClienteID"));
                    miClientes.setName(jsonRow.optString("Nombres"));
                    miClientes.setLastname(jsonRow.optString("Apellidos"));
                    miClientes.setEmail(jsonRow.optString("Email"));

                    if (miClientes.getCustomerid().equals(0) ) {
                        toastPersonalizado.mostrarMensaje(getContext(),"DATOS INCORRECTOS",R.drawable.ic_toast_bad);
                    }
                    else{
                        GlobalVariables.setCookieUsuario(true);
                        GlobalVariables.setCodigoUsuario(miClientes.getCustomerid());
                        GlobalVariables.setNombresUsuario(miClientes.getName() + " " + miClientes.getLastname());
                        GlobalVariables.setCorreoUsuario(miClientes.getEmail());

                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        WelcomeFragment fragmentBienvenido= new WelcomeFragment();
                        fragmentTransaction.replace(R.id.contenedor,fragmentBienvenido);
                        fragmentTransaction.commit();
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
                Map<String,String> parameters = new HashMap<String, String>();
                parameters.put("Email",txtEmailL.getText().toString());
                parameters.put("Clave",txtClaveL.getText().toString());
                return parameters;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onValidationSucceeded() {
        cargarWebService();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getContext());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        }
    }

}
