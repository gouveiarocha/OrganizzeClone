package com.example.organizzeclone.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.organizzeclone.Helper.FirebaseUtils;
import com.example.organizzeclone.Model.Usuario;
import com.example.organizzeclone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CadastroActivity extends AppCompatActivity {

    private EditText editNome, editEmail, editSenha;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        //REF Componentes
        editNome = findViewById(R.id.etxt_cadastro_nome);
        editEmail = findViewById(R.id.etxt_email);
        editSenha = findViewById(R.id.etxt_senha);

    }

    //Método de ação para o botão Cadastrar...
    public void btnCadastrar(View view) {

        String nome = editNome.getText().toString();
        String email = editEmail.getText().toString();
        String senha = editSenha.getText().toString();

        //Validar campos...
        if (!nome.isEmpty()) {
            if (!email.isEmpty()) {
                if (!senha.isEmpty()) {
                    Usuario usuario = new Usuario(nome, email, senha);  //Cria um obj Usuario...
                    cadastrarUsuario(usuario);  //Chama o método para cadastro do usuário...
                } else {
                    Toast.makeText(CadastroActivity.this, getString(R.string.erro_validacao_senha), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(CadastroActivity.this, getString(R.string.erro_validacao_email), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(CadastroActivity.this, getString(R.string.erro_validacao_nome), Toast.LENGTH_SHORT).show();
        }

    }

    //Método para Cadastrar o Usuario...
    public void cadastrarUsuario(final Usuario usuario) {

        auth = FirebaseUtils.getAuth();
        auth.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            usuario.salvar();
                            finish();
                        } else {

                            String excecao = "";

                            //Recuperar Exception
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                excecao = "Digite uma senha mais forte!";
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                excecao = "Digite um email Válido!";
                            } catch (FirebaseAuthUserCollisionException e) {
                                excecao = "E-mail já Cadastrado!";
                            } catch (Exception e) {
                                excecao = "Erro ao Cadastrar Usuário: " + e.getMessage();
                                e.printStackTrace();
                            }

                            //Mostrar a Exceção
                            Toast.makeText(CadastroActivity.this, excecao, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

}
