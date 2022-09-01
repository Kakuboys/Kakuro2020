package Presentacio;

import Persistencia.saveFailureException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class VistaVictoria {
    private JFrame frameVista;
    private JPanel panelContenidos = new JPanel();
    private JPanel panelBotones=new JPanel();
    private JButton afegirARanking= new JButton("Afegir a Ranking");
    private JButton finalButton = new JButton("Tornar a la pantalla principal");
    private CtrlPresentacio ctrlPresentacio;
    private int cronoSegons;
    private int cronoMinuts;
    private boolean comodins;

    //Pre: -
    //Post: Construeix una VistaVictoria, la associa al CtrlPresentacio, coloca el frameVista, posa el temps del cronometre, l'activació dels comodins i inicialitza els components
    //descripcio: constructora de VistaVictoria
    public VistaVictoria(JFrame frameVista, CtrlPresentacio ctrlPresentacio, int segons, boolean comodin) throws IOException {
    this.frameVista = frameVista;
    this.ctrlPresentacio = ctrlPresentacio;
    cronoSegons=segons%60;
    cronoMinuts=segons/60;
    comodins=comodin;
    inicializarComponentes();
}
    //Pre: els components no son nulls
    //Post: inicialitza el frame, el panel i els botons i assigna els listeners
    //descripcio: inicialitza els components de la vista
    private void inicializarComponentes() throws IOException {
        inicializar_frameVista();
        inicializar_panelContenidos();
        inicializar_panelBotones();
        asignar_listenersComponentes();
    }

    //Pre: els components no son nulls
    //Post: assigna els listeners finalButton i afegirARanking
    //descripcio: assigna els listeners als components
    private void asignar_listenersComponentes() {
    finalButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            actionPerformed_finalButton(e);
        }
    });
    afegirARanking.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                actionPerformed:afegirARankingButton(e);
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (Persistencia.saveFailureException saveFailureException) {
                saveFailureException.printStackTrace();
            }
        }
    });
    }

    //Pre: ctrl_presentacio no es null
    //Post: afegeix la partida al ranking amb el temps del cronometre, mostra un missatge dient-ho,
        //i canvia a PantallaPrincipal
    //descripcio: actionPerformed del boto afegirARanking
    private void afegirARankingButton(ActionEvent e) throws ClassNotFoundException, IOException, saveFailureException {
        ctrlPresentacio.actualitzarRankings(cronoSegons + cronoMinuts*60);
        JOptionPane.showConfirmDialog(null, "El rànquing s'ha actualitzat amb el teu resultat! ", "Guardar a rànquing", JOptionPane.DEFAULT_OPTION);
        ctrlPresentacio.sincronizacionVistaVictoria_a_pantallaPrincipal();
    }

    //Pre: ctrl_presentacio no es null
    //Post: canvia a PantallaPrincipal
    //descripcio: actionPerformed del boto finalButton
    private void actionPerformed_finalButton(ActionEvent e) {
    ctrlPresentacio.sincronizacionVistaVictoria_a_pantallaPrincipal();
    }

    //Pre: panelBotones no es null
    //Post: fa visible el panelBotones, n'inicialitza el layout, i hi insereix els botons finalButton i afegirARanking.
        //si no hi ha un usuari autenticat o els comodins estan activats, desactiva el botó afegirARanking
    //descripcio: inicialitza el panelBotones
    private void inicializar_panelBotones() {
    panelBotones.setLayout(new FlowLayout());
    panelBotones.add(finalButton);
    panelBotones.add(afegirARanking);
    if(!ctrlPresentacio.getIsLoggedIn() || comodins) afegirARanking.setEnabled(false);
    panelBotones.setVisible(true);
    }

    //Pre: els components no son nulls
    //Post: activa el frame i li insereix el panel
    //descripcio: inicialitza el frame
    private void inicializar_frameVista() {
        frameVista.setEnabled(true);
        JPanel contentPane = (JPanel) frameVista.getContentPane();
        contentPane.add(panelContenidos);
    }

    //Pre: panelContenidos no es null
    //Post: fa visible el panelBotones, n'inicialitza el layout, i hi insereix un JLabel i el panelBotones
    //descripcio: inicialitza el panelContenidos
    private void inicializar_panelContenidos() throws IOException {
        // Layout
        panelContenidos.setLayout(new BorderLayout());
        // Paneles
        JLabel text=new JLabel("Vols que el teu resultat aparegui al rànquing?");
        text.setHorizontalAlignment(SwingConstants.CENTER);
        panelContenidos.add(text,BorderLayout.CENTER);
        panelContenidos.add(panelBotones,BorderLayout.SOUTH);
        panelContenidos.setVisible(true);
    }

    //Pre: els components no son nulls
    //Post: activa el frame i inicialitza el panel.
    //descripcio: activa la vista
    public void activar() {
        frameVista.setEnabled(true);
        panelContenidos.setVisible(true);
    }

    //Pre: els components no son nulls
    //Post: desactiva el frame. fa el panel invisible.
    //descripcio: desactiva la vista
    public void desactivar() {
        frameVista.setEnabled(false);
        panelContenidos.setVisible(false);
    }
}