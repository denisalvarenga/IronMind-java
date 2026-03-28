package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Aula {

    private int id;
    private String nome;
    private String descricao;
    private int capacidadeMaxima;
    private LocalDateTime horario;
    private int duracaoMinutos;
    private Instrutor instrutor; // novo atributo

    private List<Aluno> alunos = new ArrayList<>();

    // Construtores
    public Aula(String nome, int capacidadeMaxima, LocalDateTime horario) {
        this.nome = nome;
        this.capacidadeMaxima = capacidadeMaxima;
        this.horario = horario;
    }

    public Aula(String nome, String descricao, int capacidadeMaxima, LocalDateTime horario, int duracaoMinutos) {
        this.nome = nome;
        this.descricao = descricao;
        this.capacidadeMaxima = capacidadeMaxima;
        this.horario = horario;
        this.duracaoMinutos = duracaoMinutos;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public void setCapacidadeMaxima(int capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public void setHorario(LocalDateTime horario) {
        this.horario = horario;
    }

    public int getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public void setDuracaoMinutos(int duracaoMinutos) {
        this.duracaoMinutos = duracaoMinutos;
    }

    public Instrutor getInstrutor() {
        return instrutor;
    }

    public void setInstrutor(Instrutor instrutor) {
        this.instrutor = instrutor;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    // Métodos para gerenciar alunos
    public boolean adicionarAluno(Aluno aluno) {
        if (alunos.size() >= capacidadeMaxima) {
            return false;
        }
        alunos.add(aluno);
        return true;
    }

    public boolean removerAluno(Aluno aluno) {
        return alunos.remove(aluno);
    }

    @Override
    public String toString() {
        return "Aula{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", capacidadeMaxima=" + capacidadeMaxima +
                ", horario=" + horario +
                ", duracaoMinutos=" + duracaoMinutos +
                ", instrutor=" + (instrutor != null ? instrutor.getNome() : "Não definido") +
                ", alunosMatriculados=" + alunos.size() +
                '}';
    }
}