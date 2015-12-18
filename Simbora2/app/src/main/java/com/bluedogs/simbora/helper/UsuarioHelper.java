package com.bluedogs.simbora.helper;


import android.widget.EditText;
import android.widget.Toast;

import com.bluedogs.simbora.R;
import com.bluedogs.simbora.activity.CadastroActivity;
import com.bluedogs.simbora.model.bean.Usuario;



/**
 * Created by Matheus Sousa on 22/11/2015.
 */
public class UsuarioHelper
{
    private EditText edtCampos[];
    private Usuario user;
    private CadastroActivity activity;

    public UsuarioHelper(CadastroActivity activity,int qtdCampos)
    {
        edtCampos = new EditText[qtdCampos];
        edtCampos[0] = (EditText) activity.findViewById(R.id.cadastro_edt_nome);
        edtCampos[1] = (EditText) activity.findViewById(R.id.cadastro_edt_nascimento);
        edtCampos[2] = (EditText) activity.findViewById(R.id.cadastro_edt_email);
        edtCampos[3] = (EditText) activity.findViewById(R.id.cadastro_edt_telefone);
        edtCampos[4] = (EditText) activity.findViewById(R.id.cadastro_edt_senha);
        edtCampos[5] = (EditText) activity.findViewById(R.id.cadastro_edt_confirma);

        user = new Usuario();
        this.activity = activity;
    }
    public Usuario getUser() {
        user.setNomeCompleto(edtCampos[0].getText().toString());
        user.setDataNascimento(edtCampos[1].getText().toString());
        user.setEmail(edtCampos[2].getText().toString());
        user.setTelefone(edtCampos[3].getText().toString());
        user.setSenha(edtCampos[4].getText().toString());
        user.setConfirmarSenha(edtCampos[5].getText().toString());


        return user;
    }

    public void setUser(Usuario user)
    {
        int i = 0;
        edtCampos[i].setText(user.getNomeCompleto());
        i+=1;
        edtCampos[i].setText(user.getTelefone());
        i+=1;
        edtCampos[i].setText(user.getEmail());
        i+=1;
        edtCampos[i].setText(user.getTelefone());
        i+=1;
        edtCampos[i].setText(user.getSenha());
        i+=1;
        edtCampos[i].setText(user.getConfirmarSenha());
        i+=1;
        this.user = user;
    }

    public boolean dadosValidos()
    {
        return(user.nomeOk(0) && user.senhaOk() && user.emailOk() && user.nascimentoOk() && user.telefoneOk(0));
    }
    public String alertarUser()
    {
        StringBuilder alerta = new StringBuilder();
        alerta.append("teste");
        return alerta.toString();

    }

    public void alertarUserSeNecessario()
    {
        if(!dadosValidos())
        {
            Toast.makeText(activity.getApplicationContext(),"Campos preenchidos incorretamente",Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(activity.getApplicationContext(),"Todos os campos estão preenchidos corretamente",Toast.LENGTH_LONG).show();
    }

    public EditText[] getEdtCampos() {
        return edtCampos;
    }
}
