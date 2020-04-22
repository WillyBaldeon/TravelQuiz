package sac.lobosistemas.travelquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class EsperaActivity extends AppCompatActivity {

    ImageView imagen;

    Float ini=0f, fin=360f;

    private ObjectAnimator animatorRotation;
    public static Activity EsperaActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espera);
        EsperaActivity = this;

        imagen = findViewById(R.id.imagen);
        iniciar();
    }
    @Override
    public void onBackPressed() {
        new DialogoSalir(EsperaActivity.this, EsperaActivity);
    }

    public void iniciar(){
        animatorRotation = ObjectAnimator.ofFloat(imagen, "rotation",ini,fin);
        animatorRotation.setDuration(1000);
        AnimatorSet animatorSetRotation = new AnimatorSet();
        animatorSetRotation.play(animatorRotation);
        animatorSetRotation.start();

        if(ini==0f){
            ini=360f;
            fin=0f;
        } else {
            ini=0f;
            fin=360f;
        }
    }

    public void animar(View v){
        iniciar();
    }

    public void cargar(View v){
        Intent LeerActivity = new Intent(this, sac.lobosistemas.travelquiz.LeerActivity.class);
        startActivity(LeerActivity);
        finish();
    }
}
