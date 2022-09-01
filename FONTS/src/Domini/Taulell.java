package Domini;


import CapaOrtogonal.Casella;
import CapaOrtogonal.CasellaBlanca;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Taulell implements Serializable {
    private int filas;
    private int columnas;
    private int numCelasNegras;
    private int numCelasDescobertes;
    private String nom;
    private String dificultat;
    private boolean es_personalitzat;
    private Casella[][] taulell;
    private Casella[][] taulellResolt;
    private Map<String, Integer> ranking= new HashMap<String, Integer>();
    private ArrayList<Pair<Integer,Integer>> descobertes = new ArrayList<Pair<Integer, Integer>>();
    private static final long serialVersionUID = 6529685098267758690L;

    //Pre: -
    //Post: Inicialitza un taulell amb els arguments passat per paràmetre
    //Decripció: Constructora de taulell inicialitzat amb nfilas i ncolumnas
    public Taulell(int nfilas, int ncolumnas) {
        this.filas = nfilas;
        this.columnas = ncolumnas;
        taulell = new Casella[nfilas][ncolumnas];
        calcularDificultatTaulell();
    }

    //Pre: -
    //Post: Inicialitza un taulell filas, columnas, númerod e cel·les negres i número de cel·les descobertes a 0,
    //nom i dificultat com a nulls, es_personalitzat com fals, i el taulell com una matriu de caselles de 0x0.
    //Decripció: Constructora de taulell que posa tots els atributs a null, 0 o false depenent del tipus de variable
    public Taulell() {
        this.filas = 0;
        this.columnas = 0;
        this.numCelasNegras = 0;
        this.numCelasDescobertes = 0;
        this.nom = "";
        this.dificultat = "";
        this.es_personalitzat = false;
        this.taulell = new Casella[0][0];
        this.taulellResolt = new Casella[0][0];
        calcularDificultatTaulell();
    }

    //Pre: -
    //Post: Inicialitza taulell amb els valors passats per paràmetre, es_personalitzat com a true i l'atribut taulell com una matriu de caselles de nfilas x ncolmnas.
    //Decripció: Constructora de taulell personalitzat, que iguala els atributs als arguments passats per paràmetre, marca es_personalitzat com a true i inicialitza el tamany de
    //la matriu taulell
    public Taulell(int nfilas, int ncolumnas, int nCelesDescobertes) { //l'utilitza AlgoritmeGenerarKakuro
        this.filas = nfilas;
        this.columnas = ncolumnas;
        this.numCelasNegras = 0;
        this.numCelasDescobertes = nCelesDescobertes;
        this.es_personalitzat = true;
        taulell = new Casella[nfilas][ncolumnas];
        taulellResolt = new Casella[nfilas][ncolumnas];
        calcularDificultatTaulell();
    }

    //Pre: -
    //Post: Inicialitza taulell amb els valors passats per paràmetre, amb la dificultat calculada per calcularDificultatTaulell()
    //Decripció: Constructora de taulell personalitzat, que iguala els atributs als arguments passats per paràmetre.
    public Taulell(int filas, int columnas, String nom, boolean es_personalitzat, Casella[][] taulell, Casella[][] taulellResolt) {
        this.filas = filas;
        this.columnas = columnas;
        this.nom = nom;
        this.es_personalitzat = es_personalitzat;
        this.taulell = taulell;
        this.taulellResolt = taulellResolt;
        calcularDificultatTaulell();
    }

    //Pre: -
    //Post: Inicialitza taulell amb els valors passats per paràmetre, amb la dificultat calculada per calcularDificultatTaulell(), i amb descobertes inicialitzat.
    //Decripció: Constructora de taulell personalitzat, que iguala els atributs als arguments passats per paràmetre.
    public Taulell(int filas, int columnas, int numCelasNegras, int numCelasDescobertes, String nom, boolean es_personalitzat, Casella[][] taulell, Casella[][] taulellResolt) {
        this.filas = filas;
        this.columnas = columnas;
        this.numCelasNegras = numCelasNegras;
        this.numCelasDescobertes = numCelasDescobertes;
        this.nom = nom;
        this.es_personalitzat = es_personalitzat;
        this.taulell = taulell;
        this.taulellResolt=taulellResolt;
        calcularDificultatTaulell();
        omplirDescobertes();
    }

    //Pre: -
    //Post: Si el taulellPartida es correcte, retorna true. En cas contrari retorna false
    //Descripció: Valida que el taulellPartida omplit per l'usuari es correcte
    public boolean validarTaulell(Casella[][] taulell1) {
        for (int i = 0; i < filas; ++i) {
            for (int j = 0; j < columnas; j++) {

                if(taulellResolt[i][j] instanceof CasellaBlanca && taulell1[i][j] instanceof CasellaBlanca
                        && ((CasellaBlanca)taulellResolt[i][j]).getNum() != ((CasellaBlanca)taulell1[i][j]).getNum())
                    return false;
            }
        }
        return true;
    }

    //Pre: taulell, descobertes i taulellResolt no son nulls
    //Post: retorna una matriu de Casella's que es com la passada per parametre pero
        // mostra la resposta d'una casella encara no mostrada ni resposta.
    //descripcio: mostra la resposta d'una casella del taulell
    public void demanarComodi(Casella[][] taulell_sense_comodi){
        int numblanques = 0;
        for(int i = 0; i < filas; ++i){
            for(int j = 0; j < columnas; ++j){
                if(taulell[i][j] instanceof CasellaBlanca  && ((CasellaBlanca)taulell_sense_comodi[i][j]).getNum() == 0){
                    ++numblanques;
                }
            }
        }

        int com = 1;
        for(int i = 0; i < filas; ++i){
            for(int j = 0; j < columnas; ++j){
                if(taulell_sense_comodi[i][j] instanceof CasellaBlanca && ((CasellaBlanca)taulell_sense_comodi[i][j]).getNum() == 0 && com > 0){
                    if(numblanques > com){
                        Random ran = new Random();
                        if(ran.nextInt(2) == 1 ){
                            --com;
                            descobertes.add(new Pair(i,j));
                            ((CasellaBlanca)taulell_sense_comodi[i][j]).setNum(((CasellaBlanca)taulellResolt[i][j]).getNum());
                        }
                    }
                    else{
                        --com;
                        descobertes.add(new Pair(i,j));
                        ((CasellaBlanca)taulell_sense_comodi[i][j]).setNum(((CasellaBlanca)taulellResolt[i][j]).getNum());
                    }
                    --numblanques;
                }
            }
        }
    }

    //Pre: -
    //Post: posa "facil" a dificultat si el nombre de caselles es inferior a 20.
        //hi posa "normal" si es troba entre 20 i 59, i "dificil" si es 60 o mes.
    //descripcio: calcula la dificultat del taulell utilitzant algorismes d'ultima generacio
    private void calcularDificultatTaulell() {        //[9, 19], [20, 59], [60, 100]
        int mida = filas*columnas;
        if (mida < 20)
            this.dificultat = "facil";
        else if (mida < 60)
            this.dificultat = "normal";
        else
            this.dificultat =  "dificil";
    }

    //Pre: -
    //Post: L'Objecte taulell té assignat el valor nfilas passat per paràmetre
    //Decripció: Setter de l'atribut nfilas de Taulell
	public void setFilas(int nfilas) {
		   this.filas = nfilas;
	 }

    //Pre: -
    //Post: L'Objecte taulell té assignat el valor ncolumnas passat per paràmetre
    //Decripció: Setter de l'atribut ncolumnas de Taulell
	public void setColumnas(int ncolumnas) {
		   this.columnas = ncolumnas;
	 }

    //Pre: -
    //Post: L'Objecte taulell té assignada la matriu de caselles al seu atribut taulell
    //Decripció: Setter de l'atribut taulell de Taulell
    public void setTaulell(Casella[][] taulell) {
        this.taulell = taulell;
    }

    //Pre: -
    //Post: Retorna l'atribut numCelasNegras de l'objecte taulell
    //Decripció: Getter de l'atribut numCelasNegras de Taulell
    public int getNumCelasNegras() {
        return numCelasNegras;
    }

    //Pre: -
    //Post: L'Objecte taulell té assignat el valor de  numCelasNegras passat per paràmetre
    //Decripció: Setter de l'atribut numCelasNegras de taulell
    public void setNumCelasNegras(int numCelasNegras) {
        this.numCelasNegras = numCelasNegras;
    }

    //Pre: -
    //Post: Retorna el nom de l'objecte taulell
    //Decripció: Getter de l'atribut nom de Taulell
    public String getNom() {
        return nom;
    }

    //Pre: -
    //Post: L'Objecte taulell té assignat el valor de nom passat per paràmetre
    //Decripció: Setter de l'atribut nom de Taulell
    public void setNom(String nom) {
        this.nom = nom;
    }

    //Pre: -
    //Post: Retorna la dificultat de l'objecte taulell
    //Decripció: Getter de l'atribut dificultat de Taulell
    public String getDificultat() {
        return dificultat;
    }

    //Pre: -
    //Post: L'Objecte taulell té assignada la dificultat passada per paràmetre
    //Decripció: Setter de l'atribut dificultat de Taulell
    public void setDificultat(String dificultat) {
        this.dificultat = dificultat;
    }

    //Pre: -
    //Post: Retorna l'atribut es_personalitzat de l'objecte taulell
    //Decripció: Getter de l'atribut es_personalitzat de Taulell
    public boolean isEs_personalitzat() {
        return es_personalitzat;
    }

    //Pre: -
    //Post: L'Objecte taulell té assignat el valor es_personaizat passat per paràmetre
    //Decripció: Setter de l'atribut es_personalitzat de Taulell
    public void setEs_personalitzat(boolean es_personalitzat) {   //borrat pq va dir el profe que no calia. des-comenteu-ho si resulta que calia.
        this.es_personalitzat = es_personalitzat;
    }

    //Pre: -
    //Post: Retorna l'atribut filas de l'objecte taulell
    //Decripció: Getter de l'atribut filas de Taulell
	public int getFilas() {
		return filas;
	}

    //Pre: -
    //Post: Retorna l'atribut columnas de l'objecte taulell
    //Decripció: Getter de l'atribut colmnas de Taulell
	public int getColumnas() {
		return columnas;
	}

    //Pre: -
    //Post: Retorna la matriu de caselles anomenada taulell de l'objecte taulell
    //Decripció: Getter de l'atribut taulell de Taulell
    public Casella[][] getTaulell() {
        return taulell;
    }

    //Pre: -
    //Post: Retorna l'atribut numCelasDescobertes  de l'objecte taulell
    //Decripció: Getter de l'atribut numCelasDescobertes de Taulell
	public int getNumCelasDescobertes() {
		return numCelasDescobertes;
	}
    //Pre: -
    //Post: L'Objecte taulell té assignat el valor numCelasDescobertes passat per paràmetre
    //Decripció: Setter de l'atribut numCelasDescobertes de Taulell
	public void setNumCelasDescobertes(int numCelasDescobertes) {
		this.numCelasDescobertes = numCelasDescobertes;
	}

    //Pre: -
    //Post: L'Objecte taulell té assignat el valor taulellResolt passat per paràmetre
    //Decripció: Setter de l'atribut taulellResolt de Taulell
    public void setTaulellResolt(Casella[][] taulellResolt) {
        this.taulellResolt = taulellResolt;
    }

    //Pre: -
    //Post: Retorna l'atribut taulellResolt de l'objecte taulell
    //Decripció: Getter de l'atribut taulellResolt de Taulell
    public Casella[][] getTaulellResolt() {
        return taulellResolt;
    }

    //Pre: ranking no es null
    //Post: insereix al ranking el temps i l'username indicats si no hi havia un temps inferior per a aquest username
    //descripcio: actualitza el ranking del taulell inserint-hi el temps i l'username si no hi havia un temps inferior per a aquest username
    public void actualitzaRanking(String username, int sec) {
        if (!ranking.containsKey(username) || ranking.get(username)>sec) ranking.put(username, sec);
    }

    //Pre: -
    //Post: Retorna l'atribut ranking de l'objecte taulell
    //Decripció: Getter de l'atribut ranking de Taulell
    public Map<String, Integer> getRanking() {
        return ranking;
    }

    //Pre: -
    //Post: omple l'arrayList descobertes amb les coordenades de totes les caselles blanques descobertes del taulell
    //descripcio: omple l'arrayList descobertes amb les coordenades de totes les caselles blanques descobertes del taulell
    private void omplirDescobertes() {
	    for(int i=0; i<filas; ++i) {
	        for(int j=0; j<columnas; ++j) {
	            if(taulell[i][j] instanceof CasellaBlanca && ((CasellaBlanca) taulell[i][j]).getNum()!=0) descobertes.add(new Pair(i,j));
            }
        }
    }

    //Pre: -
    //Post: Retorna l'atribut descobertes de l'objecte taulell
    //Decripció: Getter de l'atribut descobertes de Taulell
    public ArrayList<Pair<Integer, Integer>> getDescobertes() {
        return descobertes;
    }

    //Pre: Ni taulell1 ni els seus elements son nulls.
    //Post: Retorna un clon de la matriu de Casella's fet amb deep copy.
    //Descripcio: Clonadora de matrius de Casella's
    private Casella[][] trueClone(Casella[][] taulell1) {
        Casella[][] clon = new Casella[taulell1.length][taulell1[0].length];

        for (int i = 0; i < taulell1.length; i++) {
            for (int j = 0; j < taulell1[0].length; j++) {
                clon[i][j] = taulell1[i][j].cloneMeu();
            }
        }
        return clon;
    }
}
