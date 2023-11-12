package sintactico.Sentences.Exp;

public class IntegerExp extends Exp {
    public int value;

    public IntegerExp(int intValue) {
        super(ExpType.INTEGER_EXP);
        this.value = intValue;
    }
}
