package sintactico.ExpRel;

//importa exp
import sintactico.Sentences.Exp.Exp;


public class ExpRel {
    public enum OpRelType {
        NOT_EQUALS("<>"),
        LESS_THAN_OR_EQUAL("<="),
        GREATER_THAN_OR_EQUAL(">="),
        LESS_THAN("<"),
        GREATER_THAN(">"),
        EQUALS("==");

        private final String symbol;

        OpRelType(String symbol) {
            this.symbol = symbol;
        }

        public String getSymbol() {
            return symbol;
        }
    }
    public Exp Exp1;
    public Exp Exp2;

    public OpRelType OpRel;

    public ExpRel(OpRelType OpRel, Exp Exp1, Exp Exp2) {
        this.Exp1 = Exp1;
        this.OpRel = OpRel;
        this.Exp2 = Exp2;
    }
}
