package sac.lobosistemas.travelquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class LeerActivity extends AppCompatActivity {

    ProgressBar pbTiempo;

    Thread splashTread;

    public static Activity LeerActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leer);
        LeerActivity = this;

        pbTiempo = findViewById(R.id.pbTiempo);
        verPreguntas();
    }

    @Override
    public void onBackPressed() {
        new DialogoSalir(LeerActivity.this, LeerActivity);
    }

    public void verPreguntas(){
        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time

                    pbTiempo.setMax(1500);
                    while (waited < 1500) {
                        pbTiempo.setProgress(waited);
                        sleep(1);
                        waited += 1;
                    }

                    Intent RespuestasActivity = new Intent(LeerActivity.this, sac.lobosistemas.travelquiz.RespuestasActivity.class);
                    RespuestasActivity.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(RespuestasActivity);
                    finish();

                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    LeerActivity.this.finish();
                }
            }
        };

        splashTread.start();
    }
}
