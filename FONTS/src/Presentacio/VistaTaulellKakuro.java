package Presentacio;

import CapaOrtogonal.Casella;
import CapaOrtogonal.CasellaBlanca;
import CapaOrtogonal.CasellaNegra;
import Domini.Pair;
import Persistencia.GrammaticException;
import Persistencia.saveFailureException;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class VistaTaulellKakuro {
    private JFrame frameVista;
    private JPanel panelContenidos = new JPanel();
    private JPanel panelTaulell = new JPanel();
    private JPanel panelInfo = new JPanel();
    private JPanel panelInfoEsquerra = new JPanel();
    private JPanel panelEsquerra = new JPanel();
    private JPanel panelEsquerraSud = new JPanel();
    private JPanel panelDreta = new JPanel();
    private JPanel panelDretaNord = new JPanel();
    private JButton normesButton = new JButton("Normes");
    private JButton demanarComodins = new JButton("Comodí");
    private JButton validarSolucio = new JButton("Validar resposta");
    private JButton guardarPartida = new JButton("Guardar partida");
    private JButton sortir = new JButton("Sortir");
    private CtrlPresentacio ctrlPresentacio;
    private String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private Casella[][] taulell;
    private JLabel cronoLabel = new JLabel();
    private int cronoSegons;
    private int cronoMinuts;
    private boolean stopTimer;
    private int modeDeJoc;
    private int num_comodins;
    private JLabel comodins_restants = new JLabel();

    //Pre: -
    //Post: Construeix una VistaTaulellKakuro, la associa al CtrlPresentacio, coloca el frameVista i el taulell,
    // inicialitza el cronometre a 0 o al temps maxim per al mode contrarrellotge (segons la dificultat del taulell)
        // i inicialitza els components
    //descripcio: constructora de VistaTaulellKakuro
    public VistaTaulellKakuro(JFrame frameVista, CtrlPresentacio ctrlPresentacio, int modeDeJoc, int num_comodins) {
        this.frameVista = frameVista;
        this.ctrlPresentacio = ctrlPresentacio;
        taulell = ctrlPresentacio.getTaulellDeMemoria();
        String dificultat = ctrlPresentacio.getDificultatTaulell();
        if (modeDeJoc==0 || modeDeJoc==1) {
            cronoSegons = 0;
            cronoMinuts = 0;
        }
        else {
            if(dificultat.equals("facil")){
                cronoSegons = 0;
                cronoMinuts = 2;
            }
            else if(dificultat.equals("normal")){
                cronoSegons = 0;
                cronoMinuts = 4;
            }
            else {
                cronoSegons = 0;
                cronoMinuts = 6;
            }

        }

        if (modeDeJoc==1) {
            if(dificultat.equals("facil")){
                num_comodins=2;
            }
            else if(dificultat.equals("normal")){
                num_comodins=6;
            }
            else {
                num_comodins=10;
            }

        }

        stopTimer=false;
        this.modeDeJoc=modeDeJoc;
        this.num_comodins=num_comodins;
        inicializarComponentes();
    }

    //Pre: -
    //Post: Construeix una VistaTaulellKakuro, la associa al CtrlPresentacio, coloca el frameVista i el taulell,
        // inicialitza el cronometre al valor passat per parametre i inicialitza els components
    //descripcio: constructora de VistaTaulellKakuro
    public VistaTaulellKakuro(JFrame frameVista, CtrlPresentacio ctrlPresentacio,int segons, int modeDeJoc, int num_comodins) {
        this.frameVista = frameVista;
        this.ctrlPresentacio = ctrlPresentacio;
        taulell = ctrlPresentacio.getTaulellDeMemoria();
        cronoMinuts=segons/60;
        cronoSegons=segons%60;
        this.modeDeJoc=modeDeJoc;
        this.num_comodins=num_comodins;
        inicializarComponentes();
    }

    //Pre: Ni taulell1 ni els seus elements son nulls.
    //Post: Retorna un clon de la matriu de Casella's fet amb deep copy.
    //Descripcio: Clonadora de matrius de Casella's
    private Casella[][] trueClone(Casella[][] taulell1) {
        Casella[][] clon = new Casella[taulell1.length][taulell1[0].length];

        for (int i = 0; i < taulell1.length; i++) {
            for (int j = 0; j < taulell1[0].length; j++) {
                clon[i][j] = taulell1[i][j].cloneMeu();
            }
        }
        return clon;
    }

    //Pre: els components no son nulls
    //Post: inicialitza el frame, els panels i els seus continguts, i assigna els listeners
    //descripcio: inicialitza els components de la vista
    private void inicializarComponentes() {
        inicializar_frameVista();
        inicializar_panelContenidos();
        inicializar_panelInfo();
        inicializar_panelEsquerra();
        inicializar_panelDreta();
        inicialitzar_panelTaulell();
        asignar_listenersComponentes();
    }

    //Pre: els components no son nulls
    //Post: activa el frame i li insereix el panelContenidos
    //descripcio: inicialitza el frame
    private void inicializar_frameVista() {
        frameVista.setEnabled(true);
        JPanel contentPane = (JPanel) frameVista.getContentPane();
        contentPane.add(panelContenidos);

        frameVista.setSize(new Dimension(800, 600));
    }

    //Pre: panelContenidos, panelInfo, panelTaulell, panelEsquerra i panelDreta no son nulls
    //Post: inicialitza el layout del panelContenidos, fa visible el panel, hi insereix els altres panels
    //descripcio: inicialitza el panelContenidos
    private void inicializar_panelContenidos() {
        // Layout
        panelContenidos.setLayout(new BorderLayout());
        // Panels
        panelContenidos.add(panelInfo, BorderLayout.NORTH);
        panelContenidos.add(panelTaulell, BorderLayout.CENTER);
        panelContenidos.add(panelEsquerra, BorderLayout.WEST);
        panelContenidos.add(panelDreta, BorderLayout.EAST);
        panelContenidos.add(panelTaulell, BorderLayout.CENTER);
        panelContenidos.setVisible(true);
    }

    //Pre: panelInfo, panelInfoEsquerra i cronoLabel no son nulls
    //Post: inicialitza el layout del panelInfo, fa visible el panel, hi insereix panelInfoEsquerra i cronoLabel i els inicialitza
    //descripcio: inicialitza el panelInfo
    private void inicializar_panelInfo() {
        panelInfo.setLayout(new BorderLayout());
        panelInfo.add(panelInfoEsquerra, BorderLayout.WEST);
        panelInfo.add(cronoLabel, BorderLayout.EAST);
        inicializar_panelInfoEsquerra();
        inicializar_crono();
        panelInfo.setVisible(true);
    }

    //Pre: cronoLabel no es null
    //Post: inicialitza el temps del cronometre o compte enrere per al mode contrarrellotge
        // i indica el funcionament d'aquest
    //descripcio: inicialitza el cronometre/contrarrellotge (depenent del mode de joc) i el fa funcionar
    private void inicializar_crono() {
        cronoLabel.setText("Temps emprat:" + "   " + String.valueOf(cronoMinuts) + ":" + String.valueOf(cronoSegons));
        Timer timer = new Timer();
        if(modeDeJoc!=2) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (stopTimer) {
                        timer.cancel();
                        timer.purge();
                        return;
                    }
                    ++cronoSegons;
                    if (cronoSegons == 60) {
                        ++cronoMinuts;
                        cronoSegons = 0;
                    }
                    if (cronoSegons < 10 && cronoMinuts < 10)
                        cronoLabel.setText("Temps emprat:" + "   " + "0" + String.valueOf(cronoMinuts) + ":" + "0" + String.valueOf(cronoSegons));
                    else if (cronoSegons < 10)
                        cronoLabel.setText("Temps emprat:" + "   " + String.valueOf(cronoMinuts) + ":" + "0" + String.valueOf(cronoSegons));
                    else if (cronoMinuts < 10)
                        cronoLabel.setText("Temps emprat:" + "   " + "0" + String.valueOf(cronoMinuts) + ":" + String.valueOf(cronoSegons));
                    else
                        cronoLabel.setText("Temps emprat:" + "   " + String.valueOf(cronoMinuts) + ":" + String.valueOf(cronoSegons));

                }
            }, 0, 1000);
        }
        else {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (stopTimer) {
                        timer.cancel();
                        timer.purge();
                        return;
                    }

                    if (cronoSegons < 10 && cronoMinuts < 10)
                        cronoLabel.setText("Temps restant:" + "   " + "0" + String.valueOf(cronoMinuts) + ":" + "0" + String.valueOf(cronoSegons));
                    else if (cronoSegons < 10)
                        cronoLabel.setText("Temps restant:" + "   " + String.valueOf(cronoMinuts) + ":" + "0" + String.valueOf(cronoSegons));
                    else if (cronoMinuts < 10)
                        cronoLabel.setText("Temps restant:" + "   " + "0" + String.valueOf(cronoMinuts) + ":" + String.valueOf(cronoSegons));
                    else
                        cronoLabel.setText("Temps restant:" + "   " + String.valueOf(cronoMinuts) + ":" + String.valueOf(cronoSegons));

                    if (cronoSegons == 0) {
                        if(cronoMinuts>=1) {
                            --cronoMinuts;
                            cronoSegons = 60;
                        }
                        else {
                            stopTimer = true;
                            actionPerformed_endTimer();
                        }
                    }
                    --cronoSegons;

                }
            }, 0, 1000);
        }
    }

    //Pre: panelEsquerra, panelInfoEsquerra, normesButton i shortcutsButton no son nulls
    //Post: inicialitza el layout del panelInfoEsquerra, fa visible el panel, hi insereix normesButton i shortcutsButton
    //descripcio: inicialitza el panelInfoEsquerra
    private void inicializar_panelInfoEsquerra() {
        panelInfoEsquerra.setLayout(new FlowLayout());
        panelInfoEsquerra.add(normesButton);
        panelInfoEsquerra.setVisible(true);
    }

    //Pre: panelEsquerra i panelEsquerraSud no son nulls
    //Post: inicialitza el layout del panelEsquerra, fa visible el panel, hi insereix panelEsquerraSud i inicialitza aquest
    //descripcio: inicialitza el panelEsquerra
    private void inicializar_panelEsquerra() {
        panelEsquerra.setLayout(new BorderLayout());
        panelEsquerra.add(panelEsquerraSud, BorderLayout.SOUTH);
        inicializar_panelEsquerraSud();
        panelEsquerra.setVisible(true);
    }

    //Pre: panelEsquerraSud, guardarPartida i sortir no son nulls
    //Post: inicialitza el layout del panelEsquerraSud, hi insereix els botons guardarPartida i sortir, i fa visible el panel.
    //Si no hi ha una sessió iniciada, es deshabilita el botó de guardar partida.
    //descripcio: inicialitza el panelEsquerraSud
    private void inicializar_panelEsquerraSud() {
        panelEsquerraSud.setLayout(new BorderLayout());
        if(!ctrlPresentacio.getIsLoggedIn()) guardarPartida.setEnabled(false);
        panelEsquerraSud.add(guardarPartida, BorderLayout.NORTH);
        panelEsquerraSud.add(sortir, BorderLayout.SOUTH);
        panelEsquerraSud.setVisible(true);
    }

    //Pre: panelDreta i panelDretaCentre no son nulls
    //Post: inicialitza el layout del panelDreta, fa visible el panel, hi insereix panelDretaCentre i inicialitza aquest
    //descripcio: inicialitza el panelDreta
    private void inicializar_panelDreta() {
        panelDreta.setLayout(new BorderLayout());
        panelDreta.add(panelDretaNord, BorderLayout.NORTH);
        inicializar_panelDretaNord();
        panelDreta.setVisible(true);
    }

    //Pre: panelDretaNord, demanarComodins, validarSolucio i comodins_restants no son nulls
    //Post: inicialitza el layout del panelDretaNord, hi insereix els botons demanarComodins i validarSolucio i el JLabel comodins_restants, i fa visible el panel
    //descripcio: inicialitza el panelDretaNord
    private void inicializar_panelDretaNord() {
        panelDretaNord.setLayout(new BorderLayout());
        if (modeDeJoc==1) {
            panelDretaNord.add(demanarComodins, BorderLayout.CENTER);
            comodins_restants.setText("Comodins restants: "+num_comodins);
        }
        panelDretaNord.add(validarSolucio, BorderLayout.SOUTH);

        panelDretaNord.add(comodins_restants, BorderLayout.NORTH);

        panelDretaNord.setVisible(true);
    }

    //Pre: panelTaulell, taulell i ctrlPresentacio no son nulls
    //Post: mostra en el panelTaulell el taulell representat amb una graella de panels, JLabels, i comboboxes
    //descripcio: inicialitza el panelTaulell
    private void inicialitzar_panelTaulell() {
        int num_columnes = taulell[0].length;
        int num_files = taulell.length;
        panelTaulell.setLayout(new GridLayout(num_files, num_columnes, 2, 2));
        for (int i = 0; i < num_files; i++) {
            for (int j = 0; j < num_columnes; j++) {
                JPanel panelCasella;
                if (taulell[i][j] instanceof CasellaNegra) {
                    panelCasella = new PanelNegras(i, j, new BorderLayout());
                    panelCasella.setBackground(Color.black);
                    JPanel panelAdalt = new JPanel(new BorderLayout());
                    JPanel panelAbaix = new JPanel(new BorderLayout());
                    panelCasella.add(panelAdalt, BorderLayout.NORTH);
                    panelCasella.add(panelAbaix, BorderLayout.SOUTH);

                    int suma_fila = ((CasellaNegra) taulell[i][j]).getSumaFila();
                    if (suma_fila > 0) {
                        JTextArea jta = new JTextArea(String.valueOf(suma_fila));
                        jta.setBackground(Color.BLACK);
                        jta.setForeground(Color.white);
                        panelAdalt.add(jta, BorderLayout.EAST);

                    }

                    int suma_columna = ((CasellaNegra) taulell[i][j]).getSumaColumna();
                    if (suma_columna > 0) {
                        JTextArea jta = new JTextArea(String.valueOf(suma_columna));
                        jta.setBackground(Color.BLACK);
                        jta.setForeground(Color.white);
                        panelAbaix.add(jta, BorderLayout.WEST);
                    }

                    panelAbaix.setBackground(Color.black);
                    panelAdalt.setBackground(Color.black);
                } else {
                    ArrayList<Pair<Integer, Integer>> descobertes = ctrlPresentacio.getDescobertes();
                    Boolean a = false;
                    panelCasella = new JPanel(new BorderLayout());
                    panelCasella.setBackground(Color.white);
                    for (int d = 0; d < descobertes.size(); ++d) {
                        if (descobertes.get(d).getFirst() == i && descobertes.get(d).getSecond() == j) {
                            JLabel label4 = new JLabel(String.valueOf(((CasellaBlanca) taulell[i][j]).getNum()));
                            label4.setHorizontalAlignment(SwingConstants.CENTER);
                            panelCasella.add(label4, BorderLayout.CENTER);
                            a = true;
                        }
                    }
                    if (!a) {
                        JComboBox numberslist = new JComboBox();
                        for (int k = 0; k <= 9; k++) {
                            numberslist.addItem(k);
                        }
                        numberslist.setSelectedItem(((CasellaBlanca)taulell[i][j]).getNum());

                        ((JLabel) numberslist.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
                        int finalI = i;
                        int finalJ = j;
                        numberslist.addActionListener
                                (new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        actionPerformed_numbersList(e);
                                    }

                                    private void actionPerformed_numbersList(ActionEvent e) {
                                        ((CasellaBlanca) taulell[finalI][finalJ]).setNum((Integer) numberslist.getSelectedItem());
                                    }
                                });
                        panelCasella.add(numberslist);
                    }
                }
                Border borde;
                borde = BorderFactory.createLineBorder(Color.black);
                panelCasella.setBorder(borde);
                panelTaulell.add(panelCasella);
            }
        }
    }


    //Pre: els components no son nulls
    //Post: assigna els listeners als botons
    //descripcio: assigna els listeners als components
    private void asignar_listenersComponentes() {
       sortir.addActionListener
               (new ActionListener() {
                   @Override
                   public void actionPerformed(ActionEvent e) {
                       actionPerformed_sortirButton(e);
                   }
               });
       normesButton.addActionListener
               (new ActionListener() {
                   @Override
                   public void actionPerformed(ActionEvent e) {
                       try {
                           actionPerformed_normesButton(e);
                       } catch (IOException ioException) {
                           ioException.printStackTrace();
                       }
                   }
               });
        validarSolucio.addActionListener
                (new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            actionPerformed_validar(e);
                        } catch (ClassNotFoundException | saveFailureException | IOException classNotFoundException) {
                            classNotFoundException.printStackTrace();
                        } catch (GrammaticException grammaticException) {
                            grammaticException.printStackTrace();
                        }
                    }
                });
        guardarPartida.addActionListener
                (new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        actionPerformed_guardarPartidaButton(e);
                    }
                }
        );
        demanarComodins.addActionListener
                (new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        actionPerformed_demanarComodins(e);
                    }
                }
        );

    }

    //Pre: ctrl_presentacio no es null
    //Post: canvia a VistaGuardarpartida
    //descripcio: actionPerformed del boto guardarPartida
    private void actionPerformed_guardarPartidaButton(ActionEvent e) {
        ctrlPresentacio.sincronizacionVistaTaulell_a_VistaGuardar(cronoSegons+cronoMinuts*60, modeDeJoc, num_comodins);
    }

    //Pre: ctrl_presentacio no es null
    //Post: comprova si la resposta donada es valida. si ho es, actualitza els records (només si els comodins estan desactivats), mostra un missatge de victoria, i canvia a la VistaVictoria
        //sino, mostra un missatge de derrota
    //descripcio: comprova si la resposta donada es valida i fa els canvis corresponents
    private void actionPerformed_validar(ActionEvent e) throws ClassNotFoundException, saveFailureException, GrammaticException, IOException {
        if(ctrlPresentacio.validarTaulell(taulell)) {
            stopTimer=true;
            String contrarrellotge="";
            if (modeDeJoc==0) ctrlPresentacio.actualitzarRecords(cronoSegons + cronoMinuts*60);
            if (modeDeJoc==2) {
                int min=cronoMinuts;
                int seg=cronoSegons;
                if (cronoSegons==59) {
                    seg=0;
                    ++min;
                }
                else ++seg;
                contrarrellotge= " TEMPS RESTANT: " + min + " minuts i " + seg + " segons";
            }
            JOptionPane.showConfirmDialog(null, "Eureka! Has solucionat el kakuro correctament!" + contrarrellotge, "Validació", JOptionPane.DEFAULT_OPTION);
            ctrlPresentacio.sincronizacionVistaTK_a_VistaVictoria(cronoSegons+cronoMinuts*60, modeDeJoc);
        }
        else  JOptionPane.showConfirmDialog(null, "Llàstima! La solució proposada no és correcte.\n No et rendeixis!", "Validació", JOptionPane.DEFAULT_OPTION);
    }

    //Pre: ctrl_presentacio no es null
    //Post: mostra un missatge amb el text informatiu de les normes
    //descripcio: actionPerformed del boto normesButton
    public void actionPerformed_normesButton(ActionEvent event) throws IOException {
        String normes=ctrlPresentacio.get_normes();
        JOptionPane.showConfirmDialog(null, normes, "Normes del joc", JOptionPane.DEFAULT_OPTION);
    }

    //Pre: ctrl_presentacio no es null
    //Post: mostra un missatge de confirmacio per a sortir de la partida, i si l'usuari indica que si, canvia a PantallaPrincipal
    //descripcio: actionPerformed del boto Enrere
    private void actionPerformed_sortirButton(ActionEvent e) {
        if (0 == JOptionPane.showConfirmDialog(null, "Segur que voleu sortir de la partida?\ntot el progrés no guardat es perdrà.", "Sortir", JOptionPane.YES_NO_OPTION)) {
            stopTimer = true;
            ctrlPresentacio.sincronizacionVistaTaulell_a_PantallaPrincipal();
        }

    }

    //Pre: ctrl_presentacio no es null
    //Post: actualitza taulell per a que mostri la resposta d'una casella encara no mostrada ni resposta. Actualitza panelTaulell amb el taulell.
    //si num_comodins arriba a 0, es desactiva el botó. s'actualitza el JLabel comodins_restants
    //descripcio: actionPerformed del boto demanarComodins. mostra la resposta d'una casella del taulell
    private void actionPerformed_demanarComodins(ActionEvent e){
        --num_comodins;
        ctrlPresentacio.demanarComodi(taulell);
        panelTaulell.removeAll();
        inicialitzar_panelTaulell();
        if (num_comodins==0) demanarComodins.setEnabled(false);
        comodins_restants.setText("Comodins restants: "+num_comodins);
    }

    //Pre: ctrl_presentacio no es null
    //Post: mostra un missatge que impedeix seguir jugant i que indica que el temps s'ha acabat. aleshores canvia a Pantalla Principal
    //descripcio: actionPerformed per a quan s'acaba el timer
    private void actionPerformed_endTimer(){
        JOptionPane.showConfirmDialog(null, "Llàstima! Millor que provis el mode bebé ;)", "Timer ha acabat", JOptionPane.DEFAULT_OPTION);
        ctrlPresentacio.sincronizacionVistaTaulell_a_PantallaPrincipal();
    }

    //Pre: els components no son nulls
    //Post: activa el frame i inicialitza el panel.
    //descripcio: activa la vista
    public void activar() {
        frameVista.setEnabled(true);
        frameVista.setSize(new Dimension(800, 600));
        panelContenidos.setVisible(true);
    }

    //Pre: els components no son nulls
    //Post: desactiva el frame. fa el panel invisible.
    //descripcio: desactiva la vista
    public void desactivar() {
        frameVista.setSize(new Dimension(800, 450));
        frameVista.setEnabled(false);
        panelContenidos.setVisible(false);
    }

    //Pre: frameVista no es null
    //Post: retorna el frameVista
    //descripcio: getter de frameVista
    public JFrame getFrame() {
        return frameVista;
    }
}
