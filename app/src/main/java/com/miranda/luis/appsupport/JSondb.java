package com.miranda.luis.appsupport;

import android.content.ContentValues;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by luis on 28/10/15.
 */
public class JSondb {

    //Titulos de alertas
    String iFilter = "iFilter";
    String Ticket  = "Ticket";

    private Database db;


    public JSondb(){

    }


    public String jSonInt(String datosInt){

        String result="";

        try {

            JSONObject obj = new JSONObject(datosInt);
            JSONObject mensaje = obj.getJSONObject("Mensaje");

            String serie = mensaje.getString("serie");
            String orden = mensaje.getString("orden");

            JSONArray datos = mensaje.getJSONArray("datos");

            for (int i = 0; i < datos.length(); i++) {
            JSONObject obji = datos.getJSONObject(i);

                result+=obji.toString();

            }

            String origen = mensaje.getString("origen");
            String destino = mensaje.getString("destino");
            String prioridad = mensaje.getString("prioridad");
            String tamaño = mensaje.getString("tamano");
            String fecha = mensaje.getString("fecha");
            String estatus = mensaje.getString("estatus");



        } catch (JSONException e) {
            e.printStackTrace();
            result=e.getMessage();

        }
        return result;


    }


    public String jSonOut(){


        JSONObject json = new JSONObject();
        try {
            JSONObject json1 = new JSONObject();
            json1.put("name", "emil");
            json1.put("username", "emil111");
            json1.put("age", "111");
            JSONObject json2 = new JSONObject();
            json2.put("name", "emil");
            json2.put("username", "emil111");
            json2.put("age", "111");

            JSONArray jsonArray = new JSONArray();
            jsonArray.put(json1);
            jsonArray.put(json2);


            json.put("Datos", jsonArray);


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return json.toString();
    }


    public void selectDB(){


    }

    public void insertDB(){



        if(db != null){
            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put("codigo", "6");
            nuevoRegistro.put("nombre","usuariopru");

//Insertamos el registro en la base de datos
            db.insert("Usuarios", null, nuevoRegistro);
        }

    }

    public void updateDB(){


    }

    public void deleteDB(){


    }



}
