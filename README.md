# 🏋️‍♂️ IronMind SGA — Sistema de Gerenciamento de Academia

## 📌 Visão Geral

O **IronMind SGA** é um sistema de gerenciamento de academia desenvolvido em Java, com foco em:

* Programação Orientada a Objetos (POO)
* Arquitetura MVC
* Modelagem de regras de negócio
* Boas práticas (Clean Code)

O sistema permite gerenciar alunos, instrutores, planos, aulas coletivas e controle de frequência, além de aplicar regras de negócio reais do domínio de academias.

---

## 🚀 Evolução do Projeto

### 🔄 Comparação com a versão anterior (v1.1.1)

| Aspecto           | Versão anterior | Versão atual    |
| ----------------- | --------------- | --------------- |
| Arquitetura MVC   | ❌ Incompleta    | ✔ Estruturada   |
| Controllers       | ❌ Ausentes      | ✔ Implementados |
| Organização       | ❌ Confusa       | ✔ Modular       |
| Regras de negócio | ⚠️ Básicas      | ✔ Mais robustas |
| Encapsulamento    | ❌ Fraco         | ⚠️ Parcial      |
| Estrutura geral   | ❌ Inicial       | ✔ Intermediária |

### 📈 Progresso geral do sistema

**84% concluído**

* ✔ Requisitos atendidos: 26
* ❌ Não atendidos: 5
* ⚠️ Parciais: 4

---

## 🧱 Arquitetura do Sistema

O projeto segue o padrão **MVC (Model-View-Controller)**:

* **Model** → Entidades e regras de negócio
* **Service** → Lógica da aplicação
* **Controller** → Intermediação entre View e Service
* **View** → Interface CLI (menus)

### ✔ Melhorias implementadas

* Controllers reais (AlunoController, AulaController, PlanoController)
* View desacoplada dos Services
* Menu modularizado
* Classe Pessoa abstrata
* Separação clara de responsabilidades

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

Ao inscrever um aluno em uma aula, o sistema deve:

#### ✔ Verificação de plano ativo

* ✔ Cálculo de vencimento (data matrícula + duração)
* ✔ Bloqueio de inscrição se vencido
* ⚠️ Exibição da data pode melhorar

#### ✔ Verificação de capacidade da aula

* ✔ Contagem de inscritos
* ✔ Bloqueio se aula cheia
* ⚠️ Mensagens não padronizadas

#### ❌ Verificação de conflito de horário

* ❌ Comparação de horários não implementada corretamente
* ❌ Não informa qual aula está em conflito

#### ✔ Confirmação de inscrição

* ✔ Fluxo funcional implementado

---

## 📊 Consulta de Aluno

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

* Uso correto de herança (Pessoa → Aluno/Instrutor)
* Separação de responsabilidades (MVC)
* Organização clara do código
* Baixo acoplamento entre camadas

### ⚠️ Pontos de melhoria

* Encapsulamento ainda parcial (exposição de listas)
* Regras de negócio parcialmente espalhadas
* Domínio ainda pode ser mais rico

### 🧠 Nota geral de POO

**7.8 / 10**

---

## ⚠️ Problemas Identificados

### 🔴 Críticos

* Falta de verificação de conflito de horário (requisito obrigatório não concluído)

### 🟡 Moderados

* Encapsulamento incompleto
* Regras de plano parcialmente duplicadas
* Falta de padronização de mensagens

---

## 🎯 O que falta para 100%

* Implementar verificação de conflito de horário entre aulas
* Centralizar completamente regras de negócio no domínio
* Melhorar encapsulamento (não expor listas diretamente)
* Padronizar mensagens do sistema

---

## 🗄️ Banco de Dados

* Banco: **PostgreSQL**
* Estrutura prevista:

  * Alunos
  * Instrutores
  * Planos
  * Aulas
  * Frequência
  * InscricaoAula (N:N)

⚠️ Observação: camada DAO ainda não implementada completamente (fora do escopo atual)

---

## 🖥️ Interface

* Aplicação em **linha de comando (CLI)**
* Navegação via menus organizados por funcionalidade

---

## 👥 Usuários do Sistema

* Funcionários da academia
* Recepcionistas
* Gerentes

---

## 🧾 Conclusão

O projeto evoluiu significativamente e já se encontra em um nível **intermediário sólido**, com:

* Arquitetura bem definida
* Regras de negócio relevantes implementadas
* Código organizado e mais profissional

🔧 Ainda não está 100% concluído devido a um ponto crítico na regra de negócio (conflito de horário), mas está muito próximo de um nível pronto para uso real.

---

## 📌 Autor

Desenvolvido por **Denis Alvarenga** como parte de um projeto acadêmico de:

* Programação Orientada a Objetos em Java
* Banco de Dados com PostgreSQL

---
