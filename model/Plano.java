package model;

public class Plano {

    private int id;
    private String nome;
    private String descricao;
    private double valorMensal;
    private int duracaoMeses;

    // Construtor
    public Plano(String nome, double valorMensal, int duracaoMeses) {
        this.nome = nome;
        this.valorMensal = valorMensal;
        this.duracaoMeses = duracaoMeses;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public double getValorMensal() {
        return valorMensal;
    }

    public int getDuracaoMeses() {
        return duracaoMeses;
    }
}