
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Loteria {
    private List<Integer> numerosSorteados;
    private static final int NUMERO_MAXIMO = 10;
    private static final int NUMERO_DE_SELECOES = 6;
    private Random random;

    public Loteria() {
        numerosSorteados = new ArrayList<>();
        random = new Random();
    }

    public void sortearNumeros() {
        numerosSorteados.clear();
        while (numerosSorteados.size() < NUMERO_DE_SELECOES) {
            int numero = random.nextInt(NUMERO_MAXIMO) + 1;
            if (!numerosSorteados.contains(numero)) {
                numerosSorteados.add(numero);
            }
        }
    }

    public boolean numeroEhVencedor(int numero) {
        return numerosSorteados.contains(numero);
    }

    public List<Integer> getNumerosSorteados() {
        return numerosSorteados;
    }
}