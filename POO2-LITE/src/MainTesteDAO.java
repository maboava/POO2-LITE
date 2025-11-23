public class MainTesteDAO {

    public static void main(String[] args) {

        ProdutoDAO dao = new ProdutoDAO();

        // 1. Adicionando produtos
        Produto p1 = new Produto("001", "Teclado", "Teclado mecânico", 150.0, 10);
        Produto p2 = new Produto("002", "Mouse", "Mouse óptico", 80.0, 20);

        System.out.println("Adicionando p1: " + dao.adicionarProduto(p1)); // true
        System.out.println("Adicionando p2: " + dao.adicionarProduto(p2)); // true

        // Tentando adicionar outro produto com código repetido
        Produto p3 = new Produto("001", "Teclado Gamer", "Outro teclado", 200.0, 5);
        System.out.println("Adicionando p3 (codigo 001 já existe): " + dao.adicionarProduto(p3)); // false

        System.out.println("\nListagem inicial:");
        for (Produto p : dao.listarProdutos()) {
            System.out.println(p);
        }

        // 2. Buscar produto
        System.out.println("\nBuscando produto 002:");
        Produto buscado = dao.buscarProduto("002");
        System.out.println(buscado);

        // 3. Atualizar produto
        System.out.println("\nAtualizando produto 002:");
        Produto p2Atualizado = new Produto("002", "Mouse Sem Fio", "Mouse wireless", 120.0, 15);
        boolean atualizado = dao.atualizarProduto(p2Atualizado);
        System.out.println("Atualizado? " + atualizado);

        System.out.println("\nListagem após atualização:");
        for (Produto p : dao.listarProdutos()) {
            System.out.println(p);
        }

        // 4. Remover produto
        System.out.println("\nRemovendo produto 001:");
        boolean removido = dao.removerProduto("001");
        System.out.println("Removido? " + removido);

        System.out.println("\nListagem final:");
        for (Produto p : dao.listarProdutos()) {
            System.out.println(p);
        }
    }
}
