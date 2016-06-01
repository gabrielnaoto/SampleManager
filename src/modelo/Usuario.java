/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;

/**
 *
 * @author ignoi
 */
public class Usuario implements Serializable {

    private int codigo;
    private String nome;
    private String qualificacao;
    private float salario;

    public Usuario() {
    }

    public Usuario(int codigo, String nome, String qualificacao, float salario) {
        this.codigo = codigo;
        this.nome = nome;
        this.qualificacao = qualificacao;
        this.salario = salario;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getQualificacao() {
        return qualificacao;
    }

    public void setQualificacao(String qualificacao) {
        this.qualificacao = qualificacao;
    }

    public float getSalario() {
        return salario;
    }

    public void setSalario(float salario) {
        this.salario = salario;
    }

    @Override
    public String toString() {
        return "Usuario{" + "codigo=" + codigo + ", nome=" + nome + ", qualificacao=" + qualificacao + ", salario=" + salario + '}';
    }

}
