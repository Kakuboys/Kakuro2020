package Persistencia;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CtrlInfo {

    //Pre: -
    //Post: retorna una string que conté el text informatiu dels shortcuts
    //Descripcio: proporciona el text informatiu dels shortcuts
    public String get_shortcuts() throws IOException {
        String path = "./Dades/info/" + "shortcuts.txt" ;
        return readFile(path);
    }

    //Pre: -
    //Post: retorna una string que conté el text informatiu de les normes
    //Descripcio: proporciona el text informatiu de les normes
    public String get_normes() throws IOException {
        String path = "./Dades/info/" + "normes.txt" ;
        return readFile(path);
    }

    //Pre: fileName ha de ser la ruta d'un fitxer existent
    //Post: Es retorna un string amb tot el text que contenia el fitxer fileName
    //Descripció: Lector del fixter fileName
    private String readFile(String fileName) throws IOException {
        BufferedReader b = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder s = new StringBuilder();
            String line = b.readLine();
            while (line != null) {
                s.append(line);
                s.append("\n");
                line = b.readLine();
            }
            return s.toString();
        } finally {
            b.close();
        }
    }

    //Pre: -
    //Post: retorna un String amb la informacio dels modes de joc
    //Descripcion: retorna la informacio dels modes de joc
    public String get_info_modes_joc() throws IOException {
        String path = "./Dades/info/modes_de_joc.txt";
        return readFile(path);
    }
}
