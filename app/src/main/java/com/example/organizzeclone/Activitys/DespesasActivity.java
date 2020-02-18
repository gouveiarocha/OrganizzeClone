package com.example.organizzeclone.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.organizzeclone.Helper.DateCustom;
import com.example.organizzeclone.Modelo.Movimentacao;
import com.example.organizzeclone.R;
import com.google.android.material.textfield.TextInputEditText;

public class DespesasActivity extends AppCompatActivity {

    private EditText valor;
    private TextInputEditText data, categoria, descricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesas);

        valor = findViewById(R.id.etxt_valor);
        data = findViewById(R.id.etxt_data);
        categoria = findViewById(R.id.etxt_categoria);
        descricao = findViewById(R.id.etxt_descricao);

        data.setText(DateCustom.getDataAtual());

    }

    public void salvarDespesa(View view) {

        if (validarDados()) {
            double valorRec = Double.parseDouble(valor.getText().toString());
            String dataRec = data.getText().toString();
            Movimentacao movimentacao = new Movimentacao(valorRec, dataRec, categoria.getText().toString(), descricao.getText().toString(), "D");
            movimentacao.salvar(dataRec);
            finish();
        }

    }

    public Boolean validarDados() {

        String txtValor = valor.getText().toString();
        String txtData = valor.getText().toString();
        String txtCategoria = valor.getText().toString();
        String txtDescricao = valor.getText().toString();

        if (!txtValor.isEmpty()) {
            if (!txtData.isEmpty()) {
                if (!txtCategoria.isEmpty()) {
                    if (!txtDescricao.isEmpty()) {

                        return true;

                    } else {
                        Toast.makeText(this, "Atenção! Descrição não Preenchida!", Toast.LENGTH_LONG).show();
                        return false;
                    }
                } else {
                    Toast.makeText(this, "Atenção! Categoria não Preenchida!", Toast.LENGTH_LONG).show();
                    return false;
                }
            } else {
                Toast.makeText(this, "Atenção! Data não Preenchida!", Toast.LENGTH_LONG).show();
                return false;
            }
        } else {
            Toast.makeText(this, "Atenção! Valor não Preenchido!", Toast.LENGTH_LONG).show();
            return false;
        }

    }

}
