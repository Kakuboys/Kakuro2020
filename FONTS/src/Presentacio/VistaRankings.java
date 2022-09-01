package Presentacio;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class VistaRankings {
    private JButton enrereButton;
    private JPanel panel1;
    private JLabel texto;
    private JButton escollirTaulellButton;

    //els que posem nosaltres:
    private CtrlPresentacio ctrl_presentacio;
    private JFrame frameVista;

    //Pre: -
    //Post: Construeix una VistaRankings, la associa al CtrlPresentacio, coloca el frameVista, i inicialitza els components
    //descripcio: constructora de VistaRankings
    public VistaRankings(CtrlPresentacio ctrl_presentacio, JFrame frameVista) {
        this.ctrl_presentacio = ctrl_presentacio;
        this.frameVista = frameVista;
        inicializarComponentes();
    }

    //Pre: ctrl_presentacio no es null
    //Post: canvia a PantallaPrincipal
    //descripcio: actionPerformed del boto Enrere
    public void actionPerformed_enrereButton(ActionEvent event) {
        ctrl_presentacio.sincronizacionVistaRankings_a_VistaPrincipal();
    }

    //Pre: ctrl_presentacio i texto no son null
    //Post: obre un JFileChooser per a escollir un taulell sobre el que veure el ranking.
    //descripcio: actionPerformed del boto escollirTaulell. permet escollir un taulell sobre el que veure el ranking
    private void actionperformed_escollirTaulell() throws IOException, ClassNotFoundException {
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("./Dades/Taulells"));
        fc.setDialogTitle("Escollir taulell");
        int result = fc.showDialog(null, "Escollir");
        if (result == JFileChooser.APPROVE_OPTION) {
            String path = fc.getSelectedFile().getPath();
            String printeo = ctrl_presentacio.getRanking(path);
            texto.setText("");
            texto.setText(printeo);
        }
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
                        try {
                            actionperformed_escollirTaulell();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        } catch (ClassNotFoundException classNotFoundException) {
                            classNotFoundException.printStackTrace();
                        }
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
