package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa um instrutor da academia.
 *
 * Herda de Pessoa e possui especialidade,
 * horário de trabalho e aulas vinculadas.
 */
public class Instrutor extends Pessoa {

    private int idBanco;
    private String especialidade;
    private String horarioTrabalho;
    private final List<Aula> aulas = new ArrayList<>();

    /**
     * Construtor principal do instrutor.
     *
     * @param nome nome do instrutor
     * @param cpf CPF do instrutor
     * @param telefone telefone de contato
     * @param especialidade área de especialidade
     * @param horarioTrabalho horário de trabalho
     */
    public Instrutor(String nome, String cpf, String telefone,
                     String especialidade, String horarioTrabalho) {
        super(nome, cpf, telefone);
        this.especialidade = especialidade;
        this.horarioTrabalho = horarioTrabalho;
    }

    /**
     * Exibe um resumo simples do instrutor.
     *
     * @return resumo textual
     */
    @Override
    public String exibirResumo() {
        return "Instrutor: " + getNome();
    }

    /**
     * Define o ID do instrutor. Usado pelo DAO ao carregar do banco.
     *
     * @param id identificador
     */
    public void setId(int id) {
        // id é controlado pela superclasse via contadorId, mas precisamos sobrescrever para o DAO
        // Usamos reflexão via campo direto não é acadêmico — usamos um campo auxiliar aqui
        this.idBanco = id;
    }

    /**
     * Retorna o ID real do banco (sobrescreve o gerado em memória).
     *
     * @return id do banco, ou o id gerado se não definido
     */
    @Override
    public int getId() {
        return (idBanco > 0) ? idBanco : super.getId();
    }

    /**
     * Retorna a especialidade do instrutor.
     *
     * @return especialidade
     */
    public String getEspecialidade() {
        return especialidade;
    }

    /**
     * Atualiza a especialidade do instrutor.
     *
     * @param especialidade nova especialidade
     */
    public void setEspecialidade(String especialidade) {
        if (especialidade != null && !especialidade.isBlank()) {
            this.especialidade = especialidade;
        }
    }

    /**
     * Retorna o horário de trabalho.
     *
     * @return horário de trabalho
     */
    public String getHorarioTrabalho() {
        return horarioTrabalho;
    }

    /**
     * Atualiza o horário de trabalho.
     *
     * @param horarioTrabalho novo horário
     */
    public void setHorarioTrabalho(String horarioTrabalho) {
        if (horarioTrabalho != null && !horarioTrabalho.isBlank()) {
            this.horarioTrabalho = horarioTrabalho;
        }
    }

    /**
     * Retorna uma cópia da lista de aulas.
     *
     * Não expõe a lista interna diretamente.
     *
     * @return lista de aulas
     */
    public List<Aula> listarAulas() {
        return new ArrayList<>(aulas);
    }

    /**
     * Retorna o total de aulas vinculadas.
     *
     * @return quantidade de aulas
     */
    public int getTotalAulas() {
        return aulas.size();
    }

    /**
     * Adiciona uma aula ao instrutor.
     *
     * Evita duplicidade.
     *
     * @param aula aula a ser adicionada
     */
    public void adicionarAula(Aula aula) {
        if (aula != null && !aulas.contains(aula)) {
            aulas.add(aula);
        }
    }

    /**
     * Remove uma aula do instrutor.
     *
     * @param aula aula a ser removida
     */
    public void removerAula(Aula aula) {
        if (aula != null) {
            aulas.remove(aula);
        }
    }
}