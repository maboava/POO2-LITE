import javax.swing.*;
import java.awt.*;

public class MenuInicial extends JFrame {

    public MenuInicial() {
        setTitle("Menu Inicial");
        setSize(380, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Color bg = new Color(30, 30, 30);
        Color panelBg = new Color(45, 45, 45);
        Color btnBg = new Color(60, 60, 60);
        Color btnHover = new Color(90, 90, 90);
        Color textColor = new Color(230, 230, 230);

        setLayout(new BorderLayout());
        getContentPane().setBackground(bg);

        JPanel painel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(panelBg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            }
        };

        painel.setOpaque(false);
        painel.setLayout(new GridLayout(5, 1, 15, 15));
        painel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JButton btnCadastrar = criarBotao("Cadastrar", btnBg, btnHover, textColor);
        JButton btnListar = criarBotao("Listar", btnBg, btnHover, textColor);
        JButton btnAtualizar = criarBotao("Atualizar", btnBg, btnHover, textColor);
        JButton btnExcluir = criarBotao("Excluir", btnBg, btnHover, textColor);
        JButton btnSair = criarBotao("Sair", new Color(130, 0, 0), new Color(180, 0, 0), Color.WHITE);

        painel.add(btnCadastrar);
        painel.add(btnListar);
        painel.add(btnAtualizar);
        painel.add(btnExcluir);
        painel.add(btnSair);

        add(painel);

        btnCadastrar.addActionListener(e -> new TelaCadastrar());
        btnListar.addActionListener(e -> new TelaListar());
        btnAtualizar.addActionListener(e -> new TelaAtualizar());
        btnExcluir.addActionListener(e -> new TelaExcluir());
        btnSair.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    private JButton criarBotao(String texto, Color bg, Color hover, Color text) {
        JButton btn = new JButton(texto);
        btn.setFocusPainted(false);
        btn.setBackground(bg);
        btn.setForeground(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { btn.setBackground(hover); }
            public void mouseExited(java.awt.event.MouseEvent evt) { btn.setBackground(bg); }
        });

        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MenuInicial::new);
    }
}
