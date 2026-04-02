package service;

import model.Plano;

import java.util.ArrayList;
import java.util.List;

// Service de Plano.

public class PlanoService implements CrudService<Plano> {

    private List<Plano> planos = new ArrayList<>();

    public void criar(Plano p) {
        planos.add(p);
    }

    public List<Plano> listar() {
        return planos;
    }

    public void atualizar(int index, Plano p) {
        planos.set(index, p);
    }

    public void remover(int index) {
        planos.remove(index);
    }
}