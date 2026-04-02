package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Entidade Aula.

public class Aula {

    private String nome;
    private String descricao; // NOVO
    private int duracao; // em minutos - NOVO
    private LocalDateTime horario;
    private int capacidade;
    private Instrutor instrutor;

    private List<InscricaoAula> inscricoes = new ArrayList<>();

    public Aula(String nome, String descricao, int duracao,
                LocalDateTime horario, int capacidade, Instrutor instrutor) {

        this.nome = nome;
        this.descricao = descricao;
        this.duracao = duracao;
        this.horario = horario;
        this.capacidade = capacidade;
        this.instrutor = instrutor;
    }

    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public int getDuracao() { return duracao; }
    public LocalDateTime getHorario() { return horario; }
    public int getCapacidade() { return capacidade; }
    public Instrutor getInstrutor() { return instrutor; }
    public List<InscricaoAula> getInscricoes() { return inscricoes; }

    public boolean estaLotada() {
        return inscricoes.size() >= capacidade;
    }
}