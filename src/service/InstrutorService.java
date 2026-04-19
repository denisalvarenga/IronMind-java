package service;

import model.Instrutor;

import java.util.ArrayList;
import java.util.List;

// Service de Instrutor.
public class InstrutorService implements CrudService<Instrutor> {

    private List<Instrutor> instrutores = new ArrayList<>();

    @Override
    public void criar(Instrutor instrutor) {
        instrutores.add(instrutor);
    }

    @Override
    public List<Instrutor> listar() {
        return new ArrayList<>(instrutores); // evita expor lista interna
    }

    @Override
    public Instrutor buscarPorId(int id) {
        for (Instrutor i : instrutores) {
            if (i.getId() == id) {
                return i;
            }
        }
        return null;
    }

    @Override
    public void remover(int id) {
        instrutores.removeIf(i -> i.getId() == id);
    }

    // Método extra permitido (não faz parte do CRUD base)
    public void atualizar(int id, Instrutor novoInstrutor) {
        for (int i = 0; i < instrutores.size(); i++) {
            if (instrutores.get(i).getId() == id) {
                instrutores.set(i, novoInstrutor);
                return;
            }
        }
    }
}