package Presentacio;

import Persistencia.GrammaticException;
import Persistencia.saveFailureException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class VistaSignUp {
    private JPanel panel1;
    private JButton okButton;
    private JButton cancelLaButton;
    private JTextField textField1;
    private JPasswordField passwordField1;

    private CtrlPresentacio ctrl_presentacio;
    private JFrame frameVista;


    //Pre: -
    //Post: Construeix una VistaSignUp, la associa al CtrlPresentacio, coloca el frameVista, i inicialitza els components
    //descripcio: constructora de VistaSignUp
    public VistaSignUp(CtrlPresentacio ctrl_presentacio, JFrame frame) {
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
    //descripcio: desactiva la vista
    public void desactivar() {
        frameVista.setEnabled(false);
        panel1.setVisible(false);
    }

    //Pre: ctrl_presentacio, textField1 i passwordField1 no son nulls
    //Post: Realitza el sign-up cridant a CtrlDomini, buida els textFields i canvia a PantallaPrincipal
    //descripcio: actionPerformed del boto OK
    public void actionPerformed_okButton(ActionEvent event) throws ClassNotFoundException, IOException, saveFailureException, GrammaticException {
        ctrl_presentacio.signUp(textField1.getText(),String.valueOf(passwordField1.getPassword()));
        textField1.setText("");
        passwordField1.setText("");
        ctrl_presentacio.sincronizacionVistaSignUp_a_VistaPrincipal(true);
    }

    //Pre: ctrl_presentacio, textField1 i passwordField1 no son nulls
    //Post: cancella el Sign-up, buida els textFields i canvia a PantallaPrincipal
    //descripcio: actionPerformed del boto Cancella
    public void actionPerformed_cancelLaButton(ActionEvent event) {
        textField1.setText("");
        passwordField1.setText("");
        ctrl_presentacio.sincronizacionVistaSignUp_a_VistaPrincipal(false);
    }

    //Pre: els components no son nulls
    //Post: assigna els listeners als botons
    //descripcio: assigna els listeners als components
    private void asignar_listeners() {
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    actionPerformed_okButton(e);
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (Persistencia.saveFailureException saveFailureException) {
                    JOptionPane.showConfirmDialog(null,"Usuari ja existeix", "Error de registre", JOptionPane.DEFAULT_OPTION);
                }
                  catch (Persistencia.GrammaticException eg) {
                    JOptionPane.showConfirmDialog(null,"Username/Contrasenya buides", "Error de registre", JOptionPane.DEFAULT_OPTION);
                  }
            }
        });
        cancelLaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionPerformed_cancelLaButton(e);
            }
        });
    }

    //Pre: panel1 no es null
    //Post: fa visible el panel
    //descripcio: inicialitza el panel
    private void inicializar_panel1() {
        panel1.setVisible(true);
    }

    //Pre: els components no son nulls
    //Post: activa el frame i li insereix el panel
    //descripcio: inicialitza el frame
    private void inicializar_frameVista() {
        frameVista.setEnabled(true);

        JPanel contentPane = (JPanel)frameVista.getContentPane();
        contentPane.add(panel1);
    }

    //Pre: els components no son nulls
    //Post: inicialitza el frame i el panel i assigna els listeners
    //descripcio: inicialitza els components de la vista
    private void inicializarComponentes() {
        inicializar_frameVista();
        inicializar_panel1();
        asignar_listeners();
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
