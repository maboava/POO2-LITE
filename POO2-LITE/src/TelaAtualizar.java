import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class TelaAtualizar extends JFrame {

    Color bg = new Color(30, 30, 30);
    Color panelBg = new Color(45, 45, 45);
    Color btnBg = new Color(60, 60, 60);
    Color btnHover = new Color(90, 90, 90);
    Color textColor = new Color(240, 240, 240); // LETRAS SUPER VISÍVEIS

    public TelaAtualizar() {
        setTitle("Atualizar Produto");
        setSize(400, 380);
        setLocationRelativeTo(null);
        getContentPane().setBackground(bg);

        // Painel com fundo arredondado
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
        painel.setLayout(new GridLayout(7, 1, 10, 10)); // AJUSTADO
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // CAMPOS
        JTextField txtCodigo = criarCampoTexto();
        JTextField txtNovoNome = criarCampoTexto();
        JTextField txtNovoPreco = criarCampoTexto();

        // BOTÃO
        JButton btnSalvar = criarBotao("Atualizar", btnBg, btnHover, textColor);

        // LABELS NÍTIDOS
        painel.add(criarLabel("Código:"));
        painel.add(txtCodigo);

        painel.add(criarLabel("Novo Nome:"));
        painel.add(txtNovoNome);

        painel.add(criarLabel("Novo Preço:"));
        painel.add(txtNovoPreco);

        painel.add(btnSalvar);

        add(painel);

        btnSalvar.addActionListener(e ->
                atualizar(txtCodigo.getText(), txtNovoNome.getText(), txtNovoPreco.getText())
        );

        setVisible(true);
    }

    // 🔹 Atualizar arquivo
    private void atualizar(String codigo, String nome, String preco) {
        try {
            File arquivo = new File("delete_temp.txt");

            BufferedReader br = new BufferedReader(new FileReader(arquivo));
            ArrayList<String> linhas = new ArrayList<>();

            String linha;
            boolean encontrado = false;

            while ((linha = br.readLine()) != null) {
                if (linha.startsWith(codigo + ";")) {
                    linhas.add(codigo + ";" + nome + ";" + preco);
                    encontrado = true;
                } else {
                    linhas.add(linha);
                }
            }

            br.close();

            PrintWriter pw = new PrintWriter(new FileWriter(arquivo));
            for (String l : linhas) pw.println(l);
            pw.close();

            if (encontrado)
                JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso!");
            else
                JOptionPane.showMessageDialog(this, "Código não encontrado!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    // 🔹 Labels super visíveis
    private JLabel criarLabel(String texto) {
        JLabel lbl = new JLabel(texto, SwingConstants.LEFT);
        lbl.setForeground(textColor);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        return lbl;
    }

    // 🔹 Campos bem visíveis
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
