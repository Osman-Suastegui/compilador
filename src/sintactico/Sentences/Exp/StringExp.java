package sintactico.Sentences.Exp;

public class StringExp extends Exp {

    public String value;

    public StringExp(String stringValue) {
        super(ExpType.STRING_EXP);
        this.value = stringValue;
    }

}
