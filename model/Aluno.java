package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Aluno {

    private int id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String telefone;
    private String email;
    private LocalDate dataMatricula;
    private Plano plano;

    private List<Aula> aulas = new ArrayList<>();
    private List<Frequencia> frequencias = new ArrayList<>();

    public Aluno(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataMatricula = LocalDate.now();
        this.id = gerarMatricula();
    }

    // Gera matrícula de 4 dígitos
    private int gerarMatricula() {
        Random random = new Random();
        return 1000 + random.nextInt(9000);
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public LocalDate getDataMatricula() {
        return dataMatricula;
    }

    public Plano getPlano() {
        return plano;
    }

    public List<Aula> getAulas() {
        return aulas;
    }

    public List<Frequencia> getFrequencias() {
        return frequencias;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public void adicionarAula(Aula aula) {
        aulas.add(aula);
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEmail(String email) {

        if (email.contains("@gmail.com") ||
                email.contains("@outlook.com") ||
                email.contains("@icloud.com")) {

            this.email = email;
        } else {
            System.out.println("Email inválido. Use gmail, outlook ou icloud.");
        }
    }

    public void registrarEntrada() {
        frequencias.add(new Frequencia());
    }
}