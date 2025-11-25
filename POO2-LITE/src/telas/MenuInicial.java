package telas;
import javax.swing.*;

import java.awt.*;
import java.awt.event.WindowAdapter; // WindowAdapter é uma classe adaptadora para eventos de janela
import java.awt.event.WindowEvent; // WindowEvent é um evento que indica uma mudança na janela
import java.util.function.Supplier; // Supplier é uma interface funcional que fornece resultados sem aceitar argumentos

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
        btnCadastrar.addActionListener(e -> abrirTelaUnica(TelaCadastrar::new));
        btnListar.addActionListener(e -> abrirTelaUnica(TelaListagemProduto::new));
        btnAtualizar.addActionListener(e -> abrirTelaUnica(TelaAtualizar::new));
        btnExcluir.addActionListener(e -> abrirTelaUnica(TelaExcluir::new));
        btnSair.addActionListener(e -> System.exit(0));

        setVisible(true);
    }
    // Abre uma nova tela, permitindo apenas uma janela aberta por vez
    private void abrirTelaUnica(Supplier<? extends JFrame> criador) {
        // Verifica se há outra janela aberta
        if (haOutraJanelaAberta()) {
            JOptionPane.showMessageDialog(this,
                    "Feche a janela aberta antes de abrir outra tela do menu.",
                    "Janela já aberta",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Oculta o menu enquanto a nova tela está ativa
        setVisible(false);

        // Cria a nova janela através do Supplier fornecido
        JFrame novaTela = criador.get();
        novaTela.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                reexibirMenu();
            }

            @Override
            public void windowClosing(WindowEvent e) {
                reexibirMenu();
            }
        });
    }

    private boolean haOutraJanelaAberta() {
        for (Frame frame : Frame.getFrames()) {
            if (frame.isVisible() && frame != this) {
                return true;
            }
        }
        return false;
    }

    private void reexibirMenu() {
        setVisible(true);
        toFront();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(MenuInicial::new);
    }
}
