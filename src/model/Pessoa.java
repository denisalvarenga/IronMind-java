package model;

/**
 * Classe abstrata base para pessoas do sistema (Aluno e Instrutor).
 *
 * Implementa encapsulamento com atributos privados e getters/setters.
 * Define o método abstrato exibirResumo() — base para polimorfismo.
 * Herança: Aluno e Instrutor estendem esta classe.
 */
public abstract class Pessoa {

    private static int contadorId = 1;

    private int id;
    private String nome;
    private String cpf;
    private String telefone;

    /**
     * Construtor base para subclasses.
     *
     * @param nome      nome da pessoa
     * @param cpf       CPF da pessoa
     * @param telefone  telefone de contato
     */
    public Pessoa(String nome, String cpf, String telefone) {
        this.id = contadorId++;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    // ===== GETTERS =====

    /**
     * Retorna o ID gerado em memória.
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna o nome.
     *
     * @return nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna o CPF.
     *
     * @return cpf
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * Retorna o telefone.
     *
     * @return telefone
     */
    public String getTelefone() {
        return telefone;
    }

    // ===== SETTERS CONTROLADOS =====

    /**
     * Atualiza o nome se válido.
     *
     * @param nome novo nome
     */
    public void setNome(String nome) {
        if (nome != null && !nome.isBlank()) {
            this.nome = nome;
        }
    }

    /**
     * Atualiza o CPF se válido.
     *
     * @param cpf novo CPF
     */
    public void setCpf(String cpf) {
        if (cpf != null && !cpf.isBlank()) {
            this.cpf = cpf;
        }
    }

    /**
     * Atualiza o telefone se válido.
     *
     * @param telefone novo telefone
     */
    public void setTelefone(String telefone) {
        if (telefone != null && !telefone.isBlank()) {
            this.telefone = telefone;
        }
    }

    // ===== POO OBRIGATÓRIA =====

    /**
     * Exibe resumo da pessoa. Implementado diferentemente por Aluno e Instrutor.
     * Base do polimorfismo do sistema.
     *
     * @return resumo textual
     */
    public abstract String exibirResumo();
}
