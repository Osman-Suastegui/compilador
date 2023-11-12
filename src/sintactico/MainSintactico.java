package sintactico;

import lexico.LinkList;
import lexico.Nodo;
import sintactico.Sentences.Sent.Grammar.GramaticaPrograma;
import sintactico.Sentences.Sentences;

import java.util.ArrayList;
import java.util.List;

public class MainSintactico {

    LinkList tokens;

    public MainSintactico(LinkList tokens){
        this.tokens = tokens;
    }


    public void analizar() {
        System.out.println("Analizando sintacticamente...");
        System.out.println("Tokens: " + tokens);
        List<Sentences> raizAST = new ArrayList<Sentences>();
        Nodo nodoListaEnlazada = tokens.getRaiz();
        GramaticaPrograma gramaticaPrograma = new GramaticaPrograma(nodoListaEnlazada, raizAST);

    }




}
