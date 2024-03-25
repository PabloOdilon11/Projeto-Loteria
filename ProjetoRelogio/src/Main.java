
import java.util.Scanner;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Loteria loteria = new Loteria();
            SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LoteriaGUI loteriaGUI = new LoteriaGUI();
                loteriaGUI.setVisible(true);
                
                // Abre uma nova janela com a interface
                LoteriaGUI outraJanela = new LoteriaGUI();
                outraJanela.setVisible(true);
            }
        });

            System.out.print("Digite o valor que deseja depositar: ");
            double valorDeposito = scanner.nextDouble();

            Usuario usuario = new Usuario(valorDeposito);

            System.out.println("Seu saldo atual: R$" + usuario.getSaldo());

            // Solicitar ao usuário os números para apostar
            System.out.println("Escolha 6 números de 1 a 30 para apostar:");
            for (int i = 0; i < 6; i++) {
                System.out.print("Número " + (i + 1) + ": ");
                int numeroAposta = scanner.nextInt();
                usuario.fazerAposta(numeroAposta);
            }

            loteria.sortearNumeros(); // Sortear os números vencedores

            // Verificar se os números apostados são vencedores
            for (int numero : usuario.getNumerosApostados()) {
                if (loteria.numeroEhVencedor(numero)) {
                    System.out.println("Parabéns! Você acertou o número " + numero + "!");
                } else {
                    System.out.println("Infelizmente o número " + numero + " não foi sorteado.");
                }
            }
        }
    }
}