package com.parse.starter.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.parse.starter.R;
import com.parse.starter.util.ParseErros;

public class CadastroActivity extends AppCompatActivity {

    private EditText textUser;
    private EditText textEmail;
    private EditText textSenha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        textUser  = (EditText)findViewById(R.id.et_user);
        textEmail = (EditText)findViewById(R.id.et_email);
        textSenha = (EditText)findViewById(R.id.et_senha);
        Button buttonCadastro = (Button) findViewById(R.id.bt_cadastrar);
        
        buttonCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarUsuario();
            }
        });
    }

    private void cadastrarUsuario() {
        ParseUser usuario = new ParseUser();

        usuario.setUsername(textUser.getText().toString());
        usuario.setEmail(textEmail.getText().toString());
        usuario.setPassword(textSenha.getText().toString());

        usuario.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e==null){
                    Toast.makeText(CadastroActivity.this,"Cadastro efetuado com sucesso!",Toast.LENGTH_LONG).show();
                    abrirLogin();

                }else{
                    ParseErros parseErros = new ParseErros();
                    String erro = parseErros.getErro(e.getCode());
                    Toast.makeText(CadastroActivity.this,erro,Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    private void abrirLogin(){
        Intent intent = new Intent(CadastroActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();


    }
}
