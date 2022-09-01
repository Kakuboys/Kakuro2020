package Persistencia;

import Domini.Taulell;

import java.io.*;


public class CtrlTaulell {

    //Pre: -
    //Post: Crea una instància de CtrlTaulell
    //Descripció: Creadora de la classe CtrlTaulell
    public CtrlTaulell() {
    }

    //Pre: Si update=true, el Taulell passat per paràmetre existeix al fitxer
    //Post: Si update=fals, guarda un nou Taulell. Si ja existeix un Taulell amb aquest nom, afegeix un enter (n) al nom per diferenciar-lo
    //      Si update=true, actualitza el fitxer del Taulell passat per paràmetre
    //Descripció: Mètode per guardar/acttualitzar Taulells
    public void saveTaulell(Taulell t, String saveName, boolean update) throws IOException, saveFailureException {
        if (saveName.equals("")) throw new saveFailureException();
        File file = new File("./Dades/Taulells/" + t.getDificultat() + "/" + saveName + ".bin");

        if (update) file.delete();

        int i = 1;
        while (!file.createNewFile()) {
            file = new File("./Dades/Taulells/" + t.getDificultat() + "/" + saveName +"("+i+")"+ ".bin");
            i++;
        }
        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
        output.writeObject(t);
        output.close();
    }

    //Pre: El path és vàlid
    //Post: Retorna una instància de Taulell a partir del path passat per paràmetre
    //Descripció: Mètode per carregar a memòria un Taulell a partir d'un path
    public Taulell loadTaulell(String path) throws IOException, ClassNotFoundException {
        File f = new File(path);

        ObjectInputStream input= new ObjectInputStream(new FileInputStream(f));
        Taulell t = (Taulell) input.readObject();
        input.close();
        return t;
    }

    //Pre: El fitxer es un Taulell en binari
    //Post: Esborra el fitxer f del directori de Taulells
    //Descripció: Mètode per esborrar taulells guardats
    public void deleteTaulell(File f) {
        f.delete();
    }

}
