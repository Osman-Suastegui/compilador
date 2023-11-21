package sintactico;

import lexico.LinkList;
import lexico.Nodo;
import lexico.TipoToken;
import sintactico.Sentences.Sent.*;
import java.util.ArrayList;
import java.util.List;

public class MainSintactico {

    LinkList tokens;

    private Nodo current;

    public MainSintactico(LinkList tokens){
        this.tokens = tokens;
        NodoAST nodo = this.GramaticaPrograma();
//       IMPRIMOS EL AST
        nodo.print(0);
    }




    public NodoPrograma GramaticaPrograma() {
        this.current = tokens.getRaiz();
        // Validar que el programa comience con el token "inicio"
        if(!this.current.getToken().getLexema().equals("Inicio")){
            System.out.println("Falta Inicio");
            // por hacer Manejar error: El programa debe comenzar con "inicio"
            return null;
        }
        this.current = this.current.getSiguiente();

        List<NodoAST> nodosHijosProgramas = new ArrayList<>();

        while (this.current != null && !this.current.getToken().getLexema().equals("Fin") ){
            NodoAST nodo  = VerificarTipoDeNodo();
            nodosHijosProgramas.add(nodo);
        }

        // Validar que el programa termine con el token "fin"
       if(this.current == null) {
           System.out.println("Falta Fin");
           // por hacer Manejar error: El programa debe terminar con "fin"
           return null;
       }

        return new NodoPrograma(nodosHijosProgramas);
    }
//    una funcion para verificar que tipo de AST ES
    public NodoAST VerificarTipoDeNodo(){
        switch (this.current.getToken().getLexema()){
            case "Si":
                return GramaticaSi();
            case "Mientras":
                return GramaticaMientras();
            case "Escribir":
                return GramaticaEscribir();
            case "Leer":
                return GramaticaLeer();
            case "Num" :
                return GramaticaDeclaracionVariable();
            case "Cadena" :
                return GramaticaDeclaracionVariable();
        }
        if(this.current.getToken().getTipo().equals(TipoToken.IDENTIFICADOR)){
            return GramaticaAsignacion();
        }
        System.out.println("No se reconoce el token " + this.current.getToken().getLexema());
        return null;

    }



    private NodoAST GramaticaSi() {

        if(!this.current.getToken().getLexema().equals("Si")){
            System.out.println("Falta Si");
            // por hacer Manejar error:  Falta Si"
            return null;
        }
        this.current = this.current.getSiguiente();
        NodoAST condition = GramaticaRelacional();
        if(!this.current.getToken().getLexema().equals("Entonces")){
            System.out.println("Falta Entonces");
            // por hacer Manejar error: Falta Entonces"
            return null;
        }
        this.current = this.current.getSiguiente();
        List<NodoAST> bodySi = new ArrayList<>();
        List<NodoAST> bodySino = new ArrayList<>();
        while (this.current != null && !this.current.getToken().getLexema().equals("FinSi")){
            NodoAST nodoSi  = VerificarTipoDeNodo();
            if(nodoSi != null){
                bodySi.add(nodoSi);
            }

            if(this.current.getToken().getLexema().equals("Sino")){
                this.current = this.current.getSiguiente();

                while (this.current != null && !this.current.getToken().getLexema().equals("FinSi")) {
                    NodoAST nodoSino  = VerificarTipoDeNodo();
                    bodySino.add(nodoSino);

                }
            }
        }

//        System.out.println("LEXEMA " + this.current.getToken().getLexema());
        if(this.current == null){
            System.out.println("Falta FinSi");
            // por hacer Manejar error: Falta FinSi"
            return null;
        }
        this.current = this.current.getSiguiente();
        return new NodoSi(condition, bodySi,bodySino);


    }
    private  NodoAST GramaticaRelacional(){
        if(!this.current.getToken().getTipo().equals(TipoToken.IDENTIFICADOR) && !this.current.getToken().getTipo().equals(TipoToken.NUMERO) && !this.current.getToken().getTipo().equals(TipoToken.CADENA)){
            System.out.println("Error en gramatica relacional");
            // por hacer Manejar error: tipo de token no es identificador
            return null;
        }
        String var1 = this.current.getToken().getLexema();
        this.current = this.current.getSiguiente();
        if(!this.current.getToken().getTipo().equals( TipoToken.OP_RELACIONAL)){
            System.out.println("Falta operador Relacional");
            // por hacer Manejar error: Falta operador Relacional
            return null;
        }

        String operador = this.current.getToken().getLexema();
        this.current = this.current.getSiguiente();

        if(!this.current.getToken().getTipo().equals(TipoToken.IDENTIFICADOR) && !this.current.getToken().getTipo().equals(TipoToken.NUMERO) && !this.current.getToken().getTipo().equals(TipoToken.CADENA)){
            System.out.println("Falta identificador");
            // por hacer Manejar error: Falta identificador
            return null;
        }
        String var2 = this.current.getToken().getLexema();
        this.current = this.current.getSiguiente();
        NodoRelacional nodoRelacional = new NodoRelacional(var1, var2, operador);
        return nodoRelacional;

    }
    private NodoAST GramaticaMientras() {
        if(!this.current.getToken().getLexema().equals("Mientras") ){
            System.out.println("Falta Mientras");
            // por hacer Manejar error: Falta Mientras"
            return null;
        }
        this.current = this.current.getSiguiente();
        NodoAST condition = GramaticaRelacional();
        if(!this.current.getToken().getLexema().equals("Entonces")){
            System.out.println("Falta Entonces Mientras");
            // por hacer Manejar error: Falta Entonces"
            return null;
        }
        this.current = this.current.getSiguiente();
        List<NodoAST> body = new ArrayList<>();
        while (this.current != null && !this.current.getToken().getLexema().equals("FinMientras")){
            NodoAST nodo  = VerificarTipoDeNodo();
            body.add(nodo);
//            this.current = this.current.getSiguiente();
        }
        if(this.current == null){
            System.out.println("Falta FinMientras");
            // por hacer Manejar error: Falta FinMientras"
            return null;
        }
        this.current = this.current.getSiguiente();
        return new NodoMientras(condition, body);
    }
    private NodoAST GramaticaEscribir() {
        if(!this.current.getToken().getLexema().equals("Escribir")){
            System.out.println("Falta Escribir");
            // por hacer Manejar error: Falta Escribir"
            return null;
        }
        this.current = this.current.getSiguiente();
        String contenidoNodoEscribir;
        if(this.current.getToken().getTipo().equals(TipoToken.CADENA)){
            contenidoNodoEscribir = this.current.getToken().getLexema();
        }else if(this.current.getToken().getTipo().equals(TipoToken.NUMERO)){
            contenidoNodoEscribir = this.current.getToken().getLexema();
        }else if(this.current.getToken().getTipo().equals(TipoToken.IDENTIFICADOR)){
            contenidoNodoEscribir = this.current.getToken().getLexema();
        }
        else {
            System.out.println("ERROR EN ESCRIBIR");
            return null;
        }
        this.current = this.current.getSiguiente();

        return new NodoEscribir(contenidoNodoEscribir);
    }

    public NodoAST GramaticaLeer() {
        if(!this.current.getToken().getLexema().equals("Leer")){
            System.out.println("Falta Leer");
            // por hacer Manejar error: Falta Leer"
            return null;
        }
        this.current = this.current.getSiguiente();
        String identificadorLeer;
         if(this.current.getToken().getTipo().equals(TipoToken.IDENTIFICADOR)){
            identificadorLeer = this.current.getToken().getLexema();
        }
        else {
            System.out.println("ERROR EN LEER");
            return null;
        }
        this.current = this.current.getSiguiente();

        return new NodoLeer(identificadorLeer);
    }
    private NodoAST GramaticaDeclaracionVariable() {
        if(!this.current.getToken().getLexema().equals("Num") && !this.current.getToken().getLexema().equals("Cadena")){
            System.out.println("Error en declaracion de variable1");
            return null;
        }
        String tipo = this.current.getToken().getLexema();
        this.current = this.current.getSiguiente();
        if(!this.current.getToken().getTipo().equals(TipoToken.IDENTIFICADOR)){
            System.out.println("Error en declaracion de variable2");
            return null;
        }

        String nombre = this.current.getToken().getLexema();
        this.current = this.current.getSiguiente();
        return new NodoDeclaracionVariable(tipo, nombre);
    }
    private NodoAST GramaticaAsignacion() {
        if(!this.current.getToken().getTipo().equals(TipoToken.IDENTIFICADOR)){
            System.out.println("Error en asignacion");
            return null;
        }
        String nombre = this.current.getToken().getLexema();
        this.current = this.current.getSiguiente();
        if(!this.current.getToken().getTipo().equals(TipoToken.OP_ASIGNACION)){
            System.out.println("Error en asignacion");
            return null;
        }
        this.current = this.current.getSiguiente();
        if(!this.current.getToken().getTipo().equals(TipoToken.IDENTIFICADOR) && !this.current.getToken().getTipo().equals(TipoToken.NUMERO) && !this.current.getToken().getTipo().equals(TipoToken.CADENA)){
            System.out.println("Error en asignacion");
            return null;
        }
        String valor1 = this.current.getToken().getLexema();
        this.current = this.current.getSiguiente();

        if(this.current != null && this.current.getToken().getTipo().equals(TipoToken.OP_ARITMETICO)){
            String operador = this.current.getToken().getLexema();
            this.current = this.current.getSiguiente();
            if(!this.current.getToken().getTipo().equals(TipoToken.IDENTIFICADOR) && !this.current.getToken().getTipo().equals(TipoToken.NUMERO) && !this.current.getToken().getTipo().equals(TipoToken.CADENA)){
                System.out.println("Error en asignacion");
                return null;
            }
            String valor2 = this.current.getToken().getLexema();
            this.current = this.current.getSiguiente();
            return new NodoAsignacion(nombre, valor1, valor2, operador);
        }

        return new NodoAsignacion(nombre, valor1);
    }


}
