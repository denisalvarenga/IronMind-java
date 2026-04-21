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
            System.out.println("Aluno cadastrado com sucesso.");
        } else {
            System.out.println("Aluno inválido.");
        }
    }

    // ===== LISTAGEM =====

    public void listarAlunos() {

        List<Aluno> alunos = alunoService.listar();

        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
            return;
        }

        System.out.println("===== LISTA DE ALUNOS =====");

        for (int i = 0; i < alunos.size(); i++) {
            Aluno aluno = alunos.get(i);

            System.out.println("ID: " + (i + 1));
            System.out.println(aluno.exibirResumo());
            System.out.println("----------------------------");
        }
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
        Aluno aluno = alunoService.buscarPorId(id);

        if (aluno != null) {
            alunoService.remover(id);
            System.out.println("Aluno removido com sucesso.");
        } else {
            System.out.println("Aluno não encontrado.");
        }
    }

    // ===== REGRA DE NEGÓCIO =====

    public boolean planoAtivo(Aluno aluno) {
        if (aluno == null) {
            return false;
        }

        return aluno.planoAtivo();
    }
}