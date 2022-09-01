package Presentacio;

import javax.swing.*;
import java.awt.*;


public class PanelNegras extends JPanel {
    private int fila;
    private int columna;

    //Pre: -
    //Post: Nou panel negras amb atributs igualats als arguments passats per paràmetre
    //Descripció: Constructora de Panel Negras
    public PanelNegras(int fila, int columna, LayoutManager ly) {
        setLayout(ly);
        this.fila=fila;
        this.columna=columna;
    }
    //Pre: -
    //Post: Crea un objecte Graphics2D on encapsulem una línia de color blanc
    //Descripció: Dibuixa una linia \ de color blanc
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        // create a new graphics context based on the original one
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (fila != 0 || columna != 0) {
            g.setColor(Color.WHITE);
            g.drawLine(0, 0, getWidth(), getHeight());
            g2d.dispose();
        }
    }

    //Pre: -
    //Post: Retorna la fila
    //Descrició: Retorna la fila
    public int getFila() {
        return fila;
    }

    //Pre: -
    //Post: Retorna la columna
    //Descrició: Retorna la columna
    public int getColumna() {
        return columna;
    }
}
