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
            System.out.println("Aula cadastrada com sucesso.");
        } else {
            System.out.println("Aula inválida.");
        }
    }

    // ===== LISTAGEM =====

    public void listar() {

        List<Aula> aulas = aulaService.listar();

        if (aulas == null || aulas.isEmpty()) {
            System.out.println("Nenhuma aula cadastrada.");
            return;
        }

        System.out.println("===== LISTA DE AULAS =====");

        for (int i = 0; i < aulas.size(); i++) {
            Aula aula = aulas.get(i);

            if (aula != null) {
                System.out.println("ID: " + (i + 1));
                System.out.println(aula.exibirResumo());
                System.out.println("----------------------------");
            }
        }
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
        Aula aula = aulaService.buscarPorId(id);

        if (aula != null) {
            aulaService.remover(id);
            System.out.println("Aula removida com sucesso.");
        } else {
            System.out.println("Aula não encontrada.");
        }
    }

    // ===== REGRA DE NEGÓCIO =====

    public void inscreverAluno(Aluno aluno, Aula aula) {

        if (aluno == null || aula == null) {
            System.out.println("Aluno ou aula inválidos.");
            return;
        }

        String resposta = aulaService.inscrever(aluno, aula);
        System.out.println(resposta);
    }

    public void cancelarInscricao(Aluno aluno, Aula aula) {

        if (aluno == null || aula == null) {
            System.out.println("Aluno ou aula inválidos.");
            return;
        }

        String resposta = aulaService.cancelar(aluno, aula);
        System.out.println(resposta);
    }

    // ===== RELATÓRIO DE OCUPAÇÃO =====

    public void relatorioOcupacao() {

        List<Aula> aulas = aulaService.listar();

        if (aulas == null || aulas.isEmpty()) {
            System.out.println("Nenhuma aula cadastrada.");
            return;
        }

        System.out.println("===== RELATÓRIO DE OCUPAÇÃO =====");

        for (Aula aula : aulas) {
            if (aula != null) {
                System.out.println(
                        aula.getNome()
                                + " -> "
                                + aula.getTotalInscricoes()
                                + "/"
                                + aula.getCapacidade()
                );
            }
        }
    }
}