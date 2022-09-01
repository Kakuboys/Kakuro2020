package CapaOrtogonal;

import java.io.Serializable;

public abstract class Casella implements Cloneable, Serializable {

    //Pre: -
    //Post: retorna cert si i nomes si la casella es blanca i buida (no té numero)
    //Descripcio: diu si la casella es blanca i buida.
    public boolean esBlancaBuida() {
        return false;
    }

    //Pre: -
    //Post: Retorna un clon de la Casella fet amb deep copy, excepte si salta la excepció CloneNotSupportedException. En aquest cas retorna aquesta casella.
    //Descripció: Clonador de Casella
    public Casella cloneMeu() {
        try {
            return (Casella)this.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("ha saltat CloneNotSupportedException a Casella::cloneMeu");
            e.printStackTrace();
        }
        return this;
    }
}