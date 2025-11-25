package telas;
import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;

public class TelaCadastrar extends JFrame {

    public TelaCadastrar() {
        setTitle("Cadastrar Produto");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Painel padrão
        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(7, 1, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // CAMPOS padrões
        JTextField txtCodigo = new JTextField();
        JTextField txtNome = new JTextField();
        JTextField txtPreco = new JTextField();

        // BOTÃO padrão
        JButton btnSalvar = new JButton("Salvar");

        // LABELS padrão
        painel.add(new JLabel("Código:"));
        painel.add(txtCodigo);

        painel.add(new JLabel("Nome:"));
        painel.add(txtNome);

        painel.add(new JLabel("Preço:"));
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
}
