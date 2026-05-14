package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um aluno da academia.
 *
 * Herda de Pessoa e possui vínculo com plano,
 * frequências registradas e inscrições em aulas.
 */
public class Aluno extends Pessoa {

    private int idBanco;
    private LocalDate dataNascimento;
    private String email;
    private LocalDate dataMatricula;
    private Plano plano;

    private final List<Frequencia> frequencias = new ArrayList<>();
    private final List<InscricaoAula> inscricoes = new ArrayList<>();

    /**
     * Construtor principal do aluno.
     *
     * @param nome nome do aluno
     * @param cpf CPF do aluno
     * @param telefone telefone
     * @param dataNascimento data de nascimento
     * @param email email do aluno
     * @param dataMatricula data de matrícula
     * @param plano plano vinculado
     */
    public Aluno(
            String nome,
            String cpf,
            String telefone,
            LocalDate dataNascimento,
            String email,
            LocalDate dataMatricula,
            Plano plano
    ) {
        super(nome, cpf, telefone);
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.dataMatricula = dataMatricula;
        this.plano = plano;
    }

    /**
     * Exibe resumo completo do aluno.
     *
     * @return resumo textual
     */
    @Override
    public String exibirResumo() {

        String statusPlano = planoAtivo() ? "ATIVO" : "VENCIDO";

        String nomePlano = (plano != null)
                ? plano.getNome()
                : "Sem plano";

        LocalDate vencimento = getDataVencimento();

        String ultimaVisita = "Nenhuma visita registrada";

        LocalDateTime ultima = getUltimaVisita();
        if (ultima != null) {
            ultimaVisita = ultima.toString();
        }

        return
                "Aluno: " + getNome() +
                        "\nCPF: " + getCpf() +
                        "\nTelefone: " + getTelefone() +
                        "\nEmail: " + email +
                        "\nPlano: " + nomePlano +
                        "\nStatus do Plano: " + statusPlano +
                        "\nVencimento do Plano: " + vencimento +
                        "\nTotal de Visitas: " + getTotalFrequencias() +
                        "\nÚltima Visita: " + ultimaVisita +
                        "\nAulas Inscritas: " + getTotalInscricoes();
    }

    /**
     * Define o ID vindo do banco de dados.
     *
     * Usado pelo AlunoDAO após consulta SQL.
     *
     * @param id identificador do banco
     */
    public void setId(int id) {
        this.idBanco = id;
    }

    /**
     * Retorna o ID real do banco, ou o gerado em memória se não definido.
     *
     * @return identificador
     */
    @Override
    public int getId() {
        return (idBanco > 0) ? idBanco : super.getId();
    }

    /**
     * Retorna o plano atual.
     *
     * @return plano do aluno
     */
    public Plano getPlano() {
        return plano;
    }

    /**
     * Atualiza o plano do aluno.
     *
     * @param plano novo plano
     */
    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    /**
     * Retorna a data de matrícula.
     *
     * @return data de matrícula
     */
    public LocalDate getDataMatricula() {
        return dataMatricula;
    }

    /**
     * Atualiza a data de matrícula.
     *
     * @param dataMatricula nova data
     */
    public void setDataMatricula(LocalDate dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    /**
     * Retorna a data de nascimento.
     *
     * @return data de nascimento
     */
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    /**
     * Atualiza a data de nascimento.
     *
     * @param dataNascimento nova data
     */
    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    /**
     * Retorna o email.
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Atualiza o email.
     *
     * @param email novo email
     */
    public void setEmail(String email) {
        if (email != null && !email.isBlank()) {
            this.email = email;
        }
    }

    /**
     * Retorna lista de frequências.
     *
     * @return cópia da lista
     */
    public List<Frequencia> listarFrequencias() {
        return new ArrayList<>(frequencias);
    }

    /**
     * Retorna lista de inscrições.
     *
     * @return cópia da lista
     */
    public List<InscricaoAula> listarInscricoes() {
        return new ArrayList<>(inscricoes);
    }

    /**
     * Verifica se o plano está ativo.
     *
     * @return true se ativo
     */
    public boolean planoAtivo() {
        LocalDate vencimento = getDataVencimento();

        if (vencimento == null) {
            return false;
        }

        return !LocalDate.now().isAfter(vencimento);
    }

    /**
     * Calcula data de vencimento do plano.
     *
     * @return data de vencimento
     */
    public LocalDate getDataVencimento() {
        if (plano == null || dataMatricula == null) {
            return null;
        }

        return dataMatricula.plusMonths(plano.getDuracaoMeses());
    }

    /**
     * Retorna total de frequências.
     *
     * @return quantidade
     */
    public int getTotalFrequencias() {
        return frequencias.size();
    }

    /**
     * Retorna total de inscrições.
     *
     * @return quantidade
     */
    public int getTotalInscricoes() {
        return inscricoes.size();
    }

    /**
     * Retorna última visita registrada.
     *
     * @return data/hora da última visita
     */
    public LocalDateTime getUltimaVisita() {
        if (frequencias.isEmpty()) {
            return null;
        }

        Frequencia ultima = frequencias.get(frequencias.size() - 1);
        return ultima.getDataHora();
    }

    /**
     * Adiciona frequência.
     *
     * @param frequencia frequência registrada
     */
    public void adicionarFrequencia(Frequencia frequencia) {
        if (frequencia != null) {
            frequencias.add(frequencia);
        }
    }

    /**
     * Adiciona inscrição.
     *
     * Evita duplicidade.
     *
     * @param inscricao inscrição da aula
     */
    public void adicionarInscricao(InscricaoAula inscricao) {
        if (inscricao != null && !inscricoes.contains(inscricao)) {
            inscricoes.add(inscricao);
        }
    }

    /**
     * Remove inscrição.
     *
     * @param inscricao inscrição a remover
     */
    public void removerInscricao(InscricaoAula inscricao) {
        if (inscricao != null) {
            inscricoes.remove(inscricao);
        }
    }
}