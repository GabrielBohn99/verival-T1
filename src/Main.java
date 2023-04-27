public class Main {
    public static void main(String[] args) {
        CentroDistribuicao c = new CentroDistribuicao(500, 10000, 1240, 1240);
        c.recebeAlcool(11);
        System.out.println(c.gettAlcool1());
        System.out.println(c.gettAlcool2());
        int[] combustivelEncomendado = c.encomendaCombustivel(100, c.getEstrategico());
        System.out.println(combustivelEncomendado[0]);
        System.out.println(combustivelEncomendado[1]);
        System.out.println(combustivelEncomendado[2]);
        System.out.println(combustivelEncomendado[3]);
        System.out.println(c.gettAlcool1());
        System.out.println(c.gettAlcool2());
    }
}