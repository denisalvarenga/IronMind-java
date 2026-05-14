package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma aula da academia.
 *
 * Cada aula possui horário, capacidade,
 * instrutor responsável e controle de inscrições.
 */
public class Aula {

    private int id;
    private String nome;
    private String descricao;
    private int duracao;
    private LocalDateTime horario;
    private int capacidade;
    private Instrutor instrutor;

    private final List<InscricaoAula> inscricoes = new ArrayList<>();

    /**
     * Construtor principal da aula.
     *
     * @param nome nome da aula
     * @param descricao descrição da aula
     * @param duracao duração em minutos
     * @param horario horário da aula
     * @param capacidade capacidade máxima
     * @param instrutor instrutor responsável
     */
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

    /**
     * Retorna o ID da aula (vindo do banco).
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Define o ID da aula. Usado pelo DAO.
     *
     * @param id identificador
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna o nome da aula.
     *
     * @return nome da aula
     */
    public String getNome() {
        return nome;
    }

    /**
     * Atualiza o nome da aula.
     *
     * @param nome novo nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna a descrição da aula.
     *
     * @return descrição
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Atualiza a descrição da aula.
     *
     * @param descricao nova descrição
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna a duração da aula.
     *
     * @return duração em minutos
     */
    public int getDuracao() {
        return duracao;
    }

    /**
     * Atualiza a duração da aula.
     *
     * @param duracao nova duração
     */
    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    /**
     * Retorna o horário da aula.
     *
     * @return horário
     */
    public LocalDateTime getHorario() {
        return horario;
    }

    /**
     * Atualiza o horário da aula.
     *
     * @param horario novo horário
     */
    public void setHorario(LocalDateTime horario) {
        this.horario = horario;
    }

    /**
     * Retorna a capacidade máxima.
     *
     * @return capacidade
     */
    public int getCapacidade() {
        return capacidade;
    }

    /**
     * Atualiza a capacidade máxima.
     *
     * @param capacidade nova capacidade
     */
    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    /**
     * Retorna o instrutor responsável.
     *
     * @return instrutor
     */
    public Instrutor getInstrutor() {
        return instrutor;
    }

    /**
     * Atualiza o instrutor responsável.
     *
     * @param instrutor novo instrutor
     */
    public void setInstrutor(Instrutor instrutor) {
        this.instrutor = instrutor;
    }

    /**
     * Retorna uma cópia da lista de inscrições.
     *
     * Não expõe a lista interna diretamente.
     *
     * @return lista de inscrições
     */
    public List<InscricaoAula> listarInscricoes() {
        return new ArrayList<>(inscricoes);
    }

    /**
     * Retorna o total de inscritos.
     *
     * @return quantidade de inscritos
     */
    public int getTotalInscricoes() {
        return inscricoes.size();
    }

    /**
     * Verifica se a aula está lotada.
     *
     * @return true se estiver lotada
     */
    public boolean estaLotada() {
        return inscricoes.size() >= capacidade;
    }

    /**
     * Adiciona uma inscrição.
     *
     * @param inscricao inscrição a ser adicionada
     */
    public void adicionarInscricao(InscricaoAula inscricao) {
        if (inscricao != null && !inscricoes.contains(inscricao)) {
            inscricoes.add(inscricao);
        }
    }

    /**
     * Remove uma inscrição.
     *
     * @param inscricao inscrição a ser removida
     */
    public void removerInscricao(InscricaoAula inscricao) {
        if (inscricao != null) {
            inscricoes.remove(inscricao);
        }
    }

    /**
     * Exibe um resumo formatado da aula.
     *
     * Método auxiliar para View e Controller.
     *
     * @return resumo textual da aula
     */
    public String exibirResumo() {
        return
                "Aula: " + nome +
                        "\nDescrição: " + descricao +
                        "\nInstrutor: " +
                        (instrutor != null ? instrutor.getNome() : "Não definido") +
                        "\nHorário: " + horario +
                        "\nDuração: " + duracao + " min" +
                        "\nCapacidade: " + capacidade +
                        "\nInscritos: " + getTotalInscricoes() +
                        "\nStatus: " +
                        (estaLotada() ? "LOTADA" : "DISPONÍVEL");
    }
}