package Domini;

import CapaOrtogonal.Casella;
import Persistencia.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CtrlDomini {   //es comunica amb les altres capes i gestiona tots els Ctrl de Domini i tal

    private CtrlPersistencia ctrl_persistencia;
    private Taulell taulell_domini;
    private Perfil perfil_domini;
    private Boolean is_logged_in;
    private Partida partida_domini;

    //Pre: -
    //Post: construeix un CtrlDomini amb is_logged_in inicialitzat a false.
        //Crea el CtrlPersistencia i s'hi associa
    //Descripcio: Constructora de CtrlDomini
    public CtrlDomini() throws IOException {
        ctrl_persistencia= new CtrlPersistencia();//haurem de fer una funció de incialitzar
        is_logged_in = false;
    }

    //Pre: L'argument fitxer ha de ser un nom que es trobi a la carpeta resources
    //Post: Retorna un taulell amb les columnes, files i amb atribut taulell del kakuro passat per fitxer
    //Descripció: Llegeix el fitxer, i crea un taulell amb els atributs obtinguts a partir de la lectura del fitxer
    public Taulell llegirKakuro(String fitxer) {
        IOKakuro iok = new IOKakuro();
        iok.llegirKakuro(fitxer);

        Taulell t1 =  new Taulell();
        t1.setFilas(iok.getFilas());
        t1.setColumnas(iok.getColumnas());
        t1.setTaulell(iok.getTaulell());

        return t1;
    }

    //Pre: L'argument fitxer ha de ser un nom que es trobi a la carpeta resources
    //Post: Escriu per consola el kakuro resolt i retorna el seu tipus:
        // (0 == no te solucio, 1 == te solucio unica, 2 == te multiples solucions)
    //Descripció: Llegeix el fitxer que se li passa per paràmetre, el resol i l'escriu per consola.
    public int resoldreKakuro(String fitxer) {

        Taulell t1 = llegirKakuro(fitxer);
        AlgorismeSolucionarKakuro ask = new AlgorismeSolucionarKakuro(t1.getFilas(),t1.getColumnas(), t1.getTaulell());
        ask.execute();
        Casella[][] taulellResolt = ask.getTaulellResolt();

        int tipus_kakuro = ask.getN_solucions_trobades();
        IOKakuro iok = new IOKakuro();
        return tipus_kakuro;
    }

    //Pre: -
    //Post: Genera el kakuro amb els paràmetres indicats, el guarda a persistència l'escriu per consola i retorna el taulell amb el nombre de cel·les descobertes indicat  i el taulell resolt
    //Descripció: Genera un Kakuro randomitzat amb els paràmetres indicats.
    public Pair<Casella[][],Casella[][]> generarKakuro(int filas, int columnas, int numCelesDescobertes, String nom) throws IOException, saveFailureException {
        boolean trobat = false;
        Casella[][] taulellGenerat = new Casella[filas][columnas];
        Casella[][] taulellResolt = new Casella[filas][columnas];
        Casella[][] taulellDescobert = new Casella[filas][columnas];
        int numCelesNegres=0;
        int tipus = -1;
        IOKakuro IO = new IOKakuro();
        while(tipus != 1){
            trobat = false;
            while(!trobat){
                Taulell t1 = new Taulell(filas, columnas, numCelesDescobertes);
                AlgorismeGenerarKakuro agk = new AlgorismeGenerarKakuro(filas, columnas, numCelesDescobertes, t1.getTaulell());
                trobat = agk.execute();
                taulellGenerat = agk.getTaulell();
                taulellResolt = agk.getTaulellResolt();
                numCelesNegres = agk.getNumCelasNegras();

            }
            IO.printSolucio2(taulellGenerat);
            tipus = resoldreKakuro("filename.txt");


        }
        AlgorismeGenerarKakuro agk2 = new AlgorismeGenerarKakuro(filas, columnas, numCelesDescobertes, trueClone(taulellResolt));
        agk2.buidarambcelesdescobertes(numCelesDescobertes);
        taulellDescobert = agk2.getTaulell();


        Taulell TGenerat = new Taulell(filas, columnas, numCelesNegres, numCelesDescobertes, nom, true, taulellDescobert, taulellResolt);
        ctrl_persistencia.saveTaulell(TGenerat, nom, false);

        return new Pair(taulellResolt,taulellDescobert);
    }

    //Pre: -
    //Post: retorna una string que conté el text informatiu dels shortcuts
    //Descripcio: proporciona el text informatiu dels shortcuts
    public String get_shortcuts() throws IOException {
        return ctrl_persistencia.get_shortcuts();
    }

    //Pre: -
    //Post: retorna una string que conté el text informatiu de les normes
    //Descripcio: proporciona el text informatiu de les normes
    public String get_normes() throws IOException {
        return ctrl_persistencia.get_normes();
    }

    //Pre: String usuari no pot ser el nom de cap perfil guardat
    //Post: Crea el perfil i el guarda en disc. També el perfil està carregat a domini, i l'usuari està logejat.
    //Descripció: Guarda perfil a disc i logeja l'usuari
    public void savePerfil(String usuari, String contrasenya) throws IOException, ClassNotFoundException, saveFailureException, GrammaticException {
        Perfil p=new Perfil(usuari,contrasenya);
        ctrl_persistencia.savePerfil(p, false);

        perfil_domini = p;
        is_logged_in = true;
    }

    //Pre: String usuari i la contrasenya han de correspondre a un Perfil
    //Post: Perfil carregat a domini, i l'usuari està logejat.
    //Descripcó: Operació d'inici de sessió de l'usuari
    public String logIn(String username, String password) throws logInFailureException, IOException, ClassNotFoundException {
        perfil_domini = ctrl_persistencia.logIn(username, password);
        is_logged_in = true;
        return perfil_domini.getUsername();
    }

    //Pre: si is_logged_in es true, ctrl_persistencia no es null
    //Post: Si l'usuari està logejat, crea una partida amb les dades passades per paràmetre i crida al seu guardat
    //Descripcó: Mètode per crear una partida i guardar-la
    public void savePartida(int cronometre, String nomPartida, int modeDeJoc, int numComodins) throws IOException, saveFailureException {
        if(is_logged_in) {
            Partida p = new Partida();
            if (modeDeJoc==1) p= new PartidaBebe(perfil_domini, taulell_domini, cronometre, numComodins);
            else p = new Partida(perfil_domini,taulell_domini,cronometre, modeDeJoc);
            ctrl_persistencia.savePartida(p, nomPartida);
        }
    }

    //Pre: -
    //Post: Crida al guardat/actualitzacio d'un taulell a la capa de persistencia
    //Descripcó: Metode per cridar al guardat d'un taulell a la capa de persistencia
    public void saveTaulell(Taulell t, String saveName, boolean update) throws IOException, saveFailureException {
        ctrl_persistencia.saveTaulell(t, saveName, update);
    }

    //Pre: taulell_domini no es null
    //Post: retorna la matriu de Casella's del taulell actual de Domini
    //Descripcio: proporciona el taulell actual de Domini
    public Casella[][] getTaulellDeMemoria() {
        return taulell_domini.getTaulell();
    }

    //Pre: ctrl_persistencia no es null
    //Post: posa el taulell que es troba al path indicat al taulell actual de Domini
    //Descripcio: Carrega el taulell indicat a memoria
    public void loadTaulellAMemoria(String path) throws IOException, ClassNotFoundException {
        taulell_domini = ctrl_persistencia.loadTaulell(path);
    }

    //Pre: -
    //Post: retorna is_logged_in, que indica si hi ha una sessio iniciada
    //Descripcio: getter de is_logged_in
    public Boolean getIs_logged_in() {
        return this.is_logged_in;
    }

    //Pre: -
    //Post: posa el valor indicat a is_logged_in
    //Descripcio: setter de is_logged_in
    public void setIsLoggedIn(boolean b) {
        is_logged_in = b;
    }

    //Pre: perfil_domini no es null
    //Post: retorna el username del perfil actual de domini
    //Descripcio: retorna el username del perfil actual de domini
    public String getUsername() {
        return perfil_domini.getUsername();
    }

    //Pre: taulell_domini no es null
    //Post: retorna cert si i nomes si taulell te els mateixos valors que el taulell resolt
    //Descripció: Valida la solucio introduida del taulell
    public boolean validarTaulell(Casella[][] taulell) {
        return taulell_domini.validarTaulell(taulell);
    }

    //Pre: taulell_domini no es null
    //Post: retorna una matriu de Casella's que es com la passada per parametre pero
        // mostra la resposta d'una casella encara no mostrada ni resposta.
    //descripcio: mostra la resposta d'una casella del taulell
    public void demanarComodi(Casella[][] taulell){
        taulell_domini.demanarComodi(taulell);
    }

    //Pre: -
    //Post: crida a l'esborrat d'un Taulell a la capa de Persistència
    //Descripcio: mètode per cridar a l'esborrat d'un Taulell a la capa de Persistència
    public void deleteTaulell(File f) {
        ctrl_persistencia.deleteTaulell(f);
    }

    //Pre: ctrl_persistencia no es null
    //Post: crida a l'esborrat d'una Partida a la capa de Persistència
    //Descripcio: mètode per cridar a l'esborrat d'una Partida a la capa de Persistència
    public void deletePartida(File f) {
        ctrl_persistencia.deletePartida(f);
    }

    //Pre: ctrl_persistencia i perfil_domini no son nulls
    //Post: Si l'usuari esta autenticat:
        // Actualitza el temps de perfil_domini per taulell_domini si i nomes si es inferior al temps guardat o
        // si no existia cap temps per el taulell.
        // I actualitza el perfil a persistencia
    //Descripció: Actualitza el record de perfil_domini per taulell_domini, si cal
    public void actualitzarRecords(int i) throws ClassNotFoundException, saveFailureException, GrammaticException, IOException {
        if (is_logged_in) {
            perfil_domini.actualitzarRecords(taulell_domini.getNom(), i);
            ctrl_persistencia.savePerfil(perfil_domini, true);
        }
    }

    //Pre: perfil_domini no es null
    //Post: si hi ha un usuari identificat, retorna el record de l'usuari actual en el taulell identificat per name.
        //sino, retorna -1.
        //el record serà -2 si no existia un record per a aquests usuari i taulell.
    //descripcio: retorna el record de l'usuari actual en el taulell identificat per name
    public Integer getRecord(String name) {
        if (is_logged_in) {
            return perfil_domini.getRecord(name);
        }
        return -1;
    }

    //Pre: -
    //Post: si l'usuari està logejat, carrega el Taulell de taulell_domini a memòria, crida a l'operació de actualització
    // de ranking, i el torna a guardar. No fa res en cas contrari
    //Descripcio: mètode per actualitzar el ranking d'un taulell
    public void actualitzarRanking(int sec) throws IOException, ClassNotFoundException, saveFailureException {
        if (is_logged_in) {
            Taulell t = ctrl_persistencia.loadTaulell("./Dades/Taulells/" + taulell_domini.getDificultat() + "/" + taulell_domini.getNom() + ".bin");
            t.actualitzaRanking(perfil_domini.getUsername(), sec);
            ctrl_persistencia.saveTaulell(t, taulell_domini.getNom(), true);
        }
    }

    //Pre: Ni taulell1 ni els seus elements son nulls.
    //Post: Retorna un clon de la matriu de Casella's fet amb deep copy.
    //Descripcio: Clonadora de matrius de Casella's
    private Casella[][] trueClone(Casella[][] taulell1) {   //fa deep copy
        Casella[][] clon = new Casella[taulell1.length][taulell1[0].length];

        for (int i = 0; i < taulell1.length; i++) {
            for (int j = 0; j < taulell1[0].length; j++) {
                clon[i][j] = taulell1[i][j].cloneMeu();
            }
        }
        return clon;
    }

    //Pre: taulell_domini i ctrl_persistencia no son null
    //Post: retorna el ranking del taulell indicat per path en format de l'string que s'haura de mostrar per pantalla.
    //descripcio: retorna el ranking del taulell indicat per path en format de l'string que s'haura de mostrar per pantalla.
    public String getRanking(String path) throws IOException, ClassNotFoundException {
        Map<String,Integer> ranking = ctrl_persistencia.loadTaulell(path).getRanking();


        List<String> noms = new ArrayList<String>();
        List<Integer> tempss = new ArrayList<Integer>();

        for (int i = 0; i < 10; i++) {
            noms.add("");
            tempss.add(Integer.MAX_VALUE);
        }


        for (Map.Entry<String, Integer> entry : ranking.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();

            int i = 0;
            while(i <= 10 && value >= tempss.get(i)) {
                ++i;
            }
            tempss.add(i, value);
            noms.add(i, key);
        }

        int min = 10;
        if (ranking.size() < 10)
            min = ranking.size();

        String printeo = "<html>";

        for (int i = 0; i < min; i++) {
            int num_actual = i+1;
            Integer secs = tempss.get(i);
            Integer mins = secs / 60;
            secs = secs % 60;

            printeo = printeo + String.valueOf(num_actual) +". "+ noms.get(i) + ": ";
            if (mins<10)
                printeo += "0";
            printeo = printeo + String.valueOf(mins) + ":";
            if (secs <10)
                printeo += "0";
            printeo = printeo + String.valueOf(secs) + "<br/>";
        }
        printeo += "</html>";
        return printeo;
    }

    //Pre: taulell_domini no es null
    //Post: Retorna l'atribut descobertes del taulell_domini
    //Decripció: retorna l'atribut descobertes del taulell_domini
    public ArrayList<Pair<Integer, Integer>> getDescobertes() {
        return taulell_domini.getDescobertes();

    }

    //Pre: El fitxer es una Partida en binari
    //Post: Carrega la partida indicada pel fitxer i el seu taulell a partida_domini i taulell_domini respectivament,
        // i retorna el temps del cronometre i el estat dels comodins de partida_domini
    //descripcio: Carrega la partida i el taulell a memoria, i retorna el temps del cronometre
    public Pair<Integer, Pair<Integer,Integer>> loadPartidaAMemoria(File fitxer) throws IOException, ClassNotFoundException {
        partida_domini=ctrl_persistencia.loadPartida(fitxer);
        taulell_domini=partida_domini.getTaulellPartida();
        Pair<Integer, Integer> infocom= new Pair(partida_domini.getModeDeJoc(), partida_domini.getN_comodins());
        Pair<Integer, Pair<Integer, Integer>> res= new Pair(partida_domini.getCronometre(), infocom);
        return res;
    }

    //Pre: ctrl_persistencia no es null.
    //Post: si existeix un fitxer amb el nom nomTaulell:
        // llegeix el taulell que es troba al fitxer nomTaulell del directori resources en format de strings,
        // i si te solucio unica el guarda a persistencia i retorna 2. Altrament retorna 1
        // si no existia tal fitxer, retorna 0
    //descripcio: llegeix el taulell que es troba al fitxer nomTaulell del directori resources en format de strings (si existeix),
    // i comprova si te solucio unica. si es aixi el guarda a persistencia
    public int proposaTaulell(String nomTaulell) throws IOException, saveFailureException {
        if (nomTaulell.equals(""))
            return 0;

        File file1 = new File("./resources/"+nomTaulell);

        if (file1.exists()) {
            IOKakuro io = new IOKakuro();
            io.llegirKakuro(nomTaulell);
            Casella[][] taulellProposat = io.getTaulell();
            AlgorismeSolucionarKakuro ASK = new AlgorismeSolucionarKakuro(io.getFilas(), io.getColumnas(), taulellProposat);
            ASK.execute();
            if (ASK.getN_solucions_trobades() == 1) {
                Taulell t = new Taulell(io.getFilas(), io.getColumnas(), nomTaulell, true, taulellProposat, ASK.getTaulellResolt());
                ctrl_persistencia.saveTaulell(t, nomTaulell, false);
                return 2;
            }
            return 1;
        }
        else
            return 0;
    }

    //Pre: taulell_domini no es null
    //post: retorna la dificultat de taulell_domini
    //Descripcio: retorna la dificultat del taulell de DOmini
    public String getDificultatTaulell() {
        return taulell_domini.getDificultat();
    }

    //Pre: ctrl_persistencia no es null
    //Post: retorna un String amb la informacio dels modes de joc
    //Descripcion: retorna la informacio dels modes de joc
    public String getInfoModesJoc() throws IOException {
        return ctrl_persistencia.getInfoModesJoc();
    }
}
