package sintactico.Sentences.Sent;

import java.util.List;

public class SentEscribir extends Sent {
    public static final String ESCRIBIR = "Escribir";
    public static final String PUNTO_COMA = ";";
    public String escribir;
    public List<String> expresiones;
    public String puntoComa;

    public SentEscribir(List<String> expresiones) {
        super(SentType.SENT_ESCRIBIR);
        this.escribir = ESCRIBIR;
        this.expresiones = expresiones;
        this.puntoComa = PUNTO_COMA;
    }
}
