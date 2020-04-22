package sac.lobosistemas.travelquiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AccesoActivity extends AppCompatActivity {

    TextView txtNombre, txtAsiento, lblError;
    SharedPreferences preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceso);

        lblError = findViewById(R.id.lblError);
        txtNombre = findViewById(R.id.txtNombre);
        txtAsiento = findViewById(R.id.txtAsiento);
    }

    public void jugar(View v){

        if(txtNombre.getText().length()==0){

            lblError.setText("Para jugar debe ingresar su nombre.");
            ViewGroup.LayoutParams params = lblError.getLayoutParams();
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            lblError.setLayoutParams(params);

        } else if(txtAsiento.getText().length()==0) {

            lblError.setText("Ingrese su número de asiento para recibir su premio.");
            ViewGroup.LayoutParams params = lblError.getLayoutParams();
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            lblError.setLayoutParams(params);

        } else {
                //------------------Preferencias------------------//
                preferencias = getSharedPreferences("Datos", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor=preferencias.edit();
                editor.putString("Nombre", ""+txtNombre.getText().toString().trim());
                editor.putInt("Asiento", Integer.parseInt(txtAsiento.getText().toString()));
                editor.putInt("Pregunta", 1);
                editor.putInt("Puntaje", 0);
                editor.commit();

                Intent EsperaActivity = new Intent(this, sac.lobosistemas.travelquiz.EsperaActivity.class);
                startActivity(EsperaActivity);
                finish();
        }
    }
}
