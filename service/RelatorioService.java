package service;

import model.Aluno;

public class RelatorioService {

    private PlanoService planoService = new PlanoService();

    public void gerarRelatorioAluno(Aluno aluno) {

        System.out.println("========== RELATÓRIO DO ALUNO ==========");

        System.out.println("Nome: " + aluno.getNome());
        System.out.println("CPF: " + aluno.getCpf());

        if (aluno.getPlano() != null) {

            boolean ativo = planoService.planoAtivo(aluno);

            System.out.println("Plano: " + aluno.getPlano().getNome());
            System.out.println("Status: " + (ativo ? "ATIVO " : "VENCIDO "));
            System.out.println("Vencimento: " + planoService.calcularVencimento(aluno));

        } else {
            System.out.println("Plano: Nenhum");
        }

        System.out.println("Total de aulas inscritas: " + aluno.getAulas().size());

        System.out.println("========================================");
    }
}