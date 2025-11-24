import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    // 🔹 Singleton: única instância do DAO para o sistema todo
    private static ProdutoDAO instance;

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
    }

    // CREATE: adicionar produto garantindo código único
    public boolean adicionarProduto(Produto p) {
        if (buscarProduto(p.getCodigo()) != null) {
            // Já existe produto com esse código
            return false;
        }
        produtos.add(p);
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
            return true;
        }
        return false;
    }

    // READ ALL (listar todos)
    public List<Produto> listarProdutos() {
        // retorna uma cópia para evitar modificações diretas na lista interna
        return new ArrayList<>(produtos);
    }
}
