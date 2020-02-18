package com.example.organizzeclone.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.organizzeclone.Helper.DateCustom;
import com.example.organizzeclone.R;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class ReceitasActivity extends AppCompatActivity {

    private EditText valor;
    private TextInputEditText data, categoria, descricao;
    private FloatingActionButton salvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receitas);

        valor = findViewById(R.id.etxt_valor);
        data = findViewById(R.id.etxt_data);
        categoria = findViewById(R.id.etxt_categoria);
        descricao = findViewById(R.id.etxt_descricao);
        //salvar = findViewById(R.id.fab_salvar);

        data.setText(DateCustom.getDataAtual());

    }
}
