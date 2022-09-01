package Domini;

import java.io.Serializable;

public class Partida implements Serializable {
    private Perfil jugador;
    protected Taulell taulellPartida;
    private double cronometre;
    private String nom;
    private int modeDeJoc;
    private static final long serialVersionUID = 6529685098267759690L;

    //Pre: Tots els paràmetres estan correctament inicialitzats
    //Post: Es crea una nova instància de Partida amb els atributs corresponents
    //Descripció: Creadora de la classe Partida
    public Partida(Perfil jugador, Taulell taulellPartida,int cronometre, int modeDeJoc) {
        this.jugador=jugador;
        this.taulellPartida=taulellPartida;
        this.cronometre=cronometre;
        this.modeDeJoc=modeDeJoc;
    }

    public Partida() {

    }

    //Pre: taulellPartida no es null
    //Post: retorna taulellPartida
    //descripcio: getter de taulellPartida
    public Taulell getTaulellPartida() {
		return taulellPartida;
	}

    //Pre: -
    //Post: posa el String passat per parametre a nom
    //descripcio: setter de nom
    public void setNom(String nom) {
        this.nom = nom;
    }

    //Pre: jugador no es null
    //Post: retorna jugador
    //descripcio: getter de jugador
    public Perfil getJugador() {
        return jugador;
    }

    //Pre: nom no es null
    //Post: retorna nom
    //descripcio: getter de nom
    public String getNom() {
        return nom;
    }

    //Pre: -
    //Post: retorna cronometre en int
    //descripcio: getter de cronometre, en int
    public int getCronometre() {
        int enter = (int) cronometre;
        return enter;
    }

    //Pre: -
    //Post: retorna el mode de joc en int (0=classica, 1=bebe, 2=contrarellotge)
    //descripcio: getter de cronometre, en int
    public int getModeDeJoc() {
        return modeDeJoc;
    }

    //Pre: -
    //Post: retorna el nombre de comodins (=0, no hi ha comodins)
    //descripcio: getter de getN_comodins, en int
    public int getN_comodins() {
        return 0;
    }

}