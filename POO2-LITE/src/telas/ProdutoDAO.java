package telas;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ProdutoDAO {

    // 🔹 Singleton: única instância do DAO para o sistema todo
    private static ProdutoDAO instance;

    // 🔹 Caminho único do arquivo de "banco de dados"
    private static final Path ARQUIVO_BANCO = resolverCaminhoBanco();

    private static Path resolverCaminhoBanco() {
        Path base = Paths.get(System.getProperty("user.dir"));
        if (base.getFileName() != null && base.getFileName().toString().equals("bin")) {
            base = base.getParent();
        }
        return base.resolve(Paths.get("src", "banco", "produtos.txt"));
    }

    public static ProdutoDAO getInstance() {
        if (instance == null) {
            instance = new ProdutoDAO();
        }
        return instance;
    }

    // Lista interna que funciona como "banco de dados"
    private List<Produto> produtos;

    // Construtor privado para forçar uso do getInstance()
    private ProdutoDAO() {
        produtos = new ArrayList<>();
        carregarProdutosDoArquivo();
    }

    // CREATE: adicionar produto garantindo código único
    public boolean adicionarProduto(Produto p) {
        if (buscarProduto(p.getCodigo()) != null) {
            // Já existe produto com esse código
            return false;
        }
        produtos.add(p);
        salvarProdutosNoArquivo();
        return true;
    }

    // READ (buscar por código)
    public Produto buscarProduto(String codigo) {
        for (Produto p : produtos) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
                return p;
            }
        }
        return null; // não encontrado
    }

    // UPDATE (atualizar produto já existente)
    public boolean atualizarProduto(Produto produtoAtualizado) {
        for (int i = 0; i < produtos.size(); i++) {
            Produto atual = produtos.get(i);
            if (atual.getCodigo().equalsIgnoreCase(produtoAtualizado.getCodigo())) {
                atual.setNome(produtoAtualizado.getNome());
                atual.setDescricao(produtoAtualizado.getDescricao());
                atual.setPreco(produtoAtualizado.getPreco());
                atual.setQuantidade(produtoAtualizado.getQuantidade());
                salvarProdutosNoArquivo();
                return true;
            }
        }
        return false; // não encontrou para atualizar
    }

    // DELETE (remover produto por código)
    public boolean removerProduto(String codigo) {
        Produto encontrado = buscarProduto(codigo);
        if (encontrado != null) {
            produtos.remove(encontrado);
            salvarProdutosNoArquivo();
            return true;
        }
        return false;
    }

    // READ ALL (listar todos)
    public List<Produto> listarProdutos() {
        // retorna uma cópia para evitar modificações diretas na lista interna
        return new ArrayList<>(produtos);
    }

    // 🔹 Persistência em arquivo ------------------------------------------------
    private void carregarProdutosDoArquivo() {
        File arquivo = ARQUIVO_BANCO.toFile();

        try {
            Files.createDirectories(ARQUIVO_BANCO.getParent());
            if (!arquivo.exists()) {
                arquivo.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Erro ao preparar arquivo de banco: " + e.getMessage());
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 5) {
                    produtos.add(new Produto(
                            dados[0],
                            dados[1],
                            dados[2],
                            Double.parseDouble(dados[3]),
                            Integer.parseInt(dados[4])
                    ));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Erro ao carregar produtos: " + e.getMessage());
        }
    }

    private void salvarProdutosNoArquivo() {
        File arquivo = ARQUIVO_BANCO.toFile();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
            for (Produto produto : produtos) {
                bw.write(produto.getCodigo() + ";" +
                        produto.getNome() + ";" +
                        produto.getDescricao() + ";" +
                        produto.getPreco() + ";" +
                        produto.getQuantidade());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar produtos: " + e.getMessage());
        }
    }
}
