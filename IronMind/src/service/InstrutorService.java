package service;

import model.Instrutor;

import java.util.ArrayList;
import java.util.List;

// Service de Instrutor.

public class InstrutorService implements CrudService<Instrutor> {

    private List<Instrutor> instrutores = new ArrayList<>();

    public void criar(Instrutor i) {
        instrutores.add(i);
    }

    public List<Instrutor> listar() {
        return instrutores;
    }

    public void atualizar(int index, Instrutor i) {
        instrutores.set(index, i);
    }

    public void remover(int index) {
        instrutores.remove(index);
    }
}