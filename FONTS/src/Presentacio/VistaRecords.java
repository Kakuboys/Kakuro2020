package Presentacio;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class VistaRecords {
    private JLabel label1;
    private JPanel panel1;
    private JButton enrereButton;
    private JButton escollirTaulellButton;

    //els que posem nosaltres:
    private CtrlPresentacio ctrl_presentacio;
    private JFrame frameVista;

    //Pre: -
    //Post: Construeix una VistaRecords, la associa al CtrlPresentacio, coloca el frameVista, i inicialitza els components
    //descripcio: constructora de VistaRecords
    public VistaRecords(CtrlPresentacio ctrl_presentacio, JFrame frameVista) {
        this.ctrl_presentacio = ctrl_presentacio;
        this.frameVista = frameVista;
        inicializarComponentes();
    }

    //Pre: ctrl_presentacio no es null
    //Post: canvia a PantallaPrincipal
    //descripcio: actionPerformed del boto Enrere
    public void actionPerformed_enrereButton(ActionEvent event) {
        ctrl_presentacio.sincronizacionVistaRecords_a_VistaPrincipal();
    }

    //Pre: ctrl_presentacio i label1 no son null
    //Post: si hi ha un usuari autenticat, obre un JFileChooser per a escollir un taulell sobre el que veure els records de l'usuari.
        //sino, ho indica.
    //descripcio: actionPerformed del boto escollirTaulell. permet escollir un taulell sobre el que veure els records de l'usuari
    private void actionperformed_escollirTaulell() {
        if (ctrl_presentacio.getIsLoggedIn()) {
            JFileChooser fc = new JFileChooser();
            fc.setCurrentDirectory(new File("./Dades/Taulells"));
            fc.setDialogTitle("Escollir taulell");
            int result = fc.showDialog(null, "Escollir");
            if (result == JFileChooser.APPROVE_OPTION) {
                String nom = fc.getSelectedFile().getName();
                nom = nom.substring(0, nom.length() - 4);
                Integer secs = ctrl_presentacio.getRecord(nom);

                if (secs == -1)
                    label1.setText("Heu d'estar identificats per a poder veure rècords!");
                else if (secs == -2)
                    label1.setText("No teniu cap rècord per a aquest taulell");
                else {
                    Integer mins = secs / 60;
                    secs = secs % 60;
                    label1.setText("Temps rècord:" + "   " + String.valueOf(mins) + ":" + String.valueOf(secs));
                    if (secs < 10 && mins < 10)
                        label1.setText("Temps rècord:" + "   " + "0" + String.valueOf(mins) + ":" + "0" + String.valueOf(secs));
                    else if (secs < 10)
                        label1.setText("Temps rècord:" + "   " + String.valueOf(mins) + ":" + "0" + String.valueOf(secs));
                    else if (mins < 10)
                        label1.setText("Temps rècord:" + "   " + "0" + String.valueOf(mins) + ":" + String.valueOf(secs));
                    else
                        label1.setText("Temps rècord:" + "   " + String.valueOf(mins) + ":" + String.valueOf(secs));
                }
            }
        }
        else
            label1.setText("Heu d'estar identificats per a poder veure rècords");
    }

    //Pre: els components no son nulls
    //Post: activa el frame i inicialitza el panel.
    //descripcio: activa la vista
    public void activar() {
        frameVista.setEnabled(true);
        inicializar_panel1();
    }

    //Pre: els components no son nulls
    //Post: desactiva el frame. fa el panel invisible.
    //descripcio: desactiva la vista
    public void desactivar() {
        frameVista.setEnabled(false);
        panel1.setVisible(false);
    }

    //Pre: els components no son nulls
    //Post: assigna els listeners als botons
    //descripcio: assigna els listeners als components
    private void asignar_listeners() {
        enrereButton.addActionListener
                (new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        actionPerformed_enrereButton(e);
                    }
                });
        escollirTaulellButton.addActionListener
                (new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        actionperformed_escollirTaulell();
                    }
                });
    }

    //Pre: els components no son nulls
    //Post: inicialitza el frame i el panel i assigna els listeners
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
        contentPane.add(panel1);
    }

    //Pre: panel1 no es null
    //Post: fa visible el panel
    //descripcio: inicialitza el panel
    private void inicializar_panel1() {
        panel1.setVisible(true);
    }

    //Pre: -
    //Post: posa a frameVista el frame indicat
    //descripcio: setter de frameVista
    public void setFrame(JFrame frame) {
        frameVista = frame;
    }

    //Pre: frameVista no es null
    //Post: retorna el frameVista
    //descripcio: getter de frameVista
    public JFrame getFrame() {
        return frameVista;
    }
}
