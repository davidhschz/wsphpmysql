package com.example.wsphpmysql;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class SesionFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{
//Definir los objetos que se requieran para la conexión
    RequestQueue rq; // Permite crear un objeto para realizar una petición.
    JsonRequest jrq; // Permite recibir los datos en formato de JSON.
    EditText correo,clave;
    Button iniciar;
    TextView registrarseaqui;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_sesion,container,false);
        correo = vista.findViewById(R.id.etemail);
        clave = vista.findViewById(R.id.etpassword);
        iniciar = vista.findViewById(R.id.btniniciarsesion);
        registrarseaqui = vista.findViewById(R.id.tvregistrarseaqui);

        registrarseaqui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),regusuario.class));
            }
        });
        rq = Volley.newRequestQueue(getContext());
        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesion();
            }
        });
        return vista;
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_sesion, container, false);
    }

    private void iniciarSesion() {
        String url = "http://192.168.1.4/ServiciosWebAndroidPHP/buscarusuario.php?correo="+correo.getText().toString()+"&clave="+clave.getText().toString();
        jrq = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), "Usuario no encontrado", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        //Se utiliza la clase usuario para tomar los campos del arreglo datos del archivo php
        Usuario usua = new Usuario();
        //datos: arreglo que env�a los datos en formato JSON, en el archivo php
        JSONArray jsonArray = response.optJSONArray("usuario");
        JSONObject jsonObject = null;
        try {
            jsonObject = jsonArray.getJSONObject(0);//posici�n 0 del arreglo....
            usua.setUsr(jsonObject.optString("usr"));
            //usua.setClave(jsonObject.optString("clave"));
            usua.setNombre(jsonObject.optString("nombre"));
            usua.setCorreo(jsonObject.optString("correo"));

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        //Intent misesion = new Intent(getContext(),Main2Activity.class);
        //misesion.putExtra("musr",usr.getText().toString());
        //startActivity(misesion);
        Intent intlogueado = new Intent(getContext(),Logueado.class);
        intlogueado.putExtra(Logueado.nombre,usua.getNombre());
        intlogueado.putExtra(Logueado.correo,usua.getCorreo());
        startActivity(intlogueado);
    }
}