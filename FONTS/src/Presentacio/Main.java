package Presentacio;

import java.io.IOException;

public class Main {

    //Pre: -
    //descripcio: funcio main del programa
    public static void main (String[] args) {
        javax.swing.SwingUtilities.invokeLater (
                new Runnable() {
                    public void run() {
                        //new PantallaPrincipal().hacerVisible();     //no te sentit, crea 2 finestres. ells ho feien amb la vistaLEEME.
                        CtrlPresentacio ctrlPresentacion = null;
                        try {
                            ctrlPresentacion = new CtrlPresentacio();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ctrlPresentacion.inicializarPresentacion();
                    }});
    }
}
