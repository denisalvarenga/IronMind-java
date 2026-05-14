package controller;

import model.Frequencia;
import service.FrequenciaService;

import java.util.List;

/**
 * Controller responsável pelo gerenciamento de frequências.
 *
 * Faz a comunicação entre View (Menu) e Service.
 */
public class FrequenciaController {

    private final FrequenciaService frequenciaService;

    /**
     * Construtor padrão.
     */
    public FrequenciaController() {
        this.frequenciaService = new FrequenciaService();
    }

    /**
     * Registra a entrada de um aluno.
     *
     * @param frequencia frequência a ser registrada
     * @return true se registrada com sucesso
     */
    public boolean registrar(Frequencia frequencia) {

        if (frequencia != null && frequencia.getAluno() != null) {
            frequenciaService.registrarEntrada(frequencia.getAluno());
            return true;
        } else {
            System.out.println("Frequência inválida.");
            return false;
        }
    }

    /**
     * Lista todas as frequências em memória.
     *
     * @return lista de frequências
     */
    public List<Frequencia> listar() {
        return frequenciaService.listar();
    }

    /**
     * Lista frequências persistidas no banco.
     *
     * @return lista de frequências do banco
     */
    public List<Frequencia> listarDoBanco() {
        return frequenciaService.listarDoBanco();
    }
}
