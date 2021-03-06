package com.example.organizzeclone.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.organizzeclone.Helper.FirebaseUtils;
import com.example.organizzeclone.Helper.DateCustom;
import com.example.organizzeclone.Model.Movimentacao;
import com.example.organizzeclone.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class ReceitasActivity extends AppCompatActivity {

    private EditText editValor;
    private TextInputEditText editData, editCategoria, editDescricao;

    private Double receitaTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receitas);

        editValor = findViewById(R.id.etxt_valor);
        editData = findViewById(R.id.etxt_data);
        editCategoria = findViewById(R.id.etxt_categoria);
        editDescricao = findViewById(R.id.etxt_descricao);

        editValor.requestFocus();
        editData.setText(DateCustom.getDataAtual());    //Setar data atual.
        recuperarReceitaTotal();                        //Método com Listener para Recuperar a receita total do Usuário.

    }

    public void salvarReceita(View view) {

        if (validarDadosReceita()) {
            Toast.makeText(this, "Receita Salva com Sucesso!", Toast.LENGTH_SHORT).show();

            Double valor = Double.parseDouble(editValor.getText().toString());
            String data = editData.getText().toString();

            Movimentacao movimentacao = new Movimentacao(valor, data, editCategoria.getText().toString(), editDescricao.getText().toString(), "R");

            //Atualiza a receita.
            double receitaAtualizada = receitaTotal + valor;
            FirebaseUtils.refUsuarios().child(FirebaseUtils.getIdUsuario()).child("totReceita").setValue(receitaAtualizada);

            movimentacao.salvar(data);

            finish();
        }

    }

    public Boolean validarDadosReceita() {

        String valor = editValor.getText().toString();
        String data = editData.getText().toString();
        String categoria = editCategoria.getText().toString();
        String descricao = editDescricao.getText().toString();

        if (!valor.isEmpty()) {
            if (!data.isEmpty()) {
                if (!categoria.isEmpty()) {
                    if (!descricao.isEmpty()) {

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

    public void recuperarReceitaTotal() {

        FirebaseUtils.refUsuarios().child(FirebaseUtils.getIdUsuario()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Recupera os dados em tempo real...
                String valorRec = dataSnapshot.child("totReceita").getValue().toString();
                double d = Double.parseDouble(valorRec);
                receitaTotal = d;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
