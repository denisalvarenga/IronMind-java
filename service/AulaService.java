package service;

import model.Aluno;
import model.Aula;

import java.time.LocalDate;

public class AulaService {

    private PlanoService planoService = new PlanoService();

    // INSCRIÇÃO COMPLETA (REGRA DE NEGÓCIO)
    public void inscreverAluno(Aluno aluno, Aula aula) {

        // 1. Verificar plano
        if (aluno.getPlano() == null) {
            System.out.println("Aluno não possui plano.");
            return;
        }

        // 2. Verificar se plano está ativo
        if (!planoService.planoAtivo(aluno)) {

            LocalDate vencimento = planoService.calcularVencimento(aluno);

            System.out.println("Plano vencido em: " + vencimento);
            return;
        }

        // 3. Verificar conflito de horário
        for (Aula a : aluno.getAulas()) {

            if (a.getHorario().equals(aula.getHorario())) {
                System.out.println("Conflito de horário com a aula: " + a.getNome());
                return;
            }
        }

        // 4. Verificar capacidade
        if (aula.getAlunos().size() >= aula.getCapacidadeMaxima()) {
            System.out.println("Aula lotada.");
            return;
        }

        // 5. Verificar se já está inscrito
        if (aula.getAlunos().contains(aluno)) {
            System.out.println("Aluno já está inscrito nesta aula.");
            return;
        }

        // 6. Inscrever (mantendo consistência)
        aula.getAlunos().add(aluno);
        aluno.getAulas().add(aula);

        System.out.println("Inscrição realizada com sucesso!");
    }

    // CANCELAR INSCRIÇÃO
    public void cancelarInscricao(Aluno aluno, Aula aula) {

        if (!aula.getAlunos().contains(aluno)) {
            System.out.println("Aluno não está inscrito nesta aula.");
            return;
        }

        aula.getAlunos().remove(aluno);
        aluno.getAulas().remove(aula);

        System.out.println("Inscrição cancelada com sucesso.");
    }
}