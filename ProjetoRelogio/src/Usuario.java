import java.util.ArrayList;
import java.util.List;


class Usuario {
    private double saldo;
    private List<Integer> numerosApostados;

    public Usuario(double saldoInicial) {
        saldo = saldoInicial;
        numerosApostados = new ArrayList<>();
    }

    // Método para depositar dinheiro na conta do usuário
    public void depositar(double valor) {
        saldo += valor;
        System.out.println("Depósito de R$" + valor + " realizado com sucesso.");
    }

    // Método para realizar uma aposta
    public void fazerAposta(int numero) {
        numerosApostados.add(numero);
        System.out.println("Aposta no número " + numero + " realizada com sucesso.");
    }

    public List<Integer> getNumerosApostados() {
        return numerosApostados;
    }

    public double getSaldo() {
        return saldo;
    }

    // Outros métodos, se necessário...
}