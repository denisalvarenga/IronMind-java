package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Entidade Aula
public class Aula {

    private String nome;
    private String descricao;
    private int duracao;
    private LocalDateTime horario;
    private int capacidade;
    private Instrutor instrutor;

    private final List<InscricaoAula> inscricoes = new ArrayList<>();

    public Aula(
            String nome,
            String descricao,
            int duracao,
            LocalDateTime horario,
            int capacidade,
            Instrutor instrutor
    ) {
        this.nome = nome;
        this.descricao = descricao;
        this.duracao = duracao;
        this.horario = horario;
        this.capacidade = capacidade;
        this.instrutor = instrutor;
    }

    // ===== GETTERS =====

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getDuracao() {
        return duracao;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public Instrutor getInstrutor() {
        return instrutor;
    }

    // ===== ENCAPSULAMENTO =====

    // NÃO expõe a lista interna diretamente
    public List<InscricaoAula> listarInscricoes() {
        return new ArrayList<>(inscricoes);
    }

    public int getTotalInscricoes() {
        return inscricoes.size();
    }

    public boolean estaLotada() {
        return inscricoes.size() >= capacidade;
    }

    // ===== CONTROLE DE INSCRIÇÕES =====

    public void adicionarInscricao(InscricaoAula inscricao) {
        if (inscricao != null) {
            inscricoes.add(inscricao);
        }
    }

    public void removerInscricao(InscricaoAula inscricao) {
        if (inscricao != null) {
            inscricoes.remove(inscricao);
        }
    }
}