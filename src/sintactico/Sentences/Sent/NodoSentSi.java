package sintactico.Sentences.Sent;


import sintactico.ExpRel.ExpRel;
import sintactico.Sentences.Sentences;

import java.util.List;

public class NodoSentSi extends Sent{
    public static final String SI = "Si";
    public static final String PARENTESIS1 = "(";
    public static final String PARENTESIS2 = ")";
    public static final String ENTONCES = "Entonces";
    public static final String SI_NO = "SiNo";
    public static final String FIN_SI = "FinSi";
    public static final String PUNTO_COMA = ";";
    public String si;
    public String parentesis1;
    public ExpRel expresionRelacional;
    public String parentesis2;
    public String entonces;
    public List<Sentences> sentencias;
    public String siNo;
    public Sentences sentencia2;
    public String finSi;
    public String puntoComa;


    public NodoSentSi(ExpRel expresionRelacional, List<Sentences> sentencias) {
        super(SentType.SENT_SI);
        this.si = SI;
        this.parentesis1 = PARENTESIS1;
        this.expresionRelacional = expresionRelacional;
        this.parentesis2 = PARENTESIS2;
        this.entonces = ENTONCES;
        this.sentencias = sentencias;
        this.finSi = FIN_SI;
        this.puntoComa = PUNTO_COMA;
    }

    public NodoSentSi(ExpRel expresionRelacional, List<Sentences> sentencias, Sentences sentencia2) {
        super(SentType.SENT_SI);
        this.si = SI;
        this.parentesis1 = PARENTESIS1;
        this.expresionRelacional = expresionRelacional;
        this.parentesis2 = PARENTESIS2;
        this.entonces = ENTONCES;
        this.sentencias = sentencias;
        this.siNo = SI_NO;
        this.sentencia2 = sentencia2;
        this.finSi = FIN_SI;
        this.puntoComa = PUNTO_COMA;
    }


}
