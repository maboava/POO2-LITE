# 🧠 Avaliação Bimestral – Programação Orientada a Objetos I  
### 📚 2º Bimestre – Sistema de Cadastro de Produtos (CRUD em Java Swing)

---

## 🧾 Descrição do Projeto

Este projeto consiste no **desenvolvimento de um sistema de cadastro de produtos** utilizando a linguagem **Java** com o paradigma de **Programação Orientada a Objetos (POO)**.  
O sistema implementa as principais operações de um CRUD — **Create, Read, Update e Delete** — com uma **interface gráfica amigável** desenvolvida em **Swing**.

---

## 🧭 Funcionalidades Principais

### 🔹 Menu Inicial
O sistema apresenta um menu com as seguintes opções:
- ➕ **Cadastrar Produto**  
- 📋 **Listar Produtos**  
- ✏️ **Atualizar Produto**  
- 🗑️ **Excluir Produto**  
- 🚪 **Sair**

---

## 📦 Estrutura de Dados

Cada produto cadastrado contém os seguintes campos:

| Campo | Tipo | Descrição |
|-------|------|-----------|
| `codigo` | `int` | Identificador único do produto |
| `nome` | `String` | Nome do produto |
| `descricao` | `String` | Detalhes ou informações adicionais |
| `preco` | `double` | Valor unitário do produto |
| `quantidade` | `int` | Quantidade disponível em estoque |

---

## 🪟 Interface Gráfica (Swing)

- Desenvolvida com o **pacote `javax.swing`**  
- Contém **menu principal** e **tabela interativa** para exibir os produtos  
- A **tabela é atualizada automaticamente** após cada operação (cadastro, atualização ou exclusão)  
- Design voltado para **usabilidade e clareza visual**

---

## ⚙️ Funcionalidades CRUD

### ➕ Cadastro
- Permite adicionar novos produtos ao sistema  
- Realiza **validação de código duplicado**

### 📋 Listagem
- Exibe todos os produtos cadastrados em uma tabela  
- Mostra: **código**, **nome**, **descrição**, **preço**, **quantidade**

### ✏️ Atualização
- Localiza produtos pelo **código**  
- Permite editar qualquer campo do produto

### 🗑️ Exclusão
- Remove produtos do sistema com base no **código informado**

---

## ✅ Regras de Validação

- 🚫 Não é permitido cadastrar um produto com **código já existente**
- ⚠️ O sistema verifica se o **código existe** antes de atualizar ou excluir
- 🔢 Os campos numéricos (**preço** e **quantidade**) devem conter valores válidos

---

## 💡 Tecnologias Utilizadas

| Tecnologia | Descrição |
|-------------|------------|
| ☕ **Java 21+** | Linguagem de programação principal |
| 🪟 **Swing** | Biblioteca para construção da interface gráfica |
| 🧩 **POO (Encapsulamento, Herança, Polimorfismo)** | Conceitos aplicados na estrutura do sistema |
| 🧱 **MVC (Model–View–Controller)** | Padrão de arquitetura recomendado |

---

## 👥 Equipe

> Projeto em grupo (até **4 integrantes**)

| Integrante | Função | Contato |
|-------------|--------|----------|
| **Seu Nome Aqui** | Desenvolvedor | 📧 seu.email@exemplo.com |
| ... | ... | ... |

---

## 🕓 Prazo e Avaliação

- 📅 **Entrega:** até **02/12/2025 às 19h**  
- 🧩 **Avaliação:** em sala, por ordem de chegada dos grupos  
- ⚠️ **Uso de ferramentas de IA** identificadas acarretará **desconto de 30% na nota**

---

## 🚀 Como Executar o Projeto

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/usuario/nome-do-projeto.git
   ```
2. **Abra o projeto no VS Code ou IntelliJ**
3. **Compile e execute o arquivo principal:**
   ```bash
   javac src/Main.java
   java src.Main
   ```
4. **Utilize o menu para navegar entre as opções do sistema.**

---

## 📸 Exemplo Visual (Sugestão)

```
╔══════════════════════════════════╗
║       SISTEMA DE PRODUTOS        ║
╠══════════════════════════════════╣
║ [1] Cadastrar Produto            ║
║ [2] Listar Produtos              ║
║ [3] Atualizar Produto            ║
║ [4] Excluir Produto              ║
║ [5] Sair                         ║
╚══════════════════════════════════╝
```

---

## 🏁 Conclusão

Este projeto reforça os **conceitos fundamentais de POO**, integrando-os à **construção de interfaces gráficas** com **Swing**, além de promover boas práticas de desenvolvimento, organização e validação de dados.
