package Presentacio;

import CapaOrtogonal.Casella;
import Persistencia.saveFailureException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class VistaGuardarPartida {
    private JTextField textField1;
    private JButton okButton;
    private JButton cancelLaButton;
    private JPanel panel;
    private int cronometre;
    private int num_comodins;
    private int modeDeJoc;


    private CtrlPresentacio ctrl_presentacio;
    private JFrame frameVista;

    //Pre: -
    //Post: Construeix una VistaGuardarPartida, la associa al CtrlPresentacio, coloca el frameVista, el cronometre i si estan activats els comodins, i inicialitza els components
    //descripcio: constructora de VistaGuardarPartida
    public VistaGuardarPartida(CtrlPresentacio ctrl_presentacio, JFrame frame, int cronometre, int mode, int ncomodins) {
        this.ctrl_presentacio = ctrl_presentacio;
        this.cronometre=cronometre;
        this.num_comodins=ncomodins;
        this.modeDeJoc=mode;
        setFrame(frame);
        inicializarComponentes();
    }

    //Pre: -
    //Post: posa a frameVista el frame indicat
    //descripcio: setter de frameVista
    public void setFrame(JFrame frame) {
        frameVista = frame;
    }

    //Pre: els components no son nulls
    //Post: inicialitza el frame, el panel i els botons i assigna els listeners
    //descripcio: inicialitza els components de la vista
    private void inicializarComponentes() {
        inicializar_frameVista();
        inicializar_panel1();
        asignar_listeners();
    }

    //Pre: els components no son nulls
    //Post: activa el frame i li insereix el panel
    //descripcio: inicialitza el frame
    private void inicializar_frameVista() {
        frameVista.setEnabled(true);

        JPanel contentPane = (JPanel)frameVista.getContentPane();
        contentPane.add(panel);
    }

    //Pre: panel1 no es null
    //Post: fa visible el panel
    //descripcio: inicialitza el panel
    private void inicializar_panel1() {
        panel.setVisible(true);
    }


    //Pre: els components no son nulls
    //Post: assigna els listeners a okButton i cancelLaButton
    //descripcio: assigna els listeners als components
    private void asignar_listeners() {
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    actionPerformed_okButton(e);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (Persistencia.saveFailureException saveFailureException) {
                    saveFailureException.printStackTrace();
                }

            }
        });
        cancelLaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionPerformed_cancelButton(e);
            }
        });
    }

    //Pre: ctrl_presentacio no es null
    //Post: canvia a VistaTaulellKakuro
    //descripcio: actionPerformed del boto Cancel
    private void actionPerformed_cancelButton(ActionEvent e) {
        ctrl_presentacio.sincronizacionVistaGuardar_a_VistaTaulell();
    }

    //Pre: ctrl_presentacio, cronometre i textField1 no son nulls
    //Post: guarda la partida amb el nom introduit a textField1, i canvia a PantallaPrincipal
    //descripcio: actionPerformed del boto OK
    private void actionPerformed_okButton(ActionEvent e) throws IOException, saveFailureException {
        String nomPartida=textField1.getText();
        if (!nomPartida.equals("")) {
            ctrl_presentacio.savePartida(cronometre, nomPartida, modeDeJoc, num_comodins);
            JOptionPane.showConfirmDialog(null, "La partida s'ha guardat correctament", "Partida guardada", JOptionPane.DEFAULT_OPTION);
            ctrl_presentacio.sincronizacionVistaGuardar_a_VistaPrincipal();
        }
        else {
            JOptionPane.showConfirmDialog(null, "El nom de la partida no pot ser buit", "Nom de partida buit", JOptionPane.DEFAULT_OPTION);
        }
    }

    //Pre: els components no son nulls
    //Post: desactiva el frame. fa el panel invisible.
    //descripcio: desactiva la vista
    public void desactivar() {
        frameVista.setEnabled(false);
        panel.setVisible(false);
    }

    //Pre: els components no son nulls
    //Post: activa el frame i inicialitza el panel.
    //descripcio: activa la vista
    public void activar() {
        frameVista.setEnabled(true);

        panel.setVisible(true);
    }
}