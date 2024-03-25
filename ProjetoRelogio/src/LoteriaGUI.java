import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class LoteriaGUI extends JFrame {
    private JTextField nomeField;
    private JTextField numeroField;
    private JTextField cpfField;
    private JTextField depositoField;
    private List<JTextField> apostaFields;
    private Loteria loteria;
    private JTextArea resultadoTextArea;

    public LoteriaGUI() {
        loteria = new Loteria();
        setTitle("Loteria");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Parte do painel de login
        JPanel loginPanel = new JPanel(new GridLayout(4, 2));
        JLabel nomeLabel = new JLabel("Nome:");
        nomeField = new JTextField();
        JLabel numeroLabel = new JLabel("Número:");
        numeroField = new JTextField();
        JLabel cpfLabel = new JLabel("CPF:");
        cpfField = new JTextField();
        JButton loginButton = new JButton("Login");
        loginPanel.add(nomeLabel);
        loginPanel.add(nomeField);
        loginPanel.add(numeroLabel);
        loginPanel.add(numeroField);
        loginPanel.add(cpfLabel);
        loginPanel.add(cpfField);
        loginPanel.add(loginButton);

        // Parte do painel principal de aposta
        JPanel apostaPanel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel();
        JLabel label = new JLabel("Digite o valor do depósito:");
        depositoField = new JTextField(10);
        JButton depositoButton = new JButton("Depositar");
        topPanel.add(label);
        topPanel.add(depositoField);
        topPanel.add(depositoButton);

        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new GridLayout(7, 1));
        JLabel instrucaoLabel = new JLabel("Escolha 6 números de 1 a 10:");
        middlePanel.add(instrucaoLabel);
        apostaFields = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            JTextField field = new JTextField(5);
            apostaFields.add(field);
            middlePanel.add(field);
        }
        JButton apostaButton = new JButton("Fazer Aposta");
        middlePanel.add(apostaButton);

        JPanel bottomPanel = new JPanel();
        resultadoTextArea = new JTextArea(10, 30);
        resultadoTextArea.setEditable(false);
        bottomPanel.add(new JScrollPane(resultadoTextArea));

        apostaPanel.add(topPanel, BorderLayout.NORTH);
        apostaPanel.add(middlePanel, BorderLayout.CENTER);
        apostaPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(loginPanel, BorderLayout.CENTER);

        // Ação do botao de login
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Aqui pode adicionar a lógica de verificação do login arthur mzr e lucca
                JOptionPane.showMessageDialog(null, "Login realizado com sucesso.");
                remove(loginPanel);
                add(apostaPanel, BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        });

        // Ação do botao de deposito 
        depositoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double valor = Double.parseDouble(depositoField.getText());
                JOptionPane.showMessageDialog(null, "Depósito de R$" + valor + " realizado com sucesso.");
            }
        });

        // Ação do botão de aposta
        apostaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Integer> numerosApostados = new ArrayList<>();
                for (JTextField field : apostaFields) {
                    int numero = Integer.parseInt(field.getText());
                    numerosApostados.add(numero);
                }
                loteria.sortearNumeros();
                List<Integer> numerosSorteados = loteria.getNumerosSorteados();
                StringBuilder resultado = new StringBuilder("Números sorteados: ");
                resultado.append(numerosSorteados.toString()).append("\n");

                int numerosCertos = 0;
                for (int numeroApostado : numerosApostados) {
                    if (numerosSorteados.contains(numeroApostado)) {
                        numerosCertos++;
                    }
                }

                double valorApostado = Double.parseDouble(depositoField.getText());
                double valorGanho = 0;
                if (numerosCertos >= 4) {
                    if (numerosCertos == 6) {
                        valorGanho = 10 * valorApostado;
                    } else {
                        valorGanho = 0.1 * valorApostado;
                    }
                    resultado.append("Parabéns! Você acertou ").append(numerosCertos).append(" números. Você ganhou R$").append(valorGanho).append("!");
                } else {
                    resultado.append("Você não acertou nem 4 números. Você perdeu tudo.");
                }

                resultadoTextArea.setText(resultado.toString());
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LoteriaGUI loteriaGUI = new LoteriaGUI();
                loteriaGUI.setVisible(true);
            }
        });
    }
}