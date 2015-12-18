package com.bluedogs.simbora.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bluedogs.simbora.R;
import com.bluedogs.simbora.model.bean.Usuario;
import com.bluedogs.simbora.helper.UsuarioHelper;
import com.bluedogs.simbora.model.dao.UsuarioDAO;

public class CadastroActivity extends AppCompatActivity {

    private EditText edtCampos[] = new EditText[6];
    /*CAMPOS:
    * 1 - NOME
    *
    * 2 - DATA DE NASCIMENTO
    *
    * 3 - E-MAIL
    *
    * 4 - TELEFONE
    *
    * 5 - SENHA
    *
    * 6 - CONFIRMAR SENHA*/
    private Button btnCadastrar;

    private UsuarioHelper helper;

    private Usuario user;

    private UsuarioDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        btnCadastrar = (Button)findViewById(R.id.cadastro_btn_cadastrar);
        helper = new UsuarioHelper(this,edtCampos.length);
        user = null;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = helper.getUser();
                boolean campoVazio = false;
                dao = new UsuarioDAO(CadastroActivity.this);

                for(int i=0;i<helper.getEdtCampos().length;i++)
                {
                    if(helper.getEdtCampos()[i].getText().toString().isEmpty()) {
                        campoVazio = true;
                    }
                }
                if(campoVazio)
                    Toast.makeText(getApplicationContext(),"Há campos vazios, Preencha todos!",Toast.LENGTH_LONG).show();
                else
                {
                    helper.alertarUserSeNecessario();
                    if(helper.dadosValidos())
                    {
                        dao.cadastrar(user);
                        dao.close();
                        iniciarActivity(InicialActivity.class);
                        Toast.makeText(CadastroActivity.this
                                ,"Usuario Cadastrado: "+user.toString()
                                ,Toast.LENGTH_LONG).show();
                        helper.setUser(new Usuario());
                    }
                }
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void iniciarActivity(Class classe)
    {
        Intent it =  new Intent(this,classe);
        startActivity(it);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
