package model;

import java.util.ArrayList;
import java.util.List;

public class Plano {

    private String nome;
    private String descricao;
    private double valorMensal;
    private String beneficios;
    private int duracaoMeses; // AGORA É MESES DE VERDADE

    private List<Aluno> alunos = new ArrayList<>();

    public Plano(String nome, String descricao, double valorMensal,
                 String beneficios, int duracaoMeses) {

        this.nome = nome;
        this.descricao = descricao;
        this.valorMensal = valorMensal;
        this.beneficios = beneficios;
        this.duracaoMeses = duracaoMeses;
    }

    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public double getValorMensal() { return valorMensal; }
    public String getBeneficios() { return beneficios; }
    public int getDuracaoMeses() { return duracaoMeses; }

    public List<Aluno> getAlunos() { return alunos; }

    public void adicionarAluno(Aluno aluno) {
        alunos.add(aluno);
    }
}