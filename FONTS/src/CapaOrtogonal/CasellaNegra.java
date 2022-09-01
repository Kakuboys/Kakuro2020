package CapaOrtogonal;

import CapaOrtogonal.Casella;

import java.io.Serializable;

public class CasellaNegra extends Casella {
    private int suma_fila;
    private int suma_columna;
    private int n_valorsFila;
    private int n_valorsColumna;

    //Pre: -
    //Post: Crea una casella negra amb tots els atributs igualats a 0.
    //Descripció: Constructora de casella negra que iguala tots els atributs a 0.
    public CasellaNegra() {
        this.suma_fila = 0;
        this.suma_columna = 0;
        this.n_valorsFila = 0;
        this.n_valorsColumna = 0;
    }

    //Pre: -
    //Post: Crea una casella negra amb els valors suma_fila i suma_columna igualats als arguments de la funció.
    //Descripció: Creaora de casella negra amb els valors suma_fila i suma_columna igualats als arguments de la funció.
    public CasellaNegra(int suma_fila, int suma_columna) {
        this.suma_fila = suma_fila;
        this.suma_columna = suma_columna;
    }

    //Pre: -
    //Post: Crea una casella negra amb els valors suma_fila, suma_columa, n_valorsFila i n_valorsColumna igualats als arguments.
    //Descripció: Creaora de casella negra amb els valors suma_fila, suma_columa, n_valorsFila i n_valorsColumna igualats als arguments.
    public CasellaNegra(int suma_fila, int suma_columna,int n_valorsFila,int n_valorsColumna) {
        this.suma_fila = suma_fila;
        this.suma_columna = suma_columna;
        this.n_valorsFila = n_valorsFila;
        this.n_valorsColumna = n_valorsColumna;
    }

    //Pre: -
    //Post: L'Objecte de casella negra té l'atribut suma columna igualat al argument passat per paràmetre
    //Descripció: Assigna l'argument suma_columa a l'atribut suma_columna de l'objecte.
    public void setSumaColumna(int suma_columna) {
        this.suma_columna = suma_columna;
    }

    //Pre: -
    //Post: L'Objecte de casella negra té l'atribut suma fila igualat al argument passat per paràmetre
    //Descripció: Assigna l'argument suma_fila a l'atribut suma_fila de l'objecte.
    public void setSumaFila(int suma_fila) {
        this.suma_fila = suma_fila;
    }

    //Pre: -
    //Post: L'Objecte de casella negra té l'atribut n_valorsFila igualat al argument passat per paràmetre
    //Descripció: Assigna l'argument n_valorsFila a l'atribut n_valorsFila de l'objecte.
    public void setNumValorsFila(int n_valorsFila) {
        this.n_valorsFila = n_valorsFila;
    }

    //Pre: -
    //Post: L'Objecte de casella negra té l'atribut n_valorsColumna igualat al argument passat per paràmetre
    //Descripció: Assigna l'argument n_valorsColumna a l'atribut n_valorsColumna de l'objecte.
    public void setNumValorsColumna(int n_valorsColumna) {
        this.n_valorsColumna = n_valorsColumna;
    }

    //Pre: -
    //Post: Retorna el valor suma_fila de l'objecte casella negra
    //Descripció: Retorna el valor suma_fila de l'objecte casella negra
    public int getSumaFila() {
        return suma_fila;
    }

    //Pre: -
    //Post: Retorna el valor suma_columna de l'objecte casella negra
    //Descripció: Retorna el valor suma_columna de l'objecte casella negra
    public int getSumaColumna() { 
        return suma_columna;
    }

    //Pre: -
    //Post: Retorna el valor n_valorsFila de l'objecte casella negra
    //Descripció: Retorna el valor n_valorsFila de l'objecte casella negra
    public int getNumValorsFila() {
        return n_valorsFila;
    }

    //Pre: -
    //Post: Retorna el valor n_valorsColumna de l'objecte casella negra
    //Descripció: Retorna el valor n_valorsColumna de l'objecte casella negra
    public int getNumValorsColumna() {
        return n_valorsColumna;
    }
}
