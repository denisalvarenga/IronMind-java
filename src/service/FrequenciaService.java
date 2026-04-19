package service;

import model.Aluno;
import model.Frequencia;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Service de Frequência.

public class FrequenciaService {

    private final List<Frequencia> frequencias = new ArrayList<>();

    public void registrarEntrada(Aluno aluno) {
        try {
            Frequencia f = new Frequencia(aluno, LocalDateTime.now());
            aluno.adicionarFrequencia(f);
            frequencias.add(f);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public List<Frequencia> listar() {
        return frequencias;
    }

    public void relatorioPeriodo(List<Frequencia> frequencias, LocalDate inicio, LocalDate fim) {

        for (Frequencia f : frequencias) {
            if (!f.getDataHora().toLocalDate().isBefore(inicio) &&
                    !f.getDataHora().toLocalDate().isAfter(fim)) {

                System.out.println(f.getDataHora());
            }
        }
    }
}