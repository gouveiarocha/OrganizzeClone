package com.example.organizzeclone.Config;

import com.example.organizzeclone.Helper.Base64Custom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfigFirebase {

    private static FirebaseAuth auth;
    private static FirebaseDatabase database;

    /**
     * Ret. Instancias...
     */

    //Retorna a instancia do FirebaseAuth
    public static FirebaseAuth getAuth(){
        if (auth == null){
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }

    //Retorna a instancia do FirebaseDatabase
    public static FirebaseDatabase getDatabase(){
        if (database == null){
            database = FirebaseDatabase.getInstance();
        }
        return database;
    }

    //Deslogar usuario
    public static Boolean deslogar(){
        auth = ConfigFirebase.getAuth();
        auth.signOut();
        return true;
    }

    /**
     * Ret. Referencias...
     */

    //Retorna a referencia para o nó Usuarios.
    public static DatabaseReference refUsuarios(){
        database = ConfigFirebase.getDatabase();
        return database.getReference("usuarios");
    }

    //Retorna a referencia para o nó Movimentacoes.
    public static DatabaseReference refMovimentacoes(){
        database = ConfigFirebase.getDatabase();
        return database.getReference("movimentacoes");
    }

    /**
     * Ret. Dados...
     */

    //Retorna (Email/ID) do Usuario Logado
    public static String getIdUsuario(){
        auth = ConfigFirebase.getAuth();
        return Base64Custom.codificarBase64(auth.getCurrentUser().getEmail());
    }

}
