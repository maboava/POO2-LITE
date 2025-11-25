package telas;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class TelaAtualizar extends JFrame {

    public TelaAtualizar() {
        setTitle("Atualizar Produto");
        setSize(400, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Painel padrão (sem customização)
        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(7, 1, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // CAMPOS padrão
        JTextField txtCodigo = new JTextField();
        JTextField txtNovoNome = new JTextField();
        JTextField txtNovoPreco = new JTextField();

        // BOTÃO padrão
        JButton btnSalvar = new JButton("Atualizar");

        // LABELS padrão
        painel.add(new JLabel("Código:"));
        painel.add(txtCodigo);

        painel.add(new JLabel("Novo Nome:"));
        painel.add(txtNovoNome);

        painel.add(new JLabel("Novo Preço:"));
        painel.add(txtNovoPreco);

        painel.add(btnSalvar);

        add(painel);

        // Ação do botão
        btnSalvar.addActionListener(e ->
                atualizar(txtCodigo.getText(), txtNovoNome.getText(), txtNovoPreco.getText())
        );

        setVisible(true);
    }

    // Atualiza o arquivo
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
}
