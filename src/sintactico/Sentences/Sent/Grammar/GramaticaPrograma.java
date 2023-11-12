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

    public boolean validar(){
        if(nodoListaEnlazada == null){
            return false;
        }
        if(nodoListaEnlazada.getToken().getLexema().equals("Inicio")){
            nodoListaEnlazada = nodoListaEnlazada.getSiguiente();
        }
        while(nodoListaEnlazada != null && nodoListaEnlazada.getSiguiente() != null )
        {

            verificarTokenType(nodoListaEnlazada, AST);

            nodoListaEnlazada = nodoListaEnlazada.getSiguiente();

        }
        if(nodoListaEnlazada != null && nodoListaEnlazada.getToken().getLexema().equals("Fin")){
            return true;
        }
        return false;
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
