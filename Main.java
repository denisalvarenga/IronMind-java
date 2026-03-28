import model.Aluno;
import model.Plano;
import model.Aula;
import model.Instrutor;
import service.AulaService;
import service.RelatorioService;
import service.FrequenciaService;
import view.Menu;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {

        System.out.println(" IRONMIND SYSTEM STARTING...");

        // Criar aluno
        Aluno aluno = new Aluno("Denis", "12345678900");

        // Criar plano
        Plano plano = new Plano("Mensal", 99.90, 1);
        aluno.setPlano(plano);

        //  REGISTRAR FREQUÊNCIA
        FrequenciaService freqService = new FrequenciaService();
        freqService.registrarEntrada(aluno);
        freqService.registrarEntrada(aluno);

        // Criar instrutor
        Instrutor instrutor = new Instrutor();
        instrutor.setNome("Carlos");
        instrutor.setEspecialidade("Musculação");

        System.out.println("Instrutor criado: " + instrutor.getNome());

        // Criar horário
        LocalDateTime horario = LocalDateTime.now();

        // Criar aulas (agora com instrutor)
        Aula aula1 = new Aula("Musculação", 2, horario);
        Aula aula2 = new Aula("Crossfit", 2, horario);

        // Se sua classe Aula tiver setInstrutor (recomendado)
        aula1.setInstrutor(instrutor);
        aula2.setInstrutor(instrutor);

        // Serviço de aula
        AulaService aulaService = new AulaService();

        // Teste de inscrição
        aulaService.inscreverAluno(aluno, aula1);
        aulaService.inscreverAluno(aluno, aula2);

        // Relatório
        RelatorioService relatorio = new RelatorioService();
        relatorio.gerarRelatorioAluno(aluno);

        // Menu
        Menu menu = new Menu();
        menu.exibir();
    }
}