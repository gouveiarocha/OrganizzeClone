package com.example.organizzeclone.Activitys;

import android.content.Intent;
import android.os.Bundle;

import com.example.organizzeclone.Config.ConfigFirebase;
import com.example.organizzeclone.Helper.Base64Custom;
import com.example.organizzeclone.Modelo.Usuario;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.organizzeclone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

public class PrincipalActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    private TextView txtSaudacao, txtSaldo;
    private MaterialCalendarView materialCalendarView;
    private RecyclerView recyclerView;

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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_sair){
            deslogar();
        }
        return super.onOptionsItemSelected(item);
    }

    public void adicionarReceita(View view){
        startActivity(new Intent(this,ReceitasActivity.class));
    }

    public void adicionarDespesa(View view){
        startActivity(new Intent(this,DespesasActivity.class));
    }

    public void iniciarCalendario(){

        //Configurações Calendário...
        CharSequence meses[] = {"JAN", "FEV", "MAR", "ABR", "MAI", "JUN", "JUL", "AGO", "SET", "OUT", "NOV", "DEZ"};
        materialCalendarView.setTitleMonths(meses);

        //Recuperar Mes/Ano ao trocar o Mês...
        materialCalendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                Toast.makeText(PrincipalActivity.this, date.getMonth() + "/" + date.getYear(), Toast.LENGTH_SHORT).show(); //mes+ano
            }
        });

    }

    //Método para Deslogar Usuario.
    public void deslogar(){
        auth = ConfigFirebase.getAuth();
        auth.signOut();
        finish();
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
