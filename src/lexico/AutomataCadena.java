package lexico;

import java.util.List;

public class AutomataCadena {

    private List<Contenido> contenido ;
    private LinkList listaEnlazada;
    public AutomataCadena(List<Contenido> contenido, LinkList listaEnlazada) {
        this.contenido = contenido;
        this.listaEnlazada = listaEnlazada;
    }

    public int validar(int indx) throws Exception {
        int fila = this.contenido.get(indx).getRenglon();
        String lexema = "";
        int comillas = 34;

        int j = indx + 1;
        while (j < this.contenido.size()) {
            int currCaracter = this.contenido.get(j).getCaracter();

            if (this.contenido.get(j).getRenglon() != fila) {
                throw new Exception("Error en la fila " + fila + " no hay comillas de cierre");
            }

            if (currCaracter == comillas) {
                Token token = new Token();
                token.setLexema(lexema);
                token.setTipo(TipoToken.CADENA);
                listaEnlazada.insertar(token);
                return j + 1;
            }


            lexema += (char) currCaracter;
            j++;
        }

        throw new Exception("Error en la fila" + fila + " no hay comillas de cierre");

    }
}
