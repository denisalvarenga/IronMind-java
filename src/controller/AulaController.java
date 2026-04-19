package controller;

import model.Aluno;
import model.Aula;
import service.AulaService;

import java.util.List;

public class AulaController {

    private final AulaService aulaService;

    public AulaController() {
        this.aulaService = new AulaService();
    }

    // ===== CADASTRO =====

    public void criar(Aula aula) {
        if (aula != null) {
            aulaService.criar(aula);
        } else {
            System.out.println("Aula inválida.");
        }
    }

    // ===== LISTAGEM =====

    public void listar() {
        aulaService.listar();
    }

    public List<Aula> consultarTodos() {
        return aulaService.consultarTodos();
    }

    // ===== BUSCA =====

    public Aula buscarPorId(int id) {
        return aulaService.buscarPorId(id);
    }

    // ===== REMOÇÃO =====

    public void remover(int id) {
        aulaService.remover(id);
    }

    // ===== REGRA DE NEGÓCIO =====

    public boolean inscreverAluno(Aluno aluno, Aula aula) {
        if (aluno == null || aula == null) {
            System.out.println("Aluno ou aula inválidos.");
            return false;
        }

        return aulaService.inscrever(aluno, aula);
    }

    public void cancelarInscricao(Aluno aluno, Aula aula) {
        if (aluno == null || aula == null) {
            System.out.println("Aluno ou aula inválidos.");
            return;
        }

        aulaService.cancelar(aluno, aula);
    }

    public void relatorioOcupacao() {
        aulaService.relatorioOcupacao();
    }
}
