package com.example.contentcalllog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.text.format.DateFormat;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ContentValues valores = new ContentValues();
        valores.put(CallLog.Calls.DATE, new Date().getTime() );
        valores.put(CallLog.Calls.NUMBER, "555555555");
        valores.put(CallLog.Calls.DURATION, "55");
        valores.put(CallLog.Calls.TYPE, CallLog.Calls.INCOMING_TYPE);


        String[] TIPO_LLAMADA = {"", "entrante", "saliente", "perdida"};
        TextView salida = (TextView) findViewById(R.id.tvsalida);
        Uri llamadas = Uri.parse("content://call_log/calls");
        Uri nuevoElemento = getContentResolver().insert(CallLog.Calls.CONTENT_URI, valores);
//
//Reemplazando
        String[] proyeccion = new String[] {
                CallLog.Calls.DATE, CallLog.Calls.DURATION, CallLog.Calls.NUMBER, CallLog.Calls.TYPE };
        String[] argsSelecc = new String[] {"1"};
        Cursor c = managedQuery(
                llamadas, // Uri del ContentProvider
                proyeccion, // Columnas que nos interesan
                "type = ?", // consulta WHERE
                argsSelecc, // parámetros de la consulta anterior
                "date DESC"); // Ordenado por fecha, orden ascenciente
        while (c.moveToNext()) {
            salida.append("\n"
                    + DateFormat.format("dd/MM/yy k:mm (",
                    c.getLong(c.getColumnIndex(CallLog.Calls.DATE)))
                    + c.getString(c.getColumnIndex(CallLog.Calls.DURATION)) + ") "
                    + c.getString(c.getColumnIndex(CallLog.Calls.NUMBER)) + ", "
                    + TIPO_LLAMADA[Integer.parseInt(c.getString(c
                    .getColumnIndex(CallLog.Calls.TYPE)))]);
        }
        TextView salidaContactos = (TextView) findViewById(R.id.tvsalida);
    }
}
