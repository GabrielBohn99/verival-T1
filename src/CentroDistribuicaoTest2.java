import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CentroDistribuicaoTest {

    @Parameterized.Parameter(0)
    public int tAditivo;

    @Parameterized.Parameter(1)
    public int tGasolina;

    @Parameterized.Parameter(2)
    public int tAlcool1;

    @Parameterized.Parameter(3)
    public int tAlcool2;

    @Parameterized.Parameter(4)
    public CentroDistribuicao.SITUACAO expectedSituacao;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {MAX_ADITIVO / 2, MAX_GASOLINA / 2, MAX_ALCOOL / 4, MAX_ALCOOL / 4, CentroDistribuicao.SITUACAO.NORMAL},
                {MAX_ADITIVO / 4, MAX_GASOLINA / 4, MAX_ALCOOL / 8, MAX_ALCOOL / 8, CentroDistribuicao.SITUACAO.SOBRAVISO},
                {MAX_ADITIVO / 8, MAX_GASOLINA / 8, MAX_ALCOOL / 16, MAX_ALCOOL / 16, CentroDistribuicao.SITUACAO.EMERGENCIA},
                {0, MAX_GASOLINA, MAX_ALCOOL / 2, MAX_ALCOOL / 2, CentroDistribuicao.SITUACAO.EMERGENCIA},
                {MAX_ADITIVO, MAX_GASOLINA, MAX_ALCOOL / 2, MAX_ALCOOL / 2, CentroDistribuicao.SITUACAO.EMERGENCIA},
                {MAX_ADITIVO / 2, MAX_GASOLINA, MAX_ALCOOL / 2, MAX_ALCOOL / 2, CentroDistribuicao.SITUACAO.SOBRAVISO},
                {MAX_ADITIVO / 2, MAX_GASOLINA / 2, 0, MAX_ALCOOL / 2, CentroDistribuicao.SITUACAO.EMERGENCIA},
                {MAX_ADITIVO / 2, MAX_GASOLINA / 2, MAX_ALCOOL / 2, 0, CentroDistribuicao.SITUACAO.EMERGENCIA},
                {MAX_ADITIVO / 2, MAX_GASOLINA / 2, 0, 0, CentroDistribuicao.SITUACAO.EMERGENCIA},
                {MAX_ADITIVO / 2, MAX_GASOLINA / 2, MAX_ALCOOL / 2, MAX_ALCOOL / 2 + 1, CentroDistribuicao.SITUACAO.EMERGENCIA}
        });
    }

    @Test
    public void testDefineSituacao() {
        CentroDistribuicao centro = new CentroDistribuicao(tAditivo, tGasolina, tAlcool1, tAlcool2);
        assertEquals(expectedSituacao, centro.getSituacao());
    }

    // caso de teste 2: encomendaCombustivel com quantidade insuficiente de gasolina
    assertEquals(new int[] {-1,-1,-1,-1}, centro.encomendaCombustivel(500, TIPOPOSTO.COMUM));
    
    // caso de teste 3: encomendaCombustivel com quantidade insuficiente de aditivo
    assertEquals(new int[] {0,0,0,-1}, centro.encomendaCombustivel(5, TIPOPOSTO.COMUM));
    
    // caso de teste 4: encomendaCombustivel com quantidade insuficiente de alcool
    assertEquals(new int[] {0,0,0,-1}, centro.encomendaCombustivel(125, TIPOPOSTO.COMUM));
    
    // caso de teste 5: encomendaCombustivel com quantidade suficiente de combustível comum
    assertEquals(new int[] {200, 50, 100, 0}, centro.encomendaCombustivel(350, TIPOPOSTO.COMUM));
    
    // caso de teste 6: encomendaCombustivel com quantidade suficiente de combustível estratégico
    assertEquals(new int[] {1000, 200, 400, 500}, centro.encomendaCombustivel(2100, TIPOPOSTO.ESTRATEGICO));
}
