import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

// CLASSE PRODUTO INTERNA - Representa os dados de um produto (totalmente independente)
class ProdutoUpdate {
    private String codigo;
    private String nome;
    private String descricao;
    private double preco;
    private int quantidade;

    public ProdutoUpdate(String codigo, String nome, String descricao, double preco, int quantidade) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public String toFileString() {
        return codigo + ";" + nome + ";" + descricao + ";" + preco + ";" + quantidade;
    }
}

// CLASSE DAO - Gerencia a persistência dos dados no arquivo banco/produtos.txt
class ProdutoUpdateDAO {
    private static final Path ARQUIVO_PRODUTOS = resolverCaminhoBanco();
    private List<ProdutoUpdate> produtos;

    public ProdutoUpdateDAO() {
        this.produtos = new ArrayList<>();
        carregarProdutos();
    }

    private static Path resolverCaminhoBanco() {
        Path base = Paths.get(System.getProperty("user.dir"));
        if (base.getFileName() != null && base.getFileName().toString().equals("bin")) {
            base = base.getParent();
        }
        return base.resolve(Paths.get("src", "banco", "produtos.txt"));
    }

    private void carregarProdutos() {
        File arquivo = ARQUIVO_PRODUTOS.toFile();
        if (!arquivo.exists()) {
            // Cria arquivo vazio se não existir
            try {
                Files.createDirectories(ARQUIVO_PRODUTOS.getParent());
                arquivo.createNewFile();
            } catch (IOException e) {
                System.err.println("Erro ao criar arquivo: " + e.getMessage());
            }
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length >= 5) {
                    produtos.add(new ProdutoUpdate(
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

    public void salvarProdutos() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO_PRODUTOS.toFile()))) {
            for (ProdutoUpdate produto : produtos) {
                bw.write(produto.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar produtos: " + e.getMessage());
        }
    }

    public boolean atualizar(ProdutoUpdate produtoAtualizado) {
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getCodigo().equalsIgnoreCase(produtoAtualizado.getCodigo())) {
                produtos.set(i, produtoAtualizado);
                salvarProdutos();
                return true;
            }
        }
        return false;
    }

    public ProdutoUpdate buscarPorCodigo(String codigo) {
        for (ProdutoUpdate produto : produtos) {
            if (produto.getCodigo().equalsIgnoreCase(codigo)) return produto;
        }
        return null;
    }

    public List<ProdutoUpdate> listarTodos() {
        return new ArrayList<>(produtos);
    }
}

// TELA DE ATUALIZAÇÃO DE PRODUTOS
public class TelaAtualizacao extends JFrame {
    private ProdutoUpdateDAO produtoDAO;
    private JTextField txtCodigoBusca, txtCodigoDisplay, txtNomeProduto, txtDescricaoProduto, txtPrecoProduto, txtQuantidadeProduto;
    private JButton btnBuscar, btnAtualizar, btnLimpar;
    private JTable tabelaProdutos;
    private DefaultTableModel modeloTabela;
    
    public TelaAtualizacao() {
        produtoDAO = new ProdutoUpdateDAO();
        setTitle("Sistema de Atualização de Produtos");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        inicializarComponentes();
        atualizarTabela();
    }
    
    private void inicializarComponentes() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painelPrincipal.add(criarPainelFormulario(), BorderLayout.NORTH);
        painelPrincipal.add(criarPainelTabela(), BorderLayout.CENTER);
        add(painelPrincipal);
    }
    
    private JPanel criarPainelFormulario() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBusca.setBorder(BorderFactory.createTitledBorder("Buscar Produto"));
        painelBusca.add(new JLabel("Código:"));
        txtCodigoBusca = new JTextField(15);
        txtCodigoBusca.addActionListener(e -> buscarProduto());
        painelBusca.add(txtCodigoBusca);
        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarProduto());
        painelBusca.add(btnBuscar);
        
        JPanel painelDados = new JPanel(new GridLayout(5, 2, 10, 10));
        painelDados.setBorder(BorderFactory.createTitledBorder("Dados do Produto"));
        painelDados.add(new JLabel("Código:"));
        txtCodigoDisplay = new JTextField();
        txtCodigoDisplay.setEditable(false);
        txtCodigoDisplay.setBackground(new Color(240, 240, 240));
        painelDados.add(txtCodigoDisplay);
        painelDados.add(new JLabel("Nome:"));
        txtNomeProduto = new JTextField();
        painelDados.add(txtNomeProduto);
        painelDados.add(new JLabel("Descrição:"));
        txtDescricaoProduto = new JTextField();
        painelDados.add(txtDescricaoProduto);
        painelDados.add(new JLabel("Preço (R$):"));
        txtPrecoProduto = new JTextField();
        painelDados.add(txtPrecoProduto);
        painelDados.add(new JLabel("Quantidade:"));
        txtQuantidadeProduto = new JTextField();
        painelDados.add(txtQuantidadeProduto);
        
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelBotoes.setBorder(BorderFactory.createTitledBorder("Ações"));
        btnAtualizar = new JButton("Atualizar Produto");
        btnAtualizar.setEnabled(false);
        btnAtualizar.addActionListener(e -> atualizarProduto());
        painelBotoes.add(btnAtualizar);
        btnLimpar = new JButton("Limpar Campos");
        btnLimpar.addActionListener(e -> limparCampos());
        painelBotoes.add(btnLimpar);
        
        JPanel painelCentral = new JPanel(new BorderLayout(10, 10));
        painelCentral.add(painelDados, BorderLayout.CENTER);
        painelCentral.add(painelBotoes, BorderLayout.SOUTH);
        painel.add(painelBusca, BorderLayout.NORTH);
        painel.add(painelCentral, BorderLayout.CENTER);
        return painel;
    }
    
    private JPanel criarPainelTabela() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createTitledBorder("Lista de Produtos (banco/produtos.txt)"));
        String[] colunas = {"Código", "Nome", "Descrição", "Preço (R$)", "Quantidade"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaProdutos = new JTable(modeloTabela);
        tabelaProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaProdutos.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int linha = tabelaProdutos.getSelectedRow();
                    if (linha >= 0) {
                        txtCodigoBusca.setText((String) modeloTabela.getValueAt(linha, 0));
                        buscarProduto();
                    }
                }
            }
        });
        painel.add(new JScrollPane(tabelaProdutos), BorderLayout.CENTER);
        return painel;
    }
    
    private void buscarProduto() {
        String codigo = txtCodigoBusca.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o código do produto!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        ProdutoUpdate produto = produtoDAO.buscarPorCodigo(codigo);
        if (produto == null) {
            JOptionPane.showMessageDialog(this, "Produto não encontrado!", "Aviso", JOptionPane.WARNING_MESSAGE);
            limparCampos();
            return;
        }
        txtCodigoDisplay.setText(produto.getCodigo());
        txtNomeProduto.setText(produto.getNome());
        txtDescricaoProduto.setText(produto.getDescricao());
        txtPrecoProduto.setText(String.format("%.2f", produto.getPreco()));
        txtQuantidadeProduto.setText(String.valueOf(produto.getQuantidade()));
        btnAtualizar.setEnabled(true);
    }
    
    private void atualizarProduto() {
        try {
            String codigo = txtCodigoDisplay.getText();
            String nome = txtNomeProduto.getText().trim();
            String descricao = txtDescricaoProduto.getText().trim();
            double preco = Double.parseDouble(txtPrecoProduto.getText().trim());
            int quantidade = Integer.parseInt(txtQuantidadeProduto.getText().trim());
            
            if (nome.isEmpty() || descricao.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome e Descrição são obrigatórios!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (preco < 0 || quantidade < 0) {
                JOptionPane.showMessageDialog(this, "Preço e Quantidade devem ser positivos!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            ProdutoUpdate produtoAtualizado = new ProdutoUpdate(codigo, nome, descricao, preco, quantidade);
            if (produtoDAO.atualizar(produtoAtualizado)) {
                JOptionPane.showMessageDialog(this, 
                    "Produto atualizado com sucesso no arquivo banco/produtos.txt!",
                    "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
                atualizarTabela();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar produto!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Preço ou Quantidade inválidos! Use valores numéricos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limparCampos() {
        txtCodigoBusca.setText("");
        txtCodigoDisplay.setText("");
        txtNomeProduto.setText("");
        txtDescricaoProduto.setText("");
        txtPrecoProduto.setText("");
        txtQuantidadeProduto.setText("");
        btnAtualizar.setEnabled(false);
    }
    
    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        for (ProdutoUpdate produto : produtoDAO.listarTodos()) {
            modeloTabela.addRow(new Object[]{
                produto.getCodigo(), 
                produto.getNome(), 
                produto.getDescricao(), 
                String.format("R$ %.2f", produto.getPreco()), 
                produto.getQuantidade()
            });
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaAtualizacao().setVisible(true));
    }
}

