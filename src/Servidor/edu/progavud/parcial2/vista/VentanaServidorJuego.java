package Servidor.edu.progavud.parcial2.vista;

import Servidor.edu.progavud.parcial2.control.FachadaServidor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author a
 */
public class VentanaServidorJuego extends javax.swing.JFrame {

    /**
     * Creates new form VentanaServidorJuego
     */
    private FachadaServidor fachadaS;

    public VentanaServidorJuego(FachadaServidor fachadaS) {
        this.fachadaS = fachadaS;
        initComponents();
        this.botones = new JButton[]{
            vServJuegoBtn1, vServJuegoBtn2, vServJuegoBtn3, vServJuegoBtn4, vServJuegoBtn5,
            vServJuegoBtn6, vServJuegoBtn7, vServJuegoBtn8, vServJuegoBtn9, vServJuegoBtn10,
            vServJuegoBtn11, vServJuegoBtn12, vServJuegoBtn13, vServJuegoBtn14, vServJuegoBtn15,
            vServJuegoBtn16, vServJuegoBtn17, vServJuegoBtn18, vServJuegoBtn19, vServJuegoBtn20,
            vServJuegoBtn21, vServJuegoBtn22, vServJuegoBtn23, vServJuegoBtn24, vServJuegoBtn25,
            vServJuegoBtn26, vServJuegoBtn27, vServJuegoBtn28, vServJuegoBtn29, vServJuegoBtn30,
            vServJuegoBtn31, vServJuegoBtn32, vServJuegoBtn33, vServJuegoBtn34, vServJuegoBtn35,
            vServJuegoBtn36, vServJuegoBtn37, vServJuegoBtn38, vServJuegoBtn39, vServJuegoBtn40
        };

        this.labels = new JLabel[]{
            vServJuegoLbl1, vServJuegoLbl2, vServJuegoLbl3, vServJuegoLbl4, vServJuegoLbl5,
            vServJuegoLbl6, vServJuegoLbl7, vServJuegoLbl8, vServJuegoLbl9, vServJuegoLbl10,
            vServJuegoLbl11, vServJuegoLbl12, vServJuegoLbl13, vServJuegoLbl14, vServJuegoLbl15,
            vServJuegoLbl16, vServJuegoLbl17, vServJuegoLbl18, vServJuegoLbl19, vServJuegoLbl20,
            vServJuegoLbl21, vServJuegoLbl22, vServJuegoLbl23, vServJuegoLbl24, vServJuegoLbl25,
            vServJuegoLbl26, vServJuegoLbl27, vServJuegoLbl28, vServJuegoLbl29, vServJuegoLbl30,
            vServJuegoLbl31, vServJuegoLbl32, vServJuegoLbl33, vServJuegoLbl34, vServJuegoLbl35,
            vServJuegoLbl36, vServJuegoLbl37, vServJuegoLbl38, vServJuegoLbl39, vServJuegoLbl40
        };
    }

    public JButton[] getBotones() {
        return botones;
    }

    public void setBotones(JButton[] botones) {
        this.botones = botones;
    }

    public JLabel[] getLabels() {
        return labels;
    }

    public void setLabels(JLabel[] labels) {
        this.labels = labels;
    }

    public void agregarFoto(int numero, int otroNumero) {
        // 3) Cargamos la imagen desde la ruta proporcionada
        ImageIcon icono = new ImageIcon("src/Servidor/edu/progavud/parcial2/img/" + (numero + 1) + ".jpg");
        this.labels[otroNumero].setIcon(icono);
    }

    public void actualizarTurno(String nombre) {
        this.vServJuegoLblTurno.setText("Turno De: " + nombre);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        vServJuegoBtn1 = new javax.swing.JButton();
        vServJuegoLbl1 = new javax.swing.JLabel();
        vServJuegoBtn2 = new javax.swing.JButton();
        vServJuegoLbl2 = new javax.swing.JLabel();
        vServJuegoBtn3 = new javax.swing.JButton();
        vServJuegoLbl3 = new javax.swing.JLabel();
        vServJuegoBtn4 = new javax.swing.JButton();
        vServJuegoLbl4 = new javax.swing.JLabel();
        vServJuegoBtn5 = new javax.swing.JButton();
        vServJuegoLbl5 = new javax.swing.JLabel();
        vServJuegoBtn6 = new javax.swing.JButton();
        vServJuegoLbl6 = new javax.swing.JLabel();
        vServJuegoBtn7 = new javax.swing.JButton();
        vServJuegoLbl7 = new javax.swing.JLabel();
        vServJuegoBtn8 = new javax.swing.JButton();
        vServJuegoLbl8 = new javax.swing.JLabel();
        vServJuegoBtn9 = new javax.swing.JButton();
        vServJuegoLbl9 = new javax.swing.JLabel();
        vServJuegoBtn10 = new javax.swing.JButton();
        vServJuegoLbl10 = new javax.swing.JLabel();
        vServJuegoBtn11 = new javax.swing.JButton();
        vServJuegoLbl11 = new javax.swing.JLabel();
        vServJuegoBtn12 = new javax.swing.JButton();
        vServJuegoLbl12 = new javax.swing.JLabel();
        vServJuegoBtn13 = new javax.swing.JButton();
        vServJuegoLbl13 = new javax.swing.JLabel();
        vServJuegoBtn14 = new javax.swing.JButton();
        vServJuegoLbl14 = new javax.swing.JLabel();
        vServJuegoBtn15 = new javax.swing.JButton();
        vServJuegoLbl15 = new javax.swing.JLabel();
        vServJuegoBtn16 = new javax.swing.JButton();
        vServJuegoLbl16 = new javax.swing.JLabel();
        vServJuegoBtn17 = new javax.swing.JButton();
        vServJuegoLbl17 = new javax.swing.JLabel();
        vServJuegoBtn18 = new javax.swing.JButton();
        vServJuegoLbl18 = new javax.swing.JLabel();
        vServJuegoBtn19 = new javax.swing.JButton();
        vServJuegoLbl19 = new javax.swing.JLabel();
        vServJuegoBtn20 = new javax.swing.JButton();
        vServJuegoLbl20 = new javax.swing.JLabel();
        vServJuegoBtn21 = new javax.swing.JButton();
        vServJuegoLbl21 = new javax.swing.JLabel();
        vServJuegoBtn22 = new javax.swing.JButton();
        vServJuegoLbl22 = new javax.swing.JLabel();
        vServJuegoBtn23 = new javax.swing.JButton();
        vServJuegoLbl23 = new javax.swing.JLabel();
        vServJuegoBtn24 = new javax.swing.JButton();
        vServJuegoLbl24 = new javax.swing.JLabel();
        vServJuegoBtn25 = new javax.swing.JButton();
        vServJuegoLbl25 = new javax.swing.JLabel();
        vServJuegoBtn26 = new javax.swing.JButton();
        vServJuegoLbl26 = new javax.swing.JLabel();
        vServJuegoBtn27 = new javax.swing.JButton();
        vServJuegoLbl27 = new javax.swing.JLabel();
        vServJuegoBtn28 = new javax.swing.JButton();
        vServJuegoLbl28 = new javax.swing.JLabel();
        vServJuegoBtn29 = new javax.swing.JButton();
        vServJuegoLbl29 = new javax.swing.JLabel();
        vServJuegoBtn30 = new javax.swing.JButton();
        vServJuegoLbl30 = new javax.swing.JLabel();
        vServJuegoBtn31 = new javax.swing.JButton();
        vServJuegoLbl31 = new javax.swing.JLabel();
        vServJuegoBtn32 = new javax.swing.JButton();
        vServJuegoLbl32 = new javax.swing.JLabel();
        vServJuegoBtn33 = new javax.swing.JButton();
        vServJuegoLbl33 = new javax.swing.JLabel();
        vServJuegoBtn34 = new javax.swing.JButton();
        vServJuegoLbl34 = new javax.swing.JLabel();
        vServJuegoBtn35 = new javax.swing.JButton();
        vServJuegoLbl35 = new javax.swing.JLabel();
        vServJuegoBtn36 = new javax.swing.JButton();
        vServJuegoLbl36 = new javax.swing.JLabel();
        vServJuegoBtn37 = new javax.swing.JButton();
        vServJuegoLbl37 = new javax.swing.JLabel();
        vServJuegoBtn38 = new javax.swing.JButton();
        vServJuegoLbl38 = new javax.swing.JLabel();
        vServJuegoBtn39 = new javax.swing.JButton();
        vServJuegoLbl39 = new javax.swing.JLabel();
        vServJuegoBtn40 = new javax.swing.JButton();
        vServJuegoLbl40 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        vServJuegoBtnInicia = new javax.swing.JButton();
        vServJuegoLblTurno = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(190, 94, 22));
        jPanel1.setForeground(new java.awt.Color(255, 102, 255));
        jPanel1.setLayout(null);

        vServJuegoBtn1.setBackground(new java.awt.Color(255, 204, 153));
        vServJuegoBtn1.setActionCommand("1");
        jPanel1.add(vServJuegoBtn1);
        vServJuegoBtn1.setBounds(10, 10, 120, 120);

        vServJuegoLbl1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(vServJuegoLbl1);
        vServJuegoLbl1.setBounds(10, 10, 120, 120);

        vServJuegoBtn2.setBackground(new java.awt.Color(255, 204, 153));
        vServJuegoBtn2.setActionCommand("2");
        jPanel1.add(vServJuegoBtn2);
        vServJuegoBtn2.setBounds(140, 10, 120, 120);

        vServJuegoLbl2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(vServJuegoLbl2);
        vServJuegoLbl2.setBounds(140, 10, 120, 120);

        vServJuegoBtn3.setBackground(new java.awt.Color(255, 204, 153));
        vServJuegoBtn3.setActionCommand("3");
        jPanel1.add(vServJuegoBtn3);
        vServJuegoBtn3.setBounds(270, 10, 120, 120);

        vServJuegoLbl3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(vServJuegoLbl3);
        vServJuegoLbl3.setBounds(270, 10, 120, 120);

        vServJuegoBtn4.setBackground(new java.awt.Color(255, 204, 153));
        vServJuegoBtn4.setActionCommand("4");
        jPanel1.add(vServJuegoBtn4);
        vServJuegoBtn4.setBounds(400, 10, 120, 120);

        vServJuegoLbl4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(vServJuegoLbl4);
        vServJuegoLbl4.setBounds(400, 10, 120, 120);

        vServJuegoBtn5.setBackground(new java.awt.Color(255, 204, 153));
        vServJuegoBtn5.setActionCommand("5");
        jPanel1.add(vServJuegoBtn5);
        vServJuegoBtn5.setBounds(530, 10, 120, 120);

        vServJuegoLbl5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(vServJuegoLbl5);
        vServJuegoLbl5.setBounds(530, 10, 120, 120);

        vServJuegoBtn6.setBackground(new java.awt.Color(255, 204, 153));
        vServJuegoBtn6.setActionCommand("6");
        jPanel1.add(vServJuegoBtn6);
        vServJuegoBtn6.setBounds(660, 10, 120, 120);

        vServJuegoLbl6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(vServJuegoLbl6);
        vServJuegoLbl6.setBounds(660, 10, 120, 120);

        vServJuegoBtn7.setBackground(new java.awt.Color(255, 204, 153));
        vServJuegoBtn7.setActionCommand("7");
        jPanel1.add(vServJuegoBtn7);
        vServJuegoBtn7.setBounds(790, 10, 120, 120);

        vServJuegoLbl7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(vServJuegoLbl7);
        vServJuegoLbl7.setBounds(790, 10, 120, 120);

        vServJuegoBtn8.setBackground(new java.awt.Color(255, 204, 153));
        vServJuegoBtn8.setActionCommand("8");
        jPanel1.add(vServJuegoBtn8);
        vServJuegoBtn8.setBounds(920, 10, 120, 120);

        vServJuegoLbl8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(vServJuegoLbl8);
        vServJuegoLbl8.setBounds(920, 10, 120, 120);

        vServJuegoBtn9.setBackground(new java.awt.Color(255, 204, 153));
        vServJuegoBtn9.setActionCommand("9");
        jPanel1.add(vServJuegoBtn9);
        vServJuegoBtn9.setBounds(10, 140, 120, 120);

        vServJuegoLbl9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(vServJuegoLbl9);
        vServJuegoLbl9.setBounds(10, 140, 120, 120);

        vServJuegoBtn10.setBackground(new java.awt.Color(255, 204, 153));
        vServJuegoBtn10.setActionCommand("10");
        jPanel1.add(vServJuegoBtn10);
        vServJuegoBtn10.setBounds(140, 140, 120, 120);

        vServJuegoLbl10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(vServJuegoLbl10);
        vServJuegoLbl10.setBounds(140, 140, 120, 120);

        vServJuegoBtn11.setBackground(new java.awt.Color(255, 204, 153));
        vServJuegoBtn11.setActionCommand("11");
        jPanel1.add(vServJuegoBtn11);
        vServJuegoBtn11.setBounds(270, 140, 120, 120);

        vServJuegoLbl11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(vServJuegoLbl11);
        vServJuegoLbl11.setBounds(270, 140, 120, 120);

        vServJuegoBtn12.setBackground(new java.awt.Color(255, 204, 153));
        vServJuegoBtn12.setActionCommand("12");
        jPanel1.add(vServJuegoBtn12);
        vServJuegoBtn12.setBounds(400, 140, 120, 120);

        vServJuegoLbl12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(vServJuegoLbl12);
        vServJuegoLbl12.setBounds(400, 140, 120, 120);

        vServJuegoBtn13.setBackground(new java.awt.Color(255, 204, 153));
        vServJuegoBtn13.setActionCommand("13");
        jPanel1.add(vServJuegoBtn13);
        vServJuegoBtn13.setBounds(530, 140, 120, 120);

        vServJuegoLbl13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(vServJuegoLbl13);
        vServJuegoLbl13.setBounds(530, 140, 120, 120);

        vServJuegoBtn14.setBackground(new java.awt.Color(255, 204, 153));
        vServJuegoBtn14.setActionCommand("14");
        jPanel1.add(vServJuegoBtn14);
        vServJuegoBtn14.setBounds(660, 140, 120, 120);

        vServJuegoLbl14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(vServJuegoLbl14);
        vServJuegoLbl14.setBounds(660, 140, 120, 120);

        vServJuegoBtn15.setBackground(new java.awt.Color(255, 204, 153));
        vServJuegoBtn15.setActionCommand("15");
        jPanel1.add(vServJuegoBtn15);
        vServJuegoBtn15.setBounds(790, 140, 120, 120);

        vServJuegoLbl15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(vServJuegoLbl15);
        vServJuegoLbl15.setBounds(790, 140, 120, 120);

        vServJuegoBtn16.setBackground(new java.awt.Color(255, 204, 153));
        vServJuegoBtn16.setActionCommand("16");
        jPanel1.add(vServJuegoBtn16);
        vServJuegoBtn16.setBounds(920, 140, 120, 120);

        vServJuegoLbl16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(vServJuegoLbl16);
        vServJuegoLbl16.setBounds(920, 140, 120, 120);

        vServJuegoBtn17.setBackground(new java.awt.Color(255, 204, 153));
        vServJuegoBtn17.setActionCommand("17");
        jPanel1.add(vServJuegoBtn17);
        vServJuegoBtn17.setBounds(10, 270, 120, 120);

        vServJuegoLbl17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(vServJuegoLbl17);
        vServJuegoLbl17.setBounds(10, 270, 120, 120);

        vServJuegoBtn18.setBackground(new java.awt.Color(255, 204, 153));
        vServJuegoBtn18.setActionCommand("18");
        jPanel1.add(vServJuegoBtn18);
        vServJuegoBtn18.setBounds(140, 270, 120, 120);

        vServJuegoLbl18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(vServJuegoLbl18);
        vServJuegoLbl18.setBounds(140, 270, 120, 120);

        vServJuegoBtn19.setBackground(new java.awt.Color(255, 204, 153));
        vServJuegoBtn19.setActionCommand("19");
        jPanel1.add(vServJuegoBtn19);
        vServJuegoBtn19.setBounds(270, 270, 120, 120);

        vServJuegoLbl19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(vServJuegoLbl19);
        vServJuegoLbl19.setBounds(270, 270, 120, 120);

        vServJuegoBtn20.setBackground(new java.awt.Color(255, 204, 153));
        vServJuegoBtn20.setActionCommand("20");
        jPanel1.add(vServJuegoBtn20);
        vServJuegoBtn20.setBounds(400, 270, 120, 120);

        vServJuegoLbl20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(vServJuegoLbl20);
        vServJuegoLbl20.setBounds(400, 270, 120, 120);

        vServJuegoBtn21.setBackground(new java.awt.Color(255, 204, 153));
        vServJuegoBtn21.setActionCommand("21");
        jPanel1.add(vServJuegoBtn21);
        vServJuegoBtn21.setBounds(530, 270, 120, 120);

        vServJuegoLbl21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(vServJuegoLbl21);
        vServJuegoLbl21.setBounds(530, 270, 120, 120);

        vServJuegoBtn22.setBackground(new java.awt.Color(255, 204, 153));
        vServJuegoBtn22.setActionCommand("22");
        jPanel1.add(vServJuegoBtn22);
        vServJuegoBtn22.setBounds(660, 270, 120, 120);

        vServJuegoLbl22.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(vServJuegoLbl22);
        vServJuegoLbl22.setBounds(660, 270, 120, 120);

        vServJuegoBtn23.setBackground(new java.awt.Color(255, 204, 153));
        vServJuegoBtn23.setActionCommand("23");
        jPanel1.add(vServJuegoBtn23);
        vServJuegoBtn23.setBounds(790, 270, 120, 120);

        vServJuegoLbl23.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(vServJuegoLbl23);
        vServJuegoLbl23.setBounds(790, 270, 120, 120);

        vServJuegoBtn24.setBackground(new java.awt.Color(255, 204, 153));
        vServJuegoBtn24.setActionCommand("24");
        jPanel1.add(vServJuegoBtn24);
        vServJuegoBtn24.setBounds(920, 270, 120, 120);

        vServJuegoLbl24.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(vServJuegoLbl24);
        vServJuegoLbl24.setBounds(920, 270, 120, 120);

        vServJuegoBtn25.setBackground(new java.awt.Color(255, 204, 153));
        vServJuegoBtn25.setActionCommand("25");
        jPanel1.add(vServJuegoBtn25);
        vServJuegoBtn25.setBounds(10, 400, 120, 120);

        vServJuegoLbl25.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(vServJuegoLbl25);
        vServJuegoLbl25.setBounds(10, 400, 120, 120);

        vServJuegoBtn26.setBackground(new java.awt.Color(255, 204, 153));
        vServJuegoBtn26.setActionCommand("26");
        jPanel1.add(vServJuegoBtn26);
        vServJuegoBtn26.setBounds(140, 400, 120, 120);

        vServJuegoLbl26.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(vServJuegoLbl26);
        vServJuegoLbl26.setBounds(140, 400, 120, 120);

        vServJuegoBtn27.setBackground(new java.awt.Color(255, 204, 153));
        vServJuegoBtn27.setActionCommand("27");
        jPanel1.add(vServJuegoBtn27);
        vServJuegoBtn27.setBounds(270, 400, 120, 120);

        vServJuegoLbl27.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(vServJuegoLbl27);
        vServJuegoLbl27.setBounds(270, 400, 120, 120);

        vServJuegoBtn28.setBackground(new java.awt.Color(255, 204, 153));
        vServJuegoBtn28.setActionCommand("28");
        jPanel1.add(vServJuegoBtn28);
        vServJuegoBtn28.setBounds(400, 400, 120, 120);

        vServJuegoLbl28.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(vServJuegoLbl28);
        vServJuegoLbl28.setBounds(400, 400, 120, 120);

        vServJuegoBtn29.setBackground(new java.awt.Color(255, 204, 153));
        vServJuegoBtn29.setActionCommand("29");
        jPanel1.add(vServJuegoBtn29);
        vServJuegoBtn29.setBounds(530, 400, 120, 120);

        vServJuegoLbl29.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(vServJuegoLbl29);
        vServJuegoLbl29.setBounds(530, 400, 120, 120);

        vServJuegoBtn30.setBackground(new java.awt.Color(255, 204, 153));
        vServJuegoBtn30.setActionCommand("30");
        jPanel1.add(vServJuegoBtn30);
        vServJuegoBtn30.setBounds(660, 400, 120, 120);

        vServJuegoLbl30.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(vServJuegoLbl30);
        vServJuegoLbl30.setBounds(660, 400, 120, 120);

        vServJuegoBtn31.setBackground(new java.awt.Color(255, 204, 153));
        vServJuegoBtn31.setActionCommand("31");
        jPanel1.add(vServJuegoBtn31);
        vServJuegoBtn31.setBounds(790, 400, 120, 120);

        vServJuegoLbl31.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(vServJuegoLbl31);
        vServJuegoLbl31.setBounds(790, 400, 120, 120);

        vServJuegoBtn32.setBackground(new java.awt.Color(255, 204, 153));
        vServJuegoBtn32.setActionCommand("32");
        jPanel1.add(vServJuegoBtn32);
        vServJuegoBtn32.setBounds(920, 400, 120, 120);

        vServJuegoLbl32.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(vServJuegoLbl32);
        vServJuegoLbl32.setBounds(920, 400, 120, 120);

        vServJuegoBtn33.setBackground(new java.awt.Color(255, 204, 153));
        vServJuegoBtn33.setActionCommand("33");
        jPanel1.add(vServJuegoBtn33);
        vServJuegoBtn33.setBounds(10, 530, 120, 120);

        vServJuegoLbl33.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(vServJuegoLbl33);
        vServJuegoLbl33.setBounds(10, 530, 120, 120);

        vServJuegoBtn34.setBackground(new java.awt.Color(255, 204, 153));
        vServJuegoBtn34.setActionCommand("34");
        jPanel1.add(vServJuegoBtn34);
        vServJuegoBtn34.setBounds(140, 530, 120, 120);

        vServJuegoLbl34.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(vServJuegoLbl34);
        vServJuegoLbl34.setBounds(140, 530, 120, 120);

        vServJuegoBtn35.setBackground(new java.awt.Color(255, 204, 153));
        vServJuegoBtn35.setActionCommand("35");
        jPanel1.add(vServJuegoBtn35);
        vServJuegoBtn35.setBounds(270, 530, 120, 120);

        vServJuegoLbl35.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(vServJuegoLbl35);
        vServJuegoLbl35.setBounds(270, 530, 120, 120);

        vServJuegoBtn36.setBackground(new java.awt.Color(255, 204, 153));
        vServJuegoBtn36.setActionCommand("36");
        jPanel1.add(vServJuegoBtn36);
        vServJuegoBtn36.setBounds(400, 530, 120, 120);

        vServJuegoLbl36.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(vServJuegoLbl36);
        vServJuegoLbl36.setBounds(400, 530, 120, 120);

        vServJuegoBtn37.setBackground(new java.awt.Color(255, 204, 153));
        vServJuegoBtn37.setActionCommand("37");
        jPanel1.add(vServJuegoBtn37);
        vServJuegoBtn37.setBounds(530, 530, 120, 120);

        vServJuegoLbl37.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(vServJuegoLbl37);
        vServJuegoLbl37.setBounds(530, 530, 120, 120);

        vServJuegoBtn38.setBackground(new java.awt.Color(255, 204, 153));
        vServJuegoBtn38.setActionCommand("38");
        jPanel1.add(vServJuegoBtn38);
        vServJuegoBtn38.setBounds(660, 530, 120, 120);

        vServJuegoLbl38.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(vServJuegoLbl38);
        vServJuegoLbl38.setBounds(660, 530, 120, 120);

        vServJuegoBtn39.setBackground(new java.awt.Color(255, 204, 153));
        vServJuegoBtn39.setActionCommand("39");
        jPanel1.add(vServJuegoBtn39);
        vServJuegoBtn39.setBounds(790, 530, 120, 120);

        vServJuegoLbl39.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(vServJuegoLbl39);
        vServJuegoLbl39.setBounds(790, 530, 120, 120);

        vServJuegoBtn40.setBackground(new java.awt.Color(255, 204, 153));
        vServJuegoBtn40.setActionCommand("40");
        jPanel1.add(vServJuegoBtn40);
        vServJuegoBtn40.setBounds(920, 530, 120, 120);

        vServJuegoLbl40.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(vServJuegoLbl40);
        vServJuegoLbl40.setBounds(920, 530, 120, 120);

        jPanel2.setBackground(new java.awt.Color(234, 137, 40));

        vServJuegoBtnInicia.setBackground(new java.awt.Color(105, 62, 9));
        vServJuegoBtnInicia.setFont(new java.awt.Font("Snap ITC", 1, 18)); // NOI18N
        vServJuegoBtnInicia.setForeground(new java.awt.Color(255, 255, 255));
        vServJuegoBtnInicia.setText("Inciar Juego");
        vServJuegoBtnInicia.setActionCommand("iniciaJuego");

        vServJuegoLblTurno.setFont(new java.awt.Font("Showcard Gothic", 1, 18)); // NOI18N
        vServJuegoLblTurno.setText("Turno De: ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(vServJuegoLblTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 260, Short.MAX_VALUE)
                .addComponent(vServJuegoBtnInicia, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(vServJuegoBtnInicia, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(vServJuegoLblTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2);
        jPanel2.setBounds(10, 660, 1030, 100);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1051, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 768, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private JButton[] botones;
    private JLabel[] labels;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    public javax.swing.JButton vServJuegoBtn1;
    public javax.swing.JButton vServJuegoBtn10;
    public javax.swing.JButton vServJuegoBtn11;
    public javax.swing.JButton vServJuegoBtn12;
    public javax.swing.JButton vServJuegoBtn13;
    public javax.swing.JButton vServJuegoBtn14;
    public javax.swing.JButton vServJuegoBtn15;
    public javax.swing.JButton vServJuegoBtn16;
    public javax.swing.JButton vServJuegoBtn17;
    public javax.swing.JButton vServJuegoBtn18;
    public javax.swing.JButton vServJuegoBtn19;
    public javax.swing.JButton vServJuegoBtn2;
    public javax.swing.JButton vServJuegoBtn20;
    public javax.swing.JButton vServJuegoBtn21;
    public javax.swing.JButton vServJuegoBtn22;
    public javax.swing.JButton vServJuegoBtn23;
    public javax.swing.JButton vServJuegoBtn24;
    public javax.swing.JButton vServJuegoBtn25;
    public javax.swing.JButton vServJuegoBtn26;
    public javax.swing.JButton vServJuegoBtn27;
    public javax.swing.JButton vServJuegoBtn28;
    public javax.swing.JButton vServJuegoBtn29;
    public javax.swing.JButton vServJuegoBtn3;
    public javax.swing.JButton vServJuegoBtn30;
    public javax.swing.JButton vServJuegoBtn31;
    public javax.swing.JButton vServJuegoBtn32;
    public javax.swing.JButton vServJuegoBtn33;
    public javax.swing.JButton vServJuegoBtn34;
    public javax.swing.JButton vServJuegoBtn35;
    public javax.swing.JButton vServJuegoBtn36;
    public javax.swing.JButton vServJuegoBtn37;
    public javax.swing.JButton vServJuegoBtn38;
    public javax.swing.JButton vServJuegoBtn39;
    public javax.swing.JButton vServJuegoBtn4;
    public javax.swing.JButton vServJuegoBtn40;
    public javax.swing.JButton vServJuegoBtn5;
    public javax.swing.JButton vServJuegoBtn6;
    public javax.swing.JButton vServJuegoBtn7;
    public javax.swing.JButton vServJuegoBtn8;
    public javax.swing.JButton vServJuegoBtn9;
    public javax.swing.JButton vServJuegoBtnInicia;
    public javax.swing.JLabel vServJuegoLbl1;
    public javax.swing.JLabel vServJuegoLbl10;
    public javax.swing.JLabel vServJuegoLbl11;
    public javax.swing.JLabel vServJuegoLbl12;
    public javax.swing.JLabel vServJuegoLbl13;
    public javax.swing.JLabel vServJuegoLbl14;
    public javax.swing.JLabel vServJuegoLbl15;
    public javax.swing.JLabel vServJuegoLbl16;
    public javax.swing.JLabel vServJuegoLbl17;
    public javax.swing.JLabel vServJuegoLbl18;
    public javax.swing.JLabel vServJuegoLbl19;
    public javax.swing.JLabel vServJuegoLbl2;
    public javax.swing.JLabel vServJuegoLbl20;
    public javax.swing.JLabel vServJuegoLbl21;
    public javax.swing.JLabel vServJuegoLbl22;
    public javax.swing.JLabel vServJuegoLbl23;
    public javax.swing.JLabel vServJuegoLbl24;
    public javax.swing.JLabel vServJuegoLbl25;
    public javax.swing.JLabel vServJuegoLbl26;
    public javax.swing.JLabel vServJuegoLbl27;
    public javax.swing.JLabel vServJuegoLbl28;
    public javax.swing.JLabel vServJuegoLbl29;
    public javax.swing.JLabel vServJuegoLbl3;
    public javax.swing.JLabel vServJuegoLbl30;
    public javax.swing.JLabel vServJuegoLbl31;
    public javax.swing.JLabel vServJuegoLbl32;
    public javax.swing.JLabel vServJuegoLbl33;
    public javax.swing.JLabel vServJuegoLbl34;
    public javax.swing.JLabel vServJuegoLbl35;
    public javax.swing.JLabel vServJuegoLbl36;
    public javax.swing.JLabel vServJuegoLbl37;
    public javax.swing.JLabel vServJuegoLbl38;
    public javax.swing.JLabel vServJuegoLbl39;
    public javax.swing.JLabel vServJuegoLbl4;
    public javax.swing.JLabel vServJuegoLbl40;
    public javax.swing.JLabel vServJuegoLbl5;
    public javax.swing.JLabel vServJuegoLbl6;
    public javax.swing.JLabel vServJuegoLbl7;
    public javax.swing.JLabel vServJuegoLbl8;
    public javax.swing.JLabel vServJuegoLbl9;
    public javax.swing.JLabel vServJuegoLblTurno;
    // End of variables declaration//GEN-END:variables
}
