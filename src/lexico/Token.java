package lexico;

public class Token {

    TipoToken tipo;
    String lexema;
    int valor;

    int renglon;
    int columna;


    public String getLexema() {
        return lexema;
    }

    public void setTipo(TipoToken tipo) {
        this.tipo = tipo;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public void setRenglon(int renglon) {
        this.renglon = renglon;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }
}
