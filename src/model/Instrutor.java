package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidade Instrutor.
 */
public class Instrutor extends Pessoa {

    private String especialidade;
    private String horarioTrabalho;
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

    //  NÃO expõe lista interna
    public List<Aula> listarAulas() {
        return new ArrayList<>(aulas);
    }

    public int getTotalAulas() {
        return aulas.size();
    }

    public void adicionarAula(Aula aula) {
        aulas.add(aula);
    }

    public void removerAula(Aula aula) {
        aulas.remove(aula);
    }
}