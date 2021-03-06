package com.example.organizzeclone.Helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtils {

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
    public static void deslogar(){
        auth = FirebaseUtils.getAuth();
        auth.signOut();
    }

    /**
     * Ret. Referencias...
     */

    //Retorna a referencia para o nó Usuarios.
    public static DatabaseReference refUsuarios(){
        database = FirebaseUtils.getDatabase();
        return database.getReference("usuarios");
    }

    //Retorna a referencia para o nó Movimentacoes.
    public static DatabaseReference refMovimentacoes(){
        database = FirebaseUtils.getDatabase();
        return database.getReference("movimentacoes");
    }

    //Retorna a referencia para o nó Configs.
    public static DatabaseReference refConfigs(){
        database = FirebaseUtils.getDatabase();
        return database.getReference("configs");
    }

    /**
     * Ret. Dados...
     */

    //Retorna (Email/ID) do Usuario Logado
    public static String getIdUsuario(){
        auth = FirebaseUtils.getAuth();
        return Base64Custom.codificarBase64(auth.getCurrentUser().getEmail());
    }

}
