import lexico.AutomataIdentificadores;
import lexico.AutomataNumeros;
import lexico.Contenido;
import lexico.LinkList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
//        LEEMOS EL CONTENIDO DEL ARCHIVO
        File file = new File("C:\\Users\\osman\\OneDrive\\Desktop\\COMPILADOR_JAVA\\src\\archivo.txt");
        List<Contenido> contenido = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(file));

        int c;
        int renglon = 1;
        int columna = 2;

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
    //        TERMINAMOS DE LEER EL CONTENIDO DEL ARCHIVO
        int currIndx = 0;

//        CREAMOS LOS AUTOMATAS PARA IDENTIFICADORES Y NUMEROS
        LinkList listaEnlazada = new LinkList();
        AutomataIdentificadores automataIdentificadores = new AutomataIdentificadores(contenido, listaEnlazada);
        AutomataNumeros automataNumeros = new AutomataNumeros(contenido,listaEnlazada);

//   LEE EL CONTENIDO DEL ARCHIVO Y LO MANDA A LOS AUTOMATAS PARA QUE CREEN LOS TOKENS
        while (currIndx < contenido.size()) {
            int car = contenido.get(currIndx).getCaracter();

            if (Character.isLetter(car)) {
                currIndx = automataIdentificadores.validar(currIndx);
            }
            else if(Character.isDigit(car)){
                currIndx = automataNumeros.validar(currIndx);
            }
            else{
                currIndx++;
            }
        }
        System.out.println("LISTA ENLAZADA: ");
        listaEnlazada.imprimir();

    }
}