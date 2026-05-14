package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa um plano de academia.
 *
 * Cada plano possui informações de mensalidade,
 * duração, benefícios e alunos vinculados.
 */
public class Plano {

    private int id;
    private String nome;
    private String descricao;
    private double valorMensal;
    private String beneficios;
    private int duracaoMeses;

    private final List<Aluno> alunos = new ArrayList<>();

    /**
     * Construtor principal para criação manual.
     *
     * Usado normalmente quando o plano ainda não veio do banco.
     *
     * @param nome nome do plano
     * @param descricao descrição do plano
     * @param valorMensal valor mensal
     * @param beneficios benefícios inclusos
     * @param duracaoMeses duração em meses
     */
    public Plano(String nome, String descricao, double valorMensal,
                 String beneficios, int duracaoMeses) {

        this.nome = nome;
        this.descricao = descricao;
        this.valorMensal = valorMensal;
        this.beneficios = beneficios;
        this.duracaoMeses = duracaoMeses;
    }

    /**
     * Construtor usado pelo DAO para objetos vindos do banco.
     *
     * @param id identificador do plano
     * @param nome nome do plano
     * @param descricao descrição
     * @param valorMensal valor mensal
     * @param beneficios benefícios
     * @param duracaoMeses duração em meses
     */
    public Plano(int id, String nome, String descricao,
                 double valorMensal, String beneficios,
                 int duracaoMeses) {

        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.valorMensal = valorMensal;
        this.beneficios = beneficios;
        this.duracaoMeses = duracaoMeses;
    }

    /**
     * Retorna o ID do plano.
     *
     * @return identificador
     */
    public int getId() {
        return id;
    }

    /**
     * Define o ID do plano.
     *
     * Usado principalmente pelo DAO.
     *
     * @param id identificador
     */
    public void setId(int id) {
        if (id > 0) {
            this.id = id;
        }
    }

    /**
     * Retorna o nome do plano.
     *
     * @return nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Atualiza o nome do plano.
     *
     * @param nome novo nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna a descrição.
     *
     * @return descrição
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Atualiza a descrição.
     *
     * @param descricao nova descrição
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna o valor mensal.
     *
     * @return valor mensal
     */
    public double getValorMensal() {
        return valorMensal;
    }

    /**
     * Atualiza o valor mensal.
     *
     * @param valorMensal novo valor
     */
    public void setValorMensal(double valorMensal) {
        this.valorMensal = valorMensal;
    }

    /**
     * Retorna os benefícios.
     *
     * @return benefícios
     */
    public String getBeneficios() {
        return beneficios;
    }

    /**
     * Atualiza os benefícios.
     *
     * @param beneficios novos benefícios
     */
    public void setBeneficios(String beneficios) {
        this.beneficios = beneficios;
    }

    /**
     * Retorna a duração em meses.
     *
     * @return duração
     */
    public int getDuracaoMeses() {
        return duracaoMeses;
    }

    /**
     * Atualiza a duração.
     *
     * @param duracaoMeses nova duração
     */
    public void setDuracaoMeses(int duracaoMeses) {
        this.duracaoMeses = duracaoMeses;
    }

    /**
     * Retorna cópia da lista de alunos.
     *
     * @return lista de alunos
     */
    public List<Aluno> listarAlunos() {
        return new ArrayList<>(alunos);
    }

    /**
     * Retorna total de alunos.
     *
     * @return quantidade
     */
    public int getTotalAlunos() {
        return alunos.size();
    }

    /**
     * Adiciona aluno ao plano.
     *
     * @param aluno aluno
     */
    public void adicionarAluno(Aluno aluno) {
        if (aluno != null && !alunos.contains(aluno)) {
            alunos.add(aluno);
        }
    }

    /**
     * Remove aluno do plano.
     *
     * @param aluno aluno
     */
    public void removerAluno(Aluno aluno) {
        if (aluno != null) {
            alunos.remove(aluno);
        }
    }

    /**
     * Exibe resumo formatado.
     *
     * @return resumo textual
     */
    public String exibirResumo() {
        return
                "Plano: " + nome +
                        "\nDescrição: " + descricao +
                        "\nValor Mensal: R$ " + valorMensal +
                        "\nDuração: " + duracaoMeses + " meses" +
                        "\nBenefícios: " + beneficios +
                        "\nTotal de Alunos: " + getTotalAlunos();
    }
}