package sintactico.Sentences.Sent;

import sintactico.Sentences.Sent.NodoAST;

public class NodoLeer extends NodoAST {
    String identificador;

public NodoLeer(String identificador) {
        this.identificador = identificador;
    }

    @Override
    public void print(int depth) {
        for (int i = 0; i < depth; i++) {
            System.out.print("  ");
        }
        System.out.println("-NodoLeer");
    }
}
