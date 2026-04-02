package service;

import model.Aluno;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AlunoService implements CrudService<Aluno> {

    private List<Aluno> alunos = new ArrayList<>();

    public void criar(Aluno a) {
        alunos.add(a);
    }

    public List<Aluno> listar() {
        return alunos;
    }

    public void atualizar(int index, Aluno a) {
        alunos.set(index, a);
    }

    public void remover(int index) {
        alunos.remove(index);
    }

    public boolean planoAtivo(Aluno aluno) {
        LocalDate vencimento = aluno.getDataMatricula()
                .plusMonths(aluno.getPlano().getDuracaoMeses());

        return !LocalDate.now().isAfter(vencimento);
    }

    public LocalDate getVencimento(Aluno aluno) {
        return aluno.getDataMatricula()
                .plusMonths(aluno.getPlano().getDuracaoMeses());
    }
}