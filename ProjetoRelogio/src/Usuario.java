import java.util.UUID;

class Usuario {
    private String id;
    private String nomeUsuario;
    private double saldo;

    public Usuario(String nomeUsuario, double saldoInicial) {
        this.nomeUsuario = nomeUsuario;
        this.saldo = saldoInicial;
        gerarID();
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public String getId() {
        return id;
    }

    public double getSaldo() {
        return saldo;
    }

    void gerarID() {
        this.id = UUID.randomUUID().toString();
    }

    public void depositar(double valor) {
        saldo += valor;
        System.out.println("Depósito de R$" + valor + " realizado com sucesso para o usuário " + nomeUsuario + ".");
    }

    public void ganharAposta(double valorGanho) {
        saldo += valorGanho;
        System.out.println("Parabéns! Você ganhou R$" + valorGanho + " na aposta.");
    }

    public void perderAposta(double valorPerdido) {
        saldo -= valorPerdido;
        System.out.println("Infelizmente você perdeu R$" + valorPerdido + " na aposta.");
    }

    public int[] getNumerosApostados() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNumerosApostados'");
    }
}
