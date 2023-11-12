package sintactico.Sentences.Exp;

public class ConstExp extends Exp {
    public static final String SIGNO_ASIGNACION = "=";
    public static final String PUNTO_COMA = ";";
    public static final String CONST = "Const";
    public enum TipoDato {
        NUMERO, CADENA
    }
    public TipoDato tipoDato;
    public String constante;
    public String nombre;
    public String signo;
    public Exp valor;
    public String puntoComa;


    public ConstExp(Exp valor, String nombre, TipoDato TipoDato) {
        super(ExpType.CONST_EXP);
        this.constante = CONST;
        this.tipoDato = TipoDato;
        this.nombre = nombre;
        this.signo = SIGNO_ASIGNACION;
        this.valor = valor;
        this.puntoComa = PUNTO_COMA;
    }

}
