import lexico.AutomataIdentificadores;
import lexico.AutomataNumeros;
import lexico.Contenido;
import lexico.LinkList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        List<Contenido> contenido = leerContenidoDelArchivo("C:\\Users\\osman\\OneDrive\\Desktop\\COMPILADOR_JAVA\\src\\archivo.txt");

        LinkList listaEnlazada = new LinkList();
        AutomataIdentificadores AI = new AutomataIdentificadores(contenido, listaEnlazada);
        AutomataNumeros AN = new AutomataNumeros(contenido, listaEnlazada);

        procesarContenido(contenido, AI, AN, listaEnlazada);

        System.out.println("LISTA ENLAZADA: ");
        listaEnlazada.imprimir();
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
            if (c == 13) {
                continue;
            }
            contenido.add(new Contenido((char) c, renglon, columna));
            columna++;
        }
        br.close();
        return contenido;
    }
    private static boolean isRelational(String input){

        return  input.equals("=>") || input.equals("==") || input.equals("!=") || input.equals("<=") || input.equals(">=") ;
    }
    private static boolean isAritmetic(int input){

        return  input == '+' || input == '-' || input == '*' || input == '/' ;
    }
    private static void procesarContenido(List<Contenido> contenido, AutomataIdentificadores AI, AutomataNumeros AN, LinkList listaEnlazada) throws Exception {
        int currIndx = 0;

        while (currIndx < contenido.size()) {
            int car = contenido.get(currIndx).getCaracter();
            if (Character.isLetter(car)) {
                currIndx = AI.validar(currIndx);
            } else if (Character.isDigit(car)) {
                currIndx = AN.validar(currIndx);
            }
//            si es relacional con dos caractereres
            else if(currIndx +  1 < contenido.size() && isRelational( car + "" + contenido.get(currIndx + 1).getCaracter())){
                currIndx +=2;
//                CREAMOS TOKEN
            }else if(car == '=' ) {
                currIndx++;
//                CREAMOS TOKEN
//                si es relacional con 1 caracter
            }else if(car == '>' || car == '<'){
//                CREAMOS TOKEN
                currIndx++;

            }
            else if(isAritmetic(car)) {
//                creamos token
                currIndx++;
            }else {
                currIndx++;
            }
        }
    }
}
