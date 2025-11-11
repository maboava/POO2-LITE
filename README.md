# 📖 Trabalho Bimestral – POO I

Trabalho apresentado ao Professor Me. **Bruno Luiz Schuster Rech**, como requisito parcial para a composição da nota do **segundo bimestre** do **4º período** do Curso de **Bacharelado em Engenharia de Software** da **Faculdade Uniguaçu**.

**São Miguel do Iguaçu – PR, 02 de dezembro de 2025.**

---

> **"O impossível sempre parece impossível até que seja feito — e nada é mais forte do que pessoas unidas por um mesmo propósito."**  
> — *Nelson Mandela*

---

## 🧑‍💻 Autores

**Kauan Antônio Neves Gomes**  [![GitHub de Kauan](https://img.shields.io/badge/GitHub-kauansw2-black?logo=github)](https://github.com/kauansw2)  
**Matheus de Almeida Boava**  [![GitHub de Boava](https://img.shields.io/badge/GitHub-maboava-black?logo=github)](https://github.com/maboava)  
**Nichole Maria Furtado**  [![GitHub de Nichole](https://img.shields.io/badge/GitHub-Nichole_Furtado-black?logo=github)](https://github.com/Nichole-Furtado)  
**Rafael Rodrigues Pichibinski** [![GitHub de Rafael](https://img.shields.io/badge/GitHub-1Deatth-black?logo=github)](https://github.com/1Deatth)  
**Tais Mayme Ferrari**  [![GitHub de Tais](https://img.shields.io/badge/GitHub-Tais1905-black?logo=github)](https://github.com/Tais1905)  

---

## 📌 Objetivo

Desenvolver um **sistema de cadastro de produtos** em **Java**, aplicando os conceitos fundamentais de **Programação Orientada a Objetos (POO)** e integrando uma **interface gráfica** com **Swing**.

O projeto propõe a implementação completa de um **CRUD (Create, Read, Update, Delete)**, com foco em **usabilidade, validação de dados e atualização dinâmica** da tabela de produtos.

---

## 📘 Nosso Projeto

O **Sistema de Cadastro de Produtos** é uma aplicação desktop construída em **Java 21 + Swing**, cujo propósito é permitir o **gerenciamento de produtos** de forma simples, visual e funcional.  

A interface apresenta um **menu inicial intuitivo** e uma **tabela dinâmica** para exibição dos dados.  
Cada operação — **cadastro, listagem, atualização e exclusão** — é refletida instantaneamente na tela.

### 🔹 Estrutura do Sistema

- **Menu Principal:**  
  - ➕ Cadastrar Produto  
  - 📋 Listar Produtos  
  - ✏️ Atualizar Produto  
  - 🗑️ Excluir Produto  
  - 🚪 Sair  

- **Campos de Cadastro:**  
  - Código do produto (único)  
  - Nome  
  - Descrição  
  - Preço  
  - Quantidade em estoque  

---

## 🏗️ Arquitetura e Ciclo de Vida

A aplicação segue princípios de **baixo acoplamento e alta coesão**, utilizando uma estrutura próxima ao padrão **MVC (Model-View-Controller)**:

- **Model (`Produto`)**  
  - Representa o domínio principal, encapsulando os atributos e comportamentos de cada produto.  
  - Inclui validações e métodos auxiliares para exibição e comparação.

- **Controller (`ProdutoController`)**  
  - Responsável pelas operações CRUD.  
  - Gerencia a lista de produtos, garantindo unicidade de código e integridade dos dados.

- **View (`ProdutoUI`)**  
  - Interface construída em `Swing` com menus, tabelas e formulários.  
  - Usa `JTable` e `DefaultTableModel` para renderizar os dados em tempo real.  
  - Cada ação (botão ou menu) chama diretamente os métodos do controller.

📌 **Fluxo Geral:**  
`Main` → inicia `ProdutoUI` → interage com `ProdutoController` → manipula `ArrayList<Produto>` e atualiza a tabela.

---

## 🧩 Regras e Validações

- 🚫 O **código do produto** deve ser único.  
- ⚠️ É necessário validar se o código existe antes de **atualizar** ou **excluir**.  
- 💲 O **preço** deve ser numérico e positivo.  
- 📦 A **quantidade** em estoque não pode ser negativa.  
- 💡 A tabela é atualizada automaticamente após cada operação.

---

## 💾 Persistência e Estrutura de Dados

- Armazenamento em **memória (ArrayList)** para simplicidade e desempenho.  
- A implementação pode evoluir futuramente para persistência em **arquivos texto** ou **banco de dados**.  
- Métodos centralizados de leitura, escrita e validação garantem integridade e consistência.

---

## 🖥️ Interface Gráfica (Swing)

A interface gráfica foi construída com o **pacote `javax.swing`**, utilizando:

- `JFrame` e `JPanel` para a estrutura principal.  
- `JTable` com `DefaultTableModel` para exibição dos produtos.  
- `JOptionPane` para formulários e mensagens de alerta/erro.  
- `JButton`, `JTextField` e `JLabel` para inputs e ações.

### 🎨 Destaques Visuais

- Layout responsivo e intuitivo.  
- Atualização instantânea da tabela após cada ação.  
- Validação visual (mensagens amigáveis).  
- Ícones e rótulos informativos para melhor navegação.

---

## ⚙️ Tecnologias Utilizadas

| Tecnologia | Descrição |
|-------------|------------|
| ☕ **Java 21+** | Linguagem principal |
| 🪟 **Swing** | Biblioteca para criação da interface gráfica |
| 🧩 **POO (Encapsulamento, Herança, Polimorfismo)** | Paradigma aplicado |
| 🧱 **MVC** | Padrão estrutural recomendado |

---

## 🕓 Prazo e Avaliação

- 📅 **Entrega:** até **02/12/2025 às 19h**  
- 🧩 **Avaliação:** em sala, por ordem de chegada dos grupos  
- ⚠️ **Uso de ferramentas de IA** acarretará **desconto de 30% na nota**

---

## 🚀 Como Executar o Projeto

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/usuario/sistema-cadastro-produtos.git
   ```
2. **Abra o projeto no VS Code ou IntelliJ IDEA**
3. **Compile e execute o arquivo principal:**
   ```bash
   javac src/Main.java
   java src.Main
   ```
4. **Utilize o menu para navegar entre as opções do sistema.**

---


## 📚 Referências

- [Documentação Oficial do Java](https://docs.oracle.com/javase/8/docs/) — Guia completo da linguagem e APIs.  
- [Java Swing Tutorial - Oracle](https://docs.oracle.com/javase/tutorial/uiswing/) — Desenvolvimento de interfaces gráficas.  
- [Java Naming Conventions](https://www.oracle.com/java/technologies/javase/codeconventions-namingconventions.html) — Convenções oficiais de nomenclatura.  
- [Java Object-Oriented Programming Concepts](https://docs.oracle.com/javase/tutorial/java/concepts/) — Conceitos de POO.  
- [GeeksforGeeks - Java Swing](https://www.geeksforgeeks.org/java-swing/) — Exemplos práticos de Swing.  
- [Baeldung Java Tutorials](https://www.baeldung.com/java-tutorial) — Tutoriais práticos e modernos sobre Java.  

---

✅ Com esta estrutura, o projeto consolida os fundamentos de **POO e Swing**, entregando uma aplicação funcional, modular e intuitiva — perfeita para consolidar o aprendizado prático do **segundo bimestre**.
