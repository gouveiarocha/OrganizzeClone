package com.example.organizzeclone.Activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.organizzeclone.Helper.FirebaseUtils;
import com.example.organizzeclone.Helper.MovimentosAdapter;
import com.example.organizzeclone.Model.Movimentacao;
import com.github.clans.fab.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.organizzeclone.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PrincipalActivity extends AppCompatActivity {

    private TextView txtSaudacao, txtSaldo;
    private MaterialCalendarView materialCalendarView;
    private RecyclerView recyclerView;

    private List<Movimentacao> listaMovimentacoes = new ArrayList<>();
    private Movimentacao movimentacao;
    private MovimentosAdapter adapter;

    private Double saldoResumo, receitaTotal, despesaTotal;
    private String mesAnoSelecionado;
    private String configMostrarAvisoExclusao;

    private ValueEventListener valueEventListenerUsuarios;          //será utilizado p/ parar o Listener quando não estiver sendo utilizado.
    private ValueEventListener valueEventListenerMovimentacoes;     //será utilizado p/ parar o Listener quando não estiver sendo utilizado.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Visão Geral");
        setSupportActionBar(toolbar);

        //REF. Componentes
        txtSaudacao = findViewById(R.id.txt_saudacao);
        txtSaldo = findViewById(R.id.txt_saldo);
        materialCalendarView = findViewById(R.id.calendar_view);
        recyclerView = findViewById(R.id.recycler_view_principal);

        iniciarCalendario();
        iniciarSwipe();

        //avisoExclusao();

        //Configura Adapter e Recyclerview
        adapter = new MovimentosAdapter(listaMovimentacoes, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperarResumoUsuario();
        carregarListaMovimentacoes();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Remove o Listener
        Log.i("onStop", "evento foi removido");
        FirebaseUtils.refUsuarios().removeEventListener(valueEventListenerUsuarios);
        FirebaseUtils.refUsuarios().removeEventListener(valueEventListenerMovimentacoes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_sair) {
            FirebaseUtils.deslogar();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void adicionarReceita(View view) {
        startActivity(new Intent(this, ReceitasActivity.class));
    }

    public void adicionarDespesa(View view) {
        startActivity(new Intent(this, DespesasActivity.class));
    }

    public void excluirMovimentacao(final RecyclerView.ViewHolder viewHolder) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Excluir Movimentação");
        alertDialog.setMessage("Atenção: Ao excluir a movimentação, o Saldo Geral será recalculado. Confirma exclusão?");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int position = viewHolder.getAdapterPosition();         //recupera a posição do item arrastado.
                movimentacao = listaMovimentacoes.get(position);        //recupera a movimentação de acordo com o position.

                FirebaseUtils.refMovimentacoes()
                        .child(FirebaseUtils.getIdUsuario())
                        .child(mesAnoSelecionado)
                        .child(movimentacao.getKey()).removeValue();    //recupera a key e remove os valores
                adapter.notifyItemRemoved(position);                    //notifica que um item foi removido. o adapter retira o item.
                atualizarSaldo();
            }
        });
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(PrincipalActivity.this, "Exclusão Cancelada!", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
            }
        });
        alertDialog.show();

    }

    public void atualizarSaldo(){
        if (movimentacao.getTipo().equals("R")){
            receitaTotal = receitaTotal - movimentacao.getValor();
            FirebaseUtils.refUsuarios().child(FirebaseUtils.getIdUsuario()).child("totReceita").setValue(receitaTotal);
        }else if(movimentacao.getTipo().equals("D")){
            despesaTotal = despesaTotal - movimentacao.getValor();
            FirebaseUtils.refUsuarios().child(FirebaseUtils.getIdUsuario()).child("totDespesa").setValue(despesaTotal);
        }
    }

    public void recuperarResumoUsuario() {
        valueEventListenerUsuarios = FirebaseUtils.refUsuarios()
                .child(FirebaseUtils.getIdUsuario())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String nome = dataSnapshot.child("nome").getValue().toString();
                        txtSaudacao.setText("Olá, " + nome + "!");

                        receitaTotal = Double.parseDouble(dataSnapshot.child("totReceita").getValue().toString());
                        despesaTotal = Double.parseDouble(dataSnapshot.child("totDespesa").getValue().toString());
                        saldoResumo = receitaTotal - despesaTotal;

                        DecimalFormat decimalFormat = new DecimalFormat("0.00");
                        String saldoFormatado = decimalFormat.format(saldoResumo);

                        txtSaldo.setText("R$ " + saldoFormatado);

                        verificarSaldoNegativo();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    public void carregarListaMovimentacoes() {
        valueEventListenerMovimentacoes = FirebaseUtils.refMovimentacoes()
                .child(FirebaseUtils.getIdUsuario())
                .child(mesAnoSelecionado)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        listaMovimentacoes.clear(); //limpa a lista.
                        for (DataSnapshot dados : dataSnapshot.getChildren()) {   //percorre a lista de nós e armazena cada nó filho e os values de dataSnapsho(MesAno) em dados.
                            Movimentacao movimentacao = dados.getValue(Movimentacao.class);
                            movimentacao.setKey(dados.getKey());    //seta a key para a movimentação.
                            listaMovimentacoes.add(movimentacao);
                        }
                        adapter.notifyDataSetChanged(); //notifica que os dados foram atualizados.
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    public void verificarSaldoNegativo() {
        if (saldoResumo < 0) {
            txtSaldo.setTextColor(getColor(R.color.accentDespesa));
            Toast.makeText(PrincipalActivity.this, "Atenção, Saldo Negativo!!!", Toast.LENGTH_SHORT).show();
        } else {
            txtSaldo.setTextColor(getColor(R.color.myWhite));
        }
    }

    public void iniciarCalendario() {

        //Configurações Calendário...
        CharSequence meses[] = {"JAN", "FEV", "MAR", "ABR", "MAI", "JUN", "JUL", "AGO", "SET", "OUT", "NOV", "DEZ"};
        materialCalendarView.setTitleMonths(meses);

        //Recuperando MesAno atual...
        CalendarDay dataAtual = materialCalendarView.getCurrentDate();
        String mesSelecionado = String.format("%02d", dataAtual.getMonth());
        mesAnoSelecionado = String.valueOf(mesSelecionado + "" + dataAtual.getYear());

        //Recuperar Mes/Ano ao trocar o Mês...
        materialCalendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                //Atualiza o MesAno atual...
                String mesSelecionado = String.format("%02d", date.getMonth());
                mesAnoSelecionado = String.valueOf(mesSelecionado + "" + date.getYear());

                FirebaseUtils.refMovimentacoes().removeEventListener(valueEventListenerMovimentacoes);
                carregarListaMovimentacoes();
            }
        });

    }

    public void avisoExclusao(){

        //em desenvolvimento

    }

    //Movimentos de deslizar...
    public void iniciarSwipe() {
        ItemTouchHelper.Callback itemTouch = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {

                int dragsFlags = ItemTouchHelper.ACTION_STATE_IDLE; //define que não pode arrastar para cima ou baixo
                int swipeFlags = ItemTouchHelper.END; //define os movimentos de iniciarSwipe (start para o inicio e end para o fim)
                return makeMovementFlags(dragsFlags, swipeFlags);

            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                if (direction == ItemTouchHelper.END) {
                    excluirMovimentacao(viewHolder);
                } else if (direction == ItemTouchHelper.START) {
                    Log.i("iniciarSwipe", "item foi arrastado para o inicio");
                }

            }
        };

        new ItemTouchHelper(itemTouch).attachToRecyclerView(recyclerView);

    }


    /**
     * Não utilizados...
     */

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

}
