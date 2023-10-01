package lexico.tablaDeSimbolos;

import lexico.TipoToken;

public class AtributosSimbolo {
    private TipoToken tipo;
    private Double valor;

    public AtributosSimbolo() {
    }

    public TipoToken getTipo() {
        return tipo;
    }

    public void setTipo(TipoToken tipo) {
        this.tipo = tipo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
    @Override
    public String toString() {


        return String.format("%-20s",tipo) + String.format("%-20s",valor) ;
    }
}
