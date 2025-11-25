package telas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.Supplier;

public class MenuInicial extends JFrame {

    private JButton btnListar;
    private JButton btnSair;

    public MenuInicial() {
        setTitle("Menu Inicial");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Painel de fundo com imagem
        setContentPane(criarPainelComBackground());

        // Tela cheia (maximizado)
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Centraliza (irrelevante pra maximizado, mas ok)
        setLocationRelativeTo(null);

        setVisible(true);
    }

    /**
     * Cria o painel principal com a imagem de fundo e os componentes sobrepostos.
     */
    private JPanel criarPainelComBackground() {
        // Carrega a imagem de fundo (src/img/background.png)
        final Image bgImage;
        Image tempImage = null;
        try {
            tempImage = new ImageIcon(getClass().getResource("/img/background.png")).getImage();
        } catch (Exception e) {
            System.err.println("Não foi possível carregar /img/background.png. Verifique o caminho.");
        }
        bgImage = tempImage;

        JPanel painelBackground = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bgImage != null) {
                    // Desenha a imagem ocupando todo o painel
                    g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        painelBackground.setLayout(new BorderLayout());

        // ---------------- BARRA SUPERIOR ----------------
        JPanel barraSuperior = new JPanel(new BorderLayout());
        barraSuperior.setBackground(new Color(0, 0, 0, 180)); // preto semi-transparente

        // Título à esquerda
        JLabel lblTitulo = new JLabel("Mercearia do Seu Zé - Menu Inicial");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        barraSuperior.add(lblTitulo, BorderLayout.WEST);

        // ---- Menu de botões em cima (tipo barra) ----
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        painelBotoes.setOpaque(false); // deixa ver o fundo da barra

        btnListar = new JButton("Listar");
        btnSair = new JButton("Sair");

        Font fonte = new Font("Segoe UI", Font.PLAIN, 14);
        btnListar.setFont(fonte);
        btnSair.setFont(fonte);

        // Dá uma folguinha interna nos botões pra não ficar tosco
        Insets margin = new Insets(4, 10, 4, 10);
        btnListar.setMargin(margin);
        btnSair.setMargin(margin);

        painelBotoes.add(btnListar);
        painelBotoes.add(btnSair);

        barraSuperior.add(painelBotoes, BorderLayout.EAST);

        painelBackground.add(barraSuperior, BorderLayout.NORTH);

        // (Nada no CENTER – só a imagem de fundo)
        // painelBackground.add(..., BorderLayout.CENTER);

        // Ações dos botões
        btnListar.addActionListener(e -> abrirTelaUnica(TelaListagemProduto::new));
        btnSair.addActionListener(e -> System.exit(0));

        return painelBackground;
    }

    // Abre uma nova tela, permitindo apenas uma janela aberta por vez
    private void abrirTelaUnica(Supplier<? extends JFrame> criador) {
        if (haOutraJanelaAberta()) {
            JOptionPane.showMessageDialog(this,
                    "Feche a janela aberta antes de abrir outra tela do menu.",
                    "Janela já aberta",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        manterMenuVisivel();
        atualizarEstadoMenu(false);

        JFrame novaTela = criador.get();
        novaTela.setAlwaysOnTop(true);
        novaTela.setLocationRelativeTo(this);
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
        manterMenuVisivel();
        atualizarEstadoMenu(true);
    }

    private void atualizarEstadoMenu(boolean habilitar) {
        btnListar.setEnabled(habilitar);
        btnSair.setEnabled(habilitar);
    }

    private void manterMenuVisivel() {
        if (!isVisible()) {
            setVisible(true);
        }
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        toFront();
        requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MenuInicial::new);
    }
}
