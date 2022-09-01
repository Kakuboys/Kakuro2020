package Domini;

import CapaOrtogonal.Casella;
import CapaOrtogonal.CasellaBlanca;
import CapaOrtogonal.CasellaNegra;

import java.util.Stack;

public class AlgorismeSolucionarKakuro {

    private int filas;
    private int columnas;
    private Casella[][] taulell;
    private Casella[][] taulellResolt;
    private boolean teSolucio;
    private int n_solucions_trobades;

    //Pre: -
    //Post: Crea un AlgorismeSolucionarKakuro, amb els valors indicats de filas, columnas i taulell.
    //Descripcio: Constructora d'AlgorismeSolucionarKakuro
    public AlgorismeSolucionarKakuro(int filas,int columnas, Casella[][] taulell){
        this.filas=filas;
        this.columnas=columnas;
        this.taulell = taulell;
        n_solucions_trobades = 0;
        teSolucio = false;
    }

    //Pre: Ni taulell1 ni els seus elements son nulls.
    //Post: Retorna un clon de la matriu de Casella's fet amb deep copy.
    //Descripcio: Clonadora de matrius de Casella's
    private Casella[][] trueClone(Casella[][] taulell1) {
        Casella[][] clon = new Casella[filas][columnas];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                clon[i][j] = taulell1[i][j].cloneMeu();
            }
        }
        return clon;
    }

    //Pre: ni estat ni els seus elements ni cela_blanca_buida són nulls.
    //Post: retorna cert si i nomes si queda alguna cela blanca buida en el taulell, i si es així, ha posat les seves coordenades a cela_blanca_buida
    //Descripcio: Busca la proxima cela blanca buida
    private boolean proximaCelaBlancaBuida(Casella[][] estat, Pair<Integer,Integer> cela_blanca_buida) {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; ++j) {
                if (estat[i][j].esBlancaBuida()) {
                    cela_blanca_buida.first=i;
                    cela_blanca_buida.second=j;
                    return true;
                }
            }
        }
        return false;
    }

    //Pre: ni estat ni els seus elements ni cela_actual són nulls
    //Post: Retorna fals si el nombre de la cela_actual ja esta en alguna cela de la seva fila o de la seva columna,
        // o si la suma total de la fila o de la columna han sobrepassat la suma indicada per la corresponent casella negra,
        // o si es la ultima casella de la fila o de la columna i aquesta no te la suma esperada.
    //Descripció: Comprova que l'estat no violi constraints
    private boolean esPrometedora(Casella[][] estat, Pair<Integer,Integer> cela_actual) {
        int x = cela_actual.getFirst();
        int y = cela_actual.getSecond();
        int valorNostre = ((CasellaBlanca)estat[x][y]).getNum();
        int real_run_sum = 0;
        int supposed_run_total;

        //la columna:
        //anem de la casella cap a dalt
        int i;
        real_run_sum += ((CasellaBlanca)estat[x][y]).getNum();
        for (i = x-1; i >= 0 && (estat[i][y] instanceof CasellaBlanca); --i) {	//aixo esta be no?
            real_run_sum += ((CasellaBlanca)estat[i][y]).getNum();
            if (((CasellaBlanca)estat[i][y]).getNum() == valorNostre)
                return false;
        }//arribem a la negra
        supposed_run_total = ((CasellaNegra)estat[i][y]).getSumaColumna();

        //i ara a recorrer des de la casella+1 cap a baix
        boolean era_la_ultima_de_la_columna=true;
        for (i = x+1; i < filas && (estat[i][y] instanceof CasellaBlanca); ++i) {
            if(era_la_ultima_de_la_columna) era_la_ultima_de_la_columna=false;
        }//arribem al final del taulell o a una negra que no ens importa

        if (real_run_sum > supposed_run_total)
            return false;
        if(era_la_ultima_de_la_columna && real_run_sum!=supposed_run_total) return false;
        //fins aqui hem comprovat que a la columna: la suma sigui correcte i no hi hagi duplicats.

        //la fila:      //(reutilitzo variables)
        real_run_sum = 0;
        real_run_sum += ((CasellaBlanca) estat[x][y]).getNum();
        for (i = y-1; i >= 0 && (estat[x][i] instanceof CasellaBlanca); --i) {
            real_run_sum += ((CasellaBlanca) estat[x][i]).getNum();
            if (((CasellaBlanca) estat[x][i]).getNum() == valorNostre)
                return false;
        }//arribem a la negra
        supposed_run_total = ((CasellaNegra)estat[x][i]).getSumaFila();
        boolean era_la_ultima_de_la_fila=true;
        for (i = y+1; i < columnas && (estat[x][i] instanceof CasellaBlanca); ++i) {
            if(era_la_ultima_de_la_fila) era_la_ultima_de_la_fila=false;
        }//arribem al final del taulell o a una negra que no ens importa

        if (real_run_sum > supposed_run_total)
            return false;
        if(era_la_ultima_de_la_fila && real_run_sum!=supposed_run_total) return false;
        //hem comprovat que a la columna i la fila: les sumes siguin correctes i no hi hagi duplicats.
        return true;
    }


    //Pre: Ni estat ni els seus elements ni cela_actual ni pila_solucio son nulls
    //Post: Retorna cert si aquesta crida o una filla seva troben una solució al taulell, i fica la solució a pila_solucio.
    //Descripcio: Funció recursiva que utilitza backtracking per a trobar una solucio al taulell.
    private boolean solve(Casella[][] estat, Pair<Integer,Integer> cela_actual, Stack<Casella[][]> pila_solucio) {
        for(int i = 1; i < 10; ++i) {
            ((CasellaBlanca)estat[cela_actual.getFirst()][cela_actual.getSecond()]).setNum(i);
            if(esPrometedora(estat, cela_actual)) {
                Pair<Integer,Integer> cela_blanca_buida = Pair.createPair(0,0);
                if(!proximaCelaBlancaBuida(estat, cela_blanca_buida)) {//si no hi ha mes celles blanques buides

                    Casella[][] clon_estat = trueClone(estat);
                    pila_solucio.push(clon_estat);

                    if (pila_solucio.size() != 1)   //si no era la primera solucio
                        return true;
                }
                else {
                    if(solve(estat, cela_blanca_buida, pila_solucio)) {
                        return true;
                    }
                }
            }
        }
        ((CasellaBlanca)estat[cela_actual.getFirst()][cela_actual.getSecond()]).setNum(0);
        return false;
    }

    //Pre: -
    //Post: dona valor a teSolucio segons si s'ha trobat solucio,
        // dona valor a n_solucions_trobades segons la quantitat de solucions del taulell,
        // i si s'ha trobat solució la posa a taulellResolt.
    //Descripcio: Executa l'algorisme de solucionar kakuros
    public void execute() {
        Pair<Integer, Integer> cela_actual = Pair.createPair(0,0);
        Casella[][] estat = this.taulell.clone();
        proximaCelaBlancaBuida(estat, cela_actual);
        Stack<Casella[][]> pila_solucio  = new Stack<Casella[][]>();
        teSolucio = solve(estat, cela_actual, pila_solucio);
        n_solucions_trobades = pila_solucio.size();
        teSolucio = (n_solucions_trobades > 0);
        if(teSolucio) this.taulellResolt=pila_solucio.peek();
    }

    //Pre: -
    //Post: retorna teSolucio, que indica si s'ha trobat solució per al taulell
    //Descripcio: getter de teSolucio
    public boolean isTeSolucio() {
        return teSolucio;
    }

    //Pre: s'ha d'haver executat l'agorisme i s'ha d'haver trobat solucio
    //Post: retorna el taulell Resolt
    //Descripcio: getter de taulellResolt
    public Casella[][] getTaulellResolt() {
        return taulellResolt;
    }

    //Pre: -
    //Post: retorna el nombre de solucions trobades, amb un maxim de 2. (el valor 2 indica que el taulell té més d'una solució)
    //Descripcio: getter de n_solucions_trobades
    public int getN_solucions_trobades() {
        return n_solucions_trobades;
    }
}