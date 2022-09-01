package Presentacio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

import static javax.swing.Action.ACCELERATOR_KEY;

public class PantallaPrincipal {
    //lo que s'ha generat automàticament
    private JButton normesButton;
    private JButton shortcutsButton;
    private JButton signUpButton;
    private JButton logInButton;
    private JButton sortirButton;
    private JButton jugarButton;
    private JButton taulellsPersonalitzatsButton;
    private JButton rankingsButton;
    private JPanel main_pane;
    private JButton recordsButton;
    private JLabel relleno; //ocupa l'espai deixat pel boto LogIn quan desapareix

    //lo que hem posat nosaltres
    private CtrlPresentacio ctrl_presentacio;
    private JFrame frameVista = new JFrame("KAKURO 2020");

    //Pre: -
    //Post: Construeix una PantallaPrincipal
    //descripcio: constructora de PantallaPrincipal
    public PantallaPrincipal() {    }

    //Pre: -
    //Post: Construeix una PantallaPrincipal, la associa al CtrlPresentacio, i inicialitza els components
    //descripcio: constructora de PantallaPrincipal
    public PantallaPrincipal(CtrlPresentacio ctrlPresentacio) {
        ctrl_presentacio = ctrlPresentacio;
        inicializarComponentes();
    }

    //Pre: els components no son nulls
    //Post: activa el frame i fa visible el panel.
    //descripcio: activa la vista
    public void activar() {
        frameVista.setEnabled(true);
        main_pane.setVisible(true);
    }

    //Pre: frameVista no es null
    //Post: fa pack al frame i el fa visible
    //descripcio: fa visible la vista
    public void hacerVisible() {    //nomes es cridara un cop, desde CtrlPresen al crearnos. i no cal tornar a fer-ho per a aquest FRAME.
        frameVista.pack();
        frameVista.setVisible(true);
    }


    //Pre: els components no son nulls
    //Post: desactiva el frame. fa el panel invisible.
    //descripcio: desactiva la vista
    public void desactivar() {
        frameVista.setEnabled(false);
        main_pane.setVisible(false);
    }

    //Pre: ctrl_presentacio no es null
    //Post: mostra un missatge amb el text informatiu dels shortcuts
    //descripcio: actionPerformed del boto shortcutsButton
    public void actionPerformed_shortcutsButton(ActionEvent event) throws IOException {
        String shorcuts=ctrl_presentacio.get_shortcuts();
        JOptionPane.showConfirmDialog(null, shorcuts, "Shortcuts", JOptionPane.DEFAULT_OPTION);
    }

    //Pre: -
    //Post: mostra un missatge de confirmacio per a tancar el joc, i si l'usuari indica que si, s'acaba la execucio del programa
    //descripcio: actionPerformed del boto SortirButton
    public void actionPerformed_sortirButton(ActionEvent event) {
        if (0 == JOptionPane.showConfirmDialog(null, "Segur que voleu tancar el joc?", "Sortir", JOptionPane.YES_NO_OPTION))
            System.exit(0);
    }

    //Pre: ctrl_presentacio no es null
    //Post: mostra un missatge amb el text informatiu de les normes
    //descripcio: actionPerformed del boto normesButton
    public void actionPerformed_normesButton(ActionEvent event) throws IOException {
        String normes=ctrl_presentacio.get_normes();
        JOptionPane.showConfirmDialog(null, normes, "Normes del joc", JOptionPane.DEFAULT_OPTION);
    }

    //Pre: ctrl_presentacio no es null
    //Post: canvia a VistaJugar
    //descripcio: actionPerformed del boto Jugar
    public void actionPerformed_jugarButton(ActionEvent event) {
        ctrl_presentacio.sincronizacionVistaPrincipal_a_VistaJugar();
    }

    //Pre: ctrl_presentacio no es null
    //Post: canvia a VistaSignUp
    //descripcio: actionPerformed del boto signUp
    public void actionPerformed_signUpButton(ActionEvent event){
        ctrl_presentacio.sincronizacionVistaPrincipal_a_VistaSignUp();
    }

    //Pre: ctrl_presentacio no es null
    //Post: canvia a VistaLogIn
    //descripcio: actionPerformed del boto LogIn
    public void actionPerformed_logInButton(ActionEvent event){
        ctrl_presentacio.sincronizacionVistaPrincipal_a_VistaLogIn();
    }

    //Pre: els components de la pantalla no son nulls
    //Post: canvia l'estat de la pantalla a logged out
    //descripcio: actionPerformed del boto LogOut
    public void actionPerformed_logOutButton(ActionEvent event){
        changelogouttonotloggged();
    }

    //Pre: ctrl_presentacio no es null
    //Post: canvia a VistaTaulellsPersonalitzats
    //descripcio: actionPerformed del boto TaulellsPersonalitzats
    private void actionPerformed_taulellsPersonalitzats(ActionEvent e) {
        ctrl_presentacio.sincronizacionVistaPrincipal_a_TP();
    }

    //Pre: els components no son nulls
    //Post: canvia l'estat de la pantalla a logged out, canvia el text del boto SignUp a "Sign Up". fa visible el botó LogIn
    //descripcio: canvia l'estat de la pantalla a logged out
    private void changelogouttonotloggged() {
        signUpButton.setText("Sign Up");
        logInButton.setVisible(true);
            relleno.setVisible(false);
        ctrl_presentacio.setIsLoggedIn(false);
    }

    //Pre: els components no son nulls
    //Post: canvia l'estat de la pantalla a logged in, canvia el text del boto SignUp a "Log out". fa invisible el botó LogIn
    //descripcio: canvia l'estat de la pantalla a logged in
    public void changebuttontologout() {
        signUpButton.setText("Log out");
        logInButton.setVisible(false);
            relleno.setVisible(true);
    }

    //Pre: ctrl_presentacio no es null
    //Post: canvia a VistaRecords
    //descripcio: actionPerformed del boto Records
    private void actionPerformed_recordsButton(ActionEvent e) {
        ctrl_presentacio.sincronizacionVistaPrincipal_a_records();
    }

    //Pre: ctrl_presentacio no es null
    //Post: canvia a VistaRankings
    //descripcio: actionPerformed del boto Rankings
    private void actionPerformed_rankingButton(ActionEvent e) {
        ctrl_presentacio.sincronizacionVistaPrincipal_a_rankings();
    }

    //Pre: els components no son nulls
    //Post: assigna els listeners a shortcutsButton, sortirButton, normesButton,
        // jugarButton, signUpButton, logInButton, taulellsPersonalitzatsButton, recordsButton i, rankingsButton
    //descripcio: assigna els listeners als components
    private void asignar_listenersComponentes() {
        // Listeners para los botones
        shortcutsButton.addActionListener                                   //actionEvent <-> actionListener
                (new ActionListener() {
                    public void actionPerformed (ActionEvent event) {
                        try {
                            actionPerformed_shortcutsButton(event);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
        sortirButton.addActionListener
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
        jugarButton.addActionListener
                (new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        actionPerformed_jugarButton(e);
                    }
                });
        signUpButton.addActionListener
                (new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(!ctrl_presentacio.getIsLoggedIn()){
                            actionPerformed_signUpButton(e);
                        }
                        else {
                            actionPerformed_logOutButton(e);
                        }
                        }
                    }
                );

        logInButton.addActionListener
                (new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        actionPerformed_logInButton(e);
                    }
                });
        taulellsPersonalitzatsButton.addActionListener
                (new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        actionPerformed_taulellsPersonalitzats(e);
                    }
                });
        recordsButton.addActionListener
                (new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        actionPerformed_recordsButton(e);
                    }
                });
        rankingsButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        actionPerformed_rankingButton(e);
                    }
                }
        );
    }

    //Pre: els components no son nulls
    //Post: inicialitza el frame i el panel i assigna els listeners
    //descripcio: inicialitza els components de la vista
    private void inicializarComponentes() {
        inicializar_frameVista();
        inicializar_mi_pana();
        asignar_listenersComponentes();
    }

    //Pre: els components no son nulls
    //Post: activa el frame, li dona les dimensions, la posicio i el DefaultCloseOperation i li insereix el panel
    //descripcio: inicialitza el frame
    private void inicializar_frameVista() {
        // mida
        frameVista.setMinimumSize(new Dimension(800,450));
        frameVista.setPreferredSize(frameVista.getMinimumSize());
        frameVista.setResizable(false);
        // Posicio i operacions por defecte
        frameVista.setLocationRelativeTo(null);
        frameVista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Panel
        JPanel contentPane = (JPanel) frameVista.getContentPane();
        contentPane.add(main_pane);
    }

    //Pre: main_pane no es null
    //Post: fa visible el panel, i assigna els shortcuts
    //descripcio: inicialitza el panel
    private void inicializar_mi_pana() {
        main_pane.setVisible(true);
        Action jugarAction = new AbstractAction("Jugar") {

            @Override
            public void actionPerformed(ActionEvent e) {}
        };


       jugarAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_J);
       jugarButton.setAction(jugarAction);

        Action logAction = new AbstractAction("Log In") {

            @Override
            public void actionPerformed(ActionEvent e) {
            }
        };

        logAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
        logInButton.setAction(logAction);

        Action signUpAction = new AbstractAction("Sign Up") {

            @Override
            public void actionPerformed(ActionEvent e) {}
        };

        signUpAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
        signUpButton.setAction(signUpAction);

        
        Action exitAction = new AbstractAction("Sortir") {

            @Override
            public void actionPerformed(ActionEvent e) {}
        };

        exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
        sortirButton.setAction(exitAction);

        relleno.setMinimumSize(new Dimension(69,10));
        relleno.setPreferredSize(relleno.getMinimumSize());
        relleno.setVisible(false);
    }

    //Pre: frameVista no es null
    //Post: retorna el frameVista
    //descripcio: getter de frameVista
    public JFrame getFrame() {
        return frameVista;
    }
}
