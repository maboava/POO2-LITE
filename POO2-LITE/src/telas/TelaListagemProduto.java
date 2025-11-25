package telas;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

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
        btnRecarregar.addActionListener(e -> {
            modelo.setRowCount(0);
            carregarDadosDoBanco();
        });

        JPanel painelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelTop.add(btnVoltar);
        painelTop.add(btnRecarregar);

        add(painelTop, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

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
        for (Produto produto : ProdutoDAO.getInstance().listarProdutos()) {
            modelo.addRow(new Object[]{
                    produto.getCodigo(),
                    produto.getNome(),
                    produto.getDescricao(),
                    String.valueOf(produto.getPreco()),
                    produto.getQuantidade()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaListagemProduto().setVisible(true));
    }
}
