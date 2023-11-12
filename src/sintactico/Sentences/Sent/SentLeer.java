package sintactico.Sentences.Sent;

import sintactico.Sentences.Exp.VarExp;

public class SentLeer extends Sent {

    public static final String LEER = "Leer";
    public static final String PUNTO_COMA = ";";
    public String leer;
    public VarExp id;
    public String puntoComa;

    public SentLeer(VarExp id) {
        super(SentType.SENT_LEER);
        this.leer = LEER;
        this.id = id;
        this.puntoComa = PUNTO_COMA;
    }
}
