package telas;
import javax.swing.*;
import java.awt.*;

public class TelaExcluir extends JFrame {

    public TelaExcluir() {
        setTitle("Excluir Produto");
        setSize(350, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Painel padrão sem estilização
        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(3, 1, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Componentes padrão
        JLabel lblCodigo = new JLabel("Código do Produto:");
        JTextField txtCodigo = new JTextField();
        JButton btnExcluir = new JButton("Excluir");

        painel.add(lblCodigo);
        painel.add(txtCodigo);
        painel.add(btnExcluir);

        add(painel);

        // Evento
        btnExcluir.addActionListener(e -> excluir(txtCodigo.getText()));

        setVisible(true);
    }

    // Lógica de exclusão (persistente)
    private void excluir(String codigo) {
        boolean removido = ProdutoDAO.getInstance().removerProduto(codigo.trim());

        if (removido)
            JOptionPane.showMessageDialog(this, "Produto excluído com sucesso!");
        else
            JOptionPane.showMessageDialog(this, "Código não encontrado.");
    }
}
