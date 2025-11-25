import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class TelaExcluir extends JFrame {

    Color bg = new Color(30, 30, 30);
    Color panelBg = new Color(45, 45, 45);
    Color btnBg = new Color(130, 0, 0);
    Color btnHover = new Color(180, 0, 0);
    Color textColor = new Color(240, 240, 240); // LETRAS SUPER VISÍVEIS

    public TelaExcluir() {
        setTitle("Excluir Produto");
        setSize(350, 260);
        setLocationRelativeTo(null);
        getContentPane().setBackground(bg);

        // Painel arredondado igual às outras telas
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
        painel.setLayout(new GridLayout(4, 1, 12, 12)); // Ajustado
        painel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // Campo de texto estilizado
        JTextField txtCodigo = criarCampoTexto();

        // Botão estilizado
        JButton btnExcluir = criarBotao("Excluir", btnBg, btnHover, Color.WHITE);

        // Labels nítidos
        painel.add(criarLabel("Código do Produto:"));
        painel.add(txtCodigo);
        painel.add(btnExcluir);

        add(painel);

        btnExcluir.addActionListener(e -> excluir(txtCodigo.getText()));

        setVisible(true);
    }

    // 🔹 Lógica de exclusão
    private void excluir(String codigo) {
        try {
            File arquivo = new File("delete_temp.txt");

            BufferedReader br = new BufferedReader(new FileReader(arquivo));
            ArrayList<String> linhas = new ArrayList<>();

            String linha;
            boolean encontrado = false;

            while ((linha = br.readLine()) != null) {
                if (!linha.startsWith(codigo + ";")) {
                    linhas.add(linha);
                } else {
                    encontrado = true;
                }
            }

            br.close();

            PrintWriter pw = new PrintWriter(new FileWriter(arquivo));
            for (String l : linhas) pw.println(l);
            pw.close();

            if (encontrado)
                JOptionPane.showMessageDialog(this, "Produto excluído com sucesso!");
            else
                JOptionPane.showMessageDialog(this, "Código não encontrado.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    // 🔹 Labels bem brancos e visíveis
    private JLabel criarLabel(String texto) {
        JLabel lbl = new JLabel(texto, SwingConstants.LEFT);
        lbl.setForeground(textColor);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        return lbl;
    }

    // 🔹 Campos de texto com fonte maior
    private JTextField criarCampoTexto() {
        JTextField campo = new JTextField();
        campo.setForeground(Color.WHITE);
        campo.setBackground(new Color(70, 70, 70));
        campo.setCaretColor(Color.WHITE);
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        return campo;
    }

    // 🔹 Botão estilizado
    private JButton criarBotao(String texto, Color bg, Color hover, Color text) {
        JButton btn = new JButton(texto);
        btn.setFocusPainted(false);
        btn.setBackground(bg);
        btn.setForeground(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { btn.setBackground(hover); }
            public void mouseExited(java.awt.event.MouseEvent evt) { btn.setBackground(bg); }
        });

        return btn;
    }
}
