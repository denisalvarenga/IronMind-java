package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//Entidade Aluno.

public class Aluno extends Pessoa {

    private LocalDate dataNascimento;
    private String email;
    private LocalDate dataMatricula;
    private Plano plano;

    private List<Frequencia> frequencias = new ArrayList<>();
    private List<InscricaoAula> inscricoes = new ArrayList<>();

    public Aluno(String nome, String cpf, String telefone,
                 LocalDate dataNascimento, String email,
                 LocalDate dataMatricula, Plano plano) {
        super(nome, cpf, telefone);
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.dataMatricula = dataMatricula;
        this.plano = plano;
    }
    @Override
    public String exibirResumo() {
        return "Aluno: " + getNome();
    }
    public Plano getPlano() { return plano; }
    public LocalDate getDataMatricula() { return dataMatricula; }
    public List<Frequencia> getFrequencias() { return frequencias; }
    public List<InscricaoAula> getInscricoes() { return inscricoes; }

    public void adicionarFrequencia(Frequencia f) {
        frequencias.add(f);
    }

    public void adicionarInscricao(InscricaoAula i) {
        inscricoes.add(i);
    }

    public void removerInscricao(InscricaoAula i) {
        inscricoes.remove(i);
    }
}