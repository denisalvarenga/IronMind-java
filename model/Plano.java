package model;

public class Plano {

    private int id;
    private String nome;
    private String descricao;
    private double valorMensal;
    private int duracaoMeses;
    private String beneficios;

    public Plano(String nome, double valorMensal, int duracaoMeses) {
        this.nome = nome;
        this.valorMensal = valorMensal;
        this.duracaoMeses = duracaoMeses;
        definirDescricaoEBeneficios();
    }

    private void definirDescricaoEBeneficios() {

        if (nome.equalsIgnoreCase("standard")) {
            descricao = "Plano básico com acesso a uma aula por dia.";
            beneficios = "1 check-in diário em uma modalidade.";
        }

        if (nome.equalsIgnoreCase("confort")) {
            descricao = "Plano intermediário com maior flexibilidade.";
            beneficios = "Até 2 check-ins diários com intervalo mínimo de 6 horas.";
        }

        if (nome.equalsIgnoreCase("silver")) {
            descricao = "Plano premium com acesso ampliado.";
            beneficios = "Até 3 check-ins diários em aulas diferentes + 1 convidado/mês.";
        }
    }

    public String getNome() {
        return nome;
    }

    public double getValorMensal() {
        return valorMensal;
    }

    public int getDuracaoMeses() {
        return duracaoMeses;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getBeneficios() {
        return beneficios;
    }
}