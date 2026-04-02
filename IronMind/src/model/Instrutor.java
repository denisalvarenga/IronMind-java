package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidade Instrutor.
 */
public class Instrutor extends Pessoa {

    private String especialidade;
    private String horarioTrabalho; // NOVO
    private List<Aula> aulas = new ArrayList<>();

    public Instrutor(String nome, String cpf, String telefone,
                     String especialidade, String horarioTrabalho) {
        super(nome, cpf, telefone);
        this.especialidade = especialidade;
        this.horarioTrabalho = horarioTrabalho;
    }

    @Override
    public String exibirResumo() {
        return "Instrutor: " + getNome();
    }

    public String getEspecialidade() { return especialidade; }
    public String getHorarioTrabalho() { return horarioTrabalho; }
    public List<Aula> getAulas() { return aulas; }
}