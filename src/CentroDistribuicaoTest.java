import org.junit.Test;
import static org.junit.Assert.*;

public class CentroDistribuicaoTest {

    @Test
    public void testConstrutorValoresValidos() {
        CentroDistribuicao centro = new CentroDistribuicao(250, 5000, 1000, 1000);
        assertEquals(250, centro.gettAditivo());
        assertEquals(5000, centro.gettGasolina());
        assertEquals(1000, centro.gettAlcool1());
        assertEquals(1000, centro.gettAlcool2());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstrutorGasolinaInvalida() {
        CentroDistribuicao centro = new CentroDistribuicao(250, 15000, 1000, 1000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstrutorAditivoInvalido() {
        CentroDistribuicao centro = new CentroDistribuicao(600, 5000, 1000, 1000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstrutorAlcoolInvalido() {
        CentroDistribuicao centro = new CentroDistribuicao(250, 5000, 3000, 3000);
    }

    @Test
    public void testDefineSituacaoNormal() {
        CentroDistribuicao centro = new CentroDistribuicao(250, 5000, 1250, 1250);
        assertEquals(CentroDistribuicao.SITUACAO.NORMAL, centro.getSituacao());
    }

    @Test
    public void testDefineSituacaoSobraviso() {
        CentroDistribuicao centro = new CentroDistribuicao(62, 1250, 312, 312);
        assertEquals(CentroDistribuicao.SITUACAO.SOBRAVISO, centro.getSituacao());
    }

    @Test
    public void testDefineSituacaoEmergencia() {
        CentroDistribuicao centro = new CentroDistribuicao(31, 250, 62, 62);
        assertEquals(CentroDistribuicao.SITUACAO.EMERGENCIA, centro.getSituacao());
    }

    CentroDistribuicao centro = new CentroDistribuicao(250, 5000, 1250, 1250);

    @Test
    public void testRecebeAditivoQuantidadeValida() {
        int quantidadeRecebida = centro.recebeAditivo(100);
        assertEquals(100, quantidadeRecebida);
        assertEquals(350, centro.gettAditivo());
    }

    @Test
    public void testRecebeAditivoQuantidadeAcimaDoLimite() {
        int quantidadeRecebida = centro.recebeAditivo(400);
        assertEquals(250, quantidadeRecebida);
        assertEquals(500, centro.gettAditivo());
    }

    @Test
    public void testRecebeAditivoQuantidadeNegativa() {
        int quantidadeRecebida = centro.recebeAditivo(-100);
        assertEquals(-1, quantidadeRecebida);
        assertEquals(250, centro.gettAditivo());
        assertEquals(5000, centro.gettGasolina());
        assertEquals(1250, centro.gettAlcool1());
        assertEquals(1250, centro.gettAlcool2());
    }

    @Test
    void testRecebeAlcoolQtdadeInvalida() {
        int qtdade = -10;
        int expected = -1;
        int result = centro.recebeAlcool(qtdade);
        assertEquals(expected, result);
    }

    @Test
    void testRecebeAlcoolQuantidadeExcedente() {
        int qtdade = 3000;
        int expected = 1000;
        int result = centro.recebeAlcool(qtdade);
        assertEquals(expected, result);
        assertEquals(MAX_ALCOOL, centro.gettAlcool1() + centro.gettAlcool2());
        assertEquals(SITUACAO.NORMAL, centro.getSituacao());
    }

    @Test
    void testRecebeAlcoolQuantidadeInsuficiente() {
        int qtdade = 10;
        int expected = qtdade;
        int result = centro.recebeAlcool(qtdade);
        assertEquals(expected, result);
        assertEquals(SITUACAO.EMERGENCIA, centro.getSituacao());
    }

    @Test
    void testRecebeAlcool() {
        int qtdade = 500;
        int expected = qtdade;
        int result = centro.recebeAlcool(qtdade);
        assertEquals(expected, result);
        assertEquals(SITUACAO.NORMAL, centro.getSituacao());
    }

    @Test
    void testEncomendaCombustivelGasolina() {
        int qtdade = 5000;
        int[] expected = { 5000, 0, 0, -1 };
        int[] result = centro.encomendaCombustivel(qtdade, CentroDistribuicao.TIPOPOSTO.COMUM);
        assertArrayEquals(expected, result);
        assertEquals(SITUACAO.NORMAL, centro.getSituacao());
    }

    @Test
    void testEncomendaCombustivelAditivo() {
        int qtdade = 800;
        int[] expected = { 0, 800, 0, -1 };
        int[] result = centro.encomendaCombustivel(qtdade, CentroDistribuicao.TIPOPOSTO.COMUM);
        assertArrayEquals(expected, result);
        assertEquals(SITUACAO.NORMAL, centro.getSituacao());
    }

    @Test
    void testEncomendaCombustivelAlcool() {
        int qtdade = 2000;
        int[] expected = { 0, 0, 2000, -1 };
        int[] result = centro.encomendaCombustivel(qtdade, CentroDistribuicao.TIPOPOSTO.COMUM);
        assertArrayEquals(expected, result);
        assertEquals(SITUACAO.NORMAL, centro.getSituacao());
    }

    @Test
    void testEncomendaCombustivelQuantidadeInsuficiente() {
        int qtdade = 8000;
        int[] expected = { 0, 0, 0, qtdade };
        int[] result = centro.encomendaCombustivel(qtdade, CentroDistribuicao.TIPOPOSTO.COMUM);
        assertArrayEquals(expected, result);
        assertEquals(SITUACAO.NORMAL, centro.getSituacao());
    }

    @Test
    void testEncomendaCombustivelTipoPostoEstrategico() {
        int qtdade = 800;
        int[] expected = { 0, 800, 0, -1 };
        int[] result = centro.encomendaCombustivel(qtdade, CentroDistribuicao.TIPOPOSTO.ESTRATEGICO);
        assertArrayEquals(expected, result);
        assertEquals(SITUACAO.NORMAL, centro.getSituacao())
    }
}