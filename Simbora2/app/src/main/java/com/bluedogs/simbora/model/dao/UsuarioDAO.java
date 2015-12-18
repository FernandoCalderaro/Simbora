package com.bluedogs.simbora.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.bluedogs.simbora.model.bean.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matheus Sousa on 26/11/2015.
 */
public class UsuarioDAO extends SQLiteOpenHelper {

    private static final int VERSÃO = 1;
    private static final String TABELA = "Usuario";
    private static final String BANCO_DE_DADOS = "Simbora";

    private static String [] tabelas,tipos,specs;

    public UsuarioDAO(Context context)
    {
        super(context, BANCO_DE_DADOS, null, VERSÃO);
        Log.i("USUARIO-DAO","Construtor");
        tabelas = new String[7];
        tipos = new String[tabelas.length];
        specs = new String[tabelas.length];
        int i = 0;
        tabelas[i] = "id";tipos[i] = "INTEGER";specs[i] = "PRIMARY KEY";
        i+=1;
        tabelas[i] = "nome";tipos[i] = "TEXT";specs[i] = "";
        i+=1;
        tabelas[i] = "dataNascimento";tipos[i] = "TEXT";specs[i] = "";
        i+=1;
        tabelas[i] = "email";tipos[i] = "TEXT";specs[i] = "";
        i+=1;
        tabelas[i] = "telefone";tipos[i] = "TEXT";specs[i] = "";
        i+=1;
        tabelas[i] = "senha";tipos[i] = "TEXT";specs[i] = "";
        i+=1;
        tabelas[i] = "veiculo";tipos[i] = "TEXT";specs[i] = "";


        Log.i("TABELAS",tabelas[0]+" "+tabelas[1]+" "+tabelas[2]+" "+tabelas[3]+" "+tabelas[4]+" "+tabelas[5]+" "+tabelas[6]);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        ScriptSQL scriptSQL = new ScriptSQL(TABELA,BANCO_DE_DADOS);

        for (int i=0;i<specs.length;i++)
            tipos[i].concat(" "+specs[i]);

        db.execSQL(scriptSQL.criarBanco(tabelas,tipos));
        Log.i("USUARIO DAO","Criação da tabela: "+TABELA);
        Log.i("DATABASE TABLE","Tabela "+" APAGADA COM SUCESSO");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void cadastrar(Usuario usuario)
    {
        ContentValues values = new ContentValues();

        values.put(tabelas[1],usuario.getNomeCompleto());
        values.put(tabelas[2], usuario.getDataNascimento());
        values.put(tabelas[3], usuario.getEmail());
        values.put(tabelas[4], usuario.getTelefone());
        values.put(tabelas[5], String.valueOf(usuario.getSenha()));
        values.put(tabelas[6], String.valueOf(usuario.isVeiculo()));


        getWritableDatabase().insert(TABELA, null, values);
        Log.i("USUARIO DAO",usuario.toString());
    }

    public List<Usuario> listar()
    {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM "+TABELA+" order by nome";
        try
        {
            Cursor cursor = getReadableDatabase().rawQuery(sql,null);
            while(cursor.moveToNext())
            {
                Usuario usuario = new Usuario();
                usuario.setNomeCompleto(cursor.getString(1));

                Log.i("CADASTRO BD", usuario.getNomeCompleto());

                usuario.setDataNascimento(cursor.getString(2));

                Log.i("CADASTRO BD", usuario.getDataNascimento());

                usuario.setEmail(cursor.getString(3));

                Log.i("CADASTRO BD", usuario.getEmail());

                usuario.setTelefone(cursor.getString(4));

                Log.i("CADASTRO BD", usuario.getTelefone());

                usuario.setSenha(cursor.getString(5));

                Log.i("CADASTRO BD", usuario.getSenha());

                usuario.setVeiculo(Boolean.getBoolean(cursor.getString(6)));

                Log.i("CADASTRO BD", String.valueOf(usuario.isVeiculo()));

                lista.add(usuario);
            }
            cursor.close();
        }catch (SQLException e)
        {
            Log.e("EXCEÇÃO SQL",e.getMessage());
        }
        finally {

        }
        return lista;
    }

    public void alterar(Usuario usuario){
        ContentValues values = new ContentValues();
        String where;

        values.put(tabelas[1],usuario.getNomeCompleto());
        values.put(tabelas[2], usuario.getDataNascimento());
        values.put(tabelas[3], usuario.getEmail());
        values.put(tabelas[4], usuario.getTelefone());
        values.put(tabelas[5], String.valueOf(usuario.getSenha()));
        values.put(tabelas[6], String.valueOf(usuario.isVeiculo()));

        where =  " email = " + usuario.getEmail();

        getWritableDatabase().update(TABELA, values,where, null);

        Log.i("USUARIO-DAO ",String.valueOf(getWritableDatabase().update(TABELA, values,where, null))+ " linhas afetadas");
    }


    public void excluir(Usuario usuario){
        String[] args = { usuario.getEmail()};
        getWritableDatabase().delete(TABELA, tabelas[3]+" = "+args[0], args);
        Log.i("USUARIO DAO", "Usuario excluído: " + usuario.getNomeCompleto());

    }

    public String getTABELA() {
        return TABELA;
    }

    public int getVERSÃO() {
        return VERSÃO;
    }
}
