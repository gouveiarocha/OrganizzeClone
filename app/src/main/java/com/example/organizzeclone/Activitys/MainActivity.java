package com.example.organizzeclone.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.organizzeclone.Activitys.CadastroActivity;
import com.example.organizzeclone.Activitys.LoginActivity;
import com.example.organizzeclone.Config.ConfigFirebase;
import com.example.organizzeclone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;

/**
 * 1- Extender IntroActivty
 * 2- Retirar o setContentView
 * 3- Iniciar os Slides
 * 4- Verificar usuario logado e se sim, mandar para a PrincipalActivity
 */

public class MainActivity extends IntroActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startActivity(new Intent(this, PrincipalActivity.class));

        setFullscreen(true);
        setButtonBackVisible(false);
        setButtonNextVisible(false);

        iniciarSlides();

    }

    @Override
    protected void onStart() {
        super.onStart();
        verificarUsuarioLogado();
    }

    public void verificarUsuarioLogado(){

        auth = ConfigFirebase.getAuth();
        if (auth.getCurrentUser() != null){
            startActivity(new Intent(MainActivity.this, PrincipalActivity.class));
        }

    }

    public void iniciarSlides() {

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro1)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro2)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro3)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro4)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_cadastro)
                .canGoForward(false)
                .build());

    }

    public void cadastrar(View view){
        startActivity(new Intent(this, CadastroActivity.class));
    }

    public void logar(View view){
        startActivity(new Intent(this, LoginActivity.class));
    }

}
