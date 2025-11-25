package telas;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TelaListagemProduto extends JFrame {

    private JTable tabela;
    private DefaultTableModel modelo;

    public TelaListagemProduto() {
        setTitle("Listagem de Produtos");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Garante que o arquivo exista **dentro da pasta src/banco**, sem recriar a pasta banco
        criarArquivoSeNaoExistir("src/banco/update_temp.txt");

        criarTabela();
        carregarDadosDoArquivo("src/banco/update_temp.txt");

        // Botão Voltar
        JButton btnVoltar = new JButton("← Voltar");
        btnVoltar.addActionListener(e -> {
            dispose();
        });

        // Botão Recarregar
        JButton btnRecarregar = new JButton("Recarregar");
        btnRecarregar.addActionListener(e -> {
            modelo.setRowCount(0);
            carregarDadosDoArquivo("src/banco/update_temp.txt");
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

    private void criarArquivoSeNaoExistir(String caminhoRelativo) {
        File arquivo = new File(caminhoRelativo);
        try {
            if (!arquivo.exists()) {
                boolean criado = arquivo.createNewFile();
                if (criado) {
                    System.out.println("Arquivo criado: " + arquivo.getAbsolutePath());
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao criar arquivo: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarDadosDoArquivo(String caminhoRelativo) {
        File arquivo = new File(caminhoRelativo);
        if (!arquivo.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 5) {
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
        SwingUtilities.invokeLater(() -> new TelaListagemProduto().setVisible(true));
    }
}
