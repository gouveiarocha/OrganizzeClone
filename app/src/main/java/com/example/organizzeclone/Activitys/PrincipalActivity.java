package com.example.organizzeclone.Activitys;

import android.content.Intent;
import android.os.Bundle;

import com.example.organizzeclone.Config.ConfigFirebase;
import com.example.organizzeclone.Helper.Base64Custom;
import com.example.organizzeclone.Modelo.Usuario;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Toast;

import com.example.organizzeclone.R;
import com.google.firebase.auth.FirebaseAuth;

public class PrincipalActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void adicionarReceita(View view){
        startActivity(new Intent(this,ReceitasActivity.class));
    }

    public void adicionarDespesa(View view){
        startActivity(new Intent(this,DespesasActivity.class));
    }

    //Exemplo de ação dos menus usando o View.OnclickListener. Mas para um código mais limpo, usaremos o onClick para as ações dos botões.
    public void acaoMenus() {

        FloatingActionButton menuReceita = findViewById(R.id.menu_receita);
        menuReceita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PrincipalActivity.this, "Menu1 Pressionado...", Toast.LENGTH_SHORT).show();
            }
        });

        FloatingActionButton menuDespesa = findViewById(R.id.menu_despesa);
        menuDespesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PrincipalActivity.this, "Menu2 Pressionado...", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void deslogar(View v){

        auth = ConfigFirebase.getAuth();
        auth.signOut();
        finish();

    }
}
