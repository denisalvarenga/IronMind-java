# IronMind - Projeto Java POO

## Descrição
Sistema de gerenciamento de academia em Java, utilizando POO.  
Esta é a primeira etapa do projeto, apenas o back-end está implementado.

## Estrutura do projeto
- `model/` - classes do modelo (Aluno, Aula, Plano, Instrutor)
- `service/` - classes de serviço (AulaService, RelatorioService, FrequenciaService)
- `view/` - classes de interface (menu de console)
- `Main.java` - classe principal para testar funcionalidades

## Como rodar
1. Ter Java JDK instalado
2. Abrir terminal na pasta `src`
3. Compilar:
```bash
javac model\*.java service\*.java view\*.java Main.java
Rodar:
java Main
Próximas etapas
Integração com PostgreSQL
Frontend (JavaFX ou Web)
