package model;

import java.time.LocalDateTime;

public class Frequencia {

    private int id;
    private LocalDateTime dataHora;

    public Frequencia() {
        this.dataHora = LocalDateTime.now();
    }

    public Frequencia(int id, LocalDateTime dataHora) {
        this.id = id;
        this.dataHora = dataHora;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}