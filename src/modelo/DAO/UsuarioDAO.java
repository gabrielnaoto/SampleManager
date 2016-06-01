/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.DAO;

import java.util.List;
import modelo.Usuario;

/**
 *
 * @author ignoi
 */
public interface UsuarioDAO {
    
    public boolean inserir(Usuario u);
    
    public boolean deletar(int codigo);
    
    public boolean atualizar(Usuario u);
    
    public Usuario encontrar(int id);
    
    public Usuario encontrar(String nome);
    
    public List<Usuario> listar();
    
}
