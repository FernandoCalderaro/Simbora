package com.bluedogs.simbora.bean;

import java.util.Calendar;

/**
 * Created by Matheus Sousa on 22/11/2015.
 */
public class Usuario {

    private String nomeCompleto;
    private String dataNascimento;
    private String email;
    private String telefone;
    private String senha;
    private String confirmarSenha;
    private boolean Veiculo;



    public boolean nomeOk(int pos)
    {
        if(pos == (nomeCompleto.length())-1)
            return(Character.isLetter(nomeCompleto.charAt(pos)) || Character.isWhitespace(nomeCompleto.charAt(pos)));
        if(Character.isLetter(nomeCompleto.charAt(pos)) || Character.isWhitespace(nomeCompleto.charAt(pos)))
            return(nomeOk(pos+1));
        return false;
    }

    public boolean nascimentoOk()
    {
        if(dataNascimento.length()==10)
        {
            if(dataNascimento.contains("/"))
            {
                if((dataNascimento.charAt(2)& dataNascimento.charAt(5)) == '/')
                {
                    String  dia = dataNascimento.substring(0,2),
                            mes = dataNascimento.substring(3,5),
                            ano = dataNascimento.substring(6);
                    Integer.parseInt(dia);
                    Integer.parseInt(mes);
                    Integer.parseInt(ano);

                    if(Integer.parseInt(dia)>= 1 && Integer.parseInt(dia)<=31)
                        if(Integer.parseInt(mes)>=1 && Integer.parseInt(mes)<=12)
                            if((Integer.parseInt(ano)-Calendar.YEAR ) >= 18)
                                return true;
                }
            }
        }
        return false;
    }

    public boolean emailOk()
    {
        if(email.contains("@") && email.endsWith(".com"))
            return true;
        return false;
    }

    public boolean telefoneOk(int pos)
    {
        if(pos == (telefone.length()-1))
            return(Character.isDigit(telefone.charAt(pos)) || Character.isWhitespace(telefone.charAt(pos)));
        if(Character.isDigit(telefone.charAt(pos)))
            return(telefoneOk(pos + 1));
        return false;
    }

    public boolean senhaOk()
    {
        if(senha.length()>=8)
            if(senha.equals(confirmarSenha))
                return true;
        return false;
    }


    public String getNomeCompleto() {
        return nomeCompleto;
    }
    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }


    public String getDataNascimento() {
        return dataNascimento;
    }
    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }


    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }


    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }


    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }



    public boolean isVeiculo() {
        return Veiculo;
    }
    public void setVeiculo(boolean veiculo) {
        Veiculo = veiculo;
    }


    public String getConfirmarSenha() {
        return confirmarSenha;
    }
    public void setConfirmarSenha(String confirmarSenha) {
        this.confirmarSenha = confirmarSenha;
    }

    @Override
    public String toString() {
        return nomeCompleto;
    }

}
