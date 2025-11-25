# 📖 Sistema de Cadastro de Produtos (POO II)

Aplicação desktop em **Java 21 + Swing** para gerenciamento de produtos de uma mercearia. O sistema apresenta splash screen, autenticação simples e uma central de produtos com operações de cadastro, listagem, edição e exclusão persistidas em arquivo texto.

## 🧑‍💻 Autores

- **Kauan Antônio Neves Gomes** — [kauansw2](https://github.com/kauansw2)
- **Matheus de Almeida Boava** — [maboava](https://github.com/maboava)
- **Nichole Maria Furtado** — [Nichole-Furtado](https://github.com/Nichole-Furtado)
- **Rafael Rodrigues Pichibinski** — [1Deatth](https://github.com/1Deatth)
- **Tais Mayme Ferrari** — [Tais1905](https://github.com/Tais1905)

---

## ✨ Principais Funcionalidades

- **Splash Screen:** tela inicial que prepara o ambiente antes da abertura do sistema principal.
- **Login simplificado:** autenticação local com usuário e senha `admin`, `admin`.
- **Menu inicial com imagem de fundo:** navegação dedicada para abrir a central de produtos.
- **Central de produtos:**
  - Listagem em `JTable` com recarga manual.
  - Cadastro de novos itens a partir da listagem.
  - Edição e exclusão ao dar duplo clique em um produto, com diálogo dedicado.
  - Validações para código único, campos obrigatórios e tipos numéricos.
- **Persistência em arquivo texto:** registros salvos em `src/banco/produtos.txt` por meio de um `ProdutoDAO` singleton.

---

## 🧱 Arquitetura

- **Main + SplashScreenWindow:** inicializam o visual padrão do sistema e exibem a splash screen antes do login.
- **TelaLogin:** valida as credenciais e direciona para o menu inicial.
- **MenuInicial:** janela com imagem de fundo e atalho para a central de produtos.
- **TelaListagemProduto:** tabela com recarga, criação rápida e diálogo de detalhes que permite alterar ou excluir produtos.
- **ProdutoDAO:** camada de acesso a dados que garante unicidade de código, controle de lista em memória e sincronização com o arquivo texto.

Estrutura de diretórios relevante:

```
POO2-LITE/
├── README.md
├── src/
│   ├── Main.java
│   ├── img/background.png
│   ├── banco/produtos.txt
│   └── telas/...
└── bin/ (arquivos compilados)
```

---

## 🚀 Como Executar

1. **Requisitos:** Java 21 ou superior.
2. **Compilação:**
   ```bash
   cd POO2-LITE
   javac -d bin -cp src src/Main.java src/telas/*.java
   ```
3. **Execução:**
   ```bash
   java -cp bin Main
   ```
4. **Login:** use `admin` para usuário e senha.

O arquivo `src/banco/produtos.txt` é criado automaticamente se não existir e permanece sendo reutilizado entre execuções.

---

## 📚 Referências

- [Documentação Oficial do Java](https://docs.oracle.com/javase/8/docs/)
- [Java Swing Tutorial - Oracle](https://docs.oracle.com/javase/tutorial/uiswing/)
- [Java Naming Conventions](https://www.oracle.com/java/technologies/javase/codeconventions-namingconventions.html)
