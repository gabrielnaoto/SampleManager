/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author ignoi
 */
public class Indice implements Comparable<Object> {

    private String chave;
    private int valor;

    public Indice(String chave, int valor) {
        this.chave = chave;
        this.valor = valor;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    @Override
    public int compareTo(Object o) {
        return chave.compareTo(((Indice) o).getChave());
    }

    @Override
    public String toString() {
        return "Indice{" + "chave=" + chave + ", valor=" + valor + '}';
    }

}
