package lexico;

import java.util.List;

public class AutomataIdentificadores {


    private List<Contenido> contenido;
    private LinkList listaEnlazada;
    public AutomataIdentificadores(List<Contenido> contenido, LinkList listaEnlazada) {
        this.contenido = contenido;
        this.listaEnlazada = listaEnlazada;
    }

    public  int validar(int currindx) throws Exception{
        int estado = 1;
        String lexema = "";
        while (estado == 1 || estado == 2 && currindx < contenido.size()) {
            int currcar = contenido.get(currindx).getCaracter();

            if(!Character.isLetter(currcar) && !Character.isDigit(currcar)) {
                Token token = new Token();
                token.setLexema(lexema);
                token.setTipo(TipoToken.IDENTIFICADOR);
                listaEnlazada.insertar(token);
                System.out.println("Token: " + lexema);
                estado = 3;
                return currindx ;
            }

            lexema += (char) currcar;

            switch (estado) {
                case 1:
                    if (Character.isLetter(currcar)) {
                        estado = 2;
                    }
                    break;
                case 2:
                    if (Character.isLetter(currcar) || Character.isDigit(currcar)) {
                        estado = 2;
                    }
                    break;

            }
           currindx++;
        }
        Token token = new Token();
        token.setLexema(lexema);
        token.setTipo(TipoToken.IDENTIFICADOR);
        listaEnlazada.insertar(token);
        System.out.println("Token: " + lexema);
        return currindx;

    }
}
