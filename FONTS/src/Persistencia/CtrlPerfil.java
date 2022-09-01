package Persistencia;

import Domini.Perfil;
import Domini.Pair;

import java.io.*;

import java.util.ArrayList;

public class CtrlPerfil {
    private File file;

    //Pre: -
    //Post: Ha creat un fitxer ./Dades/Perfils/Perfil.bin
    //Descripció: Creadora de CtrlPerfil
    public CtrlPerfil() throws IOException {
        file = new File("./Dades/Perfils/Perfil.bin");
        file.createNewFile();
    }

    //Pre: Si update=true, el Perfil passat per paràmetre existeix al fitxer
    //Post: Si update=fals, guarda un nou Perfil. Si ja existeix un Perfil amb aquest nom, o el nom és buit, retorna excepció
    //      Si update=true, actualitza el perfil passat per paràmetre al fitxer
    //Descripció: Mètode per guardar/acttualitzar perfils en el fitxer "perfils.bin".
    public void savePerfil(Perfil p, boolean update) throws saveFailureException, IOException, ClassNotFoundException, GrammaticException {
        if (p.getUsername().equals("") || p.getPassword().equals("")) throw new GrammaticException();
        try { //llegeix tot el fitxer i el borra, afegeix al arraylist el nou perfil i el guarda al nou fitxer
            ArrayList<Perfil> perfils = loadAllPerfils();
            Pair<Boolean, Integer> aux=existeixPerfil(perfils, p.getUsername());
            if (!aux.first || update) {
                int i=aux.second;
                if (update) perfils.remove(i);
                perfils.add(p);
                clearFile();
                ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file, true));
                output.writeObject(perfils);
                output.close();
                crearDirectoriPartidesGuardades(p.getUsername()); //si ja existeix, no fa res
            }
            else throw new saveFailureException();
        }
        catch(EOFException e) { // inicialitzem file
            ArrayList<Perfil> perfils = new ArrayList<Perfil>();
            perfils.add(p);
            ObjectOutputStream output= new ObjectOutputStream(new FileOutputStream(file, true));
            output.writeObject(perfils);
            output.close();
            crearDirectoriPartidesGuardades(p.getUsername());
        }
    }

    //Pre: -
    //Post: Retorna cert si hi ha algun perfil que tingui de nom=username, i l'index de l'arraylist al que fa referència, fals en cas contrari.
    //Descripció: Comprova si hi ha algún perfil que ja tingui el nom username.
    private Pair<Boolean, Integer> existeixPerfil(ArrayList<Perfil> perfils, String username) {
        for (int i=0; i<perfils.size(); i++) {
            if (perfils.get(i).getUsername().equals(username)) return new Pair(true, i);
        }
        return new Pair(false, -1);
    }

    //Pre: -
    //Post: Retorna tots els perfils del fitxer, si en té.
    //Descripció: Obté el contingut del fitxer de perfils.
    private ArrayList<Perfil> loadAllPerfils() throws IOException, ClassNotFoundException {
        ObjectInputStream input= new ObjectInputStream(new FileInputStream(file));
        ArrayList<Perfil> perfils = (ArrayList<Perfil>) input.readObject();
        input.close();
        return perfils;
    }

    //Pre: -
    //Post: Elimina el contingut de file
    //Descripció: Mètode per eliminar contingut de file (perfils.bin)
    private void clearFile() throws IOException {
        file.delete();
        file.createNewFile();
    }

    //Pre: -
    //Post: Retorna el perfil amb nom=username, contrasenya=password. Si l'usuari no existeix o la contrasenya no és vàlida, retorna excepció.
    //Descripció: Mètode per retornar un perfil al fer log-in.
    public Perfil logIn(String username, String password) throws logInFailureException, IOException, ClassNotFoundException {
        ArrayList<Perfil> perfils= new ArrayList<Perfil>();
        try {
            perfils = loadAllPerfils();
        }
        catch (EOFException e) { //ignorem excepcio si perfils.bin esta buit
        }

        for (int i=0; i<perfils.size(); i++) {
            if (perfils.get(i).getUsername().equals(username)) {
                if (perfils.get(i).getPassword().equals(password)) return perfils.get(i);
                else throw new logInFailureException();
            }
        }
        throw new logInFailureException();
    }

    //Pre: S'ha creat un nou Perfil
    //Post: Crea un directori a Dades/Partides amb el nom del perfil passat per paràmetre
    //Descripció: Mètode per crear un directori de partides d'un Perfil
    private void crearDirectoriPartidesGuardades(String username) {
       new File("./Dades/Partides/" + username).mkdirs();
    }
}
