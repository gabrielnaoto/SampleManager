/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JOptionPane;
import modelo.DAO.ContinuoDAO;
import modelo.DAO.IndexadoDAO;
import modelo.DAO.SequencialDAO;
import modelo.DAO.UsuarioDAO;
import visao.Janela;

/**
 *
 * @author ignoi
 */
public class ControleJanela {

    private Janela janela;
    private UsuarioDAO dao;

    public ControleJanela() {
        janela = new Janela();
        janela.radioContinuo.setSelected(true);
        dao = new ContinuoDAO();
        init();
    }

    public void init() {
        janela.botaoInserir.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        janela.botaoEditar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        janela.botaoExcluir.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        janela.botaoPesquisar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        
        janela.botaoTrocar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int resultado = JOptionPane.showConfirmDialog(janela, "Ao trocar de DAO você perderá todos os registros, gostaria de prosseguir?");
                if (resultado > -1){
                    if (resultado == 0){
                        (new File("usuarios.dat")).delete();
                        if (janela.radioContinuo.isSelected()){
                            dao = new ContinuoDAO();
                        } else {
                            if (janela.radioIndexado.isSelected()){
                                dao = new IndexadoDAO();
                            } else {
                                dao = new SequencialDAO();
                            }
                        }
                    }
                }
            }
        });
    }
    
    public void executar(){
        janela.setVisible(true);
    }

}
