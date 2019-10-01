package com.petsapp;

import android.content.Intent;
import android.net.Uri;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.petsapp.Firebase.Conexao;

import java.util.UUID;

public class CadastroUser extends AppCompatActivity {

    private Button cad;
    private EditText edtnome, edtemail, edtsenha;



    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_user);
        recebe();
        eventoCadastrar();
        firebase();
    }

    private void recebe() {
        cad = (Button) findViewById(R.id.btnCadastro);
        edtnome = (EditText) findViewById(R.id.edtNome);
        edtemail = (EditText) findViewById(R.id.edtEmail);
        edtsenha = (EditText) findViewById(R.id.edtSenha);



    }

    private void alert(String msg){
        Toast.makeText(CadastroUser.this,msg,Toast.LENGTH_SHORT).show();
    }

    private void eventoCadastrar() {

        cad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario usuario = new Usuario();
                usuario.setUid(UUID.randomUUID().toString());
                usuario.setEmail(edtemail.getText().toString());
                usuario.setNome(edtnome.getText().toString());

                databaseReference.child("Usuario").child(usuario.getUid()).setValue(usuario);

                String email = edtemail.getText().toString().trim();
                String senha = edtsenha.getText().toString().trim();

                criarUser(email, senha);

                limparCampos();
            }
        });



    }
    private void firebase() {
        FirebaseApp.initializeApp(CadastroUser.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void limparCampos() {
        edtnome.setText("");
        edtemail.setText("");
        edtsenha.setText("");
    }

    private void criarUser(String email, String senha) {
        auth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(CadastroUser.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            alert("Usuário cadastrado com sucesso!");

                            Intent intent = new Intent(CadastroUser.this, Login.class);
                            startActivity(intent);
                        }else{

                            alert("Falha no cadastro.");
                        }
                    }
                });


    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();

    }
}