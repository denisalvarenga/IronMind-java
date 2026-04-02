package model;

import java.time.LocalDateTime;

// Entidade Frequência.

public class Frequencia {

    private Aluno aluno;
    private LocalDateTime dataHora;

    public Frequencia(Aluno aluno, LocalDateTime dataHora) {
        this.aluno = aluno;
        this.dataHora = dataHora;
    }

    public LocalDateTime getDataHora() { return dataHora; }
    public Aluno getAluno() { return aluno; }
}