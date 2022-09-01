package Presentacio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class VistaJugar {
    private JPanel panel1;
    private JButton enrereButton;
    private JButton partidaNova;
    private JButton carregarPartida;
    private JLabel Jugar;
    private JButton eliminarUnaPartidaGuardadaButton;

    //els que posem nosaltres:
    private CtrlPresentacio ctrl_presentacio;
    private JFrame frameVista;

    //Pre: -
    //Post: Construeix una VistaJugar, la associa al CtrlPresentacio, coloca el frameVista, i inicialitza els components
    //Descripcio: constructora de VistaJugar
    public VistaJugar(CtrlPresentacio ctrl_presentacio, JFrame frame) {
        this.ctrl_presentacio = ctrl_presentacio;
        setFrame(frame);
        inicializarComponentes();
    }

    //Pre: els components no son nulls
    //Post: activa el frame i inicialitza el panel.
    //descripcio: activa la vista
    public void activar() {     //es crida en els cavis de vista.
        frameVista.setEnabled(true);
        inicializar_panel1();
    }

    //Pre: els components no son nulls
    //Post: desactiva el frame. fa el panel invisible.
    //Descripcio: desactiva la vista
    public void desactivar() {
        frameVista.setEnabled(false);
        panel1.setVisible(false);
    }

    //Pre: ctrl_presentacio no es null
    //Post: canvia a PantallaPrincipal
    //Descripcio: actionPerformed del boto Enrere
    public void actionPerformed_enrereButton(ActionEvent event) {
        ctrl_presentacio.sincronizacionVistaJugar_a_VistaPrincipal();
    }

    //Pre: ctrl_presentacio no es null
    //Post: canvia a VistaPartidaNova
    //Descripcio: actionPerformed del boto partidaNova
    public void actionPerformed_partidaNova(ActionEvent event) {
        ctrl_presentacio.sincronizacionVistaJugar_a_VistaPartidaNova();
    }

    //Pre: ctrl_presentacio no es null
    //Post: si hi ha un usuari autenticat, obre un JFileChooser per a escollir una partida i la carrega, canviant a VistaTaulellKakuro.
        //sino, ho indica amb un missatge.
    //Descripcio: actionPerformed del boto carregarPartida. permet escollir una partida a carregar
    private void actionPerformed_carregarPartida(ActionEvent e) throws IOException, ClassNotFoundException {

        if (ctrl_presentacio.getIsLoggedIn()) {
            String nom_usuari = ctrl_presentacio.getUsername();

            JFileChooser fc = new JFileChooser();
            fc.setCurrentDirectory(new File("./Dades/Partides/" + nom_usuari));
            fc.setDialogTitle("Escollir partida per a carregar");

            int result = fc.showDialog(null, "Obrir");  //aixo fa que s'obri la ventanita i et deixa posar el text de la opció "OK". result diu què ha passat.

            if (result == JFileChooser.APPROVE_OPTION) {
                File fitxer = fc.getSelectedFile();
                ctrl_presentacio.sincronizacionVistaJugar_a_VistaTK(fitxer);
            }
        }
        else {
            JOptionPane.showConfirmDialog(null, "No podeu carregar partides si no esteu autenticats", "No hi ha una sessió iniciada", JOptionPane.DEFAULT_OPTION);
        }
    }

    //Pre: ctrl_presentacio no es null
    //Post: si hi ha un usuari autenticat, obre un JFileChooser per a escollir una partida i la elimina.
        //sino, ho indica amb un missatge.
    //Descripcio: actionPerformed del boto eliminarUnaPartidaGuardada. permet escollir una partida a eliminar
    private void actionPerformed_eliminarUnaPartidaGuardada(ActionEvent e) {
        if (ctrl_presentacio.getIsLoggedIn()) {
            String nom_usuari = ctrl_presentacio.getUsername();
            JFileChooser fc = new JFileChooser();
            fc.setCurrentDirectory(new File("./Dades/Partides/" + nom_usuari));
            fc.setDialogTitle("Escull una partida per a eliminar");

            int result = fc.showDialog(null, "Eliminar");

            if (result == JFileChooser.APPROVE_OPTION) {
                ctrl_presentacio.deletePartida(fc.getSelectedFile());
            }
        }
        else {
            JOptionPane.showConfirmDialog(null, "No teniu partides si no esteu autenticats", "No hi ha una sessió iniciada", JOptionPane.DEFAULT_OPTION);
        }
    }

    //Pre: els components no son nulls
    //Post: assigna els listeners a enrereButton, partidaNova, carregarPartida i eliminarUnaPartidaGuardadaButton
    //descripcio: assigna els listeners als components
    private void asignar_listeners() {
        enrereButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionPerformed_enrereButton(e);
            }
        });
        partidaNova.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionPerformed_partidaNova(e);
            }
        });
        carregarPartida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    actionPerformed_carregarPartida(e);
                } catch (IOException | ClassNotFoundException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        eliminarUnaPartidaGuardadaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionPerformed_eliminarUnaPartidaGuardada(e);
            }
        });
    }

    //Pre: els components no son nulls
    //Post: inicialitza el frame, el panel i els botons i assigna els listeners
    //descripcio: inicialitza els components de la vista
    private void inicializarComponentes() {
        inicializar_frameVista();
        inicializar_panel1();
        ini_botons();
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
    private void ini_botons() {
        partidaNova.setMinimumSize(new Dimension(250, 50));
        partidaNova.setPreferredSize(partidaNova.getMinimumSize());
        carregarPartida.setMinimumSize(new Dimension(250, 50));
        carregarPartida.setPreferredSize(carregarPartida.getMinimumSize());
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
