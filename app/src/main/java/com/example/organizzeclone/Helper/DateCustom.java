package com.example.organizzeclone.Helper;

import java.text.SimpleDateFormat;

public class DateCustom {

    public static String getDataAtual() {
        long dataAtual = System.currentTimeMillis(); //recupera a data atual
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy"); //criar uma formatação simples.
        String data = simpleDateFormat.format(dataAtual);
        return data;
    }

    public static String getDataMesAno(String data) {

        //Quebra a data, neste caso usando o caracter / e atribui em um array.
        String res[] = data.split("/");

        //Valores atribuidos pelo split
        String dia = res[0];
        String mes = res[1];
        String ano = res[2];

        //Criar o resultado desejado.
        String mesAno = mes + ano;

        return mesAno;

    }

}
