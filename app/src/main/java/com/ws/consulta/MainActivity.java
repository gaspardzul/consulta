package com.ws.consulta;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * Este es un ejemplo en el cual podemos ver de que manera podemos usar funciones genericas
 * para hacer llamadas a WS desde android
 * Aquí tenemos 2 ejemplos, el primero es la consulta de info de un personaje
 * y la segunda es la consulta de los nombres de los paises a partir de un iso code 2 (ej. MX, PA, US, etc)
 *
 */
public class MainActivity extends AppCompatActivity implements ListenerRequest {
    EditText txtCode;
    TextView txtNameCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Consulta de Paises");
        setContentView(R.layout.activity_main);
        txtCode = (EditText) findViewById(R.id.txtCode);
        txtNameCountry = (TextView) findViewById(R.id.txtNameCountry);
    }

    /**
     * En esta función obtenemos el nombre completo de un país pasando como parámetro
     * dentro de la url el iso code 2
     * @param v
     */
    public void getCountries(View v){
        WSRequest ws = new WSRequest(this,this);
        String code = txtCode.getText().toString().trim();
        if(!code.isEmpty()){
            ws.RequestGET("http://services.groupkt.com/country/get/iso2code/"+code);
        }else {
            txtCode.setError("Se requiere un valor!");
        }

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
               String nameCountry= json.getJSONObject("RestResponse").getJSONObject("result").getString("name");
                txtNameCountry.setText(nameCountry);
                txtCode.setText("");
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this,"La consulta no devolvió resultados",Toast.LENGTH_LONG).show();
            txtNameCountry.setText("--");

        }

    }

    /**
    Esta función nos sirve para obtener la respuesta fallida de la petición
     **/
    @Override
    public void onError(String response) {
    }
}
