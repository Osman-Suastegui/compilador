import lexico.*;
import lexico.tablaDeSimbolos.AtributosSimbolo;
import lexico.tablaDeSimbolos.ManejadorErrores;
import lexico.tablaDeSimbolos.TablaSimbolos;
import sintactico.MainSintactico;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        List<Contenido> contenido = leerContenidoDelArchivo("C:\\Users\\osman\\OneDrive\\Desktop\\COMPILADOR_JAVA\\src\\archivo.txt");

        LinkList listaEnlazada = new LinkList();
        AutomataIdentificadores AI = new AutomataIdentificadores(contenido, listaEnlazada);
        AutomataNumeros AN = new AutomataNumeros(contenido, listaEnlazada);
        AutomataCadena AC = new AutomataCadena(contenido,listaEnlazada);
        procesarContenido(contenido, AI, AN,AC, listaEnlazada);
        insertarEnTablaDeSimbolos(listaEnlazada);
        TablaSimbolos t = TablaSimbolos.obtenerInstancia();
        System.out.println(t.toString());
<<<<<<< HEAD
        MainSintactico sint = new MainSintactico(listaEnlazada);
=======
        ManejadorErrores manejadorErrores = ManejadorErrores.obtenerInstancia();
        manejadorErrores.mostrarErrores();
>>>>>>> fbcb55c62e44081cc91b1ae54c88351a40425bf0

    }


    //    esta funcion hace uso de la lista enlazada para construir una parte de la tabla de simbolos
    public static void insertarEnTablaDeSimbolos(LinkList listaEnlazada){
        listaEnlazada.imprimir();
        Nodo nodo = listaEnlazada.getRaiz();
        TablaSimbolos tabla = TablaSimbolos.obtenerInstancia();

        while(nodo != null){
            Token token = nodo.getToken();
            String lexema = token.getLexema();

            AtributosSimbolo atributos = new AtributosSimbolo();
            atributos.setTipo(token.getTipo());
            atributos.setValor(token.getValor());
            atributos.setLexema(lexema);
            atributos.setRenglon(token.getRenglon());
            atributos.setColumna(token.getColumna());
            tabla.insertar(atributos);

            nodo = nodo.getSiguiente();
        }

    }


    private static List<Contenido> leerContenidoDelArchivo(String filePath) throws IOException {
        List<Contenido> contenido = new ArrayList<>();
        File file = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(file));

        int c;
        int renglon = 1;
        int columna = 1;

        while ((c = br.read()) != -1) {
            if (c == 10) {
                renglon++;
                columna = 1;

            }

            contenido.add(new Contenido((char) c, renglon, columna));
            columna++;
        }
        br.close();
        return contenido;
    }
    private static boolean isRelational(String input){

        return  input.equals("=>") || input.equals("==") || input.equals("<>") || input.equals("<=") || input.equals(">=") ;
    }
    private static boolean isAritmetic(int input){

        return  input == '+' || input == '-' || input == '*' || input == '/' ;
    }
    private static void procesarContenido(List<Contenido> contenido, AutomataIdentificadores AI, AutomataNumeros AN, AutomataCadena AC, LinkList listaEnlazada) throws Exception {
        ManejadorErrores manejadorErrores = ManejadorErrores.obtenerInstancia();
        int currIndx = 0;
        int comillas = 34;
        int saltoDeLinea = 10;
        int enter = 13;
        int espacio = 32;

        while (currIndx < contenido.size()) {
            int car = contenido.get(currIndx).getCaracter();
            int curRenglon = contenido.get(currIndx).getRenglon();
            int curColumna = contenido.get(currIndx).getColumna();
            if (Character.isLetter(car)) {
                currIndx = AI.validar(currIndx,"",curRenglon,curColumna);
            } else if (Character.isDigit(car)) {

                currIndx = AN.validar(currIndx);
            }else if(car == 34){
                currIndx = AC.validar(currIndx);
            }
//            si es relacional con dos caractereres

            else if(currIndx +  1 < contenido.size() && isRelational ((char) car + "" + contenido.get(currIndx + 1).getCaracter())){
                String lex = (char) car + "" + contenido.get(currIndx + 1).getCaracter();
                Token token = new Token(TipoToken.OP_RELACIONAL,lex,0.0,curRenglon,curColumna);
                listaEnlazada.insertar(token);
                currIndx += 2;
            }else if(car == '=' ) {
                Token token = new Token(TipoToken.OP_ASIGNACION,"=",0.0,curRenglon,curColumna);
                listaEnlazada.insertar(token);

                currIndx++;
            }else if(car == '>' || car == '<'){
                //si es relacional con 1 caracter
                String lex = (char) car + "";
                Token token = new Token(TipoToken.OP_RELACIONAL,lex,0.0,curRenglon,curColumna);
                listaEnlazada.insertar(token);
                currIndx++;
            }
            else if(isAritmetic(car)) {
                String lexema = (char) car + "";
                Token token = new Token(TipoToken.OP_ARITMETICO,lexema,0.0,curRenglon,curColumna);
                listaEnlazada.insertar(token);
                currIndx++;
            }else if(car == espacio || car == saltoDeLinea  || car == enter) {
                currIndx++;
                continue;
            }
            else {
                String caracterErroneo = (char) car + "";
                manejadorErrores.agregarError("CARACTER " + caracterErroneo + " NO VALIDO EN LA LINEA " + curRenglon);
                currIndx++;
            }
        }
    }
}
