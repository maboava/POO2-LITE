import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class TelaListar extends JFrame {

    Color bg = new Color(30, 30, 30);
    Color panelBg = new Color(45, 45, 45);
    Color textColor = new Color(230, 230, 230);

    public TelaListar() {
        setTitle("Listar Produtos");
        setSize(700, 550);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(bg);

        // Painel externo centralizador
        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(bg);

        // Painel arredondado
        JPanel painel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(panelBg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 35, 35);
            }
        };

        painel.setOpaque(false);
        painel.setLayout(new BorderLayout(15, 15));
        painel.setPreferredSize(new Dimension(600, 450));
        painel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // ---------------------------
        // TÍTULO
        // ---------------------------
        JLabel titulo = new JLabel("Lista de Produtos", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setForeground(textColor);
        painel.add(titulo, BorderLayout.NORTH);

        // ---------------------------
        // CABEÇALHO FORA DO SCROLL
        // ---------------------------
        JTextArea cabecalho = new JTextArea();
        cabecalho.setEditable(false);
        cabecalho.setBackground(new Color(55, 55, 55));
        cabecalho.setForeground(textColor);
        cabecalho.setFont(new Font("Consolas", Font.BOLD, 15));
        cabecalho.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        cabecalho.append(String.format("%-12s │ %-25s │ %-12s\n",
                "CÓDIGO", "NOME", "PREÇO"));

        JPanel painelCabecalho = new JPanel(new BorderLayout());
        painelCabecalho.setOpaque(false);
        painelCabecalho.add(cabecalho, BorderLayout.CENTER);

        painel.add(painelCabecalho, BorderLayout.BEFORE_FIRST_LINE);

        // ---------------------------
        // ÁREA COM SCROLL (produtos)
        // ---------------------------
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setBackground(new Color(55, 55, 55));
        area.setForeground(textColor);
        area.setFont(new Font("Consolas", Font.PLAIN, 15));
        area.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));

        JScrollPane scroll = new JScrollPane(
                area,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        scroll.setBorder(BorderFactory.createLineBorder(new Color(70, 70, 70)));
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        painel.add(scroll, BorderLayout.CENTER);

        // ---------------------------
        // LEITURA DO ARQUIVO

        try (BufferedReader br = new BufferedReader(new FileReader("delete_temp.txt"))) {

            String linha;
            boolean vazio = true;

            while ((linha = br.readLine()) != null) {
                linha = linha.trim();
                if (linha.isEmpty()) continue;

                String[] partes = linha.split(";");

                if (partes.length == 3) {
                    area.append(String.format("%-12s │ %-25s │ %-12s\n",
                            partes[0], partes[1], partes[2]));
                    area.append("──────────────────────────────────────────────────────────────\n");
                }

                vazio = false;
            }

            if (vazio) {
                area.setText("Nenhum produto encontrado.");
            }

        } catch (Exception e) {
            area.setText("Arquivo inexistente ou vazio.");
        }

        // ---------------------------
        // BOTÃO VOLTAR
        // ---------------------------
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnVoltar.setBackground(new Color(70, 70, 70));
        btnVoltar.setForeground(textColor);
        btnVoltar.setFocusPainted(false);

        JPanel rodape = new JPanel();
        rodape.setOpaque(false);
        rodape.add(btnVoltar);

        painel.add(rodape, BorderLayout.SOUTH);

        btnVoltar.addActionListener(e -> {
            new MenuInicial().setVisible(true);
            dispose();
        });

        // Centralizar tudo
        container.add(painel);
        add(container);

        setVisible(true);
    }

    public static void main(String[] args) {
        new TelaListar();
    }
}
