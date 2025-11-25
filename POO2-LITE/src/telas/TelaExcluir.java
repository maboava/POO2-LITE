package telas;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class TelaExcluir extends JFrame {

    public TelaExcluir() {
        setTitle("Excluir Produto");
        setSize(350, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Painel padrão sem estilização
        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(3, 1, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Componentes padrão
        JLabel lblCodigo = new JLabel("Código do Produto:");
        JTextField txtCodigo = new JTextField();
        JButton btnExcluir = new JButton("Excluir");

        painel.add(lblCodigo);
        painel.add(txtCodigo);
        painel.add(btnExcluir);

        add(painel);

        // Evento
        btnExcluir.addActionListener(e -> excluir(txtCodigo.getText()));

        setVisible(true);
    }

    // Lógica de exclusão (mantida)
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
}
