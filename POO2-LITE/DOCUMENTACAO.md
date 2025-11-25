# Documentação do Sistema de Cadastro de Produtos

Este documento descreve, de forma detalhada, como o sistema foi estruturado. Ele começa pela classe `Main`, passa pelas telas (UI) e fecha com os componentes de domínio e persistência. Use-o como guia de estudos e como referência rápida de onde cada responsabilidade está localizada.

## 1. Fluxo inicial (`Main`)
- **Arquivo:** `src/Main.java`
- **Função principal:** `public static void main(String[] args)` apenas chama `splashScreen()` para iniciar a interface no *Event Dispatch Thread* do Swing.
- **`configurarInterface()`:** aplica o *look and feel* do sistema operacional e traduz os botões padrão de `JOptionPane` para português (Sim, Não, OK, Cancelar).
- **`splashScreen()`:** agenda na fila do Swing a criação da janela de splash (`SplashScreenWindow`), exibe por 3 segundos com um `Timer`, e em seguida chama `iniciarAplicacao()`.
- **`iniciarAplicacao()`:** instancia `telas.TelaLogin` (primeira tela real) e a torna visível.
- **Classe interna `SplashScreenWindow`:** estende `JWindow`, renderiza um painel com título, subtítulo e `JProgressBar` indeterminado. É usada apenas durante a fase de carregamento.

## 2. Telas de interface (`src/telas`)
As telas seguem um padrão: cada uma estende `JFrame` (ou `JDialog` no caso do diálogo de detalhes) e é responsável por compor seus componentes, registrar *listeners* e acionar o `ProdutoDAO` para ler/gravar dados.

### 2.1 Tela de Login (`TelaLogin`)
- **Arquivo:** `src/telas/TelaLogin.java`
- **Propósito:** autenticação simples antes de entrar no menu.
- **Credenciais fixas:** usuário `admin`, senha `admin`.
- **Componentes principais:** campos de usuário e senha, rótulo para mensagens, botões *Entrar* e *Sair*.
- **Fluxo:** ao confirmar (`realizarLogin()`), valida as credenciais. Se corretas, exibe `MenuInicial` e fecha a tela de login; se incorretas, limpa a senha e mostra mensagem de erro.

### 2.2 Menu Inicial com fundo customizado (`MenuInicial`)
- **Arquivo:** `src/telas/MenuInicial.java`
- **Propósito:** servir como hub após o login.
- **Layout:** `JFrame` maximizado com imagem de fundo (`src/img/background.png`) desenhada manualmente em um `JPanel` sobrescrito.
- **Barra superior:** título à esquerda e botões à direita ("Central de Produtos" e "Sair"). A barra usa fundo semitransparente.
- **Controle de janelas:** método `abrirTelaUnica` só permite uma janela secundária visível por vez; as ações de abertura usam *Suppliers* para criar a tela necessária.
- **Ações principais:**
  - **Central de Produtos:** abre `TelaListagemProduto` e desabilita botões do menu até a janela fechar.
  - **Sair:** encerra a aplicação (`System.exit(0)`).

### 2.3 Central de produtos (`TelaListagemProduto`)
- **Arquivo:** `src/telas/TelaListagemProduto.java`
- **Propósito:** listar, abrir detalhes, cadastrar e recarregar produtos.
- **Tabela:** `JTable` com `DefaultTableModel` não editável. Colunas de código, nome, descrição, preço e quantidade; renderizadores centralizam código/preço/quantidade.
- **Carregamento:** `carregarDadosDoBanco()` usa `ProdutoDAO.listarProdutos()` e formata o preço em real (locale pt-BR) antes de adicionar linhas ao modelo.
- **Toolbar:** botões "Voltar" (fecha janela), "Recarregar" (limpa e recarrega tabela) e "Cadastrar" (abre `TelaCadastrar`). Uma dica textual muda de cor automaticamente conforme a luminância do fundo.
- **Duplo clique:** `MouseListener` abre o `DetalheProdutoDialog` com o produto selecionado; o diálogo permite editar ou excluir o item e chama `recarregarTabela()` ao fechar.

#### 2.3.1 Diálogo de detalhes/edição (`DetalheProdutoDialog`)
- **Tipo:** `JDialog` modal criado dentro de `TelaListagemProduto`.
- **Campos:** exibe e permite editar nome, descrição, preço (aceita formato brasileiro) e quantidade; código é somente leitura.
- **Salvar:** converte preço/quantidade para formato numérico interno, monta novo `Produto` e chama `ProdutoDAO.atualizarProduto()`. Em sucesso, informa o usuário e dispara `onChange` para atualizar a tabela.
- **Excluir:** confirma com `JOptionPane` e chama `ProdutoDAO.removerProduto()`; atualiza a listagem em caso de sucesso.

### 2.4 Tela de cadastro (`TelaCadastrar`)
- **Arquivo:** `src/telas/TelaCadastrar.java`
- **Propósito:** criar novos produtos com validação de entrada.
- **Validações:**
  - Código: aceita apenas dígitos.
  - Nome/descrição: bloqueia `;` e força caixa alta.
  - Preço/quantidade: aceita dígitos e vírgula; converte vírgula para ponto para *parse*.
  - Quantidade deve ser inteira (mesmo se digitado com decimal, ex.: `10,0`).
- **Persistência:** monta `Produto` e usa `ProdutoDAO.adicionarProduto()`. Em caso de código duplicado, mostra erro.

### 2.5 Tela de atualização (`TelaAtualizar`)
- **Arquivo:** `src/telas/TelaAtualizar.java`
- **Propósito:** atualizar um produto existente informando o código e novos campos.
- **Validações:** idênticas às do cadastro (restrições de caracteres, conversões de vírgula/ponto, quantidade inteira).
- **Fluxo:** busca o produto pelo código; se não existir, mostra erro. Caso contrário, cria novo `Produto` com os valores e chama `ProdutoDAO.atualizarProduto()`.

### 2.6 Tela de exclusão rápida (`TelaExcluir`)
- **Arquivo:** `src/telas/TelaExcluir.java`
- **Propósito:** remover um produto informando apenas o código.
- **Fluxo:** aciona `ProdutoDAO.removerProduto()` e informa sucesso ou falha (código não encontrado).

## 3. Domínio e persistência

### 3.1 Modelo de produto (`Produto`)
- **Arquivo:** `src/telas/Produto.java`
- **Atributos:** `codigo`, `nome`, `descricao`, `preco`, `quantidade`.
- **Métodos:** construtor completo, getters/setters padrão e `toString()` para depuração.
- **Observação:** o campo `codigo` é tratado como identificador único pelo `ProdutoDAO`.

### 3.2 Acesso a dados (`ProdutoDAO`)
- **Arquivo:** `src/telas/ProdutoDAO.java`
- **Padrão:** Singleton — `getInstance()` garante uma única lista de produtos e um único arquivo de armazenamento (`src/banco/produtos.txt`).
- **CRUD:**
  - **Create:** `adicionarProduto(Produto)` recusa códigos duplicados.
  - **Read:** `buscarProduto(codigo)` retorna produto ou `null`; `listarProdutos()` devolve cópia da lista interna.
  - **Update:** `atualizarProduto(Produto)` substitui os campos do produto que tiver o mesmo código.
  - **Delete:** `removerProduto(codigo)` elimina o registro e persiste a alteração.
- **Persistência em arquivo:**
  - `carregarProdutosDoArquivo()`: cria pasta/arquivo se necessário e lê cada linha separada por `;`, convertendo para `Produto`.
  - `salvarProdutosNoArquivo()`: percorre a lista em memória e grava cada produto em uma linha (`codigo;nome;descricao;preco;quantidade`).
- **Tratamento de caminho:** `resolverCaminhoBanco()` garante que o arquivo seja criado na pasta `src/banco` mesmo quando a aplicação é executada a partir de `/bin` (após compilação).

## 4. Organização do projeto
```
POO2-LITE/
├── DOCUMENTACAO.md          # Este documento de estudo
├── README.md                # Visão geral e instruções de execução
├── bin/                     # Saída da compilação (gerado pelo javac)
└── src/
    ├── Main.java            # Ponto de entrada e splash screen
    ├── img/background.png   # Imagem usada no menu inicial
    ├── telas/               # Telas, modelo e DAO
    │   ├── Produto.java
    │   ├── ProdutoDAO.java
    │   ├── MenuInicial.java
    │   ├── TelaLogin.java
    │   ├── TelaListagemProduto.java (e diálogo interno de detalhes)
    │   ├── TelaCadastrar.java
    │   ├── TelaAtualizar.java
    │   └── TelaExcluir.java
    └── banco/produtos.txt   # Criado automaticamente em tempo de execução
```

## 5. Pontos-chave para estudo
- Fluxo de entrada: **Main → SplashScreen → Login → Menu Inicial → Central de Produtos**.
- **DAO Singleton** mantém consistência entre a lista em memória e o arquivo texto.
- **Validações** de entrada padronizadas (bloqueio de caracteres, formatação brasileira para preços, quantidade inteira).
- **Interação de janelas**: menu controla visibilidade e previne múltiplas telas simultâneas; listagem usa duplo clique para edição/exclusão via diálogo modal.

Com este mapa, você tem uma visão completa das responsabilidades e do caminho de execução do sistema, facilitando revisões ou apresentações.
