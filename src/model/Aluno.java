package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Entidade Aluno
public class Aluno extends Pessoa {

    private LocalDate dataNascimento;
    private String email;
    private LocalDate dataMatricula;
    private Plano plano;

    private final List<Frequencia> frequencias = new ArrayList<>();
    private final List<InscricaoAula> inscricoes = new ArrayList<>();

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

    // ===== GETTERS SEGUROS =====

    public Plano getPlano() {
        return plano;
    }

    public LocalDate getDataMatricula() {
        return dataMatricula;
    }

    public List<Frequencia> listarFrequencias() {
        return new ArrayList<>(frequencias);
    }

    public List<InscricaoAula> listarInscricoes() {
        return new ArrayList<>(inscricoes);
    }

    // ===== REGRA DE NEGÓCIO (PLANO) =====

    public boolean planoAtivo() {
        LocalDate vencimento = getDataVencimento();
        if (vencimento == null) return false;

        return !LocalDate.now().isAfter(vencimento);
    }

    public LocalDate getDataVencimento() {
        if (plano == null || dataMatricula == null) {
            return null;
        }

        return dataMatricula.plusMonths(plano.getDuracaoMeses());
    }

    // ===== MÉTODOS DE CONTROLE =====

    public int getTotalFrequencias() {
        return frequencias.size();
    }

    public int getTotalInscricoes() {
        return inscricoes.size();
    }

    public LocalDateTime getUltimaVisita() {
        if (frequencias.isEmpty()) return null;

        Frequencia ultima = frequencias.get(frequencias.size() - 1);
        return ultima.getDataHora();
    }

    public void adicionarFrequencia(Frequencia frequencia) {
        if (frequencia != null) {
            frequencias.add(frequencia);
        }
    }

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