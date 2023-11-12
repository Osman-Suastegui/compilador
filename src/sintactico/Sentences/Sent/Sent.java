package sintactico.Sentences.Sent;

import sintactico.Sentences.Sentences;

import java.util.List;

public class Sent extends Sentences {

    public enum SentType {
        SENT_SI, SENT_MIENTRAS, SENT_LEER, SENT_ESCRIBIR
    }

    public SentType tagSent;


    public Sent(SentType tagSent) {
        super(SentencesType.SENTENCIA_SENT);
        this.tagSent = tagSent;
    }
}
