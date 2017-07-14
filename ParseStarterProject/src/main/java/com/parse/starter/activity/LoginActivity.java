package com.parse.starter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.starter.R;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsuario;
    private EditText editTextSenha;
    private Button   buttonLogar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        verificarUsuarioLogado();

        editTextUsuario   = (EditText)findViewById(R.id.et_user);
        editTextSenha     = (EditText)findViewById(R.id.et_senha);
        buttonLogar       = (Button)findViewById(R.id.bt_logar);

        buttonLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usuario = editTextUsuario.getText().toString();
                String senha   = editTextSenha.getText().toString();

                verificarLogin(usuario,senha);
            }
        });


    }

    private void verificarLogin(String usuario, String senha) {

        ParseUser.logInInBackground(usuario, senha, new LogInCallback() {

            @Override
            public void done(ParseUser user, ParseException e) {
                if (e==null){

                    Toast.makeText(LoginActivity.this,"Login com sucesso",Toast.LENGTH_LONG).show();
                    abrirTelaPrincipal();

                }else{

                    Toast.makeText(LoginActivity.this,"Login erro", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void abrirCadastro(View view) {

        Intent intent = new Intent(LoginActivity.this,CadastroActivity.class);
        startActivity(intent);

    }

    private void verificarUsuarioLogado(){
        if (ParseUser.getCurrentUser() != null){
            abrirTelaPrincipal();
        }
    }

    private void abrirTelaPrincipal() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
