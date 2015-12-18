package com.bluedogs.simbora.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bluedogs.simbora.R;
import com.bluedogs.simbora.model.bean.Usuario;
import com.bluedogs.simbora.model.dao.UsuarioDAO;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;



import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtSenha;
    private EditText edtLogin;
    private Button btnEntrar;
    private Button btnCadastrar;

    private LoginButton login;
    private CallbackManager callbackManager;
    private boolean logado;

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logado = false;
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager =  CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object,
                                            GraphResponse response) {
                                        // Application code
                                        Log.v("LoginActivity", response.toString());
                                        Toast.makeText(getApplicationContext(),"DADOS RECEBIDOS: "+response.toString(),Toast.LENGTH_LONG).show();
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender");
                        request.setParameters(parameters);
                        request.executeAsync();

                        iniciarActivity(InicialActivity.class,parameters);
                    }


                    @Override
                    public void onCancel() {
                        // App code
                        Log.v("LoginActivity", "cancel");
                        Toast.makeText(getApplicationContext(),"Cancelado",Toast.LENGTH_LONG).show();
                        LoginManager.getInstance().logOut();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.v("LoginActivity", exception.getCause().toString());
                        Toast.makeText(getApplicationContext(),"Erro: "+exception.toString(),Toast.LENGTH_LONG).show();
                    }
                });
        setContentView(R.layout.activity_login);
        login= (LoginButton) findViewById(R.id.login_button);
        btnEntrar = (Button) findViewById(R.id.login_btn_entrar);
        edtLogin = (EditText) findViewById(R.id.login_edt_email);
        edtSenha = (EditText) findViewById(R.id.login_edt_senha);
        btnCadastrar = (Button) findViewById(R.id.login_btn_cadastrar);
        login.setOnClickListener(this);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsuarioDAO dao = new UsuarioDAO(LoginActivity.this);
                List<Usuario> listaUsuarios = dao.listar();
                Log.i("LISTA","LISTA CARREGADA");
                Log.i("LISTA VAZIA",String.valueOf(listaUsuarios.isEmpty()));
                for(Usuario usuario:listaUsuarios)
                {
                    Log.i("LISTA LOGIN",usuario.getEmail());
                    Log.i("LISTA SENHA", usuario.getSenha());
                    if(usuario.getEmail().equals(edtLogin.getText().toString()))
                    {
                        if(usuario.getSenha().equals(edtSenha.getText().toString()))
                        {
                            Toast.makeText(getApplicationContext(),"Bem-Vindo "+usuario.getNomeCompleto(),Toast.LENGTH_SHORT).show();
                            iniciarActivity(InicialActivity.class);
                        }
                    }
                }
                dao.close();
            }
        });
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarActivity(CadastroActivity.class);
            }
        });
    }

    private void iniciarActivity(Class classe,Bundle bundle)
    {
        Intent it =  new Intent(this,classe);
        startActivity(it,bundle);
    }
    private void iniciarActivity(Class classe)
    {
        Intent it =  new Intent(this,classe);
        startActivity(it);
    }
    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }
    @Override
    public void onClick(View v) {
        if(logado)
            LoginManager.getInstance().logOut();
        else
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends", "email"));
        logado = logado && false;
    }
}
