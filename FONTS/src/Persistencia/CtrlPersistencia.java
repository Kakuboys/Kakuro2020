package Persistencia;

import Domini.Partida;
import Domini.Perfil;
import Domini.Taulell;

import java.io.File;
import java.io.IOException;

public class CtrlPersistencia {
    private CtrlInfo ctrl_info;
    private CtrlPerfil ctrl_perfil;
    private CtrlPartida ctrl_partida;
    private CtrlTaulell ctrl_taulell;

    public CtrlPersistencia() throws IOException {

        ctrl_info=new CtrlInfo();
        ctrl_perfil= new CtrlPerfil();
        ctrl_partida= new CtrlPartida();
        ctrl_taulell= new CtrlTaulell();
    }

    //Pre: -
    //Post: retorna una string que conté el text informatiu dels shortcuts
    //Descripcio: proporciona el text informatiu dels shortcuts
    public String get_shortcuts() throws IOException {
        return ctrl_info.get_shortcuts();
    }

    //Pre: -
    //Post: retorna una string que conté el text informatiu de les normes
    //Descripcio: proporciona el text informatiu de les normes
    public String get_normes() throws IOException {
        return ctrl_info.get_normes();
    }

    //Pre: -
    //Post: crida al guardat/actualització del perfil en el seu Ctrl
    //Descripcio: mètode per cridar al guardat/actualització d'un Perfil
    public void savePerfil(Perfil p, Boolean update) throws IOException, ClassNotFoundException, saveFailureException, GrammaticException {
        ctrl_perfil.savePerfil(p, update);
    }

    //Pre: -
    //Post: crida al log-in del perfil en el seu Ctrl i retorna un Perfil, si no s'activa cap excepció
    //Descripcio: mètode per retornar un perfil a partir d'un nom i una contraseña
    public Perfil logIn(String username, String password) throws logInFailureException, IOException, ClassNotFoundException {
        return ctrl_perfil.logIn(username, password);
    }

    //Pre: -
    //Post: crida al guardat d'una Partida en el seu Ctrl
    //Descripcio: mètode per cridar al guardat d'una Partida
    public void savePartida(Partida p, String saveName) throws IOException, saveFailureException {
        ctrl_partida.savePartida(p, saveName);
    }

    //Pre: -
    //Post: crida al guardat/actualització d'un Taulell en el seu Ctrl
    //Descripcio: mètode per cridar al guardat/actualització d'un Taulell
    public void saveTaulell(Taulell t, String saveName, boolean update) throws IOException, saveFailureException {
        ctrl_taulell.saveTaulell(t, saveName, update);
    }

    //Pre: -
    //Post: crida al load d'un Taulell en el seu Ctrl
    //Descripcio: mètode per carregar un Taulell
    public Taulell loadTaulell(String path) throws IOException, ClassNotFoundException {
        return ctrl_taulell.loadTaulell(path);
    }

    //Pre: -
    //Post: crida a l'esborrat d'un Taulell en el seu Ctrl
    //Descripcio: mètode per cridar a l'esborrat d'un Taulell
    public void deleteTaulell(File f) {
       ctrl_taulell.deleteTaulell(f);
    }

    //Pre: -
    //Post: crida a l'esborrat d'una Partida en el seu Ctrl
    //Descripcio: mètode per cridar a l'esborrat d'una Partida
    public void deletePartida(File f) {
        ctrl_partida.deletePartida(f);
    }

    //Pre: El fitxer es una Partida en binari
    //Post: Retorna una instància de Partida a partir del fitxer f
    //Descripció: Mètode per carregar a memòria una Partida a partir d'un fitxer
    public Partida loadPartida(File fitxer) throws IOException, ClassNotFoundException {
        return ctrl_partida.loadPartida(fitxer);
    }

    //Pre: ctrl_info no es null
    //Post: retorna un String amb la informacio dels modes de joc
    //Descripcion: retorna la informacio dels modes de joc
    public String getInfoModesJoc() throws IOException {
        return ctrl_info.get_info_modes_joc();
    }
}
