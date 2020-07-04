package com.example.organizzeclone.Helper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.organizzeclone.Model.Movimentacao;
import com.example.organizzeclone.R;

import java.util.List;

import android.content.Context;

public class MovimentosAdapter extends RecyclerView.Adapter<MovimentosAdapter.MyViwerHolder> {

    private List<Movimentacao> listaMovimentacoes;
    Context context;

    public MovimentosAdapter(List<Movimentacao> movimentacoes, Context context) {
        this.listaMovimentacoes = movimentacoes;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViwerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_movimentacoes2, parent, false);
        return new MyViwerHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViwerHolder holder, int position) {
        Movimentacao movimentacao = listaMovimentacoes.get(position);

        holder.categoria.setText(movimentacao.getCategoria());
        holder.descricao.setText(movimentacao.getDescricao());
        holder.valor.setText(String.valueOf(movimentacao.getValor()));

        holder.valor.setTextColor(context.getResources().getColor(R.color.accentReceita));

        if (movimentacao.getTipo() == "D" || movimentacao.getTipo().equals("D")) {
            holder.valor.setTextColor(context.getResources().getColor(R.color.accentDespesa));
            holder.valor.setText("-" + movimentacao.getValor());
        }

    }

    @Override
    public int getItemCount() {
        return this.listaMovimentacoes.size();
    }

    public class MyViwerHolder extends RecyclerView.ViewHolder {

        TextView descricao, categoria, valor;

        public MyViwerHolder(@NonNull View itemView) {
            super(itemView);
            descricao = itemView.findViewById(R.id.txt_descricao);
            categoria = itemView.findViewById(R.id.txt_categoria);
            valor = itemView.findViewById(R.id.txt_valor);
        }
    }
}
