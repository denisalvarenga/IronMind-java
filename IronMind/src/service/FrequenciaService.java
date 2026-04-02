package service;

import model.Aluno;
import model.Frequencia;

import java.time.LocalDate;
import java.time.LocalDateTime;

// Service de Frequência.

public class FrequenciaService {

    public void registrarEntrada(Aluno aluno) {
        try {
            Frequencia f = new Frequencia(aluno, LocalDateTime.now());
            aluno.adicionarFrequencia(f);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    // Relatório por período.

    public void relatorioPeriodo(Aluno aluno, LocalDate inicio, LocalDate fim) {

        for (Frequencia f : aluno.getFrequencias()) {
            if (!f.getDataHora().toLocalDate().isBefore(inicio) &&
                    !f.getDataHora().toLocalDate().isAfter(fim)) {

                System.out.println(f.getDataHora());
            }
        }
    }
}