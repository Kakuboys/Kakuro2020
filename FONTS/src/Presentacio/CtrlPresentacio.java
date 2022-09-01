package Presentacio;

import CapaOrtogonal.Casella;
import Domini.CtrlDomini;
import Domini.Pair;
import Persistencia.GrammaticException;
import Persistencia.logInFailureException;
import Persistencia.saveFailureException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class CtrlPresentacio {
    private CtrlDomini ctrl_domini;
    private PantallaPrincipal pantalla_principal = null;
    private VistaJugar vista_jugar = null;
    private VistaSignUp vista_signup = null;
    private VistaLogIn vista_logIn = null;
    private VistaPartidaNova vista_partida_nova = null;
    private VistaTaulellsPersonalitzats vista_TP = null;
    private VistaGenerarKakuroAuto vista_GKA = null;
    private VistaTaulellKakuro vista_taulell = null;
    private VistaRecords vista_records = null;
    private VistaRankings vista_rankings = null;
    private VistaGuardarPartida vista_guardar=null;
    private VistaVictoria vista_victoria=null;
    private VistaProposarKakuro vista_proposa=null;

    //Pre: -
    //Post: construeix un CtrlPresentacio. crea el CtrlDomini i s'hi associa. Crea la pantalla_principal i s'hi associa
    //Descripcio: Constructora de CtrlPresentacio
    public CtrlPresentacio() throws IOException {
        ctrl_domini = new CtrlDomini();
        if (pantalla_principal == null)  // innecesari
            pantalla_principal = new PantallaPrincipal(this);
    }

    //Pre: pantalla_principal no es null
    //Post: fa visible pantalla_principal
    //Descripcio: fa visible la primera vista
    public void inicializarPresentacion() {
        pantalla_principal.hacerVisible();
    }

    //Pre: pantalla_principal i vista_jugar son not null
    //Post: desactiva la VistaJugar i activa la PantallaPrincipal.
    //Descripcio: realitza el canvi de la vista VistaJugar a PantallaPrincipal
    public void sincronizacionVistaJugar_a_VistaPrincipal() {
        vista_jugar.desactivar();
        pantalla_principal.activar();
    }

    //Pre: pantalla_principal no es null
    //Post: desactiva la PantallaPrincipal, crea la instancia de VistaJugar si no existia, li passa el frame i la activa.
    //Descripcio: realitza el canvi de la vista PantallaPrincipal a VistaJugar
    public void sincronizacionVistaPrincipal_a_VistaJugar() {
        pantalla_principal.desactivar();

        if (vista_jugar == null) {
            JFrame frame = pantalla_principal.getFrame();
            vista_jugar = new VistaJugar(this, frame);
        }
        else
            vista_jugar.activar();  //coses que es fan dins de la constructora, que cal fer sempre que canviem de vista, no nomes el 1r cop.
    }

    //Pre: pantalla_principal no es null
    //Post: desactiva la PantallaPrincipal, crea la instancia de VistaSignUp si no existia, li passa el frame i la activa.
    //Descripcio: realitza el canvi de la vista PantallaPrincipal a VistaSignUp
    public void sincronizacionVistaPrincipal_a_VistaSignUp() {
        pantalla_principal.desactivar();

        if (vista_signup == null) {
            JFrame frame = pantalla_principal.getFrame();
            vista_signup = new VistaSignUp(this, frame);
        }
        else
            vista_signup.activar();  //coses que es fan dins de la constructora, que cal fer sempre que canviem de vista, no nomes el 1r cop.
    }

    //Pre: pantalla_principal i vista_signup son not null
    //Post: desactiva la VistaSignUp i activa la PantallaPrincipal. Si s'ha iniciat una sessio, canvia el contingut del frame adequadament
    //Descripcio: realitza el canvi de la vista VistaSignUp a PantallaPrincipal
    public void sincronizacionVistaSignUp_a_VistaPrincipal(boolean venimDeOk){
        vista_signup.desactivar();
        if(venimDeOk)   //CtrlDomini ja haura posat logged_in a true al rebre el SignUp (savePerfil)
            pantalla_principal.changebuttontologout();
        pantalla_principal.activar();
    }

    //Pre: -
    //Post: retorna una string que conté el text informatiu dels shortcuts
    //Descripcio: proporciona el text informatiu dels shortcuts
    public String get_shortcuts() throws IOException {
        return ctrl_domini.get_shortcuts();
    }

    //Pre: -
    //Post: retorna una string que conté el text informatiu de les normes
    //Descripcio: proporciona el text informatiu de les normes
    public String get_normes() throws IOException {
        return ctrl_domini.get_normes();
    }

    //Pre: pantalla_principal no es null
    //Post: desactiva la PantallaPrincipal, crea la instancia de VistaLogIn si no existia, li passa el frame i la activa.
    //Descripcio: realitza el canvi de la vista PantallaPrincipal a VistaLogIn
    public void sincronizacionVistaPrincipal_a_VistaLogIn() {
        pantalla_principal.desactivar();

        if (vista_logIn == null) {
            JFrame frame = pantalla_principal.getFrame();
            vista_logIn = new VistaLogIn(this, frame);
        }
        else
            vista_logIn.activar();  //coses que es fan dins de la constructora, que cal fer sempre que canviem de vista, no nomes el 1r cop.

    }

    //Pre: pantalla_principal i vista_login son not null
    //Post: desactiva la VistaLogIn i activa la PantallaPrincipal. Si s'ha iniciat una sessio, canvia el contingut del frame adequadament
    //Descripcio: realitza el canvi de la vista VistaLogIn a PantallaPrincipal
    public void sincronizacionVistaLogIn_a_VistaPrincipal(boolean venimDeOk) {//venimDeOk vol dir que s'ha loggejat enlloc de cancelar.
        vista_logIn.desactivar();
        if(venimDeOk)   //CtrlDomini ja haura posat logged_in a true al rebre el SignUp (savePerfil)
            pantalla_principal.changebuttontologout();

        pantalla_principal.activar();
    }

    //Pre: String usuari i la contrasenya han de correspondre a un Perfil. ctrl_domini no ha de ser null.
    //Post: Perfil carregat a domini, i l'usuari està logejat.
    //Descripcó: Operació d'inici de sessió de l'usuari
    public String logIn(String usuari, String contrasenya) throws logInFailureException, IOException, ClassNotFoundException {
        return ctrl_domini.logIn(usuari,contrasenya);
    }

    //Pre: ctrl_domini no es null i text no es el nom de cap perfil guardat
    //Post: Crea el perfil i el guarda en disc. També el perfil està carregat a domini, i l'usuari està logejat.
    //Descripció: Guarda perfil a disc i logeja l'usuari
    public void signUp(String text, String text1) throws saveFailureException, IOException, ClassNotFoundException, GrammaticException {
        ctrl_domini.savePerfil(text,text1);
    }

    //Pre: vista_jugar es not null
    //Post: desactiva la VistaJugar i crea i inicialitza la VistaPartidaNova.
    //Descripcio: realitza el canvi de la vista VistaJugar a VistaPartidaNova
    public void sincronizacionVistaJugar_a_VistaPartidaNova() {
        vista_jugar.desactivar();

        JFrame frame = vista_jugar.getFrame();

        vista_partida_nova = new VistaPartidaNova(this, frame);
    }

    //Pre: vista_partida_nova i vista_jugar son not null
    //Post: desactiva la VistaPartidaNova i activa la VistaJugar.
    //Descripcio: realitza el canvi de la vista VistaPartidaNova a VistaJugar
    public void sincronizacionVistaPartidaNova_a_VistaJugar() {
        vista_partida_nova.desactivar();
        vista_jugar.activar();
    }

    //Pre: pantalla_principal no es null
    //Post: desactiva la PantallaPrincipal, crea la instancia de VistaTaulellsPersonalitzats si no existia, li passa el frame i la activa.
    //Descripcio: realitza el canvi de la vista PantallaPrincipal a VistaTaulellsPersonalitzats
    public void sincronizacionVistaPrincipal_a_TP() {   //TP == taulells personalitzats
        pantalla_principal.desactivar();
        if (vista_TP == null) {
            JFrame frame = pantalla_principal.getFrame();
            vista_TP = new VistaTaulellsPersonalitzats(this, frame);
        }
        else
            vista_TP.activar();
    }

    //Pre: vista_TP i pantalla_principal son not null
    //Post: desactiva la VistaTaulellsPersonalitzats i activa la PantallaPrincipal.
    //Descripcio: realitza el canvi de la vista VistaTaulellsPersonalitzats a PantallaPrincipal
    public void sincronizacionVistaTP_a_VistaPrincipal() {
        vista_TP.desactivar();
        pantalla_principal.activar();
    }

    //Pre: vista_TP no es null
    //Post: desactiva la VistaTaulellsPersonalitzats, crea la instancia de VistaGenerarKakuroAuto si no existia, li passa el frame i la activa.
    //Descripcio: realitza el canvi de la vista VistaTaulellsPersonalitzats a VistaGenerarKakuroAuto
    public void sincronizacionVistaTP_a_VistaGKA() {
        vista_TP.desactivar();

        if (vista_GKA == null) {
            JFrame frame = vista_TP.getFrame();
            vista_GKA = new VistaGenerarKakuroAuto(this, frame);
        }
        else
            vista_GKA.activar();
    }

    //Pre: vista_TP i vista_GKA son not null
    //Post: desactiva la VistaGenerarKakuroAuto i la elimina. Activa la VistaTaulellsPersonalitzats.
    //Descripcio: realitza el canvi de la vista VistaGenerarKakuroAuto a VistaTaulellsPersonalitzats
    public void sincronizacionVistaGenerarKakuro_a_VistaTP() {
        vista_GKA.desactivar();
        vista_GKA = null;
        vista_TP.activar();
    }

    //Pre: vista_partida_nova no es null
    //Post: desactiva la VistaPartidaNova, crea la instancia de VistaTaulellKakuro si no existia, li passa el frame i l'activació dels comodins i la activa.
    //Descripcio: realitza el canvi de la vista VistaPartidaNova a VistaTaulellKakuro
    public void sincronizacionVistaPartidaNova_a_VistaTaulell(int ModeDeJoc) {
        vista_partida_nova.desactivar();

        if (vista_taulell == null) {
            JFrame frame = vista_partida_nova.getFrame();
            vista_taulell = new VistaTaulellKakuro(frame, this, ModeDeJoc, 3);
        }
        else
            vista_taulell.activar();
    }

    //Pre: vista_taulell i pantalla_principal son not null
    //Post: desactiva la VistaTaulellKakuro i la elimina. Activa la PantallaPrincipal.
    //Descripcio: realitza el canvi de la vista VistaTaulellKakuro a PantallaPrincipal
    public void sincronizacionVistaTaulell_a_PantallaPrincipal() {
        vista_taulell.desactivar();
        vista_taulell=null;
        pantalla_principal.activar();
    }

    //Pre: ctrl_domini no es null
    //Post: Genera el kakuro amb els paràmetres indicats, el guarda a persistència l'escriu per consola i retorna el taulell amb el nombre de cel·les descobertes indicat  i el taulell resolt
    //Descripció: Genera un kakuro randomitzat amb els paràmetres indicats.
    public Pair<Casella[][],Casella[][]> generarKakuroAuto(int files, int columnes, int CDescobertes, String nom) throws IOException, saveFailureException {
        return ctrl_domini.generarKakuro(files, columnes, CDescobertes, nom);
    }

    //Pre: ctrl_domini no es null
    //Post: retorna la matriu de Casella's del taulell actual de Domini
    //Descripcio: proporciona el taulell actual de Domini
    public Casella[][] getTaulellDeMemoria() {
        return  ctrl_domini.getTaulellDeMemoria();
    }

    //Pre: ctrl_domini no es null
    //Post: posa el taulell que es troba al path indicat al taulell actual de Domini
    //Descripcio: Carrega el taulell indicat a memoria
    public void loadTaulellAMemoria(String path) throws IOException, ClassNotFoundException {
        ctrl_domini.loadTaulellAMemoria(path);
    }

    //Pre: -
    //Post: retorna CtrlDomini::is_logged_in, que indica si hi ha una sessio iniciada
    //Descripcio: getter de CtrlDomini::is_logged_in
    public boolean getIsLoggedIn() {
        return ctrl_domini.getIs_logged_in();
    }

    //Pre: ctrl_domini no es null
    //Post: retorna el username del perfil actual de domini
    //Descripcio: retorna el username del perfil actual de domini
    public String getUsername() {
        return ctrl_domini.getUsername();
    }

    //Pre: -
    //Post: posa el valor indicat a CtrlDomini::is_logged_in
    //Descripcio: setter de CtrlDomini::is_logged_in
    public void setIsLoggedIn(boolean b) {
        ctrl_domini.setIsLoggedIn(b);
    }

    //Pre: ctrl_domini no es null
    //Post: retorna cert si i nomes si taulell te els mateixos valors que el taulell resolt
    //Descripció: Valida la solucio introduida del taulell
    public boolean validarTaulell(Casella[][] taulell) {
        return ctrl_domini.validarTaulell(taulell);
    }

    //Pre: ctrl_domini no es null
    //Post: retorna una matriu de Casella's que es com la passada per parametre pero
        // mostra la resposta d'una casella encara no mostrada ni resposta.
    //descripcio: mostra la resposta d'una casella del taulell
    public void demanarComodi(Casella[][] taulell){
        ctrl_domini.demanarComodi(taulell);
    }

    //Pre: ctrl_domini no es null
    //Post: crida a l'esborrat d'un Taulell a la capa de Domini
    //Descripcio: mètode per cridar a l'esborrat d'un Taulell a la capa de Domini
    public void deleteTaulell(File f) {
        ctrl_domini.deleteTaulell(f);
    }

    //Pre: ctrl_domini no es null
    //Post: crida a l'esborrat d'una Partida a la capa de Domini
    //Descripcio: metode per cridar a l'esborrat d'una Partida a la capa de Domini
    public void deletePartida(File f) {
        ctrl_domini.deletePartida(f);
    }

    //Pre: pantalla_principal no es null
    //Post: desactiva la PantallaPrincipal, crea la instancia de VistaRecords si no existia, li passa el frame i la activa.
    //Descripcio: realitza el canvi de la vista PantallaPrincipal a VistaRecords
    public void sincronizacionVistaPrincipal_a_records() {
        pantalla_principal.desactivar();

        if (vista_records == null) {
            JFrame frame = pantalla_principal.getFrame();
            vista_records = new VistaRecords(this, frame);
        }
        else
            vista_records.activar();
    }

    //Pre: vista_records i pantalla_principal son not null
    //Post: desactiva la VistaRecords i activa la PantallaPrincipal.
    //Descripcio: realitza el canvi de la vista VistaRecords a PantallaPrincipal
    public void sincronizacionVistaRecords_a_VistaPrincipal() {
        vista_records.desactivar();
        vista_records = null;
        pantalla_principal.activar();
    }

    //Pre: ctrl_domini no es null
    //Post: Si l'usuari esta autenticat:
    // Actualitza el temps de perfil_domini per taulell_domini si i nomes si es inferior al temps guardat o
    // si no existia cap temps per el taulell.
    // I actualitza el perfil a persistencia
    //Descripció: Actualitza el record de perfil_domini per taulell_domini, si cal
    public void actualitzarRecords(int i) throws ClassNotFoundException, saveFailureException, GrammaticException, IOException {
        ctrl_domini.actualitzarRecords(i);
    }

    //Pre: pantalla_principal no es null
    //Post: desactiva la PantallaPrincipal, crea la instancia de VistaRankings si no existia, li passa el frame i la activa.
    //Descripcio: realitza el canvi de la vista PantallaPrincipal a VistaRankings
    public void sincronizacionVistaPrincipal_a_rankings() {
        pantalla_principal.desactivar();

        if(vista_rankings == null) {
            JFrame frame = pantalla_principal.getFrame();
            vista_rankings = new VistaRankings(this, frame);
        }
        else
            vista_rankings.activar();
    }

    //Pre: vista_rankings i pantalla_principal son not null
    //Post: desactiva la VistaRankings i la elimina. Activa la PantallaPrincipal.
    //Descripcio: realitza el canvi de la vista VistaRankings a PantallaPrincipal
    public void sincronizacionVistaRankings_a_VistaPrincipal() {
        vista_rankings.desactivar();
        vista_rankings = null;
        pantalla_principal.activar();
    }

    //Pre: ctrl_domini no es null
    //Post: si hi ha un usuari identificat, retorna el record de l'usuari actual en el taulell identificat per name.
        //sino, retorna -1.
        //el record serà -2 si no existia un record per a aquests usuari i taulell.
    //descripcio: retorna el record de l'usuari actual en el taulell identificat per name
    public Integer getRecord(String name) {
        return ctrl_domini.getRecord(name);
    }

    //Pre: vista_taulell no es null
    //Post: desactiva la VistaTaulellKakuro, crea la instancia de VistaGuardar si no existia, li passa el frame, temps i l'activacio dels comodins i la activa.
    //Descripcio: realitza el canvi de la vista VistaTaulellKakuro a VistaGuardar
    public void sincronizacionVistaTaulell_a_VistaGuardar(int cronometre, int modeDeJoc, int num_comodins) {
        vista_taulell.desactivar();

        if(vista_guardar == null) {
            JFrame frame = vista_taulell.getFrame();
            vista_guardar = new VistaGuardarPartida(this, frame,cronometre, modeDeJoc, num_comodins);
        }
        else
            vista_guardar.activar();
    }

    //Pre: vista_guardar i vista_taulell son not null
    //Post: desactiva la VistaGuardar i la elimina. Activa la VistaTaulellKakuro.
    //Descripcio: realitza el canvi de la vista VistaGuardar a VistaTaulellKakuro
    public void sincronizacionVistaGuardar_a_VistaTaulell() {
        vista_guardar.desactivar();
        vista_guardar = null;
        vista_taulell.activar();
    }

    //Pre: ctrl_domini no es null
    //Post: Si l'usuari està logejat, crea una partida amb les dades passades per parametre i crida al seu guardat
    //Descripcó: Metode per crear una partida i guardar-la
    public void savePartida(int cronometre, String nomPartida, int modeDeJoc, int numComodins) throws IOException, saveFailureException {
        ctrl_domini.savePartida(cronometre,nomPartida, modeDeJoc, numComodins);
    }

    //Pre: vista_guardar i pantalla_principal son not null
    //Post: desactiva la VistaGuardar i la elimina. Activa la PantallaPrincipal. elimina la vista_taulell.
    //Descripcio: realitza el canvi de la vista VistaGuardar a PantallaPrincipal
    public void sincronizacionVistaGuardar_a_VistaPrincipal() {
        vista_guardar.desactivar();
        vista_guardar = null;
        vista_taulell=null;
        pantalla_principal.activar();
    }

    //Pre: CtrlDomini no es null
    //Post: Crida a la funcio de ctrlDomini de acualitzar el ranking
    //Descripcio: mètode per actualitzar el ranking d'un taulell
    public void actualitzarRankings(int sec) throws saveFailureException, IOException, ClassNotFoundException {
        ctrl_domini.actualitzarRanking(sec);
    }

    //Pre: perfil_domini no es null
    //Post: retorna el
    //el record serà -2 si no existia un record per a aquests usuari i taulell.
    //descripcio: retorna el record de l'usuari actual en el taulell identificat per name
    public String getRanking(String path) throws IOException, ClassNotFoundException {
        return ctrl_domini.getRanking(path);
    }

    //Pre: ctrl_domini no es null
    //Post: Retorna l'atribut descobertes del taulell_domini
    //Decripció: retorna l'atribut descobertes del taulell_domini
    public ArrayList<Pair<Integer, Integer>> getDescobertes() {
        return ctrl_domini.getDescobertes();
    }

    //Pre: vista_jugar, ctrl_domini no son nulls
    //Post: carrega la partida indicada pel fitxer i el seu taulell a partida_domini i taulell_domini respectivament.
    // desactiva la VistaJugar,crea la instancia de VistaTaulellKakuro passant-li el frame i el cronometre de la partida si no existia, i la activa si existia.
    //Descripcio: carrega la partida i realitza el canvi de la vista VistaJugar a VistaTaulellKakuro
    public void sincronizacionVistaJugar_a_VistaTK(File fitxer) throws IOException, ClassNotFoundException {
        Pair<Integer, Pair<Integer, Integer>> dadesPartida=ctrl_domini.loadPartidaAMemoria(fitxer);
        vista_jugar.desactivar();

        if(vista_taulell == null) {
            JFrame frame = vista_jugar.getFrame();
            vista_taulell = new VistaTaulellKakuro(frame,this,dadesPartida.first, dadesPartida.second.first, dadesPartida.second.second);
        }
        else
            vista_taulell.activar();
    }

    //Pre: vista_taulell no es null
    //Post: desactiva la VistaTaulellKakuro, crea la instancia de VistaVictoria passant-li el frame, el temps de partida i l'activacio dels comodins si no existia, i la activa en cas contrari.
    //Descripcio: realitza el canvi de la vista VistaTaulellKakuro a VistaVictoria
    public void sincronizacionVistaTK_a_VistaVictoria(int segons, int modeDeJoc) throws IOException {
        vista_taulell.desactivar();
        if (vista_victoria == null) {
            JFrame frame = vista_taulell.getFrame();
            vista_victoria = new VistaVictoria(frame, this, segons, (modeDeJoc!=0));
        }
        else vista_victoria.activar();
    }

    //Pre: vista_victoria i pantalla_principal son not null
    //Post: desactiva la VistaVictoria i la elimina. Activa la PantallaPrincipal. elimina la vista_taulell.
    //Descripcio: realitza el canvi de la vista VistaVictoria a PantallaPrincipal
    public void sincronizacionVistaVictoria_a_pantallaPrincipal() {
        vista_victoria.desactivar();
        vista_victoria=null;
        vista_taulell=null;
        pantalla_principal.activar();
    }

    //Pre: vista_TP no es null
    //Post: desactiva la VistaTaulellsPersonalitzats, crea la instancia de VistaProposarKakuro passant-li el frame si no existia, i la activa si existia.
    //Descripcio: realitza el canvi de la vista VistaTaulellsPersonalitzats a VistaProposarKakuro
    public void sincronizacionVistaTP_a_VistaProposarKakuro() {
        vista_TP.desactivar();
        if (vista_proposa == null) {
            JFrame frame = vista_TP.getFrame();
            vista_proposa = new VistaProposarKakuro(this, frame);
        }
        else vista_proposa.activar();
    }

    //Pre: vista_proposa i vista_TP son not null
    //Post: desactiva la VistaProposarKakuro i la elimina. Activa la VistaTaulellsPersonalitzats.
    //Descripcio: realitza el canvi de la vista VistaProposarKakuro a VistaTaulellsPersonalitzats
    public void sincronizacionVistaProposarKakuro_a_VistaTP() {
        vista_proposa.desactivar();
        vista_proposa=null;
        vista_TP.activar();
    }

    //Pre: ctrl_domini no es null.
    //Post: si existeix un fitxer amb el nom nomTaulell:
        // llegeix el taulell que es troba al fitxer nomTaulell del directori resources en format de strings,
        // i si te solucio unica el guarda a persistencia i retorna 2. Altrament retorna 1
        // si no existia tal fitxer, retorna 0
    //descripcio: llegeix el taulell que es troba al fitxer nomTaulell del directori resources en format de strings (si existeix),
    // i comprova si te solucio unica. si es aixi el guarda a persistencia
    public int proposaTaulell(String nomTaulell) throws IOException, saveFailureException {
        return ctrl_domini.proposaTaulell(nomTaulell);
    }

    //Pre: ctrl_domini no es null
    //Post: retorna un String amb la informacio dels modes de joc
    //Descripcion: retorna la informacio dels modes de joc
    public String getInfoModesJoc() throws IOException {
        return ctrl_domini.getInfoModesJoc();
    }

    //Pre: ctrl_domini no es null
    //post: retorna la dificultat del taulell de Domini
    //Descripcio: retorna la dificultat del taulell de Domini
    public String getDificultatTaulell() {
        return ctrl_domini.getDificultatTaulell();
    }
}
