package model;

// Representa a inscrição do aluno em uma aula (N:N).
public class InscricaoAula {

    private Aluno aluno;
    private Aula aula;

    public InscricaoAula(Aluno aluno, Aula aula) {
        if (aluno == null || aula == null) {
            throw new IllegalArgumentException("Aluno e Aula são obrigatórios para inscrição.");
        }

        this.aluno = aluno;
        this.aula = aula;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public Aula getAula() {
        return aula;
    }

    // ===== APOIO PARA VIEW/CONTROLLER =====

    public String exibirResumo() {
        return
                "Aluno: " + aluno.getNome() +
                        " | Aula: " + aula.getNome() +
                        " | Horário: " + aula.getHorario();
    }
}
