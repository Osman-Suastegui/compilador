package sintactico.Sentences.Sent.Grammar;

import lexico.Nodo;
import sintactico.Sentences.Sentences;

import java.util.List;

public class GramaticaSentSi {
    public final String SI = "Si";
    public final String ENTONCES = "Entonces";
    public final String SINO = "Sino";
    public final String FIN_SI = "FinSi";
    public final String PARENTESIS_IZQ = "(";
    public final String PARENTESIS_DER = ")";
    public final String PUNTO_COMA = ";";
    public final String COMA = ",";
    public final String DOS_PUNTOS = ":";

    public String condicion;

    public String token;

    public Nodo nodo;
    public List<Sentences> AST;


    public GramaticaSentSi(Nodo nodo, List<Sentences> AST){
        this.nodo = nodo;
        this.AST = AST;
    }

    public boolean ValidarSentSi(){
        if(token.equals(SI)){
            //token++;
           // if (to)
        }
        return false;
    }




}
