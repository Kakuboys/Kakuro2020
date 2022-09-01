package Presentacio;

import CapaOrtogonal.Casella;
import CapaOrtogonal.CasellaBlanca;
import CapaOrtogonal.CasellaNegra;
import Domini.AlgorismeSolucionarKakuro;
import Domini.CtrlDomini;
import Domini.Pair;
import Persistencia.saveFailureException;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.renderable.ContextualRenderedImageFactory;
import java.io.IOException;

public class VistaGenerarKakuroAuto {
    private JPanel panel1;
    private JComboBox comboBoxFiles;
    private JComboBox comboBoxColumnes;
    private JComboBox comboBoxCDescobertes;
    private JButton enrereButton;
    private JButton generarButton;
    private JTextField nomTaulell;
    private JPanel PanelTaulellGenerat;
    private JPanel PanelTaulellResolt;
    private JLabel label7;

    //els que posem nosaltres:
    private CtrlPresentacio ctrl_presentacio;
    private JFrame frameVista;
    private Casella[][] taulellGenerat;
    private Casella[][] taulellResolt;
    private Casella[][] taulellDescobert;

    //Pre: -
    //Post: Construeix una VistaGenerarKakuroAuto, la associa al CtrlPresentacio, coloca el frameVista, i inicialitza els components
    //descripcio: constructora de VistaGenerarKakuroAuto
    public VistaGenerarKakuroAuto(CtrlPresentacio ctrl_presentacio, JFrame frameVista) {
        this.ctrl_presentacio = ctrl_presentacio;
        this.frameVista = frameVista;
        inicializarComponentes();
    }

    //Pre: ctrl_presentacio no es null
    //Post: canvia a VistaTaulellsPersonalitzats
    //descripcio: actionPerformed del boto Enrere
    public void actionPerformed_enrereButton(ActionEvent event) {
        ctrl_presentacio.sincronizacionVistaGenerarKakuro_a_VistaTP();
    }

    //Pre: ctrl_presentacio i els components no son nulls
    //Post: Si el nom introduit al JTextField nomTaulell és buit, ho indica amb un missatge.
        //sino, genera un taulell amb els parametres indicats i el mostra a ell i la seva versio resolta per pantalla.
    //descripcio: actionPerformed del boto Generar
    private void actionPerformed_generar(ActionEvent e) throws IOException, saveFailureException {
        String nom = nomTaulell.getText();
        if (!nom.equals("")) {

            Pair<Casella[][], Casella[][]> p = ctrl_presentacio.generarKakuroAuto((int)comboBoxFiles.getSelectedItem(),
                    (int)comboBoxColumnes.getSelectedItem(), (int)comboBoxCDescobertes.getSelectedItem(), nom);
            taulellResolt = p.first;
            taulellDescobert = p.second;

            PanelTaulellGenerat.removeAll();
            PanelTaulellResolt.removeAll();

            //mostrar els dos taulells
            showTaulell(false);
            showTaulell(true);

            //fa que es repinti la pantalla somehow.
            comboBoxFiles.addItem(-1);
            comboBoxFiles.removeItem(-1);
        }
        else {
            JOptionPane.showConfirmDialog(null, "Introduïu un nom per al taulell", "Nom del taulell buit", JOptionPane.DEFAULT_OPTION);
        }
    }

    //Pre: els components no son nulls
    //Post: fica al combobox comboBoxCDescobertes els valors adients segons l el nobre de fines i de columnes introduits
    //descripcio: actionPerformed dels comboboxes comboBoxFiles i comboBoxColumnes
    private void actionPerformed_FC(ActionEvent e) {

        comboBoxCDescobertes.removeAllItems();

        int max = ((int)comboBoxFiles.getSelectedItem() * (int)comboBoxColumnes.getSelectedItem()) / 4;

        for (int i = 0; i <= max; i++) {
            comboBoxCDescobertes.addItem(i);
        }
    }

    //Pre: els components no son nulls
    //Post: activa el frame i el redimensiona, i inicialitza el panel.
    //descripcio: activa la vista
    public void activar() {
        frameVista.setEnabled(true);
        frameVista.setSize(new Dimension(1010, 800));
        inicializar_panel1();
    }

    //Pre: els components no son nulls
    //Post: desactiva el frame i el redimensiona. fa el panel invisible.
    //descripcio: desactiva la vista
    public void desactivar() {
        frameVista.setSize(new Dimension(800, 450));
        frameVista.setEnabled(false);
        panel1.setVisible(false);
    }

    //Pre: els components no son nulls
    //Post: assigna els listeners a enrereButton, generarButton, comboBoxFiles i comboBoxColumnes
    //descripcio: assigna els listeners als components
    private void asignar_listeners() {
        enrereButton.addActionListener
                (new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        actionPerformed_enrereButton(e);
                    }
                });

        generarButton.addActionListener
                (new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            actionPerformed_generar(e);
                        } catch (IOException | saveFailureException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                });
        comboBoxFiles.addActionListener
                (new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        actionPerformed_FC(e);
                    }
                });
        comboBoxColumnes.addActionListener
                (new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        actionPerformed_FC(e);
                    }
                });
    }

    //Pre: els components no son nulls
    //Post: si el_resolt es true, mostra el taulellResolt al seu panel en la pantalla
        //sino, mostra el taulellDescobert al seu panel en la pantalla
    //descripcio: mostra el taulell indicat a la pantalla
    private void showTaulell(boolean el_resolt) {
        JPanel panel2;
        Casella[][] taulell2;
        if (el_resolt) {
            panel2 = PanelTaulellResolt;
            taulell2 = taulellResolt;
        }
        else {
            panel2 = PanelTaulellGenerat;
            taulell2 = taulellDescobert;
        }
        panel2.setLayout(new GridLayout(taulell2.length,taulell2[0].length));

        for (int i=0; i<taulell2.length;i++){
            for(int j=0; j<taulell2[0].length; j++) {
                JPanel panelCasella;
                if (taulell2[i][j] instanceof CasellaNegra) {
                    panelCasella= new PanelNegras(i,j,new BorderLayout());
                    panelCasella.setBackground(Color.black);
                    JPanel panelAdalt=new JPanel(new BorderLayout());
                    JPanel panelAbaix=new JPanel(new BorderLayout());
                    panelCasella.add(panelAdalt,BorderLayout.NORTH);
                    panelCasella.add(panelAbaix,BorderLayout.SOUTH);

                    int suma_fila = ((CasellaNegra)taulell2[i][j]).getSumaFila();
                    if (suma_fila > 0) {
                        JTextArea jta = new JTextArea(String.valueOf(suma_fila));
                        jta.setBackground(Color.BLACK);
                        jta.setForeground(Color.white);
                        panelAdalt.add(jta, BorderLayout.EAST);

                    }

                    int suma_columna = ((CasellaNegra)taulell2[i][j]).getSumaColumna();
                    if (suma_columna > 0) {
                        JTextArea jta = new JTextArea(String.valueOf(suma_columna));
                        jta.setBackground(Color.BLACK);
                        jta.setForeground(Color.white);
                        panelAbaix.add(jta, BorderLayout.WEST);
                    }
                    panelAbaix.setBackground(Color.black);
                    panelAdalt.setBackground(Color.black);
                }
                else {
                    panelCasella = new JPanel(new BorderLayout());
                    panelCasella.setBackground(Color.white);

                    if(el_resolt || ((CasellaBlanca) taulellDescobert[i][j]).getNum() != 0) {
                        JLabel label4 = new JLabel(String.valueOf(((CasellaBlanca)taulellResolt[i][j]).getNum()));
                        label4.setHorizontalAlignment(SwingConstants.CENTER);
                        panelCasella.add(label4, BorderLayout.CENTER);
                    }
                }
                Border borde;
                borde = BorderFactory.createLineBorder(Color.black);
                panelCasella.setBorder(borde);
                panel2.add(panelCasella);
            }
        }
    }

    //Pre: els components no son nulls
    //Post: inicialitza el frame, el panel i els comboboxes i assigna els listeners
    //descripcio: inicialitza els components de la vista
    private void inicializarComponentes() {
        inicializar_frameVista();
        inicializar_panel1();
        ini_elements();
        asignar_listeners();
    }

    //Pre: els components no son nulls
    //Post: activa el frame i el redimensiona, i li insereix el panel
    //descripcio: inicialitza el frame
    private void inicializar_frameVista() {
        frameVista.setEnabled(true);

        JPanel contentPane = (JPanel)frameVista.getContentPane();
        contentPane.add(panel1);

        frameVista.setSize(new Dimension(1010, 800));
    }

    //Pre: panel1 no es null
    //Post: fa visible el panel
    //descripcio: inicialitza el panel
    private void inicializar_panel1() {
        panel1.setVisible(true);
    }

    //Pre: els comboboxes no son nulls
    //Post: dona els valors inicials als comboboxes
    //descripcio: inicialitza els comboboxes
    private void ini_elements() {
        for (int i = 3; i <= 10; i++) {
            comboBoxFiles.addItem(i);
            comboBoxColumnes.addItem(i);
        }

        for (int i = 0; i <= 2 ; i++) {
            comboBoxCDescobertes.addItem(i);
        }
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
