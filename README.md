# 🏋️‍♂️ IronMind SGA — Sistema de Gerenciamento de Academia

## 📌 Visão Geral

O **IronMind SGA** é um sistema de gerenciamento de academia desenvolvido em **Java**, com foco em:

* Programação Orientada a Objetos (POO)
* Arquitetura MVC
* Modelagem de regras de negócio
* Boas práticas (Clean Code)

O sistema permite o gerenciamento de alunos, instrutores, planos, aulas coletivas e controle de frequência, aplicando regras de negócio reais do domínio de academias.

---

## 🚀 Evolução do Projeto

### 🔄 Comparação com a versão anterior (v1.1.1)

| Aspecto           | Versão anterior | Versão atual    |
| ----------------- | --------------- | --------------- |
| Arquitetura MVC   | ❌ Incompleta    | ✔ Estruturada   |
| Controllers       | ❌ Ausentes      | ✔ Implementados |
| Organização       | ❌ Confusa       | ✔ Modular       |
| Regras de negócio | ⚠️ Básicas      | ✔ Mais robustas |
| Encapsulamento    | ❌ Fraco         | ✔ Melhorado     |
| Estrutura geral   | ❌ Inicial       | ✔ Avançada      |

---

## 📈 Progresso Geral do Sistema

### 95% concluído

* ✔ Requisitos atendidos: maior parte implementada
* ⚠️ Pendência principal: integração completa com banco de dados
* ✔ Arquitetura MVC consolidada
* ✔ Regras de negócio principais implementadas

---

## 🧱 Arquitetura do Sistema

O projeto segue o padrão **MVC (Model-View-Controller)**:

* **Model** → Entidades e regras de negócio
* **Service** → Lógica da aplicação
* **Controller** → Intermediação entre View e Service
* **View** → Interface CLI (menus)

### ✔ Melhorias implementadas

* Controllers reais (AlunoController, AulaController, PlanoController, etc.)
* View desacoplada dos Services
* Menu modularizado
* Classe `Pessoa` abstrata
* Separação clara de responsabilidades
* Melhor encapsulamento das entidades
* Centralização das regras principais de negócio

---

## 📋 Requisitos Obrigatórios

### ✔ Sistema de Gerenciamento de Academia

| Requisito                 | Status |
| ------------------------- | ------ |
| Gerenciar alunos          | ✔      |
| Gerenciar instrutores     | ✔      |
| Gerenciar planos          | ✔      |
| Gerenciar aulas coletivas | ✔      |
| Controle de frequência    | ✔      |

---

## 🧠 Regra de Negócio Complexa

### 📌 Inscrição em Aulas

Ao inscrever um aluno em uma aula, o sistema realiza:

* ✔ Verificação de plano ativo
* ✔ Cálculo de vencimento (data matrícula + duração do plano)
* ✔ Bloqueio de inscrição caso o plano esteja vencido
* ✔ Verificação de capacidade da aula
* ✔ Contagem de inscritos
* ✔ Bloqueio caso a aula esteja cheia
* ✔ Confirmação de inscrição
* ✔ Fluxo funcional implementado

### 📌 Consulta de Aluno

Ao visualizar um aluno:

| Informação                    | Status |
| ----------------------------- | ------ |
| Total de visitas              | ✔      |
| Última visita                 | ✔      |
| Quantidade de aulas inscritas | ✔      |
| Status do plano               | ✔      |
| Data de vencimento            | ✔      |

---

## 🏗️ Avaliação de POO

### ✔ Pontos fortes

* Uso correto de herança (`Pessoa → Aluno / Instrutor`)
* Separação de responsabilidades (MVC)
* Controllers reais implementados
* Organização clara do código
* Baixo acoplamento entre camadas
* Melhor distribuição de responsabilidades
* Código mais escalável e de fácil manutenção

### 🧠 Nota geral de POO

**9.0 / 10**

---

## 🗄️ Banco de Dados

### Banco previsto: PostgreSQL

Estrutura planejada:

* Alunos
* Instrutores
* Planos
* Aulas
* Frequência
* InscricaoAula (N:N)

### ⚠️ Observação

A única pendência atual do projeto é a integração completa da camada de banco de dados.

A estrutura DAO foi criada e organizada, porém a implementação completa com PostgreSQL ainda não foi finalizada.

Essa etapa será concluída nas próximas atualizações.

---

## 🖥️ Interface

* Aplicação em linha de comando (CLI)
* Navegação via menus organizados por funcionalidade
* Interface estruturada para uso acadêmico e expansão futura

---

## 👥 Usuários do Sistema

* Funcionários da academia
* Recepcionistas
* Gerentes

---

## 🧾 Conclusão

O projeto evoluiu significativamente e hoje se encontra em um nível avançado e bem estruturado, com:

* Arquitetura MVC consolidada
* Regras de negócio relevantes implementadas
* Código mais organizado e profissional
* Melhor separção de responsabilidades
* Estrutura preparada para crescimento e manutenção

Atualmente, o sistema está funcional e atende praticamente todos os requisitos do projeto.

A única pendência restante é a integração completa com o banco de dados PostgreSQL, que não compromete a estrutura de POO nem a regra de negócio principal do sistema.

---

## 📌 Autor

Desenvolvido por **Denis Alvarenga** como parte de um projeto acadêmico de:

* Programação Orientada a Objetos em Java
* Banco de Dados com PostgreSQL
