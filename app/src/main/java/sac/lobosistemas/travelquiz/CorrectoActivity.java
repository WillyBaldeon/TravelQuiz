package sac.lobosistemas.travelquiz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CorrectoActivity extends AppCompatActivity {
    
    SharedPreferences preferencias;
    TextView txtPuntaje, txtAcumulado, lblNombre;
    Thread thread;
    int puntaje=0, acumulado, pregunta;

    public static Activity CorrectoActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correcto);
        CorrectoActivity = this;
        
        lblNombre = findViewById(R.id.lblNombre);
        txtPuntaje = findViewById(R.id.txtPuntaje);
        txtAcumulado = findViewById(R.id.txtAcumulado);

        //------------------Preferencias------------------//
        preferencias = getSharedPreferences("Datos", Context.MODE_PRIVATE);

        acumulado = preferencias.getInt("Puntaje",0);
        puntaje = 1000; //Obtenemos el puntaje de la respuesta

        lblNombre.setText(preferencias.getString("Nombre","Puntaje")+" :");
        txtAcumulado.setText(""+acumulado);
        txtPuntaje.setText("+ "+puntaje);

        SharedPreferences.Editor editor=preferencias.edit();
        editor.putInt("Puntaje", acumulado+puntaje);
        editor.commit();

        thread = new Thread(){
            @Override public void run() {
                try {
                    synchronized (this) {
                        wait(1000);

                        while(puntaje>=0){
                            wait(1);
                            final int finalPuntaje = puntaje;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                txtPuntaje.setText("+ "+finalPuntaje); //Actualizamos los Textos
                                txtAcumulado.setText(""+(acumulado-1)); //Reducimos uno para que el puntaje encaje
                                }
                            });
                            puntaje-=1;
                            acumulado+=1;
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
        };

        thread.start();
        
    }

    @Override
    public void onBackPressed() {
        new DialogoSalir(CorrectoActivity.this, CorrectoActivity);
    }

    public void siguiente(View v){

        pregunta = preferencias.getInt("Pregunta",1);

        SharedPreferences.Editor editor=preferencias.edit();
        editor.putInt("Pregunta", pregunta+1);
        editor.commit();

        if(pregunta == 8){
            Intent FinalActivity = new Intent(this, sac.lobosistemas.travelquiz.FinalActivity.class);
            startActivity(FinalActivity);
            finish();
        } else {
            Intent LeerActivity = new Intent(this, sac.lobosistemas.travelquiz.LeerActivity.class);
            startActivity(LeerActivity);
            finish();
        }

    }
}
