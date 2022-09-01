package Persistencia;

import Domini.Partida;

import java.io.*;

public class CtrlPartida {

    public CtrlPartida() {
    }

    //Pre: -
    //Post: Guarda la partida p al directori de les partides del Perfil amb nom saveName (afegint un enter (n) al nom si es repetit)
    //Descripció: Mètode per guardar partides en els directoris dels perfils
    public void savePartida(Partida p, String saveName) throws IOException, saveFailureException {
        if (saveName.equals("")) throw new saveFailureException(); //nom buit
        File file = new File("./Dades/Partides/" + p.getJugador().getUsername() + "/" + saveName + ".bin");
        int i = 1;
        while (!file.createNewFile()) {
            file = new File("./Dades/Partides/" + p.getJugador().getUsername() + "/" + saveName +"("+i+")"+ ".bin");
            i++;
        }
        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
        output.writeObject(p);
        output.close();
    }

    //Pre: El fitxer es una Partida en binari
    //Post: Retorna una instància de Partida a partir del fitxer f
    //Descripció: Mètode per carregar a memòria una Partida a partir d'un fitxer
    public Partida loadPartida(File f) throws IOException, ClassNotFoundException {
        ObjectInputStream input= new ObjectInputStream(new FileInputStream(f));
        return (Partida) input.readObject();
    }


    //Pre: El fitxer es una Partida en binari
    //Post: Esborra el fitxer f del directori de Partides (al Perfil corresponent)
    //Descripció: Mètode per esborrar partides guardades
    public void deletePartida(File f) {
        f.delete();
    }
}
