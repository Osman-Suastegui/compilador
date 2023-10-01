package lexico.tablaDeSimbolos;

import java.util.LinkedHashMap;
import java.util.Map;

public class TablaSimbolos {
    private static TablaSimbolos instancia; // Instancia única de la tabla de símbolos

//    unordered map
    private  Map<String, AtributosSimbolo> tabla = new LinkedHashMap<>();


    private TablaSimbolos() {}
    public static TablaSimbolos obtenerInstancia() {
        if (instancia == null) {
            instancia = new TablaSimbolos();
        }
        return instancia;
    }


    public void insertar(String lexema, AtributosSimbolo atributos) {
        this.tabla.put(lexema, atributos);
    }

    public AtributosSimbolo buscar(String lexema) {
        return this.tabla.get(lexema);
    }

    public boolean existe(String lexema) {
        return this.tabla.containsKey(lexema);
    }

    @Override
    public String toString() {
        String res = String.format("%-20s" ,"LEXEMA")  + String.format("%-20s","TIPO") + String.format("%-20s","VALOR") + "\n";

        for(Map.Entry<String, AtributosSimbolo> entry : this.tabla.entrySet()) {
            res += String.format("%-20s" ,entry.getKey())  + String.format("%-20s",entry.getValue().toString())+ "\n";
        }
        return res;

    }
}
