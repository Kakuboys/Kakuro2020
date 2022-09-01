package Presentacio;

import Domini.Taulell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class VistaPartidaNova {
    private JButton escollirTaulellButton;
    private JPanel panel1;
    private JButton comencarPartidaButton;
    private JButton enrereButton;
    private JRadioButton bebeRadioButton;
    private JRadioButton contrarellotgeRadioButton;
    private JRadioButton classicRadioButton;
    private JButton InfoModesJoc;

    //els que posem nosaltres:
    private CtrlPresentacio ctrl_presentacio;
    private JFrame frameVista;

    //Pre: -
    //Post: Construeix una VistaPartidaNova, la associa al CtrlPresentacio, coloca el frameVista, i inicialitza els components
    //descripcio: constructora de VistaPartidaNova
    public VistaPartidaNova(CtrlPresentacio ctrl_presentacio, JFrame frame) {
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

    //Pre: ctrl_presentacio i comencarPartidaButton no son null
    //Post: canvia a VistaJugar, i desactiva el boto comencarPartidaButton
    //descripcio: actionPerformed del boto Enrere
    public void actionPerformed_enrereButton(ActionEvent event) {
        comencarPartidaButton.setEnabled(false);
        ctrl_presentacio.sincronizacionVistaPartidaNova_a_VistaJugar();
    }

    //Pre: ctrl_presentacio i comencarPartidaButton no son null
    //Post: obre un JFileChooser per a escollir un taulell i el carrega
    //descripcio: actionPerformed del boto escollirTaulellButton. permet escollir un taulell per a jugar
    private void actionPerformed_escollirTaulellButton(ActionEvent e) throws IOException, ClassNotFoundException {
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("./Dades/Taulells"));
        fc.setDialogTitle("Escollir taulell per a carregar");

        int result = fc.showDialog(null, "Obrir");  //aixo fa que s'obri la ventanita i et deixa posar el text de la opció "OK". result diu què ha passat.

        File fitxer;

        if (result == JFileChooser.APPROVE_OPTION) {
            comencarPartidaButton.setEnabled(true); //activem el botó começarPartida

            fitxer = fc.getSelectedFile();

            ctrl_presentacio.loadTaulellAMemoria(fitxer.getPath());
        }
    }

    //Pre: ctrl_presentacio i comencarPartidaButton no son null
    //Post: desactiva el boto comencarPartidaButton i canvia a VistaTaulellKakuro, passant el valor del mode de joc escollit.
    //descripcio: actionPerformed del boto comencarPartida
    private void actionPerformed_comencarPartida(ActionEvent e) {
        comencarPartidaButton.setEnabled(false);
        int mode=-1;
        if (classicRadioButton.isSelected())
            mode = 0;
        else if (bebeRadioButton.isSelected())
            mode = 1;
        else if (contrarellotgeRadioButton.isSelected())
            mode = 2;

        ctrl_presentacio.sincronizacionVistaPartidaNova_a_VistaTaulell(mode);
    }

    //Pre: ctrl_presentacio no es null
    //Post: mostra un missatge amb la informació dels modes de joc
    //Descripcion: actionPerformed del boto infoModesJoc
    private void actionPerformed_infoModesJoc() throws IOException {
        String infoMJ =ctrl_presentacio.getInfoModesJoc();
        JOptionPane.showConfirmDialog(null, infoMJ, "Modes de joc", JOptionPane.DEFAULT_OPTION);
    }

    //Pre: els components no son nulls
    //Post: assigna els listeners als botons
    //descripcio: assigna els listeners als components
    private void asignar_listeners() {
        enrereButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionPerformed_enrereButton(e);
            }
        });
        escollirTaulellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    actionPerformed_escollirTaulellButton(e);
                } catch (IOException | ClassNotFoundException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        comencarPartidaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionPerformed_comencarPartida(e);
            }
        });
        InfoModesJoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    actionPerformed_infoModesJoc();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
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
        escollirTaulellButton.setMinimumSize(new Dimension(250, 50));
        escollirTaulellButton.setPreferredSize(escollirTaulellButton.getMinimumSize());
        comencarPartidaButton.setMinimumSize(new Dimension(250, 50));
        comencarPartidaButton.setPreferredSize(comencarPartidaButton.getMinimumSize());
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
