package model;

import java.time.LocalDateTime;

// Entidade Frequência.
public class Frequencia {

    private Aluno aluno;
    private LocalDateTime dataHora;

    public Frequencia(Aluno aluno, LocalDateTime dataHora) {
        if (aluno == null || dataHora == null) {
            throw new IllegalArgumentException("Aluno e data/hora são obrigatórios.");
        }

        this.aluno = aluno;
        this.dataHora = dataHora;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public Aluno getAluno() {
        return aluno;
    }

    // ===== APOIO PARA VIEW/CONTROLLER =====

    public String exibirResumo() {
        return
                "Aluno: " + aluno.getNome() +
                        " | Data/Hora: " + dataHora;
    }
}