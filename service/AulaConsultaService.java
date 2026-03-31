package service;

import model.Aula;
import model.Aluno;

import java.time.LocalDateTime;
import java.util.List;

public class AulaConsultaService {

    // Listar aulas por instrutor
    public void listarPorInstrutor(List<Aula> aulas, String nomeInstrutor) {

        System.out.println("Aulas do instrutor: " + nomeInstrutor);

        boolean encontrou = false;

        for (Aula aula : aulas) {
            if (aula.getInstrutor() != null &&
                    aula.getInstrutor().getNome().equalsIgnoreCase(nomeInstrutor)) {

                System.out.println(aula.getNome() + " - " + aula.getHorario());
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhuma aula encontrada para esse instrutor.");
        }
    }

    // Listar aulas por horário
    public void listarPorHorario(List<Aula> aulas, LocalDateTime horario) {

        System.out.println("Aulas no horário: " + horario);

        boolean encontrou = false;

        for (Aula aula : aulas) {
            if (aula.getHorario().equals(horario)) {
                System.out.println(aula.getNome());
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhuma aula encontrada nesse horário.");
        }
    }

    // Detalhe da aula
    public void detalharAula(Aula aula) {

        System.out.println("===== DETALHE DA AULA =====");

        System.out.println("Nome: " + aula.getNome());
        System.out.println("Horario: " + aula.getHorario());
        System.out.println("Capacidade: " + aula.getCapacidadeMaxima());

        if (aula.getInstrutor() != null) {
            System.out.println("Instrutor: " + aula.getInstrutor().getNome());
        } else {
            System.out.println("Instrutor: Não definido");
        }

        System.out.println("Alunos inscritos:");

        if (aula.getAlunos().isEmpty()) {
            System.out.println("Nenhum aluno inscrito.");
        } else {
            for (Aluno aluno : aula.getAlunos()) {
                System.out.println("- " + aluno.getNome());
            }
        }

        System.out.println("===========================");
    }
}