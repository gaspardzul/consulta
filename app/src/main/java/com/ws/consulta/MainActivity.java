package com.ws.consulta;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Este es un ejemplo en el cual podemos ver de que manera podemos usar funciones genericas
 * para hacer llamadas a WS desde android
 * Aquí tenemos 2 ejemplos, el primero es la consulta de info de un personaje
 * y la segunda es la consulta de los nombres de los paises a partir de un iso code 2 (ej. MX, PA, US, etc)
 *
 */
public class MainActivity extends AppCompatActivity implements ListenerRequest {
    EditText txtCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtCode = (EditText) findViewById(R.id.txtCode);
    }

    /**
     * En esta funcion nosotros obtenemos la información personal de un personaje
     * en la liga http://api.androidhive.info/volley/person_object.json
     * @param v
     */
    public void getInfoFromWS(View v){
        WSRequest ws = new WSRequest(this,this);
        JSONObject json =new  JSONObject();
        ws.RequestGET("http://api.androidhive.info/volley/person_object.json");
    }

    /**
     * En esta función obtenemos el nombre completo de un país pasando como parámetro
     * dentro de la url el iso code 2
     * @param v
     */
    public void getCountries(View v){
        WSRequest ws = new WSRequest(this,this);
        String code = txtCode.getText().toString().trim();
        ws.RequestGET("http://services.groupkt.com/country/get/iso2code/"+code);
    }


    /**
        Esta función nos permite obtener la respuesta finalizada de la peticion
     **/
    @Override
    public void onResponse(String response) {
        Log.d("finalizado",response);
        try {
            //en esta línea parseamos el String en formato json  un Objeto JSONOBJECT de android
            // para manipular mas facilmente la respuesta
            JSONObject json = new JSONObject(response);

            // la función connectionFinish
            if(json.has("name")){
                Toast.makeText(this,json.getString("name") +" - "+ json.getString("email"),Toast.LENGTH_LONG).show();
            }else{
                //
               String nameCountry= json.getJSONObject("RestResponse").getJSONObject("result").getString("name");
               Toast.makeText(this,nameCountry,Toast.LENGTH_LONG).show();
                txtCode.setText(nameCountry);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this,"La consulta no devolvió resultados",Toast.LENGTH_LONG).show();

        }

    }

    /**
    Esta función nos sirve para obtener la respuesta fallida de la petición
     **/
    @Override
    public void onError(String response) {
        Log.d("error",response);
    }
}
