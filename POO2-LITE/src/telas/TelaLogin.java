package telas;

import javax.swing.*;
import java.awt.*;

/**
 * Tela de Login simples com usuário/senha fixos: admin / admin.
 * Sem main(), para ser chamada a partir da Main.java.
 */
public class TelaLogin extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtSenha;
    private JButton btnEntrar;
    private JButton btnSair;
    private JLabel lblMensagem;

    public TelaLogin() {
        setTitle("Login do Sistema");
        setSize(400, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        configurarComponentes();
    }

    private void configurarComponentes() {

        // Painel principal com fundo padrão e form centralizado
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        JPanel painelCentro = new JPanel(new GridBagLayout());
        painelCentro.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        int y = 0;

        // Mensagem (erros)
        lblMensagem = new JLabel(" ", SwingConstants.CENTER);
        lblMensagem.setForeground(new Color(200, 0, 0));
        gbc.gridx = 0;
        gbc.gridy = y++;
        gbc.gridwidth = 2;
        painelCentro.add(lblMensagem, gbc);

        // Usuário
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = y;
        painelCentro.add(new JLabel("Usuário:"), gbc);

        gbc.gridx = 1;
        txtUsuario = new JTextField();
        txtUsuario.setColumns(15); // aumenta o campo
        painelCentro.add(txtUsuario, gbc);

        // Senha
        gbc.gridy = ++y;
        gbc.gridx = 0;
        painelCentro.add(new JLabel("Senha:"), gbc);

        gbc.gridx = 1;
        txtSenha = new JPasswordField();
        txtSenha.setColumns(15); // aumenta o campo
        painelCentro.add(txtSenha, gbc);

        // Botões
        gbc.gridy = ++y;
        gbc.gridx = 0;
        btnSair = new JButton("Sair");
        painelCentro.add(btnSair, gbc);

        gbc.gridx = 1;
        btnEntrar = new JButton("Entrar");
        painelCentro.add(btnEntrar, gbc);

        // Ações
        btnEntrar.addActionListener(e -> realizarLogin());
        txtSenha.addActionListener(e -> realizarLogin());
        btnSair.addActionListener(e -> System.exit(0));

        painelPrincipal.add(painelCentro, BorderLayout.CENTER);
        setContentPane(painelPrincipal);
    }

    private void realizarLogin() {
        String usuario = txtUsuario.getText().trim();
        String senha = new String(txtSenha.getPassword());

        if (usuario.equals("admin") && senha.equals("admin")) {
            lblMensagem.setText(" ");

            JOptionPane.showMessageDialog(
                    this,
                    "Bem vindo(a) Administrador!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE
            );

            
            new MenuInicial().setVisible(true);
            dispose();

        } else {
            lblMensagem.setText("Usuário ou senha inválidos.");
            txtSenha.setText("");
            txtSenha.requestFocus();
        }
    }
}
