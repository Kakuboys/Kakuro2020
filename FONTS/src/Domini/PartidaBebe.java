package Domini;
import java.io.Serializable;
import java.util.Random;

public class PartidaBebe extends Partida {

    private int n_comodins;

    //Pre: Tots els paràmetres estan correctament inicialitzats
    //Post: Es crea una nova instància de PartidaBebe amb els atributs corresponents
    //Descripció: Creadora de la classe PartidaBebe
    public PartidaBebe(Perfil jugador, Taulell taulellPartida, int cronometre, int n_com) {
        super(jugador, taulellPartida,cronometre, 1);
        this.n_comodins=n_com;
    }

    //Pre: -
    //Post: retorna el nombre de comodins (=0, no hi ha comodins)
    //descripcio: getter de getN_comodins, en int
    public int getN_comodins() {
        return n_comodins;
    }

}