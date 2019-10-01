package com.petsapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.petsapp.Firebase.Conexao;

public class RedefinirSenha extends AppCompatActivity {

    private EditText edtResetEmail;
    private Button btnResetar;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redefinir_senha);
        edtResetEmail = (EditText)findViewById(R.id.edtEmailRecu);
        btnResetar = (Button)findViewById(R.id.btnRecuperar);


        btnResetar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtResetEmail.getText().toString().trim();
                redefinirSenha(email);
            }
        });
    }

    private void redefinirSenha(String email){
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(RedefinirSenha.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            alert("Senha Redefinida com sucesso!");
                            finish();
                        }else{
                            alert("Falha ao redefinir senha!");
                        }

                    }
                });
    }
    private void alert(String msg){
        Toast.makeText(RedefinirSenha.this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }
}
