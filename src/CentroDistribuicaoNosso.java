public class CentroDistribuicaoNosso {
    public enum SITUACAO {
        NORMAL, SOBRAVISO, EMERGENCIA
    }

    public enum TIPOPOSTO {
        COMUM, ESTRATEGICO
    }

    private int aditivo;
    private int gasolina;
    private int alcool1;
    private int alcool2;
    private SITUACAO situacao;

    public static final int MAX_ADITIVO = 500;
    public static final int MAX_ALCOOL = 2500;
    public static final int MAX_GASOLINA = 10000;

    public CentroDistribuicao(int tAditivo, int tGasolina, int tAlcool1, int tAlcool2) {
        if (tAditivo <= 0 || tAditivo > MAX_ADITIVO || tGasolina <= 0 || tGasolina > MAX_GASOLINA
                || tAlcool1 <= 0 || tAlcool1 > MAX_ALCOOL || tAlcool2 <= 0 || tAlcool2 > MAX_ALCOOL
                || tAlcool1 != tAlcool2) {
            throw new IllegalArgumentException("Quantidade inválida de um ou mais componentes.");
        }
        this.aditivo = tAditivo;
        this.gasolina = tGasolina;
        this.alcool1 = tAlcool1;
        this.alcool2 = tAlcool2;
        defineSituacao();
    }

    public void defineSituacao() {
        if (alcool1 < 0 || alcool2 < 0 || aditivo < 0 || gasolina < 0) {
            throw new IllegalStateException("Quantidade de um ou mais componentes inválida.");
        } else if (alcool1 <= MAX_ALCOOL / 4 || alcool2 <= MAX_ALCOOL / 4 || aditivo <= MAX_ADITIVO / 2
                || gasolina <= MAX_GASOLINA / 2) {
            if (alcool1 <= MAX_ALCOOL / 8 || alcool2 <= MAX_ALCOOL / 8 || aditivo <= MAX_ADITIVO / 4
                    || gasolina <= MAX_GASOLINA / 4) {
                situacao = SITUACAO.EMERGENCIA;
            } else {
                situacao = SITUACAO.SOBRAVISO;
            }
        } else {
            situacao = SITUACAO.NORMAL;
        }
    }

    public SITUACAO getSituacao() {
        return situacao;
    }

    public int gettGasolina() {
        return gasolina;
    }

    public int gettAditivo() {
        return aditivo;
    }

    public int gettAlcool1() {
        return alcool1;
    }

    public int gettAlcool2() {
        return alcool2;
    }

    public TIPOPOSTO getComum() {
        return TIPOPOSTO.COMUM;
    }

    public TIPOPOSTO getEstrategico() {
        return TIPOPOSTO.ESTRATEGICO;
    }

    public int recebeAditivo(int qtdAditivo) {
        if (qtdAditivo < 0) {
            return -1;
        }
        aditivo += qtdAditivo;
        if (aditivo > MAX_ADITIVO) {
            aditivo = MAX_ADITIVO;
        }
        defineSituacao();
        return aditivo;
    }

    public int recebeGasolina(int qtdGas) {
        if (qtdGas < 0) {
            return -1;
        }
        gasolina += qtdGas;
        if (gasolina > MAX_GASOLINA) {
            gasolina = MAX_GASOLINA;
        }
        defineSituacao();
        return gasolina;
    }

    public int recebeAlcool(int qtdAlc) {
        if (qtdAlc < 0) {
            return -1;
        }
        alcool1 += (int) Math.round(qtdAlc / 2);
        alcool2 += (int) Math.round(qtdAlc / 2);
        if (alcool1 + alcool2 > MAX_ALCOOL) {
            alcool1 = MAX_ALCOOL / 2;
            alcool2 = MAX_ALCOOL / 2;
        }
        defineSituacao();
        return alcool1 + alcool2;
    }

    public int[] encomendaCombustivel(int qtdade, TIPOPOSTO tipoPosto) {
        int[] combustivelEncomendado = new int[4];
        int gasolinaEncomendada = 0;
        int aditivoEncomendado = 0;
        int alcoolEncomendado = 0;
        int erro = 0;

        if (getSituacao() == SITUACAO.NORMAL) {
            gasolinaEncomendada = (int) Math.round(qtdade * 0.7);
            aditivoEncomendado = (int) Math.round(qtdade * 0.05);
            alcoolEncomendado = (int) Math.round(qtdade * 0.25);
        } else if (getSituacao() == SITUACAO.SOBRAVISO) {
            if (tipoPosto == TIPOPOSTO.COMUM) {
                gasolinaEncomendada = (int) Math.round(qtdade * 0.7 * 0.5);
                aditivoEncomendado = (int) Math.round(qtdade * 0.05 * 0.5);
                alcoolEncomendado = (int) Math.round(qtdade * 0.25 * 0.5);
            } else {
                gasolinaEncomendada = (int) Math.round(qtdade * 0.7);
                aditivoEncomendado = (int) Math.round(qtdade * 0.05);
                alcoolEncomendado = (int) Math.round(qtdade * 0.25);
            }
        } else {
            if (tipoPosto == TIPOPOSTO.COMUM) {
                gasolinaEncomendada = -1;
                aditivoEncomendado = -1;
                alcoolEncomendado = -1;
            } else {
                gasolinaEncomendada = (int) Math.round(qtdade * 0.7 * 0.5);
                aditivoEncomendado = (int) Math.round(qtdade * 0.05 * 0.5);
                alcoolEncomendado = (int) Math.round(qtdade * 0.25 * 0.5);
            }
        }

        if (qtdade < 0) {
            erro = -7;
        }

        if (gasolinaEncomendada == -1 && aditivoEncomendado == -1 && alcoolEncomendado == -1) {
            erro = -14;
        }

        if (gasolinaEncomendada > gasolina || alcoolEncomendado > alcool1 + alcool2 || aditivoEncomendado > aditivo) {
            erro = -21;
        }

        if (erro < 0) {
            combustivelEncomendado[0] = erro;
            combustivelEncomendado[1] = 0;
            combustivelEncomendado[2] = 0;
            combustivelEncomendado[3] = 0;
            return combustivelEncomendado;
        }

        gasolina -= gasolinaEncomendada;
        aditivo -= aditivoEncomendado;
        alcool1 -= (int) Math.round(alcoolEncomendado / 2);
        alcool2 -= (int) Math.round(alcoolEncomendado / 2);

        defineSituacao();
        combustivelEncomendado[0] = aditivo;
        combustivelEncomendado[1] = gasolina;
        combustivelEncomendado[2] = alcool1;
        combustivelEncomendado[3] = alcool2;
        return combustivelEncomendado;
    }

}