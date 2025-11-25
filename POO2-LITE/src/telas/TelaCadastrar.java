package telas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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

        // CAMPOS
        JTextField txtCodigo = new JTextField();
        JTextField txtNome = new JTextField();
        JTextField txtDescricao = new JTextField();
        JTextField txtPreco = new JTextField();
        JTextField txtQuantidade = new JTextField();

        // ---------- VALIDAÇÕES DOS CAMPOS ----------

        // 1) CÓDIGO: permitir somente números
        txtCodigo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                // bloqueia tudo que não for dígito e não for tecla de controle (backspace, delete, etc.)
                if (!Character.isDigit(c) && !Character.isISOControl(c)) {
                    e.consume();
                }
            }
        });

        // 2) NOME / DESCRIÇÃO: bloquear ponto e vírgula (;)
        KeyAdapter bloquearPontoEVirgula = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (c == ';') {
                    e.consume();
                }
            }
        };
        txtNome.addKeyListener(bloquearPontoEVirgula);
        txtDescricao.addKeyListener(bloquearPontoEVirgula);

        // 3) PREÇO / QUANTIDADE: aceitar só número, vírgula e ponto
        KeyAdapter numeroComVirgulaAdapter = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                // permite dígitos, vírgula, ponto e teclas de controle
                if (!Character.isDigit(c) && c != ',' && c != '.' && !Character.isISOControl(c)) {
                    e.consume();
                }
            }
        };
        txtPreco.addKeyListener(numeroComVirgulaAdapter);
        txtQuantidade.addKeyListener(numeroComVirgulaAdapter);

        // BOTÃO
        JButton btnSalvar = new JButton("Salvar");

        // LABELS + CAMPOS
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

        // ---------- AÇÃO DO BOTÃO SALVAR ----------
        btnSalvar.addActionListener(e -> {
            try {
                String codigo = txtCodigo.getText().trim();
                String nome = txtNome.getText().trim();
                String descricao = txtDescricao.getText().trim();

                // troca vírgula por ponto para o parse funcionar
                String precoTexto = txtPreco.getText().trim().replace(",", ".");
                String quantidadeTexto = txtQuantidade.getText().trim().replace(",", ".");

                if (codigo.isEmpty() || nome.isEmpty() || descricao.isEmpty()
                        || precoTexto.isEmpty() || quantidadeTexto.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Preencha todos os campos!",
                            "Aviso",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                double preco = Double.parseDouble(precoTexto);

                // quantidade como número, mas garantindo que seja inteiro
                double quantidadeDouble = Double.parseDouble(quantidadeTexto);
                if (quantidadeDouble % 1 != 0) {
                    JOptionPane.showMessageDialog(this,
                            "A quantidade deve ser um número inteiro.",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int quantidade = (int) quantidadeDouble;

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
                    JOptionPane.showMessageDialog(this,
                            "Já existe um produto com esse código.",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Preço ou quantidade inválidos!",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }
}
