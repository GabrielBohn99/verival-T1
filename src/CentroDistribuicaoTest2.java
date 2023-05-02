import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class CentroDistribuicaoTest2 {

  private CentroDistribuicao centroDistribuicao;
  private CentroDistribuicao.SITUACAO expectedSituacao;
  private int expectedtGasolina;
  private int expectedtAditivo;
  private int expectedtAlcool1;
  private int expectedtAlcool2;
  private int quantidade;
  private CentroDistribuicao.TIPOPOSTO tipoPosto;
  private int[] resultadoEsperado;

  public CentroDistribuicaoTest(
    CentroDistribuicao centroDistribuicao,
    CentroDistribuicao.SITUACAO expectedSituacao,
    int expectedtGasolina,
    int expectedtAditivo,
    int expectedtAlcool1,
    int expectedtAlcool2,
    int quantidade,
    CentroDistribuicao.TIPOPOSTO tipoPosto,
    int[] resultadoEsperado
  ) {
    this.centroDistribuicao = centroDistribuicao;
    this.expectedSituacao = expectedSituacao;
    this.expectedtGasolina = expectedtGasolina;
    this.expectedtAditivo = expectedtAditivo;
    this.expectedtAlcool1 = expectedtAlcool1;
    this.expectedtAlcool2 = expectedtAlcool2;
    this.quantidade = quantidade;
    this.tipoPosto = tipoPosto;
    this.resultadoEsperado = resultadoEsperado;
  }

  @Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(
      new Object[][] {
        {
          new CentroDistribuicao(500, 10000, 1000, 1000),
          CentroDistribuicao.SITUACAO.NORMAL,
          10000,
          500,
          1000,
          1000,
          5000,
          CentroDistribuicao.TIPOPOSTO.COMUM,
          new int[] { 250, 6500, 375, 375 },
        },
        {
          new CentroDistribuicao(200, 3000, 500, 500),
          CentroDistribuicao.SITUACAO.SOBRAVISO,
          3000,
          200,
          500,
          500,
          2000,
          CentroDistribuicao.TIPOPOSTO.ESTRATEGICO,
          new int[] { 100, 1600, 250, 250 },
        },
        {
          new CentroDistribuicao(200, 3000, 500, 500),
          CentroDistribuicao.SITUACAO.SOBRAVISO,
          3000,
          200,
          500,
          500,
          1000,
          CentroDistribuicao.TIPOPOSTO.COMUM,
          new int[] { 175, 2650, 438, 438 },
        },
        {
          new CentroDistribuicao(100, 5000, 300, 300),
          CentroDistribuicao.SITUACAO.EMERGENCIA,
          5000,
          100,
          300,
          300,
          1000,
          CentroDistribuicao.TIPOPOSTO.COMUM,
          new int[] { -14, 0, 0, 0 },
        },
        {
          new CentroDistribuicao(100, 5000, 300, 300),
          CentroDistribuicao.SITUACAO.EMERGENCIA,
          5000,
          100,
          300,
          300,
          1000,
          CentroDistribuicao.TIPOPOSTO.ESTRATEGICO,
          new int[] { 75, 4650, 238, 238 },
        },
        {
          new CentroDistribuicao(500, 10000, 1000, 1000),
          CentroDistribuicao.SITUACAO.NORMAL,
          10000,
          500,
          1000,
          1000,
          0,
          CentroDistribuicao.TIPOPOSTO.ESTRATEGICO,
          new int[] { 500, 10000, 1000, 1000 },
        },
        {
          new CentroDistribuicao(500, 10000, 1000, 1000),
          CentroDistribuicao.SITUACAO.NORMAL,
          10000,
          500,
          1000,
          1000,
          -100,
          CentroDistribuicao.TIPOPOSTO.ESTRATEGICO,
          new int[] { -7, 0, 0, 0 },
        },
        {
          new CentroDistribuicao(50, 10000, 1000, 1000),
          CentroDistribuicao.SITUACAO.EMERGENCIA,
          10000,
          50,
          1000,
          1000,
          2000,
          CentroDistribuicao.TIPOPOSTO.ESTRATEGICO,
          new int[] { -21, 0, 0, 0 },
        },
      }
    );
  }

  @Test
  public void testAbastecimento() {
    centroDistribuicao.abastece(quantidade, tipoPosto);
    assertEquals(expectedSituacao, centroDistribuicao.getSituacao());
    assertEquals(expectedtGasolina, centroDistribuicao.gettGasolina());
    assertEquals(expectedtAditivo, centroDistribuicao.gettAditivo());
    assertEquals(expectedtAlcool1, centroDistribuicao.gettAlcool1());
    assertEquals(expectedtAlcool2, centroDistribuicao.gettAlcool2());
    int[] result = centroDistribuicao.verificaEstoque();
    assertEquals(resultadoEsperado.length, result.length);
    for (int i = 0; i < resultadoEsperado.length; i++) {
      assertEquals(resultadoEsperado[i], result[i]);
    }
  }
}
