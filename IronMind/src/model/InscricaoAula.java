package model;

//Representa a inscrição do aluno em uma aula (N:N).

public class InscricaoAula {

    private Aluno aluno;
    private Aula aula;

    public InscricaoAula(Aluno aluno, Aula aula) {
        this.aluno = aluno;
        this.aula = aula;
    }

    public Aluno getAluno() { return aluno; }
    public Aula getAula() { return aula; }
}
