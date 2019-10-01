package com.petsapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.petsapp.Firebase.Conexao;

public class Login extends AppCompatActivity {

    private Button btnLogin;
    private TextView cadastro, redefinirSenha;
    private FirebaseAuth auth;
    private ProgressBar mProgressCircle;
    private EditText tLogin, tSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inicializarComponentes();
        eventoClick();
    }

    private void eventoClick() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tLogin = findViewById(R.id.tLogin);
                tSenha = findViewById(R.id.tSenha);
                String loginMail = tLogin.getText().toString();
                String senha = tSenha.getText().toString();
                tLogin.setEnabled(false);
                tSenha.setEnabled(false);
                btnLogin.setEnabled(false);
                cadastro.setEnabled(false);
                redefinirSenha.setEnabled(false);
                if (loginMail.equals("") || senha.equals("")) {
                    alert("Preencha todos os campos para continuar");
                    tLogin.setEnabled(true);
                    tSenha.setEnabled(true);
                    btnLogin.setEnabled(true);
                    cadastro.setEnabled(false);
                    redefinirSenha.setEnabled(false);
                } else {
                    mProgressCircle.setVisibility(View.VISIBLE);
                    autenticacao(loginMail, senha);
                }
            }
        });
        cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastro.setEnabled(false);
                Intent intent = new Intent(Login.this,
                        CadastroUser.class);
                startActivity(intent);
            }
        });
        redefinirSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redefinirSenha.setEnabled(false);
                Intent intent = new Intent(Login.this,
                        RedefinirSenha.class);
                startActivity(intent);
            }
        });
    }

    private void autenticacao(String loginMail, String senha) {
        auth.signInWithEmailAndPassword(loginMail, senha).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    mProgressCircle.setVisibility(View.INVISIBLE);
                    finish();
                } else {
                    mProgressCircle.setVisibility(View.INVISIBLE);
                    alert("E-mail ou senha incorretos");
                }
                tLogin.setEnabled(true);
                tSenha.setEnabled(true);
                btnLogin.setEnabled(true);
                cadastro.setEnabled(true);
                redefinirSenha.setEnabled(true);
            }
        });
    }

    private void inicializarComponentes() {
        btnLogin = findViewById(R.id.btnLogin);
        cadastro = findViewById(R.id.cadastro);
        redefinirSenha = findViewById(R.id.redefinirSenha);
        mProgressCircle = findViewById(R.id.progressCircle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cadastro.setEnabled(true);
        redefinirSenha.setEnabled(true);
    }


    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }
    private void alert(String a) {
        Toast.makeText(this, a, Toast.LENGTH_LONG).show();
    }
}
