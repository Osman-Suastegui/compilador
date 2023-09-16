package lexico;

import java.util.List;

public class AutomataNumeros {

    private List<Contenido> contenido;
    private LinkList listaEnlazada;

    public AutomataNumeros(List<Contenido> contenido, LinkList listaEnlazada) {
        this.contenido = contenido;
        this.listaEnlazada = listaEnlazada;
    }

    public int validar(int currIndx) {
        char currChar = contenido.get(currIndx).getCaracter();
        String lexema = "";
        while (currIndx < contenido.size() && (Character.isDigit(currChar) || (currChar == '.' && lexema.indexOf(".") == -1))) {

            lexema += currChar;
            currIndx++;
            if (currIndx < contenido.size()) {
                currChar = contenido.get(currIndx).getCaracter();
            }
        }
        System.out.println("Token: " + lexema);

        Token token = new Token();
        token.setLexema(lexema);
        token.setTipo(TipoToken.NUMERO);
        listaEnlazada.insertar(token);
        return currIndx;

    }
}
