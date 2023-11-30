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
        String file = "C:\\Users\\osman\\OneDrive\\Desktop\\COMPILADOR_JAVA\\src\\archivo.txt";
        MainLexico lexico = new MainLexico(file);

    }

}
