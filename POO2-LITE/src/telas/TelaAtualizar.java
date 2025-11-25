package telas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TelaAtualizar extends JFrame {

    public TelaAtualizar() {
        setTitle("Atualizar Produto");
        setSize(400, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Painel padrão (sem customização)
        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(11, 1, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // CAMPOS padrão
        JTextField txtCodigo = new JTextField();
        JTextField txtNovoNome = new JTextField();
        JTextField txtNovaDescricao = new JTextField();
        JTextField txtNovoPreco = new JTextField();
        JTextField txtNovaQuantidade = new JTextField();

        // ---------- VALIDAÇÕES DOS CAMPOS ----------

        // 1) CÓDIGO: permitir somente números
        txtCodigo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && !Character.isISOControl(c)) {
                    e.consume();
                }
            }
        });

        // 2) NOME / DESCRIÇÃO: bloquear ponto e vírgula (;) e deixar texto em caixa alta
        KeyAdapter bloquearPontoEVirgula = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (c == ';') {
                    e.consume();
                } else {
                    e.consume();
                    char upperCaseChar = Character.toUpperCase(c);
                    JTextField source = (JTextField) e.getSource();
                    int pos = source.getCaretPosition();
                    source.setText(source.getText().substring(0, pos) + upperCaseChar + source.getText().substring(pos));
                    source.setCaretPosition(pos + 1);
                }
            }
        };
        txtNovoNome.addKeyListener(bloquearPontoEVirgula);
        txtNovaDescricao.addKeyListener(bloquearPontoEVirgula);

        // 3) PREÇO / QUANTIDADE: aceitar só número, vírgula e bloquear ponto
        KeyAdapter numeroComVirgulaAdapter = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != ',' && !Character.isISOControl(c)) {
                    e.consume();
                }
            }
        };
        txtNovoPreco.addKeyListener(numeroComVirgulaAdapter);
        txtNovaQuantidade.addKeyListener(numeroComVirgulaAdapter);

        // BOTÃO padrão
        JButton btnSalvar = new JButton("Atualizar");

        // LABELS padrão
        painel.add(new JLabel("Código:"));
        painel.add(txtCodigo);

        painel.add(new JLabel("Novo Nome:"));
        painel.add(txtNovoNome);

        painel.add(new JLabel("Nova Descrição:"));
        painel.add(txtNovaDescricao);

        painel.add(new JLabel("Novo Preço:"));
        painel.add(txtNovoPreco);

        painel.add(new JLabel("Nova Quantidade:"));
        painel.add(txtNovaQuantidade);

        painel.add(btnSalvar);

        add(painel);

        // Ação do botão
        btnSalvar.addActionListener(e -> atualizar(
                txtCodigo.getText(),
                txtNovoNome.getText(),
                txtNovaDescricao.getText(),
                txtNovoPreco.getText(),
                txtNovaQuantidade.getText()
        ));

        setVisible(true);
    }

    // Atualiza o "banco" único
    private void atualizar(String codigo, String nome, String descricao, String preco, String quantidade) {
        try {
            Produto existente = ProdutoDAO.getInstance().buscarProduto(codigo.trim());
            if (existente == null) {
                JOptionPane.showMessageDialog(this, "Código não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String precoTexto = preco.trim().replace(",", ".");
            String quantidadeTexto = quantidade.trim().replace(",", ".");

            if (codigo.trim().isEmpty() || nome.trim().isEmpty() || descricao.trim().isEmpty()
                    || precoTexto.isEmpty() || quantidadeTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            double novoPreco = Double.parseDouble(precoTexto);

            double novaQtdDouble = Double.parseDouble(quantidadeTexto);
            if (novaQtdDouble % 1 != 0) {
                JOptionPane.showMessageDialog(this, "A quantidade deve ser um número inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int novaQtd = (int) novaQtdDouble;

            Produto atualizado = new Produto(codigo.trim(), nome.trim(), descricao.trim(), novoPreco, novaQtd);
            boolean sucesso = ProdutoDAO.getInstance().atualizarProduto(atualizado);

            if (sucesso)
                JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso!");
            else
                JOptionPane.showMessageDialog(this, "Não foi possível atualizar o produto.", "Erro", JOptionPane.ERROR_MESSAGE);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Preço ou quantidade inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
