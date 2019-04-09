package br.com.smarttech.acs.logitica;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

        private FirebaseAuth mAuth;
        private EditText editLogin;
        private EditText editSenha;
        private TextView textStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        editLogin = findViewById(R.id.editLogin);
        editSenha = findViewById(R.id.editSenha);
        textStatus = findViewById(R.id.textStatus);
    }

    @Override
    public void onStart(){
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    void updateUI(FirebaseUser currentUser){
        if(currentUser != null){
            dashRedirect();
        }
        else{
            Toast.makeText(this, "Usuário não logado.", Toast.LENGTH_SHORT).show();
        }
    }

    public void signIn(View view) {
        String login = editLogin.getText().toString();
        String senha = editSenha.getText().toString();
        textStatus.setVisibility(View.VISIBLE);
        textStatus.setText("Buscando info sobre usuário");
        mAuth.signInWithEmailAndPassword(login,senha)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                textStatus.setVisibility(View.GONE);
                updateUI(mAuth.getCurrentUser());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                textStatus.setText("Usuário/Senha não conferem.");
            }
        });
    }

    public void signUp(View view) {
        signUpRedirect();
    }

    void dashRedirect(){
        Intent novaTela = new Intent(MainActivity.this,DashActivity.class);
        startActivity(novaTela);
    }
    void signUpRedirect(){
        Intent novaTela = new Intent(MainActivity.this,SignUpActivity.class);
        startActivity(novaTela);
    }
}
