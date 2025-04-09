import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class BakersgameGUI {

    //Atributos logicos del juego
    private Bakersgame bakersgame;
    private Baraja baraja;
    private ArrayList<JLabel> posicionesFreeCell;
    private ArrayList<JLabel> posicionesFundaciones;
    private ArrayList<ArrayList<JLabel>> posicionesCascadas;

    //Atributos GUI
    private Color colorPoker;
    private JFrame frame;
    private JPanel panelFreeCell, panelFundaciones, panelTableau, panelControl, panelSuperior;
    private JButton botonHint, botonUndo, botonReiniciar;
    private BarajaGUI barajaGUI;

    public BakersgameGUI()
    {
        colorPoker = new Color(53,94,59);
        baraja = new Baraja();
        barajaGUI = new BarajaGUI(baraja);
        bakersgame = new Bakersgame(baraja);

        frame = new JFrame("Baker's Game");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Panel control y sus elementos

        panelControl = new JPanel();
        panelControl.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelControl.setOpaque(true);

        botonHint = new JButton("Hint");
        botonHint.addActionListener(action -> botonHint());
        botonHint.setFocusPainted(false);
        botonHint.setPreferredSize(new Dimension(90,20));
        botonHint.setOpaque(true);
        botonHint.setBackground(new Color(236,234,227));

        botonUndo = new JButton("Undo");
        botonUndo.addActionListener(action -> botonUndo());
        botonUndo.setPreferredSize(new Dimension(90,20));
        botonUndo.setFocusPainted(false);
        botonUndo.setOpaque(true);
        botonUndo.setBackground(new Color(236,234,227));

        botonReiniciar = new JButton("Reiniciar");
        botonReiniciar.addActionListener(action -> botonReiniciar());
        botonReiniciar.setPreferredSize(new Dimension(90,20));
        botonReiniciar.setFocusPainted(false);
        botonReiniciar.setOpaque(true);
        botonReiniciar.setBackground(new Color(236,234,227));

        panelControl.add(botonHint);
        panelControl.add(botonUndo);
        panelControl.add(botonReiniciar);

        //Espaciado entre elementos
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 1;
        gbc.gridx = 1;
        gbc.insets = new Insets(5,7,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;


        //Panel de free cells

        panelFreeCell = new JPanel(new GridBagLayout());
        panelFreeCell.setBackground(colorPoker);


        posicionesFreeCell = new ArrayList<>(4);
        for (int i = 0; i < 4; ++i) {
            JLabel labelFC = new JLabel();
            labelFC.setOpaque(true);
            labelFC.setBorder(BorderFactory.createLineBorder(new Color(1,50,32)));
            labelFC.setBackground(colorPoker);
            labelFC.setPreferredSize(new Dimension(100,140));
            labelFC.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("SEXOOOOOOOOOOOO");
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
            posicionesFreeCell.add(labelFC);

            panelFreeCell.add(labelFC, gbc);
            ++gbc.gridx;
        }
        gbc.gridx = 1;



        //Panel fundaciones

        panelFundaciones = new JPanel(new GridBagLayout());
        panelFundaciones.setBackground(colorPoker);
        gbc.insets = new Insets(5,5,5,7);

        posicionesFundaciones = new ArrayList<>(4);

        for (int i = 0; i < 4; ++i) {
            JLabel labelF = new JLabel();
            labelF.setOpaque(true);
            labelF.setBorder(BorderFactory.createLineBorder(new Color(1,50,32)));
            labelF.setBackground(colorPoker);
            labelF.setPreferredSize(new Dimension(100,140));
            labelF.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("NOSEXOOOOOOOOOOOOOOO");
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
            posicionesFundaciones.add(labelF);

            panelFundaciones.add(labelF, gbc);
            ++gbc.gridx;
        }
        gbc.gridx = 0;
        gbc.gridy = 0;

        //Panel tableau

        panelTableau = new JPanel();
        panelTableau.setLayout(null);
        panelTableau.setBackground(colorPoker);

        posicionesCascadas = new ArrayList<>(8);

        for (int i = 0; i < 8; ++i) {
            if (i < 4) {
                posicionesCascadas.add(new ArrayList<>(7));
            } else {
                posicionesCascadas.add(new ArrayList<>(6));
            }
        }

        int mulX = 30;
        int mulY = 30;
        for (int i = 0; i < 8; ++i) {
            ArrayList<JLabel> posicionCascada = posicionesCascadas.get(i);

            if (i < 4) {
                for (int j = 0; j < 7; ++j) {
                    JLabel labelCas = new JLabel();
                    if (j == 6) {
                        labelCas.setBorder(BorderFactory.createLineBorder(new Color(1,50,32)));
                        labelCas.setOpaque(true);
                    }
                    labelCas.setBackground(colorPoker);
                    labelCas.setBounds(40+mulX,345-mulY,100,140);
                    labelCas.addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            System.out.println("SEXOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
                        }

                        @Override
                        public void mousePressed(MouseEvent e) {

                        }

                        @Override
                        public void mouseReleased(MouseEvent e) {

                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {

                        }

                        @Override
                        public void mouseExited(MouseEvent e) {

                        }
                    });
                    posicionCascada.add(labelCas);
                    panelTableau.add(labelCas);
                    mulY = mulY + 45;
                }
            } else {
                for (int j = 0; j < 6; ++j) {
                    JLabel labelCas = new JLabel();
                    if (j == 5) {
                        labelCas.setBorder(BorderFactory.createLineBorder(new Color(1,50,32)));
                        labelCas.setOpaque(true);
                    }
                    labelCas.setBackground(colorPoker);
                    labelCas.setBounds(40+mulX,300-mulY,100,140);
                    labelCas.addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            System.out.println("SEXOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
                        }

                        @Override
                        public void mousePressed(MouseEvent e) {

                        }

                        @Override
                        public void mouseReleased(MouseEvent e) {

                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {

                        }

                        @Override
                        public void mouseExited(MouseEvent e) {

                        }
                    });
                    posicionCascada.add(labelCas);
                    panelTableau.add(labelCas);
                    mulY = mulY + 45;

                }
            }
            mulY = 30;
            mulX = mulX + 110;


        }

        panelSuperior = new JPanel();
        panelSuperior.setLayout(new BorderLayout());
        panelSuperior.setBackground(colorPoker);

        JPanel freeCellWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        freeCellWrapper.setBackground(colorPoker);
        freeCellWrapper.add(panelFreeCell);

        JPanel fundacionesWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        fundacionesWrapper.setBackground(colorPoker);
        fundacionesWrapper.add(panelFundaciones);

        panelSuperior.add(freeCellWrapper, BorderLayout.WEST);
        panelSuperior.add(fundacionesWrapper, BorderLayout.EAST);

        frame.add(panelControl, BorderLayout.SOUTH);
        frame.add(panelTableau, BorderLayout.CENTER);
        frame.add(panelSuperior, BorderLayout.NORTH);
        frame.setSize(1000,800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void setAtributosLogicos()
    {
        for (int i = 0; i < posicionesCascadas.size(); ++i) {
            ArrayList<JLabel> cascada = posicionesCascadas.get(i);

            for (int j = 0; j < cascada.size(); ++j) {
                cascada.get(j).setIcon(barajaGUI.getBarajaGUI().get(j).getCartaImagen().getIcon());
            }
        }
        panelTableau.repaint();
        panelTableau.revalidate();
    }

    private boolean origenDestino()
    {
        return true;
    }

    public void iniciarJuego()
    {
        setAtributosLogicos();
    }

    private void botonHint()
    {

    }

    private void botonUndo()
    {

    }

    private void botonReiniciar()
    {

    }
}
