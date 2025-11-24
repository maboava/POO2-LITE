package telas;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class TelaListagemProduto extends JFrame {

    public TelaListagemProduto() {
        super("Listagem de Produto");

        JLabel label = new JLabel("Hello World", SwingConstants.CENTER);
        add(label);

        setSize(300, 200);
        setLocationRelativeTo(null); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}