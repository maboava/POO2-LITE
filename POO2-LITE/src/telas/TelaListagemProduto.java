package telas;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;
import java.util.Locale;

public class TelaListagemProduto extends JFrame {

    private JTable tabela;
    private DefaultTableModel modelo;

    public TelaListagemProduto() {
        setTitle("Listagem de Produtos");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        criarTabela();
        carregarDadosDoBanco();

        // Botão Voltar
        JButton btnVoltar = new JButton("← Voltar");
        btnVoltar.addActionListener(e -> {
            dispose();
        });

        // Botão Recarregar
        JButton btnRecarregar = new JButton("Recarregar");
        btnRecarregar.addActionListener(e -> recarregarTabela());

        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.addActionListener(e -> abrirTelaCadastro());

        JPanel painelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelTop.add(btnVoltar);
        painelTop.add(btnRecarregar);
        painelTop.add(btnCadastrar);

        JLabel lblDica = new JLabel("Clique duas vezes sobre o produto para editar.");

        // ---------------------------------------------------> AJUSTE DA COR DO TEXTO DA DICA
        Color bg = UIManager.getColor("Panel.background");
        if (bg == null) bg = painelTop.getBackground();

        double luminancia = (0.299 * bg.getRed()) + (0.587 * bg.getGreen()) + (0.114 * bg.getBlue());

        if (luminancia > 128) {
            lblDica.setForeground(Color.BLACK);
        } else {
            lblDica.setForeground(Color.WHITE);
        }

        painelTop.add(lblDica);
        // ---------------------------------------------------< FIM DO BLOCO DA DICA

        add(painelTop, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        tabela.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int linha = tabela.rowAtPoint(e.getPoint());
                    if (linha != -1) {
                        tabela.setRowSelectionInterval(linha, linha);
                        String codigo = (String) modelo.getValueAt(linha, 0);
                        Produto produto = ProdutoDAO.getInstance().buscarProduto(codigo);
                        if (produto != null) {
                            new DetalheProdutoDialog(TelaListagemProduto.this, produto, TelaListagemProduto.this::recarregarTabela);
                        }
                    }
                }
            }
        });

        setVisible(true);
    }

    private void criarTabela() {
        String[] colunas = {"Código", "Nome", "Descrição", "Preço", "Quantidade"};

        modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modelo);
        tabela.setFillsViewportHeight(true);
        tabela.setRowHeight(25);

        centralizarColunas();
    }

    private void centralizarColunas() {
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);

        tabela.getColumnModel().getColumn(0).setCellRenderer(centralizado); // Código
        tabela.getColumnModel().getColumn(3).setCellRenderer(centralizado); // Preço
        tabela.getColumnModel().getColumn(4).setCellRenderer(centralizado); // Quantidade
    }

    private void carregarDadosDoBanco() {
        // Formatação brasileira também na tabela
        Locale localeBR = Locale.forLanguageTag("pt-BR");
        NumberFormat nf = NumberFormat.getCurrencyInstance(localeBR);

        for (Produto produto : ProdutoDAO.getInstance().listarProdutos()) {
            String precoFormatado = nf.format(produto.getPreco());
            modelo.addRow(new Object[]{
                    produto.getCodigo(),
                    produto.getNome(),
                    produto.getDescricao(),
                    precoFormatado,               // mostra R$ 1.234,56
                    produto.getQuantidade()
            });
        }
    }

    private void recarregarTabela() {
        modelo.setRowCount(0);
        carregarDadosDoBanco();
    }

    private void abrirTelaCadastro() {
        TelaCadastrar telaCadastrar = new TelaCadastrar();
        telaCadastrar.setLocationRelativeTo(this);
        telaCadastrar.setAlwaysOnTop(true);
        telaCadastrar.toFront();
        telaCadastrar.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                recarregarTabela();
            }

            @Override
            public void windowClosing(WindowEvent e) {
                recarregarTabela();
            }
        });
    }

    private static class DetalheProdutoDialog extends JDialog {

        private final JTextField txtCodigo;
        private final JTextField txtNome;
        private final JTextField txtDescricao;
        private final JTextField txtPreco;
        private final JTextField txtQuantidade;

        private final double precoOriginal; // mantém o valor numérico original

        DetalheProdutoDialog(Frame owner, Produto produto, Runnable onChange) {
            super(owner, "Detalhes do Produto", true);
            setLayout(new BorderLayout(10, 10));
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            this.precoOriginal = produto.getPreco();

            JPanel painelCampos = new JPanel(new GridLayout(5, 2, 8, 8));
            painelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            painelCampos.add(new JLabel("Código:"));
            txtCodigo = new JTextField(produto.getCodigo());
            txtCodigo.setEditable(false);
            painelCampos.add(txtCodigo);

            painelCampos.add(new JLabel("Nome:"));
            txtNome = new JTextField(produto.getNome());
            painelCampos.add(txtNome);

            painelCampos.add(new JLabel("Descrição:"));
            txtDescricao = new JTextField(produto.getDescricao());
            painelCampos.add(txtDescricao);

            // Preço formatado no padrão brasileiro sem R$
            painelCampos.add(new JLabel("Preço:"));
            Locale localeBR = Locale.forLanguageTag("pt-BR");
            NumberFormat nf = NumberFormat.getNumberInstance(localeBR); 
            String precoFormatado = nf.format(precoOriginal); 
            txtPreco = new JTextField(precoFormatado);
            painelCampos.add(txtPreco);

            painelCampos.add(new JLabel("Quantidade:"));
            txtQuantidade = new JTextField(String.valueOf(produto.getQuantidade()));
            painelCampos.add(txtQuantidade);

            JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
            JButton btnSalvar = new JButton("Salvar alterações");
            JButton btnExcluir = new JButton("Excluir");
            JButton btnCancelar = new JButton("Cancelar");

            painelBotoes.add(btnExcluir);
            painelBotoes.add(btnCancelar);
            painelBotoes.add(btnSalvar);

            btnSalvar.addActionListener(e -> salvarAlteracoes(onChange));
            btnExcluir.addActionListener(e -> excluirProduto(onChange));
            btnCancelar.addActionListener(e -> dispose());

            add(painelCampos, BorderLayout.CENTER);
            add(painelBotoes, BorderLayout.SOUTH);

            pack();
            setLocationRelativeTo(owner);
            setVisible(true);
        }

        private void salvarAlteracoes(Runnable onChange) {
            try {
                String nome = txtNome.getText().trim();
                String descricao = txtDescricao.getText().trim();
                String precoTexto = txtPreco.getText().trim();
                String quantidadeTexto = txtQuantidade.getText().trim();

                if (nome.isEmpty() || descricao.isEmpty() || precoTexto.isEmpty() || quantidadeTexto.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // --------- TRATAR PREÇO DIGITADO - PRECISA PQ SE NÃO VAI DAR RUIM PRA SALVAR O CADASTRO (ex: 1.234,56 → 1234.56) ----------
                precoTexto = precoTexto.replace(".", ""); 
                precoTexto = precoTexto.replace(",", ".");

                double preco = Double.parseDouble(precoTexto);

                // --------- TRATAR QUANTIDADE (permite vírgula mas vira inteiro) ----------
                quantidadeTexto = quantidadeTexto.replace(",", "."); // se digitar "10,0", mas não permite "10,6" por exemploo...
                double quantidadeDouble = Double.parseDouble(quantidadeTexto);
                if (quantidadeDouble % 1 != 0) {
                    JOptionPane.showMessageDialog(this,
                            "A quantidade deve ser um número inteiro.",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int quantidade = (int) quantidadeDouble;

                Produto atualizado = new Produto(txtCodigo.getText(), nome, descricao, preco, quantidade);
                boolean sucesso = ProdutoDAO.getInstance().atualizarProduto(atualizado);

                if (sucesso) {
                    JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso!");
                    if (onChange != null) {
                        onChange.run();
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Não foi possível atualizar o produto.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Preço ou quantidade inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void excluirProduto(Runnable onChange) {
            int opcao = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir este produto?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (opcao == JOptionPane.YES_OPTION) {
                boolean removido = ProdutoDAO.getInstance().removerProduto(txtCodigo.getText());
                if (removido) {
                    JOptionPane.showMessageDialog(this, "Produto excluído com sucesso!");
                    if (onChange != null) {
                        onChange.run();
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Código não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaListagemProduto().setVisible(true));
    }
}
