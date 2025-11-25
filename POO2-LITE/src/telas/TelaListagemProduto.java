package telas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class TelaListagemProduto extends JFrame {

    private JTable tabela;
    private DefaultTableModel modelo;

    public TelaListagemProduto() {
        setTitle("Listagem de Produtos");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        criarTabela();
        // Lê arquivo de dentro do classpath/JAR
        carregarDadosDoArquivo("/banco/update_temp.txt");

        // ---------------- BOTÃO VOLTAR ----------------
        JButton btnVoltar = new JButton("← Voltar");
        btnVoltar.addActionListener(e -> {
            new MenuInicial().setVisible(true); // abre o menu principal
            dispose(); // fecha esta tela
        });

        // ---------------- BOTÃO RECARREGAR ----------------
        JButton btnRecarregar = new JButton("Recarregar");
        btnRecarregar.addActionListener(e -> {
            modelo.setRowCount(0); // limpa a tabela
            carregarDadosDoArquivo("/banco/update_temp.txt"); // recarrega dados
        });

        // Painel superior alinhado à ESQUERDA
        JPanel painelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelTop.add(btnVoltar);
        painelTop.add(btnRecarregar);

        add(painelTop, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);
    }

    private void criarTabela() {
        String[] colunas = {"Código", "Nome", "Descrição", "Preço", "Quantidade"};

        modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // tabela somente leitura
                return false;
            }
        };

        tabela = new JTable(modelo);
        tabela.setFillsViewportHeight(true);
        tabela.setRowHeight(25);

        // 👉 Centralizar algumas colunas
        centralizarColunas();
    }

    private void centralizarColunas() {
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);

        tabela.getColumnModel().getColumn(0).setCellRenderer(centralizado); // Código
        tabela.getColumnModel().getColumn(3).setCellRenderer(centralizado); // Preço
        tabela.getColumnModel().getColumn(4).setCellRenderer(centralizado); // Quantidade
    }

    private void carregarDadosDoArquivo(String resourcePath) {
        InputStream input = getClass().getResourceAsStream(resourcePath);

        if (input == null) {
            JOptionPane.showMessageDialog(this,
                    "Arquivo não encontrado no classpath:\n" + resourcePath,
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(input, StandardCharsets.UTF_8))) {

            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 5) { // Código;Nome;Descrição;Preço;Quantidade
                    modelo.addRow(dados);
                }
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao ler arquivo: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaListagemProduto().setVisible(true);
        });
    }
}
