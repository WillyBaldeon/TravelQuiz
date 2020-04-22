package sac.lobosistemas.travelquiz;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RespuestasActivity extends AppCompatActivity {

    ImageButton imageButton;

    Button btn1, btn2, btn3, btn4;
    JSONArray ja = null;

    SharedPreferences preferencias;
    String nombre;
    boolean detener;

    TextView txtInformacion, txtPregunta;
    int puntaje, pregunta;

    Thread splashTread;
    ProgressBar pbTiempo;

    public static Activity RespuestasActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respuestas);
        RespuestasActivity = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);

        imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogoSalir(RespuestasActivity.this, RespuestasActivity);
            }
        });

        txtInformacion = findViewById(R.id.txtInformacion);
        txtPregunta = findViewById(R.id.txtPregunta);

        pbTiempo = findViewById(R.id.pbTiempo);

        //------------------Preferencias------------------//
        preferencias = getSharedPreferences("Datos", Context.MODE_PRIVATE);

        nombre = preferencias.getString("Nombre","Puntaje"); //Cargamos los datos
        puntaje = preferencias.getInt("Puntaje",0);
        pregunta = preferencias.getInt("Pregunta",1);

        txtInformacion.setText(nombre+": "+puntaje);
        txtPregunta.setText(pregunta+"/8");

        cuentaRegresiva(); //Iniciamos el Contador
        new ConsultarDatos().execute("https://11coolest.es/consulta.php?id="+pregunta);
    }

    @Override
    public void onBackPressed() {
        new DialogoSalir(RespuestasActivity.this, RespuestasActivity);
    }

    public void responder(View v){
        Intent RespuestaActivity = new Intent(this, CorrectoActivity.class);
        startActivity(RespuestaActivity);
        detener=true;
        finish();
    }

    public void cuentaRegresiva(){

        detener = false;
        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time

                    pbTiempo.setMax(3500);
                    while (waited < 3500 && detener == false) {
                        pbTiempo.setProgress(waited);
                        sleep(1);
                        waited += 1;
                    }

                    if(detener == false){
                        Intent RespuestaActivity = new Intent(RespuestasActivity.this, IncorrectoActivity.class);
                        RespuestaActivity.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(RespuestaActivity);
                        RespuestasActivity.this.finish();
                    }

                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    RespuestasActivity.this.finish();
                }
            }
        };

        splashTread.start();
    }

    //----------------------Consultas-------------------//
    private class ConsultarDatos extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            List<String> idBus=new ArrayList<String>();

            try {
                ja = new JSONArray(result);

                btn1.setText(ja.getJSONObject(0).getString("texto_respuesta"));
                btn2.setText(ja.getJSONObject(1).getString("texto_respuesta"));
                btn3.setText(ja.getJSONObject(2).getString("texto_respuesta"));
                btn4.setText(ja.getJSONObject(3).getString("texto_respuesta"));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    private String downloadUrl(String myurl) throws IOException {
        Log.i("URL",""+myurl);
        myurl=myurl.replace(" ","%20");
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 5000;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("respuesta", "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }


    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

}
