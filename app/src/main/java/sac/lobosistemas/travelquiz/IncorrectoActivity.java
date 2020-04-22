package sac.lobosistemas.travelquiz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class IncorrectoActivity extends AppCompatActivity {
    
    SharedPreferences preferencias;
    TextView txtPuntaje, txtAcumulado, lblNombre;
    Thread thread;
    int acumulado, pregunta;

    public static Activity IncorrectoActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incorrecto);
        IncorrectoActivity = this;
        
        lblNombre = findViewById(R.id.lblNombre);
        txtPuntaje = findViewById(R.id.txtPuntaje);
        txtAcumulado = findViewById(R.id.txtAcumulado);

        //------------------Preferencias------------------//
        preferencias = getSharedPreferences("Datos", Context.MODE_PRIVATE);

        acumulado = preferencias.getInt("Puntaje",0);

        lblNombre.setText(preferencias.getString("Nombre","Puntaje")+" :");
        txtAcumulado.setText(""+acumulado);
        txtPuntaje.setText("+ 0");

        SharedPreferences.Editor editor=preferencias.edit();
        editor.putInt("Puntaje", acumulado);
        editor.commit();
        
    }

    @Override
    public void onBackPressed() {
        new DialogoSalir(IncorrectoActivity.this, IncorrectoActivity);
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
