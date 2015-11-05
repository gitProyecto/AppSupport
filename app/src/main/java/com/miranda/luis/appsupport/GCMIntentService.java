package com.miranda.luis.appsupport;

/**
 * Created by luis on 22/10/15.
 */
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {

    public GCMIntentService() {
        super("853205055727");
    }

    @Override
    protected void onError(Context context, String errorId) {
        Log.d("GCMTest", "REGISTRATION: Error -> " + errorId);
    }

    @Override
    protected void onMessage(Context context, Intent intent) {

        String msg      =    intent.getExtras().getString("mensaje");

        String datos[] = {intent.getExtras().getString("serie"),intent.getExtras().getString("tipo"),intent.getExtras().getString("mensaje"),
                          intent.getExtras().getString("datos"),intent.getExtras().getString("origen"),intent.getExtras().getString("destino"),
                          intent.getExtras().getString("prioridad"),intent.getExtras().getString("tamaño"),intent.getExtras().getString("fecha"),
                          intent.getExtras().getString("estatus")};

        try {
            mostrarNotificacion(context, msg);

        }catch (Exception ex){  Log.d("GCMTest", "Error: " + ex.getMessage());     }
    }

    @Override
    protected void onRegistered(Context context, String regId) {
        Log.d("GCMTest", "REGISTRATION: Registrado OK.");


    }

    @Override
    protected void onUnregistered(Context context, String regId) {
        Log.d("GCMTest", "REGISTRATION: Desregistrado OK.");
    }




    private void mostrarNotificacion(Context context, String message){

        //JSondb json = new JSondb();

        //Identificar tipo de alerta y obtener texto

        //String mensaje = json.jSonInt(message);

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 1, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Resources res = context.getResources();
        Notification.Builder builder = new Notification.Builder(context);

        builder.setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.icono_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.icono_launcher))
                .setTicker("Nueva Alerta")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle("Alerta # " + "..")
                .setContentText(message);



        long[] pattern = {500,500,500,500,500,500,500,500,500};
        builder.setVibrate(pattern);

        Uri sound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.tono);

        builder.setSound(sound);

        builder.setLights(Color.RED, 3000, 3000);


        Notification n = builder.build();



        //n.defaults |= Notification.DEFAULT_SOUND;
        nm.notify(1, n);

    }






}