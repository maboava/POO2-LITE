import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;

public class TelaCadastrar extends JFrame {

    Color bg = new Color(30, 30, 30);
    Color panelBg = new Color(45, 45, 45);
    Color btnBg = new Color(60, 60, 60);
    Color btnHover = new Color(90, 90, 90);
    Color textColor = new Color(240, 240, 240); // LETRAS MAIS VISÍVEIS

    public TelaCadastrar() {
        setTitle("Cadastrar Produto");
        setSize(400, 380);
        setLocationRelativeTo(null);
        getContentPane().setBackground(bg);

        // Painel principal com fundo arredondado
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
        painel.setLayout(new GridLayout(7, 1, 10, 10)); // Ajustado
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // CAMPOS
        JTextField txtCodigo = criarCampoTexto();
        JTextField txtNome = criarCampoTexto();
        JTextField txtPreco = criarCampoTexto();

        // BOTÃO
        JButton btnSalvar = criarBotao("Salvar", btnBg, btnHover, textColor);

        // LABELS COM ALTA VISIBILIDADE
        painel.add(criarLabel("Código:"));
        painel.add(txtCodigo);

        painel.add(criarLabel("Nome:"));
        painel.add(txtNome);

        painel.add(criarLabel("Preço:"));
        painel.add(txtPreco);

        painel.add(btnSalvar);

        add(painel);

        // AÇÃO DO BOTÃO
        btnSalvar.addActionListener(e -> {
            try (FileWriter fw = new FileWriter("delete_temp.txt", true)) {
                fw.write(txtCodigo.getText() + ";" + txtNome.getText() + ";" + txtPreco.getText() + "\n");
                JOptionPane.showMessageDialog(this, "Produto cadastrado!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });

        setVisible(true);
    }

    // 🔹 Labels muito mais nítidos
    private JLabel criarLabel(String texto) {
        JLabel lbl = new JLabel(texto, SwingConstants.LEFT);
        lbl.setForeground(textColor);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18)); // fonte grande e nítida
        return lbl;
    }

    // 🔹 Campos com fonte maior e texto claro
    private JTextField criarCampoTexto() {
        JTextField campo = new JTextField();
        campo.setForeground(Color.WHITE);
        campo.setBackground(new Color(70, 70, 70));
        campo.setCaretColor(Color.WHITE);
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 16)); // texto aparece muito mais
        return campo;
    }

    // 🔹 Botões com texto forte
    private JButton criarBotao(String texto, Color bg, Color hover, Color text) {
        JButton btn = new JButton(texto);
        btn.setFocusPainted(false);
        btn.setBackground(bg);
        btn.setForeground(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18)); // maior e mais visível

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { btn.setBackground(hover); }
            public void mouseExited(java.awt.event.MouseEvent evt) { btn.setBackground(bg); }
        });

        return btn;
    }
}
