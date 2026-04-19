package controller;

import model.Aluno;
import service.AlunoService;

import java.util.List;

public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController() {
        this.alunoService = new AlunoService();
    }

    // ===== CADASTRO =====

    public void cadastrarAluno(Aluno aluno) {
        if (aluno != null) {
            alunoService.criar(aluno);
        } else {
            System.out.println("Aluno inválido.");
        }
    }

    // ===== LISTAGEM =====

    public void listarAlunos() {
        alunoService.listar();
    }

    public List<Aluno> consultarTodos() {
        return alunoService.consultarTodos();
    }

    // ===== BUSCA =====

    public Aluno buscarAluno(int id) {
        return alunoService.buscarPorId(id);
    }

    // ===== REMOÇÃO =====

    public void removerAluno(int id) {
        alunoService.remover(id);
    }

    // ===== REGRA DE NEGÓCIO =====

    public boolean planoAtivo(Aluno aluno) {
        if (aluno == null) {
            return false;
        }

        return aluno.planoAtivo();
    }
}