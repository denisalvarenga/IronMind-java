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
    private Instrutor instrutor;

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

    // Getters
    public String getNome() {
        return nome;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public Instrutor getInstrutor() {
        return instrutor;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    // Setters
    public void setInstrutor(Instrutor instrutor) {
        this.instrutor = instrutor;
    }

    // Regra de negócio: adicionar aluno respeitando capacidade
    public boolean adicionarAluno(Aluno aluno) {

        if (alunos.size() >= capacidadeMaxima) {
            return false;
        }

        alunos.add(aluno);
        return true;
    }
}