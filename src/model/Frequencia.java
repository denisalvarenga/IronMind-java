package model;

import java.time.LocalDateTime;

/**
 * Representa o registro de uma frequência (visita) de um aluno.
 *
 * Cada frequência é vinculada a um aluno e possui data/hora da entrada.
 */
public class Frequencia {

    private Aluno aluno;
    private LocalDateTime dataHora;

    /**
     * Construtor da frequência.
     *
     * @param aluno   aluno que realizou a visita
     * @param dataHora data e hora da entrada
     * @throws IllegalArgumentException se aluno ou dataHora forem nulos
     */
    public Frequencia(Aluno aluno, LocalDateTime dataHora) {
        if (aluno == null || dataHora == null) {
            throw new IllegalArgumentException("Aluno e data/hora são obrigatórios.");
        }

        this.aluno = aluno;
        this.dataHora = dataHora;
    }

    /**
     * Retorna a data/hora da visita.
     *
     * @return data e hora
     */
    public LocalDateTime getDataHora() {
        return dataHora;
    }

    /**
     * Retorna o aluno vinculado.
     *
     * @return aluno
     */
    public Aluno getAluno() {
        return aluno;
    }

    /**
     * Exibe resumo da frequência.
     *
     * @return string com aluno e data/hora
     */
    public String exibirResumo() {
        return "Aluno: " + aluno.getNome() + " | Data/Hora: " + dataHora;
    }
}
