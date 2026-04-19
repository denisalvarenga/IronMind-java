package model;

public abstract class Pessoa {

    private static int contadorId = 1;

    private int id;
    private String nome;
    private String cpf;
    private String telefone;

    public Pessoa(String nome, String cpf, String telefone) {
        this.id = contadorId++; // gera ID automático
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    // ===== GETTERS =====

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    // ===== SETTERS CONTROLADOS =====

    public void setNome(String nome) {
        if (nome != null && !nome.isBlank()) {
            this.nome = nome;
        }
    }

    public void setCpf(String cpf) {
        if (cpf != null && !cpf.isBlank()) {
            this.cpf = cpf;
        }
    }

    public void setTelefone(String telefone) {
        if (telefone != null && !telefone.isBlank()) {
            this.telefone = telefone;
        }
    }

    // ===== POO OBRIGATÓRIA =====

    public abstract String exibirResumo();
}