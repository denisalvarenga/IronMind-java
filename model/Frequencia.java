package model;

import java.time.LocalDateTime;

public class Frequencia {

    private int id;
    private int alunoId;
    private LocalDateTime dataHora;

    public Frequencia() {
        this.dataHora = LocalDateTime.now();
    }

    public Frequencia(int id, int alunoId, LocalDateTime dataHora) {
        this.id = id;
        this.alunoId = alunoId;
        this.dataHora = dataHora;
    }

    public int getId() {
        return id;
    }

    public int getAlunoId() {
        return alunoId;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAlunoId(int alunoId) {
        this.alunoId = alunoId;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}