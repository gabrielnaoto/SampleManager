/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import controle.ControleJanela;

/**
 *
 * @author ignoi
 */
public class Principal {

    public static void main(String[] args) {
        try {
           ControleJanela cj = new ControleJanela();
           cj.executar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
