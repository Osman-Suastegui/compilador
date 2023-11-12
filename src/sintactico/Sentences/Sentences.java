package sintactico.Sentences;

public class Sentences {

    public enum SentencesType {
        SENTENCIA_EXP,
        SENTENCIA_SENT
    }

    public SentencesType tag;

    public Sentences sentencia;

    public Sentences(SentencesType tag) {
        this.tag = tag;
    }

    public Sentences(SentencesType tag, Sentences sentencia) {
        this.tag = tag;
        this.sentencia = sentencia;
    }

}
