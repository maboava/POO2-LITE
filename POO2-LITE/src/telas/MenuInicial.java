package telas;
import javax.swing.*;

import java.awt.*;

public class MenuInicial extends JFrame {

    public MenuInicial() {
        setTitle("Menu Inicial");
        setSize(380, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout padrão (sem customização)
        setLayout(new BorderLayout());

        // Painel padrão do Swing
        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(5, 1, 15, 15));
        painel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // Botões padrão (sem cores personalizadas)
        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnListar = new JButton("Listar");
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnSair = new JButton("Sair");

        btnCadastrar.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnListar.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnAtualizar.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnExcluir.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnSair.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        // Adiciona os botões no painel
        painel.add(btnCadastrar);
        painel.add(btnListar);
        painel.add(btnAtualizar);
        painel.add(btnExcluir);
        painel.add(btnSair);

        add(painel, BorderLayout.CENTER);

        // Ações dos botões
        btnCadastrar.addActionListener(e -> new TelaCadastrar());
        btnListar.addActionListener(e -> new TelaListagemProduto());
        btnAtualizar.addActionListener(e -> new TelaAtualizar());
        btnExcluir.addActionListener(e -> new TelaExcluir());
        btnSair.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MenuInicial::new);
    }
}
