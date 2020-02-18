package com.example.organizzeclone.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.organizzeclone.Config.ConfigFirebase;
import com.example.organizzeclone.Modelo.Usuario;
import com.example.organizzeclone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class LoginActivity extends AppCompatActivity {

    private EditText editEmail, editSenha;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail = findViewById(R.id.etxt_email);
        editSenha = findViewById(R.id.etxt_senha);

    }

    //Método de ação para o botão Logar...
    public void btnLogar(View view) {

        String email = editEmail.getText().toString();
        String senha = editSenha.getText().toString();

        //Validar campos
        if (!email.isEmpty()) {
            if (!senha.isEmpty()) {

                Usuario usuario = new Usuario(email, senha);  //Cria um obj Usuario...
                logarUsuario(usuario);

            } else {
                Toast.makeText(LoginActivity.this, getString(R.string.erro_validacao_senha), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(LoginActivity.this, getString(R.string.erro_validacao_email), Toast.LENGTH_SHORT).show();
        }

    }

    //Método para Logar o Usuário...
    public void logarUsuario(Usuario usuario) {

        auth = ConfigFirebase.getAuth();
        auth.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            startActivity(new Intent(LoginActivity.this, PrincipalActivity.class));
                            finish();

                        } else {

                            String excecao = "";

                            //Recuperar Exception
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidUserException e) {
                                excecao = "Usuário não Cadastrado!";
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                excecao = "E-mail e/ou senha Incorretos!";
                            } catch (Exception e) {
                                excecao = "Erro ao Logar Usuário: " + e.getMessage();
                                e.printStackTrace();
                            }

                            Toast.makeText(LoginActivity.this, excecao, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

}
