# 🏋️ IronMind - Sistema de Gerenciamento de Academia

## 📋 Sobre o Projeto

O **IronMind** é um sistema de gerenciamento de academia desenvolvido em Java, com integração ao PostgreSQL via JDBC.

Este projeto foi construído como parte de um trabalho interdisciplinar, integrando conceitos de:

* Programação Orientada a Objetos (POO)
* Banco de Dados Relacional

O sistema permite o gerenciamento completo de alunos, instrutores, planos, aulas e frequência.

---

## 🎯 Objetivos do Projeto

### 🧠 Programação Orientada a Objetos

* Aplicar os 4 pilares da POO
* Desenvolver um sistema completo em Java
* Utilizar boas práticas de desenvolvimento
* Documentar o código com Javadoc

---

### 🗄️ Banco de Dados

* Modelar banco relacional com PostgreSQL
* Implementar relacionamentos (1:N e N:N)
* Desenvolver CRUD completo
* Executar consultas com JOIN

---

### 🔗 Integração

* Conectar Java com PostgreSQL via JDBC
* Persistir objetos no banco de dados
* Sincronizar modelo orientado a objetos com modelo relacional

---

## ⚙️ Funcionalidades

* ✔ CRUD de Alunos
* ✔ CRUD de Instrutores
* ✔ CRUD de Planos
* ✔ CRUD de Aulas
* ✔ Inscrição e cancelamento em aulas
* ✔ Controle de frequência
* ✔ Relatórios de ocupação

---

## 🚨 Regra de Negócio Complexa

O sistema implementa controle de inscrições em aulas com validações:

### 📌 Inscrição de Alunos

* Verificação de plano ativo (com cálculo de vencimento)
* Validação de capacidade da aula
* Verificação de conflito de horário
* Confirmação apenas se todas as condições forem atendidas

---

### 📊 Dados do Aluno

O sistema também calcula:

* Total de visitas
* Última visita
* Quantidade de aulas inscritas
* Status do plano (ativo ou vencido)

---

## 🗄️ Estrutura do Banco de Dados

O sistema utiliza PostgreSQL com as seguintes entidades principais:

* Aluno
* Instrutor
* Plano
* Aula
* Frequência
* Inscrição (relação N:N)

---

## 🧠 Requisitos Técnicos Atendidos

### POO

* ✔ Mínimo de 5 classes
* ✔ Herança
* ✔ Encapsulamento
* ✔ Polimorfismo
* ✔ Interface ou classe abstrata
* ✔ Tratamento de exceções
* ✔ Javadoc

---

### Banco de Dados

* ✔ Mínimo de 5 tabelas
* ✔ Relacionamentos 1:N e N:N
* ✔ CRUD completo
* ✔ Consultas com JOIN
* ✔ Regra de negócio implementada

---

## 🧪 Auditoria do Projeto

Para validar se o projeto atende aos requisitos, utilize o prompt abaixo em uma IA:

```
Você é um auditor técnico de software especializado em Java e PostgreSQL.

Analise TODO o código do projeto e verifique se atende aos requisitos:

- POO (herança, polimorfismo, encapsulamento)
- Banco de dados (CRUD, JOIN, relacionamentos)
- Regra de negócio (inscrição em aula com validações)

⚠️ Regras:
- Não inventar código
- Analisar apenas o que existe
- Ser direto e técnico

Retorne:
- Resumo geral
- Pontos positivos
- Problemas encontrados
- O que está faltando
- Veredito final (OK / Parcial / Incompleto)
```

---

## 🚀 Versão Atual

📦 `v2.1.0-rc2` (Release Candidate)

Esta versão representa a etapa final de ajustes antes da release oficial.

---

## 👨‍💻 Equipe

**Denis Alvarenga**

* Estudante de Análise e Desenvolvimento de Sistemas
* Estudante de Engenharia de Software

**Daniel Fortes Paiva**

* Desenvolvedor de Banco de Dados (PostgreSQL)

