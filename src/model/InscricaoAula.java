package model;

/**
 * Representa a inscrição de um aluno em uma aula (relação N:N).
 *
 * Vincula um aluno a uma aula específica e serve como
 * entidade intermediária no relacionamento muitos-para-muitos.
 */
public class InscricaoAula {

    private Aluno aluno;
    private Aula aula;

    /**
     * Construtor da inscrição.
     *
     * @param aluno aluno inscrito
     * @param aula  aula na qual o aluno está inscrito
     * @throws IllegalArgumentException se aluno ou aula forem nulos
     */
    public InscricaoAula(Aluno aluno, Aula aula) {
        if (aluno == null || aula == null) {
            throw new IllegalArgumentException("Aluno e Aula são obrigatórios para inscrição.");
        }

        this.aluno = aluno;
        this.aula = aula;
    }

    /**
     * Retorna o aluno inscrito.
     *
     * @return aluno
     */
    public Aluno getAluno() {
        return aluno;
    }

    /**
     * Retorna a aula da inscrição.
     *
     * @return aula
     */
    public Aula getAula() {
        return aula;
    }

    /**
     * Exibe resumo da inscrição.
     *
     * @return string com aluno, aula e horário
     */
    public String exibirResumo() {
        return "Aluno: " + aluno.getNome() +
               " | Aula: " + aula.getNome() +
               " | Horário: " + aula.getHorario();
    }
}
