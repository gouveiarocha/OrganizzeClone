package com.example.organizzeclone.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.organizzeclone.Helper.FirebaseUtils;
import com.example.organizzeclone.Helper.DateCustom;
import com.example.organizzeclone.Modelo.Movimentacao;
import com.example.organizzeclone.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DespesasActivity extends AppCompatActivity {

    private EditText editValor;
    private TextInputEditText editData, editCategoria, editDescricao;

    private Double despesaTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesas);

        //REF. Componentes
        editValor = findViewById(R.id.etxt_valor);
        editData = findViewById(R.id.etxt_data);
        editCategoria = findViewById(R.id.etxt_categoria);
        editDescricao = findViewById(R.id.etxt_descricao);

        editData.setText(DateCustom.getDataAtual());    //Setar data atual.
        recuperarDespesaTotal();                        //Método com Listener para Recuperar a despesa total do Usuário.

    }

    public void salvarDespesa(View view) {

        if (validarDadosDespesa()) {
            Toast.makeText(this, "Despesa Salva com Sucesso!", Toast.LENGTH_SHORT).show();

            Double valor = Double.parseDouble(editValor.getText().toString());
            String data = editData.getText().toString();

            Movimentacao movimentacao = new Movimentacao(valor, data, editCategoria.getText().toString(), editDescricao.getText().toString(), "D");

            //Atualiza a despesa.
            double despesaAtualizada = despesaTotal + valor;
            FirebaseUtils.refUsuarios().child(FirebaseUtils.getIdUsuario()).child("totDespesa").setValue(despesaAtualizada);

            movimentacao.salvar(data);

            finish();
        }

    }

    public Boolean validarDadosDespesa() {

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

    public void recuperarDespesaTotal() {

        FirebaseDatabase database = FirebaseUtils.getDatabase();
        final String idUsuario = FirebaseUtils.getIdUsuario();
        DatabaseReference usuarios = database.getReference("usuarios").child(idUsuario);

        usuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Recupera os dados em tempo real...

                String valorRec = dataSnapshot.child("totDespesa").getValue().toString();
                double d = Double.parseDouble(valorRec);

                despesaTotal = d;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
