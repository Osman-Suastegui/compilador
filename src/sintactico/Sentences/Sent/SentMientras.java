package sintactico.Sentences.Sent;

import sintactico.Sentences.Sentences;
import sintactico.ExpRel.ExpRel;

import java.util.List;


public class SentMientras extends Sent{
    public static final String MIENTRAS = "Mientras";
    public static final String PARENTESIS1 = "(";
    public static final String PARENTESIS2 = ")";
    public static final String ENTONCES = "Entonces";
    public static final String FIN_MIENTRAS = "FinMientras";
    public static final String PUNTO_COMA = ";";

    public String mientras;
    public String parentesis1;
    public ExpRel expresionRelacional;
    public String parentesis2;
    public String entonces;
    public List<Sentences> sentencias;
    public String finMientras;
    public String puntoComa;

    public SentMientras(ExpRel expresionRelacional, List<Sentences> sentencias) {
        super(SentType.SENT_MIENTRAS);
        this.mientras = MIENTRAS;
        this.parentesis1 = PARENTESIS1;
        this.expresionRelacional = expresionRelacional;
        this.parentesis2 = PARENTESIS2;
        this.entonces = ENTONCES;
        this.sentencias = sentencias;
        this.finMientras = FIN_MIENTRAS;
        this.puntoComa = PUNTO_COMA;
    }

}
