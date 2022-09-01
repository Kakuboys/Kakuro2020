package CapaOrtogonal;

import CapaOrtogonal.Casella;

import java.io.Serializable;

public class CasellaBlanca extends Casella {
    private int num;

    //Pre: -
    //Post: crea una CasellaBlanca, amb 0 com a numero (que representa que és buida)
    //Descripcio: crea una CasellaBlanca, amb 0 com a numero (que representa que és buida)
    public CasellaBlanca() {
        this.num = 0;
    }
    //Pre: -
    //Post: crea una CasellaBlanca, amb num com a numero
    //Descripcio: crea una CasellaBlanca, amb num com a numero
    public CasellaBlanca(int num) {
        this.num = num;
    }

    //Pre: -
    //Post: dona a num el valor indicat
    //Descripcio: setter de num
    public void setNum(int num) {
        this.num = num;
        //teNum = true;
    }

    //Pre: -
    //Post: Retorna el numero de la Casella Blanca. 0 representa que és buida.
    //Descripció: getter de num
    public int getNum() {
        return num;
    }

    //Pre: -
    //Post: retorna cert si i nomes si la casella es buida (no té numero)
    //Descripcio: diu si la casella es blanca i buida.
    public boolean esBlancaBuida() {
        return (num==0);
    }
}