package service;

import model.Aluno;

import java.util.ArrayList;
import java.util.List;

public class AlunoService implements CrudService<Aluno> {

    private final List<Aluno> alunos;

    public AlunoService() {
        this.alunos = new ArrayList<>();
    }

    // ===== CRUD =====

    @Override
    public void criar(Aluno aluno) {
        if (aluno != null) {
            alunos.add(aluno);
        }
    }

    @Override
    public List<Aluno> listar() {
        return new ArrayList<>(alunos);
    }

    @Override
    public Aluno buscarPorId(int id) {
        if (id <= 0 || id > alunos.size()) {
            return null;
        }

        return alunos.get(id - 1);
    }

    @Override
    public void remover(int id) {
        Aluno aluno = buscarPorId(id);

        if (aluno != null) {
            alunos.remove(aluno);
        }
    }

    // ===== CONSULTA =====

    public List<Aluno> consultarTodos() {
        return new ArrayList<>(alunos);
    }
}