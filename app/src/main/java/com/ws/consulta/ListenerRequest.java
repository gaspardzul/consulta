package com.ws.consulta;

/**
 * Created by gaspar on 10/06/16.
 */
public interface ListenerRequest {

    /**
     * termina la peticion de connection
     * @param response es el data que devuelve el servidor
     */
    public void onResponse(String response);

    /**
     * falla la coneccion de connection
     * @param response es el data que devuelve el servidor
     */
    public void onError(String response);
}
