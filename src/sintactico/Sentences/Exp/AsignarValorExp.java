package sintactico.Sentences.Exp;

import sintactico.Sentences.Exp.Exp;


public class AsignarValorExp extends Exp {
    public static final String SIGNO_ASIGNACION = "=";
    public static final String PUNTO_COMA = ";";

    public VarExp variable;
    public String signo;
    public Exp valor;
    public String puntoComa;

    public AsignarValorExp(VarExp variable, Exp valor) {
        super(ExpType.ASIGNAR_VALOR_EXP);
        this.variable = variable;
        this.signo = SIGNO_ASIGNACION;
        this.valor = valor;
        this.puntoComa = PUNTO_COMA;
    }

}