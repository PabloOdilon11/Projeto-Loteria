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
    private JTextField apostaField;
    private List<JTextField> apostaFields;
    private Loteria loteria;
    private JTextArea resultadoTextArea;
    private Usuario usuario;
    private JTabbedPane tabbedPane;
    private JButton depositoButton;

    public LoteriaGUI() {
        loteria = new Loteria();
        setTitle("Loteria");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Criando o painel de abas
        tabbedPane = new JTabbedPane();

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

        tabbedPane.addTab("Login", loginPanel);
        add(tabbedPane, BorderLayout.CENTER);

        // Ação do botão de login
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Login realizado com sucesso.");
                String nome = nomeField.getText();
                usuario = new Usuario(nome, 0); // Cria um usuário com saldo zero
                usuario.gerarID(); // Gera um ID único para o usuário

                // Criando o painel de aposta
                JPanel apostaPanel = criarPainelAposta();
                tabbedPane.addTab("Aposta", apostaPanel);
                tabbedPane.setSelectedComponent(apostaPanel);

                revalidate();
                repaint();
            }
        });

        // Parte do botão do perfil
        JButton perfilButton = new JButton("Perfil");
        perfilButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (usuario != null) {
                    exibirPerfil(usuario);
                } else {
                    JOptionPane.showMessageDialog(null, "Você precisa fazer login para acessar o perfil.");
                }
            }
        });
        add(perfilButton, BorderLayout.NORTH);
    }

    // Método para criar o painel de aposta
    private JPanel criarPainelAposta() {
        JPanel apostaPanel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel();
        JLabel label = new JLabel("Digite o valor do depósito:");
        depositoField = new JTextField(10);
        depositoButton = new JButton("Depositar");
        topPanel.add(label);
        topPanel.add(depositoField);
        topPanel.add(depositoButton);

        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new GridLayout(4, 2));
        JLabel instrucaoLabel = new JLabel("Digite o valor da aposta:");
        apostaField = new JTextField(10);
        middlePanel.add(instrucaoLabel);
        middlePanel.add(apostaField);
        JButton valorApostaButton = new JButton("Confirmar Valor");
        middlePanel.add(valorApostaButton);

        JPanel bottomPanel = new JPanel();
        resultadoTextArea = new JTextArea(10, 30);
        resultadoTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultadoTextArea);
        bottomPanel.add(scrollPane);

        apostaPanel.add(topPanel, BorderLayout.NORTH);
        apostaPanel.add(middlePanel, BorderLayout.CENTER);
        apostaPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Ação do botão de deposito
        depositoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double valor = Double.parseDouble(depositoField.getText());
                usuario.depositar(valor); // Deposita o valor para o usuário atual
                JOptionPane.showMessageDialog(null, "Depósito de R$" + valor + " realizado com sucesso.");
                depositoButton.setVisible(false); // Esconde o botão de depósito após o depósito ser feito
            }
        });

        // Ação do botão de confirmar valor de aposta
        valorApostaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double valorAposta = Double.parseDouble(apostaField.getText());
                if (valorAposta > usuario.getSaldo()) {
                    JOptionPane.showMessageDialog(null, "Você não pode apostar mais do que tem na conta.");
                } else if (valorAposta <= 0) {
                    JOptionPane.showMessageDialog(null, "O valor da aposta deve ser maior que zero.");
                } else {
                    fazerAposta(valorAposta);
                }
            }
        });

        return apostaPanel;
    }

    // Método para fazer a aposta
    private void fazerAposta(double valorAposta) {
        // Criando os campos de aposta
        JPanel apostaFieldsPanel = new JPanel(new GridLayout(2, 3));
        apostaFields = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            JTextField field = new JTextField(5);
            apostaFields.add(field);
            apostaFieldsPanel.add(field);
        }

        // Adicionando os campos de aposta ao painel
        JPanel apostaPanel = new JPanel(new BorderLayout());
        apostaPanel.add(apostaFieldsPanel, BorderLayout.CENTER);

        // Botão para realizar a aposta
        JButton realizarApostaButton = new JButton("Realizar Aposta");
        apostaPanel.add(realizarApostaButton, BorderLayout.SOUTH);

        // Ação do botão de realizar aposta
        realizarApostaButton.addActionListener(new ActionListener() {
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

                double valorGanho = 0;
                if (numerosCertos >= 4) {
                    if (numerosCertos == 6) {
                        valorGanho = 10 * valorAposta;
                    } else {
                        valorGanho = 0.1 * valorAposta;
                    }
                    usuario.ganharAposta(valorGanho); // Atualiza o saldo do usuário
                    resultado.append("Parabéns! Você acertou ").append(numerosCertos).append(" números. Você ganhou R$").append(valorGanho).append("!");
                } else {
                    usuario.perderAposta(valorAposta); // Atualiza o saldo do usuário
                    resultado.append("Você não acertou nem 4 números. Você perdeu tudo.");
                }

                // Exibindo o resultado da aposta na aba de aposta
                resultadoTextArea.setText(resultado.toString());

                if (usuario.getSaldo() <= 0) {
                    usuario.getSaldo(); // Zera o saldo do usuário
                    JOptionPane.showMessageDialog(null, "Você perdeu tudo. Seu saldo foi zerado.");
                    tabbedPane.removeTabAt(1); // Remove a aba de apostas
                    depositoButton.setVisible(true); // Mostra o botão de depósito
                }
            }
        });

        // Adicionando a aba de aposta à interface
        tabbedPane.addTab("Aposta", apostaPanel);
        tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1); // Seleciona a aba de aposta

        // Atualizando a aba de perfil para refletir o saldo atualizado após a aposta
        exibirPerfil(usuario);
    }

    // Método para exibir o perfil do usuário
    private void exibirPerfil(Usuario usuario) {
        StringBuilder perfil = new StringBuilder("Perfil do Usuário:\n");
        perfil.append("Nome: ").append(usuario.getNomeUsuario()).append("\n");
        perfil.append("ID: ").append(usuario.getId()).append("\n");
        perfil.append("Saldo: R$").append(usuario.getSaldo()).append("\n");
        JOptionPane.showMessageDialog(null, perfil.toString());
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
