package telas;
import javax.swing.*;
import java.awt.*;

public class TelaCadastrar extends JFrame {

    public TelaCadastrar() {
        setTitle("Cadastrar Produto");
        setSize(400, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Painel padrão
        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(11, 1, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // CAMPOS padrões
        JTextField txtCodigo = new JTextField();
        JTextField txtNome = new JTextField();
        JTextField txtDescricao = new JTextField();
        JTextField txtPreco = new JTextField();
        JTextField txtQuantidade = new JTextField();

        // BOTÃO padrão
        JButton btnSalvar = new JButton("Salvar");

        // LABELS padrão
        painel.add(new JLabel("Código:"));
        painel.add(txtCodigo);

        painel.add(new JLabel("Nome:"));
        painel.add(txtNome);

        painel.add(new JLabel("Descrição:"));
        painel.add(txtDescricao);

        painel.add(new JLabel("Preço:"));
        painel.add(txtPreco);

        painel.add(new JLabel("Quantidade:"));
        painel.add(txtQuantidade);

        painel.add(btnSalvar);

        add(painel);

        // AÇÃO DO BOTÃO
        btnSalvar.addActionListener(e -> {
            try {
                String codigo = txtCodigo.getText().trim();
                String nome = txtNome.getText().trim();
                String descricao = txtDescricao.getText().trim();
                double preco = Double.parseDouble(txtPreco.getText().trim());
                int quantidade = Integer.parseInt(txtQuantidade.getText().trim());

                if (codigo.isEmpty() || nome.isEmpty() || descricao.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Produto novo = new Produto(codigo, nome, descricao, preco, quantidade);
                boolean cadastrado = ProdutoDAO.getInstance().adicionarProduto(novo);

                if (cadastrado) {
                    JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso!");
                    txtCodigo.setText("");
                    txtNome.setText("");
                    txtDescricao.setText("");
                    txtPreco.setText("");
                    txtQuantidade.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Já existe um produto com esse código.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Preço ou quantidade inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }
}
