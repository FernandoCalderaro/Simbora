package com.bluedogs.simbora.model.dao;

/**
 * Created by Matheus Sousa on 26/11/2015.
 */
public class ScriptSQL
{
    private String tabela;
    private String bancoDeDados;
    private StringBuilder ddl;

    public ScriptSQL(String tabela,String bancoDeDados)
    {
        this.bancoDeDados = bancoDeDados;
        this.tabela = tabela;
        ddl =  new StringBuilder();
    }

    public String criarBanco(String colunas[],String tipos[])
    {
        ddl.append("CREATE TABLE " + tabela + " (");
        for(int i = 0;i<colunas.length;i++)
            ddl.append(colunas[i]+" "+tipos[i]+" ,");

        ddl.deleteCharAt(ddl.length()-1);
        ddl.append(")");
        return ddl.toString();
    }

    public String pesquisa(String campo,String tabela)
    {
        StringBuilder ddl = new StringBuilder();
        ddl.append("SELECT ");
        ddl.append(campo);
        ddl.append(" FROM TABLE "+tabela);
        return ddl.toString();
    }
}
