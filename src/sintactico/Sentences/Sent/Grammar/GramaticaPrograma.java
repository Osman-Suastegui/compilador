package sintactico.Sentences.Sent.Grammar;

import lexico.Nodo;
import sintactico.Sentences.Sentences;

import java.util.List;

public class GramaticaPrograma {

    Nodo nodoListaEnlazada;

    List<Sentences> AST;

    public GramaticaPrograma( Nodo nodoListaEnlazada, List<Sentences> AST){
        this.nodoListaEnlazada = nodoListaEnlazada;
        this.AST = AST;
    }




    public String verificarTokenType(Nodo nodo, List<Sentences> AST){
        switch (nodo.getToken().getLexema()){
            case "Leer":
                return "Leer";
            case "Si":
                GramaticaSentSi gramaticaSentSi = new GramaticaSentSi(nodo, AST);
                gramaticaSentSi.ValidarSentSi();
            case "Mientras":
                return "Mientras";
            case "Escribir":
                return "Escribir";
        }
        return "";
    }



}
