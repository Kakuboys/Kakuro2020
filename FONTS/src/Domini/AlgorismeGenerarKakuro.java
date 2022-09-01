package Domini;

import CapaOrtogonal.Casella;
import CapaOrtogonal.CasellaBlanca;
import CapaOrtogonal.CasellaNegra;

import java.util.Random;

public class AlgorismeGenerarKakuro {
    private int filas;
    private int columnas;
    private int numCelasNegras;
    private int numCelasDescobertes;
    private Casella[][] taulell;
    private Casella[][] taulellResolt;
    private Integer[][] valors = {   {0},//0
            {0},//1
            {3, 4, 16, 17}, //2
            {6,7,23,24}, // 3
            {10,11,29,30},//4
            {15,16,34,35},//5
            {21,22,38,39},//6
            {28,29,41,42},//7
            {36,37,38,39,40,41,42,43,44},//8
            {45},//9
    };

    private Integer[][][] valors2 = {   {{0}},//0
            {{0}},//1
            {{3,1,2}, {4,1,3}, {16,7,9}, {17,8,9}}, //2
            {{6,1,2,3},{7,1,2,4},{23,6,8,9},{24,7,8,9}}, //3
            {{10,1,2,3,4},{11,1,2,3,5},{29,5,7,8,9},{30,6,7,8,9}},//4
            {{15,1,2,3,4,5},{16,1,2,3,4,6},{34,4,6,7,8,9},{35,5,6,7,8,9,}},//5
            {{21,1,2,3,4,5,6},{22,1,2,3,4,5,7},{38,3,5,6,7,8,9},{39,4,5,6,7,8,9}},//6
            {{28,1,2,3,4,5,6,7},{29,1,2,3,4,5,6,8},{41,2,4,5,6,7,8,9},{42,3,4,5,6,7,8,9}},//7
            {{36,1,2,3,4,5,6,7,8},{37,1,2,3,4,5,6,7,9},{38,1,2,3,4,5,6,8,9},{39,1,2,3,4,5,7,8,9},{40,1,2,3,4,6,7,8,9},{41,1,2,3,5,6,7,8,9},{42,1,2,4,5,6,7,8,9},{43,1,3,4,5,6,7,8,9},{44,2,3,4,5,6,7,8,9}},//8
            {{45,1,2,3,4,5,6,7,8,9}},//9
    };
    //Pre: -
    //Post: Es crea una objecte de la classe
    //Descripció: Constructora de la classe
    public AlgorismeGenerarKakuro(int filas,int columnas, int nCelesDescobertes, Casella[][] taulell){
        this.filas = filas;
        this.columnas = columnas;
        this.numCelasNegras = 0;
        this.numCelasDescobertes = nCelesDescobertes;
        this.taulell = taulell;
    }

    //Pre: -
    //Post: Retorna un array de valors2 que compleixi les restriccions
    //Descripció: Retorna un array de valors2 que compleixi les restriccions
    private Integer[] calcularCombinacions(int blanques, int valor) {
        for(int i = 0; i < valors2[blanques].length; ++ i) {
            if(valors2[blanques][i][0] == valor) return valors2[blanques][i];
        }
        Integer[] v = new Integer[2];
        return v;

    }

    //Pre: -
    //Post: Inserta les cel·les negres al taulell
    //Descripció: Primer inserta la primera fila i columna amb negres i després inserta fins que no troba ningún lloc disponible
    private void insertaCelesNegres() { //inserta una L de celes negres i les demes random

        for (int i = 0;i < filas; i++) { //primer possem tot el taulell en blanc
            for (int j = 0; j < columnas; j++){
                taulell[i][j] = new CasellaBlanca();
            }
        }

        for (int i = 0; i < filas; i++) { //L de negres
            taulell[i][0] = new CasellaNegra();
            ++numCelasNegras;
        }
        for (int i = 1; i < columnas; i++) {
            taulell[0][i] = new CasellaNegra();
            ++numCelasNegras;
        }

        Random rand = new Random();
        boolean stop=false;
        while (!stop) {
            boolean found=false;
            long start = System.currentTimeMillis();
            long end=start+1;
            while (!found) {
                int j = rand.nextInt(filas - 1) + 1;
                int k = rand.nextInt(columnas - 1) + 1;
                if (taulell[j][k] instanceof CasellaBlanca && estaADistancia(j, k)) {
                    taulell[j][k] = new CasellaNegra();
                    ++numCelasNegras;
                    found=true;
                }
                if (System.currentTimeMillis()>end) stop=found=true;
            }
        }
    }

    //Pre: -
    //Post: Retorna un booleà que comprova que no es deixen espais d'una cel·la blanca entre negres
    //Descripció: Retorna un booleà que comprova que no es deixen espais d'una cel·la blanca entre negres
    private boolean estaADistancia(int fila, int columna) {
        if ((columna == columnas - 2 && taulell[fila][columna+1] instanceof CasellaBlanca) || (fila == filas - 2 && taulell[fila+1][columna] instanceof CasellaBlanca)) return false;
        if ((fila + 2 < filas && taulell[fila+2][columna] instanceof CasellaNegra && taulell[fila+1][columna] instanceof CasellaBlanca) || (fila - 2 >= 0 && taulell[fila-2][columna] instanceof CasellaNegra && taulell[fila-1][columna] instanceof CasellaBlanca) ||
                (columna + 2 < columnas && taulell[fila][columna+2] instanceof CasellaNegra && taulell[fila][columna+1] instanceof CasellaBlanca) || (columna - 2 >= 0 && taulell[fila][columna-2] instanceof CasellaNegra && taulell[fila][columna-1] instanceof CasellaBlanca)) {
            return false;
        }
        return true;
    }


    //Pre: -
    //Post: Retorna un booleà que comprova les restriccions de les cel·les blanques
    //Descripció:  Comprova que no hi hagi cap valor repetit ni en la fila ni en la columna fins a arribar a una cel·la negra
    private boolean valorIsOk(int valor, int fila, int columna) {
        int limit = 0;
        if (filas > columnas) limit = filas;
        else limit = columnas;
        boolean res = true;
        boolean finishAbaix = false, finishAdalt = false, finishDreta = false, finishEsquerra = false;
        for (int i = 1; i < limit && res; i++) {
            if (!finishAbaix) { //cap abaix
                if (fila + i == filas || taulell[fila+i][columna] instanceof CasellaNegra) finishAbaix = true;
                else if (((CasellaBlanca)taulell[fila+i][columna]).getNum() == valor) res = false;
            }

            if (!finishAdalt) { //cap adalt
                if (fila - i < 0 || taulell[fila-i][columna] instanceof CasellaNegra) finishAdalt = true;
                else if (((CasellaBlanca)taulell[fila-i][columna]).getNum() == valor) res = false;
            }

            if (!finishDreta) { //cap a la dreta
                if (columna+i == columnas || taulell[fila][columna+i] instanceof CasellaNegra) finishDreta = true;
                else if (((CasellaBlanca)taulell[fila][columna+i]).getNum() == valor) res = false;
            }

            if (!finishEsquerra) { //cap a l'esquerra
                if (columna - i < 0 || taulell[fila][columna-i] instanceof CasellaNegra) finishEsquerra = true;
                else if (((CasellaBlanca)taulell[fila][columna-i]).getNum() == valor) res = false;
            }
        }
        return res;
    }

    //Pre: -
    //Post:  Retorna quantes blanques hi han en una columna a partir d’una cel·la negra
    //Descripció:  Retorna quantes blanques hi han en una columna a partir d’una cel·la negra
    private int quantesBlanquesColumna(int fila, int columna) {
        if (fila == filas - 1) return 0;
        int sum = 0;
        for (int i = fila + 1; i < filas; i++) {
            if (taulell[i][columna] instanceof CasellaBlanca) ++sum;
            else return sum;
        }
        return sum;
    }

    //Pre: -
    //Post:  Retorna quantes blanques hi han en una fila a partir d’una cel·la negra
    //Descripció:  Retorna quantes blanques hi han en una fila a partir d’una cel·la negra
    private int quantesBlanquesFila(int fila, int columna) {
        if (columna == columnas-1) return 0;
        int sum = 0;
        for (int j = columna + 1; j < columnas; j++) {
            if (taulell[fila][j] instanceof CasellaBlanca) ++sum;
            else return sum;
        }
        return sum;
    }
    //Pre: -
    //Post:  Retorna la suma de números que estan dins les cel·les blanques a partir d’una cel·la negra i fins a una altre cel·la negra
    //Descripció:  Retorna la suma de números que estan dins les cel·les blanques a partir d’una cel·la negra i fins a una altre cel·la negra
    private int mirarValorsFila(int i, int j) {
        int suma = 0;
        boolean negra = false;

        for(int a = j + 1; a < columnas && !negra; ++a) {
            if(taulell[i][a] instanceof CasellaBlanca) {
                suma += ((CasellaBlanca) taulell[i][a]).getNum();
            }
            else negra = true;
        }
        return suma;
    }


    //Pre: Array de ints no buit
    //Post:  Retorna un array de ints amb la posició i del array eliminada
    //Descripció:  Elimina la posició i del array arrayObjetos
    private  static Integer[]  removeElement(Integer[] arrayObjetos, int i) {
        Integer[] nuevoArray = new Integer[arrayObjetos.length - 1];
        if (i > 0){
            System.arraycopy(arrayObjetos, 0, nuevoArray, 0, i);
        }
        if (nuevoArray.length > i){
            System.arraycopy(arrayObjetos, i + 1, nuevoArray, i, nuevoArray.length - i);
        }
        return nuevoArray;
    }

    //Pre: -
    //Post:  Posa un 0 a les caselles blanques a partir d’una cel·la negra i fins a una cel·la negra
    //Descripció:  Posa un 0 a les caselles blanques a partir d’una cel·la negra i fins a una cel·la negra
    private void borrarelements(int i, int j) {
        for(int t = i; t < filas - 1; ++t) {
            for(int k = 0; k < columnas ; ++k) {
                if(taulell[t][k] instanceof CasellaNegra) {
                    if(taulell[t+1][k] instanceof CasellaBlanca) {
                        ((CasellaBlanca)taulell[t+1][k]).setNum(0);
                    }
                }
            }
        }
    }




    //Pre: -
    //Post:   Omple cada una de les cel·les negres amb sumes columna
    //Descripció:  Omple cada una de les cel·les negres amb sumes columna
    private Boolean omplirSumesColumna() {
        for(int i = 0; i < filas - 1; ++i) {
            for(int j = 0; j < columnas ; ++j) {
                Boolean trobatfinal = false;
                if(taulell[i][j] instanceof CasellaNegra) {
                    if(taulell[i+1][j] instanceof CasellaBlanca) {
                        int numblanques = quantesBlanquesColumna(i,j);
                        Random ran = new Random();
                        Integer[] h = (Integer []) valors[numblanques].clone();
                        int mida = h.length;
                        boolean trobat = false;
                        for(int l = 0; l < mida && !trobat; ++l) {
                            int v = ran.nextInt(h.length);
                            Boolean t = comprovarRestriccions(numblanques,h[v],i,j);
                            if(t) {
                                trobat = true;
                                trobatfinal = true;
                            }
                            else {
                                h = removeElement(h,v);
                                borrarelements(i,j);
                            }
                        }
                        if(!trobatfinal) {
                            return false;
                        }
                        else trobatfinal = true;
                    }

                }
            }
        }
        return true;
    }


    //Pre: -
    //Post:   Omple cada una de les cel·les negres amb sumes columna
    //Descripció:  Omple cada una de les cel·les negres amb sumes columna
    private void omplirSumesFila() {
        for (int i = 0; i < filas ; ++i){
            for(int j = 0; j < columnas - 1 ; ++j) {
                if(taulell[i][j] instanceof CasellaNegra) {
                    if(taulell[i][j+1] instanceof CasellaBlanca) {
                        int suma = mirarValorsFila(i,j);
                        ((CasellaNegra) taulell[i][j]).setSumaFila(suma);
                    }
                }
            }
        }
    }

    //Pre: -
    //Post:  Omple els valors corresponent de les cel·les blanques amb una combinació agafada aleatòriament.
    //Descripció: Omple els valors corresponent de les cel·les blanques amb una combinació agafada aleatòriament.
    private boolean comprovarRestriccions(int numblanques, int v, int i, int j) {
        Integer[] comb = calcularCombinacions(numblanques,v);
        comb = removeElement(comb,0);
        int mida = comb.length;
        Random ran = new Random();

        for(int a = i + 1; a < filas && taulell[a][j] instanceof CasellaBlanca; ++a) {
            Integer[] comb2 = (Integer []) comb.clone();
            boolean trobat = false;
            for(int k = 0; k < comb.length && !trobat; ++k) {
                int p = ran.nextInt(comb2.length);
                if(valorIsOk(comb2[p],a,j)) {
                    trobat = true;
                    ((CasellaBlanca) taulell[a][j]).setNum(comb2[p]);
                    boolean trob = false;
                    for(int y = 0; y < comb.length && !trob; ++y) {
                        if(comb[y] == comb2[p]) {
                            trob = true;
                            comb = removeElement(comb,y);
                        }
                    }

                    comb2 = removeElement(comb2,p);
                }
                else comb2 = removeElement(comb2,p);
            }
            if(!trobat) return false;
        }
        ((CasellaNegra)taulell[i][j]).setSumaColumna(v);
        return true;
    }



    //Pre: -
    //Post:   Buida els números posats a les cel·les blanques ( posats per a generar el taulell)
    //Descripció:  Buida els números posats a les cel·les blanques ( posats per a generar el taulell)
    private void buidar() {
        for(int i = 0; i < filas ; ++i) {
            for(int j = 0; j < columnas; ++j) {
                if(taulell[i][j] instanceof CasellaBlanca) ((CasellaBlanca) taulell[i][j]).setNum(0);
            }
        }
    }

    //Pre: El taulell ha de estar resolt
    //Post:   Buida els números posats a les cel·les blanques del taulell resolt i deixa alguna cel·les blanques descobertes ( amb el número posat )
    //Descripció:  Buida els números posats a les cel·les blanques del taulell resolt i deixa alguna cel·les blanques descobertes ( amb el número posat )
    public void buidarambcelesdescobertes(int numCelesDescobertes){
        int ncd = numCelasDescobertes;
        int numblanques = 0;
        for(int i = 0; i < filas; ++i){
            for(int j = 0; j < columnas; ++j){
                if(taulell[i][j] instanceof CasellaBlanca){
                    ++numblanques;
                }

            }
        }

        for(int i = 0; i < filas ; ++i) {
            for(int j = 0; j < columnas; ++j) {
                if(taulell[i][j] instanceof CasellaBlanca){
                    if(ncd > 0){
                        if(numblanques > ncd){
                            Random ran = new Random();
                            int si_no = ran.nextInt(2);
                            if(si_no == 0 ) ((CasellaBlanca) taulell[i][j]).setNum(0);
                            else {
                                --ncd;
                            }
                        }
                        else{
                            --ncd;
                        }

                    }
                    else  ((CasellaBlanca) taulell[i][j]).setNum(0);
                    --numblanques;
                }
            }
        }
    }






    //Pre: -
    //Post:   Executa cada una de les funcions del algoritme
    //Descripció:   Executa cada una de les funcions del algoritme
    public Boolean execute() {
        insertaCelesNegres();
        if(!omplirSumesColumna()) return false;
        omplirSumesFila();
        taulellResolt = trueClone(taulell);
        buidar();
        return true;
    }

    //Pre: Ni taulell1 ni els seus elements son nulls.
    //Post: Retorna un clon de la matriu de Casella's fet amb deep copy.
    //Descripcio: Clonadora de matrius de Casella's
    private Casella[][] trueClone(Casella[][] taulell1) {   //fa deep copy
        Casella[][] clon = new Casella[taulell1.length][taulell1[0].length];

        for (int i = 0; i < taulell1.length; i++) {
            for (int j = 0; j < taulell1[0].length; j++) {
                clon[i][j] = taulell1[i][j].cloneMeu();
            }
        }
        return clon;
    }



    //Pre: -
    //Post:   Retorna taulellResolt
    //Descripció:  Retorna taulellResolt
    public Casella[][] getTaulellResolt() {
        return taulellResolt;
    }

    //Pre: -
    //Post:   Retorna taulell
    //Descripció:  Retorna taulell
    public Casella[][] getTaulell() {
        return taulell;
    }

    //Pre: -
    //Post: Retorna numCelasNegras
    //Decripció: Retorna numCelasNegras
    public int getNumCelasNegras() {
        return numCelasNegras;
    }
}