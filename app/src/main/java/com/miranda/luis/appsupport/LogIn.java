package com.miranda.luis.appsupport;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;

import java.io.IOException;
import java.util.Random;

public class LogIn extends AppCompatActivity implements View.OnClickListener {

    EditText user,pass;
    Button boton, boton1;
    String result = "";
    int segundos = 0;
    ProgressDialog progressDialog;
    boolean status = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        StrictMode.enableDefaults();

        user = (EditText) findViewById(R.id.editText);
        pass = (EditText) findViewById(R.id.editText2);

        boton = (Button) findViewById(R.id.Login);
        boton.setOnClickListener(this);

        boton1 = (Button) findViewById(R.id.button);
        boton1.setOnClickListener(this);


        GCMRegistrar.checkDevice(LogIn.this);
        GCMRegistrar.checkManifest(LogIn.this);

        GCMRegistrar.register(LogIn.this, "853205055727");

        boton1.setVisibility(View.GONE);

        if (!verificaConexion(this)) {
            new AlertDialog.Builder(LogIn.this).setTitle("Error").setMessage("Comprueba tu conexión a Internet!")
                    .setNeutralButton("Ok", null).show();
            this.finish();
        }

        SharedPreferences prefs = getSharedPreferences("LogIn", MODE_PRIVATE);

        Boolean registro = prefs.getBoolean("registro", false);
        if(registro == false){

            Toast.makeText(getApplicationContext(),"Favor de registrse",Toast.LENGTH_LONG).show();

        }else {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

        }




    }

    public static boolean verificaConexion(Context ctx) {
        boolean bConectado = false;
        ConnectivityManager connec = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] redes = connec.getAllNetworkInfo();
        for (int i = 0; i < 2; i++) {
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) { bConectado = true; }
        }
        return bConectado;
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.Login){

            post();

        }

        if(v.getId() == R.id.button){

            eliminarRegistro();
            Toast.makeText(this,"Registrado eliminado",Toast.LENGTH_LONG).show();
        }
    }





    public void post(){


        String regId = GCMRegistrar.getRegistrationId(LogIn.this);
        final String datos[]={user.getText().toString().trim(),pass.getText().toString().trim(),regId};
        final httpHandler handler = new httpHandler();
        progressDialog = ProgressDialog.show(LogIn.this, "", "Loading...");

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {


                    do {
                        segundos++;
                        Log.d("Luis",segundos+"");
                        result = handler.post("http://flores.rosales.engineer/appSupport/register.php", datos);



                        if(result.equals("Identificacion valida")){
                            progressDialog.dismiss();
                            Iniciar();   break;
                        }

                        if(result.equals("Identificacion no valida")){
                            result="Identificacion no valida";
                            progressDialog.dismiss(); break;
                        }else
                        if(segundos == 60) {
                            progressDialog.dismiss();
                            result="Servidor no disponible";
                            break;
                        }
                    } while (status == false);

                    runOnUiThread(new Runnable() {
                        public void run() {
                            segundos=0; status=false;
                            new AlertDialog.Builder(LogIn.this).setTitle("Error").setMessage(result).setNeutralButton("Ok", null).show();
                        }
                    });


                } catch (Exception e) {

                }

            }



        }).start();









    }


    public void eliminarRegistro(){

        final String regId = GCMRegistrar.getRegistrationId(LogIn.this);
        if (!regId.equals("")) {
            GCMRegistrar.unregister(LogIn.this);
        } else {
            Log.v("GCMTest", "Ya des-registrado");
        }

    }


    public void Iniciar(){

        SharedPreferences.Editor editor = getSharedPreferences("LogIn", MODE_PRIVATE).edit();
        editor.putBoolean("registro", true);
        editor.commit();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }



}
