package sintactico;

import lexico.LinkList;
import lexico.Nodo;
import lexico.TipoToken;
import lexico.tablaDeSimbolos.ManejadorErrores;
import sintactico.Sentences.Sent.*;
import java.util.ArrayList;
import java.util.List;

public class MainSintactico {

    LinkList tokens;
    ManejadorErrores manejadorErrores;
    private Nodo current;

    public MainSintactico(LinkList tokens){
        manejadorErrores = ManejadorErrores.obtenerInstancia();
        this.tokens = tokens;
        NodoAST nodo = this.GramaticaPrograma();
        if(manejadorErrores.getCantidadErrores() == 0){
            nodo.print(0);
        }
    }




    public NodoPrograma GramaticaPrograma() {
        this.current = tokens.getRaiz();
        // Validar que el programa comience con el token "inicio"
        if(!this.current.getToken().getLexema().equals("Inicio")){
            manejadorErrores.agregarError("Token Inicio no detectado");
            return  new NodoPrograma(new ArrayList<>());
        }
        this.current = this.current.getSiguiente();

        List<NodoAST> nodosHijosProgramas = new ArrayList<>();

        while (this.current != null && !this.current.getToken().getLexema().equals("Fin") ){
            NodoAST nodo  = VerificarTipoDeNodo();
            if(nodo == null){
                return null;
            }
            nodosHijosProgramas.add(nodo);
        }

        // Validar que el programa termine con el token "fin"
       if(this.current == null || !this.current.getToken().getLexema().equals("Fin")) {
           manejadorErrores.agregarError("Token Fin no detectado");
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
            case "Const":
                return GramaticaDeclaracionConstante();
        }
        if(this.current.getToken().getTipo().equals(TipoToken.IDENTIFICADOR)){
            return GramaticaAsignacion();
        }

        manejadorErrores.agregarError("No se reconoce el token " + this.current.getToken().getLexema() + " en la linea " + this.current.getToken().getRenglon() + " columna " + this.current.getToken().getColumna());
        return null;

    }



    private NodoAST GramaticaSi() {

        if(!this.current.getToken().getLexema().equals("Si")){
            manejadorErrores.agregarError("Falta el token Si en la linea " + this.current.getToken().getRenglon());
            return null;
        }
        this.current = this.current.getSiguiente();
        NodoAST condition = GramaticaRelacional();
        if(!this.current.getToken().getLexema().equals("Entonces")){
            manejadorErrores.agregarError("Falta el token Entonces en la linea " + this.current.getToken().getRenglon());
            return null;
        }
        this.current = this.current.getSiguiente();
        List<NodoAST> bodySi = new ArrayList<>();
        List<NodoAST> bodySino = new ArrayList<>();
        while (this.current != null && !this.current.getToken().getLexema().equals("FinSi")){
            NodoAST nodoSi  = VerificarTipoDeNodo();
            if(nodoSi == null){
                return null;
            }
            bodySi.add(nodoSi);

            if(this.current.getToken().getLexema().equals("Sino")){
                this.current = this.current.getSiguiente();

                while (this.current != null && !this.current.getToken().getLexema().equals("FinSi")) {
                    NodoAST nodoSino  = VerificarTipoDeNodo();
                    bodySino.add(nodoSino);

                }
            }
        }

        if(this.current == null){
            manejadorErrores.agregarError("Falta el token FinSi en la linea " + this.current.getToken().getRenglon());
            return null;
        }
        this.current = this.current.getSiguiente();
        return new NodoSi(condition, bodySi,bodySino);


    }
    private  NodoAST GramaticaRelacional(){
        if(this.current == null || (!this.current.getToken().getTipo().equals(TipoToken.IDENTIFICADOR) && !this.current.getToken().getTipo().equals(TipoToken.NUMERO) && !this.current.getToken().getTipo().equals(TipoToken.CADENA))){
            if(this.current == null){
                manejadorErrores.agregarError("tipo de token identificador, numero o cadena esperado");
            }
            manejadorErrores.agregarError("tipo de token no valido en la linea " + this.current.getToken().getRenglon() + " columna " + this.current.getToken().getColumna());
            return null;
        }
        String var1 = this.current.getToken().getLexema();
        this.current = this.current.getSiguiente();
        if(this.current == null || !this.current.getToken().getTipo().equals( TipoToken.OP_RELACIONAL)){
            if(this.current == null){
                manejadorErrores.agregarError("tipo de token operador relacional esperado");
            }
            manejadorErrores.agregarError("Falta operador Relacional en la linea " + this.current.getToken().getRenglon() + " columna " + this.current.getToken().getColumna());
            return null;
        }

        String operador = this.current.getToken().getLexema();
        this.current = this.current.getSiguiente();

        if(this.current == null || (!this.current.getToken().getTipo().equals(TipoToken.IDENTIFICADOR) && !this.current.getToken().getTipo().equals(TipoToken.NUMERO) && !this.current.getToken().getTipo().equals(TipoToken.CADENA))){
            if(this.current == null){
                manejadorErrores.agregarError("tipo de token identificador, numero o cadena esperado");
            }
            manejadorErrores.agregarError("tipo de token no valido en la linea " + this.current.getToken().getRenglon() + " columna " + this.current.getToken().getColumna());
            return null;
        }
        String var2 = this.current.getToken().getLexema();
        this.current = this.current.getSiguiente();
        NodoRelacional nodoRelacional = new NodoRelacional(var1, var2, operador);
        return nodoRelacional;

    }
    private NodoAST GramaticaMientras() {
        if(!this.current.getToken().getLexema().equals("Mientras") ){
            manejadorErrores.agregarError("Falta Mientras en la linea " + this.current.getToken().getRenglon());
            return null;
        }
        this.current = this.current.getSiguiente();
        NodoAST condition = GramaticaRelacional();
        if(this.current == null || !this.current.getToken().getLexema().equals("Entonces")){
            if(this.current == null){
                manejadorErrores.agregarError("Token Entonces esperado");
                return null;
            }
            manejadorErrores.agregarError("Falta Entonces en la linea " + this.current.getToken().getRenglon());
            return null;
        }
        this.current = this.current.getSiguiente();
        List<NodoAST> body = new ArrayList<>();
        while (this.current != null && !this.current.getToken().getLexema().equals("FinMientras")){
            NodoAST nodo  = VerificarTipoDeNodo();
            if(nodo == null){
                return null;
            }
            body.add(nodo);
//            this.current = this.current.getSiguiente();
        }
        if(this.current == null || !this.current.getToken().getLexema().equals("FinMientras")){
            if(this.current == null){
                manejadorErrores.agregarError("Token FinMientras esperado");
                return null;
            }
            manejadorErrores.agregarError("Falta FinMientras en la linea " + this.current.getToken().getRenglon());
            return null;
        }
        this.current = this.current.getSiguiente();
        return new NodoMientras(condition, body);
    }
    private NodoAST GramaticaEscribir() {
        if(!this.current.getToken().getLexema().equals("Escribir")){
            System.out.println("Falta Escribir");
            manejadorErrores.agregarError("Falta Escribir en la linea " + this.current.getToken().getRenglon());
            // por hacer Manejar error: Falta Escribir"
            return null;
        }
        this.current = this.current.getSiguiente();
        if(this.current == null){
            manejadorErrores.agregarError("Token de cadena  numero o identificador esperado");
            return null;
        }
        String contenidoNodoEscribir;
        if(this.current.getToken().getTipo().equals(TipoToken.CADENA)){
            contenidoNodoEscribir = this.current.getToken().getLexema();
        }else if(this.current.getToken().getTipo().equals(TipoToken.NUMERO)){
            contenidoNodoEscribir = this.current.getToken().getLexema();
        }else if(this.current.getToken().getTipo().equals(TipoToken.IDENTIFICADOR)){
            contenidoNodoEscribir = this.current.getToken().getLexema();
        }
        else {
            manejadorErrores.agregarError("Error en Escribir en la linea " + this.current.getToken().getRenglon() + " columna " + this.current.getToken().getColumna());
            return null;
        }
        this.current = this.current.getSiguiente();

        return new NodoEscribir(contenidoNodoEscribir);
    }

    public NodoAST GramaticaLeer() {
        if(!this.current.getToken().getLexema().equals("Leer")){
            manejadorErrores.agregarError("Falta Leer en la linea " + this.current.getToken().getRenglon());
            // por hacer Manejar error: Falta Leer"
            return null;
        }
        this.current = this.current.getSiguiente();
        if(this.current == null){
            manejadorErrores.agregarError("Token identificador esperado");
            return null;
        }
        String identificadorLeer;
         if(this.current.getToken().getTipo().equals(TipoToken.IDENTIFICADOR)){
            identificadorLeer = this.current.getToken().getLexema();
        }
        else {
            manejadorErrores.agregarError("Error en Leer: token identificador esperado en la linea " + this.current.getToken().getRenglon() + " columna " + this.current.getToken().getColumna());
            return null;
        }
        this.current = this.current.getSiguiente();

        return new NodoLeer(identificadorLeer);
    }
    private NodoAST GramaticaDeclaracionVariable() {
        if(!this.current.getToken().getLexema().equals("Num") && !this.current.getToken().getLexema().equals("Cadena")){
            manejadorErrores.agregarError("Error en declaracion de variable " + this.current.getToken().getRenglon());
            return null;
        }
        String tipo = this.current.getToken().getLexema();
        this.current = this.current.getSiguiente();
        if(this.current == null){
            manejadorErrores.agregarError("Token identificador esperado");
            return null;
        }
        if(!this.current.getToken().getTipo().equals(TipoToken.IDENTIFICADOR)){
            manejadorErrores.agregarError("Error en declaracion de variable: token identificador esperado linea " + this.current.getToken().getRenglon() + " columna " + this.current.getToken().getColumna() );
            return null;
        }

        String nombre = this.current.getToken().getLexema();
        this.current = this.current.getSiguiente();
        return new NodoDeclaracionVariable(tipo, nombre);
    }
    private NodoAST GramaticaAsignacion() {
        if(!this.current.getToken().getTipo().equals(TipoToken.IDENTIFICADOR)){
            System.out.println("Error en asignacion");
            manejadorErrores.agregarError("Error en asignacion: token identificador esperado linea " + this.current.getToken().getRenglon() + " columna " + this.current.getToken().getColumna() );
            return null;
        }
        String nombre = this.current.getToken().getLexema();
        this.current = this.current.getSiguiente();
        if(this.current == null || !this.current.getToken().getTipo().equals(TipoToken.OP_ASIGNACION)){
            if(this.current == null){
                manejadorErrores.agregarError("Token de asignacion esperado");
                return null;
            }
            manejadorErrores.agregarError("Error en asignacion: token = esperado linea " + this.current.getToken().getRenglon() + " columna " + this.current.getToken().getColumna() );
            return null;
        }
        this.current = this.current.getSiguiente();
        if(this.current == null || (!this.current.getToken().getTipo().equals(TipoToken.IDENTIFICADOR) && !this.current.getToken().getTipo().equals(TipoToken.NUMERO) && !this.current.getToken().getTipo().equals(TipoToken.CADENA))){
            if(this.current == null){
                manejadorErrores.agregarError("Token de asignacion esperado");
                return null;
            }
            manejadorErrores.agregarError("Error en asignacion: token identificador, numero o cadena esperado linea " + this.current.getToken().getRenglon() + " columna " + this.current.getToken().getColumna() );
            return null;
        }
        String valor1 = this.current.getToken().getLexema();
        this.current = this.current.getSiguiente();
        if(this.current == null){
            manejadorErrores.agregarError("Token aritmetico esperado");
        }
        if(this.current.getToken().getTipo().equals(TipoToken.OP_ARITMETICO)){
            String operador = this.current.getToken().getLexema();
            this.current = this.current.getSiguiente();
            if(this.current == null || (!this.current.getToken().getTipo().equals(TipoToken.IDENTIFICADOR) && !this.current.getToken().getTipo().equals(TipoToken.NUMERO))){
                manejadorErrores.agregarError("Error en asignacion: token identificador, numero o cadena esperado linea " + this.current.getToken().getRenglon() + " columna " + this.current.getToken().getColumna() );
                return null;
            }
            String valor2 = this.current.getToken().getLexema();
            this.current = this.current.getSiguiente();
            return new NodoAsignacion(nombre, valor1, valor2, operador);
        }

        return new NodoAsignacion(nombre, valor1);
    }

    private NodoAST GramaticaDeclaracionConstante() {
        if(!this.current.getToken().getLexema().equals("Const")){
            manejadorErrores.agregarError("Error en declaracion de constante: token Const esperado");
            return null;
        }
        this.current = this.current.getSiguiente();
        if(this.current == null){
            manejadorErrores.agregarError("Error en declaracion de constante: token Num o Cadena esperado");
            return null;
        }
        if(!this.current.getToken().getLexema().equals("Num") && !this.current.getToken().getLexema().equals("Cadena")){
            manejadorErrores.agregarError("Tipo de dato no valido en la linea " + this.current.getToken().getRenglon() + " columna " + this.current.getToken().getColumna());
            return null;
        }
        String tipo = this.current.getToken().getLexema();
        this.current = this.current.getSiguiente();
        if(this.current == null){
            manejadorErrores.agregarError("Tipo de token identificador esperado");
            return null;
        }
        if(!this.current.getToken().getTipo().equals(TipoToken.IDENTIFICADOR)){
            manejadorErrores.agregarError("Tipo de token identificador esperado en linea " + this.current.getToken().getRenglon() + " columna " + this.current.getToken().getColumna());
            return null;
        }
        String nombre = this.current.getToken().getLexema();
        this.current = this.current.getSiguiente();
        return new NodoDeclaracionConstante(tipo, nombre);

    }

}
