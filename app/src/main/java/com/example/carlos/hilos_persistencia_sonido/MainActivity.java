package com.example.carlos.hilos_persistencia_sonido;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ayuda (View vista){

        Intent intencion=new Intent(this,AyudaActividad.class);
        startActivity(intencion);
    }


    public void dificultad(View vista){

        String dific=(String)((Button) vista).getText();

        int dificultad =1;
        if(dific.equals(getString(R.string.medio))) dificultad =2;
        if(dific.equals(getString(R.string.dificil))) dificultad =3;

        Intent in=new Intent(this,Gestion.class);

        in.putExtra("DIFICULTAD",dificultad);

        //startActivity(in);

        startActivityForResult(in,1);

    }

    protected void onActivityResult(int peticion, int codigo, Intent puntuacion) {
        if(peticion!=1 || codigo!=RESULT_OK) return;

        int resultado=puntuacion.getIntExtra("PUNTUACION",0);

        if(resultado>record){

            record=resultado;

            TextView caja = (TextView)findViewById(R.id.record);

            caja.setText("Record: " +record);

            guardaRecord();

        }else{



            String puntuacionPartida="";

            if (resultado==1) puntuacionPartida=""+resultado + " toque";

            if (resultado==0 || resultado >1) puntuacionPartida=""+resultado + " toques";

            Toast miToast=Toast.makeText(this,puntuacionPartida,Toast.LENGTH_SHORT);

            miToast.setGravity(Gravity.CENTER,0,0);

            miToast.show();

        }

    }

    public void onResume (){

        super.onResume();

        leeRecord();

    }

    private void guardaRecord(){

        SharedPreferences datos=PreferenceManager.getDefaultSharedPreferences (this);

        SharedPreferences.Editor miEditor = datos.edit();

        miEditor.putInt("RECORD", record);

        miEditor.apply();

    }

    private void leeRecord(){

        SharedPreferences datos= PreferenceManager.getDefaultSharedPreferences(this);

        record=datos.getInt("RECORD",0);

        TextView caja=(TextView)findViewById(R.id.record);

        caja.setText("Record:" +record);

    }



}
