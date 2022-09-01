package Presentacio;

import Persistencia.saveFailureException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class VistaProposarKakuro {
    private JTextField textField1;
    private JPanel panel1;
    private JButton proposaButton;
    private JButton cancelLaButton;

    private CtrlPresentacio ctrl_presentacio;
    private JFrame frameVista;

    //Pre: -
    //Post: Construeix una VistaProposarKakuroAuto, la associa al CtrlPresentacio, coloca el frameVista, i inicialitza els components
    //descripcio: constructora de VistaproposarKakuroAuto
    public VistaProposarKakuro(CtrlPresentacio ctrl_presentacio, JFrame frameVista) {
        this.ctrl_presentacio = ctrl_presentacio;
        this.frameVista = frameVista;
        inicializarComponentes();
    }

    //Pre: ctrl_presentacio no es null
    //Post: canvia a VistaTaulellsPersonalitzats
    //descripcio: actionPerformed del boto Cancella
    public void actionPerformed_cancelaButton(ActionEvent event) {
        ctrl_presentacio.sincronizacionVistaProposarKakuro_a_VistaTP();
    }

    //Pre: els components no son nulls
    //Post: activa el frame, i inicialitza el panel.
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
    //Post: assigna els listeners a cancelaButton i proposaButton
    //descripcio: assigna els listeners als components
    private void asignar_listeners() {
        proposaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    actionPerformed_proposaButton(e);
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
                actionPerformed_cancelaButton(e);
            }
        });

    }

    //Pre: ctrl_presentacio i textField1 no son nulls
    //Post: si existeix un fitxer amb el nom indicat pel textField1:
        // Si el taulell proposat te una unica solucio es guarda, si no es rebutja.
        // si no existia, s'indica.
    //Descripció: Proposta de taulell
    private void actionPerformed_proposaButton(ActionEvent e) throws IOException, saveFailureException {
            String nomTaulell=textField1.getText();
            switch (ctrl_presentacio.proposaTaulell(nomTaulell)) {
                case 2:
                    JOptionPane.showConfirmDialog(null, "El taulell proposat té una única solució i s'ha guardat correctament", "Guardar", JOptionPane.DEFAULT_OPTION);
                break;
                case 1:
                    JOptionPane.showConfirmDialog(null, "El taulell proposat no té solució o en té més d'una", "Rebutjat", JOptionPane.DEFAULT_OPTION);
                break;
                case 0:
                    JOptionPane.showConfirmDialog(null, "No s'ha trobat el fitxer amb el nom "+nomTaulell, "Fitxer no trobat", JOptionPane.DEFAULT_OPTION);
            }
    }

    //Pre: els components no son nulls
    //Post: inicialitza el frame, el panel i els comboboxes i assigna els listeners
    //descripcio: inicialitza els components de la vista
    private void inicializarComponentes() {
        inicializar_frameVista();
        inicializar_panel1();
        asignar_listeners();
    }

    //Pre: els components no son nulls
    //Post: activa el frame, i li insereix el panel
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


