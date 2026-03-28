package service;

import model.Aluno;
import model.Aula;

public class AulaService {

    private PlanoService planoService = new PlanoService();

    // Verifica conflito de horário
    private boolean temConflitoHorario(Aluno aluno, Aula novaAula) {

        for (Aula aula : aluno.getAulas()) {

            if (aula.getHorario().equals(novaAula.getHorario())) {
                System.out.println(" Conflito de horário com a aula: " + aula.getNome());
                return true;
            }
        }

        return false;
    }

    // Método principal
    public boolean inscreverAluno(Aluno aluno, Aula aula) {

        // 1. Plano ativo
        if (!planoService.planoAtivo(aluno)) {
            System.out.println(" Plano vencido!");
            return false;
        }

        // 2. Capacidade
        if (aula.getAlunos().size() >= aula.getCapacidadeMaxima()) {
            System.out.println(" Aula lotada!");
            return false;
        }

        // 3. Conflito de horário
        if (temConflitoHorario(aluno, aula)) {
            return false;
        }

        // 4. Inscrever
        boolean sucesso = aula.adicionarAluno(aluno);

        if (sucesso) {
            aluno.adicionarAula(aula);
            System.out.println(" Inscrição realizada!");
        }

        return sucesso;
    }
}