package com.ws.consulta;

import android.content.Context;
import android.content.ReceiverCallNotAllowedException;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by gaspar on 10/06/16.
 * Las funciones mas importantes en esta clase son  RequestPOST y RequestGET
 */
public class WSRequest {
    Context context;
    ListenerRequest listener;

    public WSRequest(Context ctx, ListenerRequest listener){
        this.context = ctx;
        this.listener = listener;

    }

    //funcion generica para hacer POST
    public void RequestPOST(String url, JSONObject json) {
        RequestQueue queue = Volley.newRequestQueue(context);
        final JSONObject jsonData = json;

        //creamos los Listeners para onResponse y ErroResponse
        Response.Listener<String> onResponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject responseJson = null;
                int code=10000;
                try {
                    responseJson = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                listener.onResponse(response);


            }
        };

        //en caso de que exista error
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse != null) {
                    listener.onError(error.getMessage());
                }
            }
        };


        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                onResponseListener,
                errorListener) {
            //agregamos datos para enviar
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = generarParametros(jsonData);
                return params;
            }
        };

        queue.add(stringRequest);
    }

    //funcion generica para hacer GET
    public void RequestGET(String url) {
        RequestQueue queue = Volley.newRequestQueue(context);

        //creamos los Listeners para onResponse y ErroResponse
        Response.Listener<String> onResponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject responseJson = null;
                int code=10000;
                try {
                    responseJson = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                listener.onResponse(response);


            }
        };

        //en caso de que exista error
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse != null) {
                    listener.onError(error.getMessage());
                }
            }
        };


        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                onResponseListener,
                errorListener) {

        };

        queue.add(stringRequest);
    }


    //Esta funcion nos devuelve un objeto HashMap a partir de un objeto JSON
    private HashMap<String,String> generarParametros(JSONObject json){
        HashMap<String, String> params= new HashMap<String,String>();
        Iterator<?> permisos = json.keys();
        try {
            while(permisos.hasNext()){
                String key = (String)permisos.next();
                params.put(key,json.getString(key));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return params;
    }

}
