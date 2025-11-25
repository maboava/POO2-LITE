package telas;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;

// Import correto quando MenuInicial está no mesmo pacote "telas"
import telas.MenuInicial;

public class TelaListar extends JFrame {

    public TelaListar() {
        setTitle("Listar Produtos");
        setSize(700, 550);
        setLocationRelativeTo(null);
        setResizable(false);

        // Painel centralizador
        JPanel container = new JPanel(new GridBagLayout());

        // Painel principal (tema padrão)
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setPreferredSize(new Dimension(600, 450));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // -----------------------------------------------------
        // TÍTULO
        // -----------------------------------------------------
        JLabel titulo = new JLabel("Lista de Produtos", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        painel.add(titulo, BorderLayout.NORTH);

        // -----------------------------------------------------
        // CABEÇALHO CENTRALIZADO
        // -----------------------------------------------------
        JTextArea cabecalho = new JTextArea();
        cabecalho.setEditable(false);
        cabecalho.setFont(new Font("Consolas", Font.BOLD, 15));

        cabecalho.append(String.format(
                "%-15s │ %-30s │ %-15s\n",
                center("CÓDIGO", 15),
                center("NOME", 30),
                center("PREÇO", 15)
        ));

        painel.add(cabecalho, BorderLayout.BEFORE_FIRST_LINE);

        // -----------------------------------------------------
        // ÁREA COM SCROLL (LISTAGEM)
        // -----------------------------------------------------
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Consolas", Font.PLAIN, 15));

        JScrollPane scroll = new JScrollPane(area);
        painel.add(scroll, BorderLayout.CENTER);

        // -----------------------------------------------------
        // LEITURA DO ARQUIVO
        // -----------------------------------------------------
        try (BufferedReader br = new BufferedReader(new FileReader("delete_temp.txt"))) {

            String linha;
            boolean vazio = true;

            while ((linha = br.readLine()) != null) {
                linha = linha.trim();
                if (linha.isEmpty()) continue;

                String[] partes = linha.split(";");

                if (partes.length == 3) {
                    area.append(String.format(
                            "%-15s │ %-30s │ %-15s\n",
                            center(partes[0], 15),
                            center(partes[1], 30),
                            center(partes[2], 15)
                    ));
                    area.append("--------------------------------------------------------------------------\n");
                }

                vazio = false;
            }

            if (vazio) {
                area.setText("Nenhum produto encontrado.");
            }

        } catch (Exception e) {
            area.setText("Arquivo inexistente ou vazio.");
        }

        // -----------------------------------------------------
        // BOTÃO VOLTAR
        // -----------------------------------------------------
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JPanel rodape = new JPanel();
        rodape.add(btnVoltar);

        painel.add(rodape, BorderLayout.SOUTH);

        btnVoltar.addActionListener(e -> {
            new MenuInicial().setVisible(true);
            dispose();
        });

        container.add(painel);
        add(container);

        setVisible(true);
    }

    // Função para centralizar texto dentro de largura fixa
    private String center(String text, int width) {
        int left = (width - text.length()) / 2;
        int right = width - text.length() - left;
        return " ".repeat(Math.max(0, left)) + text + " ".repeat(Math.max(0, right));
    }

    public static void main(String[] args) {
        new TelaListar();
    }
}
