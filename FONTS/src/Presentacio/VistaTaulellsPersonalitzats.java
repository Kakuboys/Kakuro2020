package Presentacio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class VistaTaulellsPersonalitzats {

    private JButton enrereButton;
    private JButton veureIEliminarTaulellsButton;
    private JButton crearTaulellPersonalitzatButton;
    private JPanel panel1;
    private JLabel taulellsPersonalitzats;
    private JButton proposarTaulellButton;

    //els que posem nosaltres:
    private CtrlPresentacio ctrl_presentacio;
    private JFrame frameVista;

    //Pre: -
    //Post: Construeix una VistaTaulellsPersonalitzats, la associa al CtrlPresentacio, coloca el frameVista, i inicialitza els components
    //descripcio: constructora de VistaTaulellsPersonalitzats
    public VistaTaulellsPersonalitzats(CtrlPresentacio ctrl_presentacio, JFrame frame) {
        this.ctrl_presentacio = ctrl_presentacio;
        this.frameVista = frame;
        inicializarComponentes();
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

    //Pre: ctrl_presentacio no es null
    //Post: canvia a PantallaPrincipal
    //descripcio: actionPerformed del boto Enrere
    private void actionPerformed_enrere(ActionEvent e) {
        ctrl_presentacio.sincronizacionVistaTP_a_VistaPrincipal();
    }

    //Pre: ctrl_presentacio no es null
    //Post: canvia a VistaGenerarKakuroAuto
    //descripcio: actionPerformed del boto Crear
    private void actionPerformed_Crear(ActionEvent e) {
        ctrl_presentacio.sincronizacionVistaTP_a_VistaGKA();
    }

    //Pre: ctrl_presentacio no es null
    //Post: Obre un JFileChooser per a visualitzar els taulells guardats. Si es selecciona un s'elimina
    //descripcio: actionPerformed del boto veureIEliminarTaulellsButton
    private void actionPerformed_veureTP(ActionEvent e) {
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("./Dades/Taulells"));
        fc.setDialogTitle("Taulells personalitzats");

        int result = fc.showDialog(null, "Esborrar Taulell");


        if (result == JFileChooser.APPROVE_OPTION) {
            if (0 == JOptionPane.showConfirmDialog(null, "Segur que voleu eliminar aquest taulell?", "Eliminar taulell", JOptionPane.YES_NO_OPTION)) {
                ctrl_presentacio.deleteTaulell(fc.getSelectedFile());
            }
        }
    }

    //Pre: els components no son nulls
    //Post: assigna els listeners a enrereButton, crearTaulellPersonalitzatButton, veureIEliminarTaulellsButton i proposarTaulellButton
    //descripcio: assigna els listeners als components
    private void asignar_listeners() {
        enrereButton.addActionListener
                (new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        actionPerformed_enrere(e);
                    }
                });
        crearTaulellPersonalitzatButton.addActionListener
                (new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        actionPerformed_Crear(e);
                    }
                });
        veureIEliminarTaulellsButton.addActionListener
                (new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        actionPerformed_veureTP(e);
                    }
                });
        proposarTaulellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionPerformed_proposarTaulellButton(e);
            }
        });

    }

    //Pre: ctrl_presentacio no es null
    //Post: canvia a VistaProposarKakuro
    //descripcio: actionPerformed del boto proposarTaulell
    private void actionPerformed_proposarTaulellButton(ActionEvent e) {
        ctrl_presentacio.sincronizacionVistaTP_a_VistaProposarKakuro();
    }

    //Pre: els components no son nulls
    //Post: inicialitza el frame, el panel i els botons i assigna els listeners
    //descripcio: inicialitza els components de la vista
    private void inicializarComponentes() {
        inicializar_frameVista();
        inicializar_panel1();
        ini_elements();
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

    //Pre: els botons no son nulls
    //Post: dona les dimensions als botons
    //descripcio: inicialitza els botons
    private void ini_elements() {
        veureIEliminarTaulellsButton.setMinimumSize(new Dimension(280, 50));
        veureIEliminarTaulellsButton.setPreferredSize(veureIEliminarTaulellsButton.getMinimumSize());
        crearTaulellPersonalitzatButton.setMinimumSize(new Dimension(280, 50));
        crearTaulellPersonalitzatButton.setPreferredSize(crearTaulellPersonalitzatButton.getMinimumSize());
        proposarTaulellButton.setMinimumSize(new Dimension(280, 50));
        proposarTaulellButton.setPreferredSize(proposarTaulellButton.getMinimumSize());
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
