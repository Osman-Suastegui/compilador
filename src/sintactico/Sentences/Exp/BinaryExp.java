package sintactico.Sentences.Exp;

public class BinaryExp extends Exp {
        public String binaryOperator;
        public Exp left;
        public Exp right;

    public BinaryExp(String operator, Exp leftExp, Exp rightExp) {
        super(ExpType.BINARY_EXP);
        this.binaryOperator = operator;
        this.left = leftExp;
        this.right = rightExp;
    }



}