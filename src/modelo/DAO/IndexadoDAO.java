/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import modelo.Indice;
import modelo.Usuario;

/**
 *
 * @author ignoi
 */
public class IndexadoDAO implements UsuarioDAO {

    private List<Usuario> usuarios = new ArrayList<>();
    private List<Indice> indices = new ArrayList<>();

    public IndexadoDAO() {
        try {
            File f = new File("usuarios.dat");
            if (f.isFile()) {
                FileInputStream fis = new FileInputStream(f); //abr. arquivo
                ObjectInputStream ois = new ObjectInputStream(fis); //Mnp. arquivo
                while (fis.available() != 0) {
                    usuarios.add((Usuario) ois.readObject());
                }
                ois.close();
                fis.close(); // fecha o manipulador e arquivo
                atualizarIndices();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean inserir(Usuario u) {
        /*
         Para inserir em uma lista ordenada temos que sempre pensar em três
         situações diferentes: quando não houverem elementos, quando houver
         apenas um elemento na lista e quando houver mais que um elemento na
         lista.
         */
        if (usuarios.isEmpty()) { // Caso seja uma lista nova
            usuarios.add(u);
        } else { // caso haja pelo menos um elemento na lista, então:
            boolean inseriu = false; // criamos um flag para vermos se conseguimos inserir
            if (usuarios.size() < 2) { // se a informação do novo nó for menor que a informação do primeiro nó, temos que inserir antes do primeiro elemento, para isto:
                if (u.getCodigo() <= usuarios.get(0).getCodigo()) {
                    Usuario antigo = usuarios.get(0);
                    usuarios.add(u);
                    usuarios.add(antigo);
                } else {
                    usuarios.add(u);
                }
                inseriu = true; // marcamos a flag dizendo que conseguimos inserir
            } else { // caso a posição a ser inserida não seja a primeira criamos variáveis auxiliares p/ percorrer a lista
                int indice = 0;
                Usuario anterior = usuarios.get(indice); // sabemos que o anterior é o início já que descartamos a hipótese do dado ser menor que o primeiro
                indice++; //incrementamos o indice
                Usuario atual = usuarios.get(indice); // começamos a verificar a lista a partir da segunda posição
                do { // iremos verificar enquanto a variável atual seja diferente de nulo, ou seja, até o fim
                    if (u.getCodigo() <= atual.getCodigo()) { // se o dado do novo nó for menor ou igual que o dado do elemento atual, encontramos a posição certa, então:
                        usuarios.add(indice, u); //adiciona o usuário na posição indicada, move o restante p/ direita
                        inseriu = true; // marcamos a flag dizendo que encontramos a posição e inserimos o novo nó
                        break; // quebramos o laço de repetição pois já encontramos a posição certa
                    }
                    /*
                     * se a condição acima não for atendida, devemos andar a
                     * lista, para isto fazemos:
                     */
                    indice++;
                    anterior = atual;
                    if (indice >= usuarios.size()) {
                        break;
                    } else {
                        atual = usuarios.get(indice);
                    }
                } while (true);
            }
            if (!inseriu) { // se ao final da lista não conseguimos inserir, o valor informado é maior que todos os contidos na lista, então
                usuarios.add(u);
            }
        }
        atualizarIndices();
        try {
            (new File("usuarios.dat")).delete(); // não sei como funciona o binário, mas vamos supor q ele continue salvando do fim, temos q apagar toda a lista para começar a salvar tudo novamente
            FileOutputStream fos = new FileOutputStream("usuarios.dat", false); //abertura de arquivo
            ObjectOutputStream oos = new ObjectOutputStream(fos); //Manipulador do arquivo
            for (Usuario usuario : usuarios) { //repete p/ todos os usuarios
                oos.writeObject(usuario); //Gravacao do objeto
            }
            oos.flush();
            oos.close(); // grava o stream / fecha o manipulador
            fos.close(); // fecha o arquivo
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean deletar(int codigo) {
        try {
            int indice = encontrar(0, usuarios.size() - 1, codigo);
            if (indice != -1) {
                usuarios.remove(indice);
                atualizarIndices();
                try { //removemos a posição agora temos q persistir os dados novamente
                    (new File("usuarios.dat")).delete(); // não sei como funciona o binário, mas vamos supor q ele continue salvando do fim, temos q apagar toda a lista para começar a salvar tudo novamente
                    FileOutputStream fos = new FileOutputStream("usuarios.dat", false); //abertura de arquivo
                    ObjectOutputStream oos = new ObjectOutputStream(fos); //Manipulador do arquivo
                    for (Usuario usuario : usuarios) { //repete p/ todos os usuarios
                        oos.writeObject(usuario); //Gravacao do objeto
                    }
                    oos.flush();
                    oos.close(); // grava o stream / fecha o manipulador
                    fos.close(); // fecha o arquivo
                    return true;
                } catch (Exception e) {
                    System.out.println(e);
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean atualizar(Usuario u) { //n garante atomicidade da transação
        int indice = encontrar(0, usuarios.size() - 1, u.getCodigo());
        if (indice != -1) {
            usuarios.remove(indice);
            usuarios.add(indice, u);
            try { //removemos a posição agora temos q persistir os dados novamente
                (new File("usuarios.dat")).delete(); // não sei como funciona o binário, mas vamos supor q ele continue salvando do fim, temos q apagar toda a lista para começar a salvar tudo novamente
                FileOutputStream fos = new FileOutputStream("usuarios.dat", false); //abertura de arquivo
                ObjectOutputStream oos = new ObjectOutputStream(fos); //Manipulador do arquivo
                for (Usuario usuario : usuarios) { //repete p/ todos os usuarios
                    oos.writeObject(usuario); //Gravacao do objeto
                }
                oos.flush();
                oos.close(); // grava o stream / fecha o manipulador
                fos.close(); // fecha o arquivo
                return true;
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public Usuario encontrar(int id) {
        int indice = encontrar(0, usuarios.size() - 1, id);
        if (indice == -1) {
            return null;
        } else {
            return usuarios.get(indice);
        }
    }

    @Override
    public Usuario encontrar(String nome) { 
        int indice = encontrar(0, indices.size() - 1, nome);
        if (indice == -1){
            return null;
        } else {
            return usuarios.get(indices.get(indice).getValor());
        }
    }

    /**
     * Método que realiza a busca binária recursiva.
     *
     * @param menor representa o menor indice do intervalo de verificação
     * @param maior representa o maior indice do intervalo de verificação
     * @param chave representa a chave que se está sendo procurada (código)
     * @return -1 se não encontrar, indice do objeto caso encontrar.
     */
    private int encontrar(int menor, int maior, int chave) {
        int media = (maior + menor) / 2;
        int valorMeio = usuarios.get(media).getCodigo();
        if (menor > maior) {
            return -1;
        } else if (valorMeio == chave) {
            return media;
        } else if (valorMeio < chave) {
            return encontrar(media + 1, maior, chave);
        } else {
            return encontrar(menor, media - 1, chave);
        }
    }

    /**
     * Método que realiza a busca binária recursiva.
     *
     * @param menor representa o menor indice do intervalo de verificação
     * @param maior representa o maior indice do intervalo de verificação
     * @param chave representa a chave que se está sendo procurada (código)
     * @return -1 se não encontrar, indice do objeto caso encontrar.
     */
    private int encontrar(int menor, int maior, String chave) {
        int media = (maior + menor) / 2;
        String valorMeio = indices.get(media).getChave();

        if (menor > maior) {
            return -1;
        } else {
            if (valorMeio.compareTo(chave) == 0) {
                return media;
            } else {
                if (valorMeio.compareTo(chave) < 0) {
                    return encontrar(media + 1, maior, chave);
                } else {
                    return encontrar(menor, media - 1, chave);
                }
            }
        }
    }

    @Override
    public List<Usuario> listar() {
        usuarios = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream("usuarios.dat"); //abr. arquivo
            ObjectInputStream ois = new ObjectInputStream(fis); //Mnp. arquivo
            while (fis.available() != 0) {
                usuarios.add((Usuario) ois.readObject());
            }
            ois.close();
            fis.close(); // fecha o manipulador e arquivo
            return usuarios;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void atualizarIndices() {
        indices = new ArrayList<>();
        for (int i = 0; i < usuarios.size(); i++) {
            Indice index = new Indice(usuarios.get(i).getNome(), i);
            indices.add(index);
        }
        Object[] ordenados = indices.toArray();
        Arrays.sort(ordenados);
        indices.removeAll(indices);
        for (Object ordenado : ordenados) {
            indices.add((Indice) ordenado);
        }
    }

}
