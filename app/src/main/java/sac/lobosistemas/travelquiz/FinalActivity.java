package sac.lobosistemas.travelquiz;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class FinalActivity extends AppCompatActivity {

    SharedPreferences preferencias;

    String nombre;
    int puntaje;

    TextView txtMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        txtMensaje = findViewById(R.id.txtMensaje);

        //------------------Preferencias------------------//
        preferencias = getSharedPreferences("Datos", Context.MODE_PRIVATE);

        nombre = preferencias.getString("Nombre","Bien");
        puntaje = preferencias.getInt("Puntaje",0);

        txtMensaje.setText(nombre+",\n ¡OBTUVISTE "+puntaje+ " PUNTOS!");
    }
}
