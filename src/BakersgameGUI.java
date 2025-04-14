import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;

public class BakersgameGUI {

    //Atributos logicos del juego
    private Bakersgame bakersgame;
    private Baraja baraja;
    private ArrayList<Carta> origenDestino;
    private boolean estadoJuego;

    //Atributos GUI
    private Color colorPoker;
    private JFrame frame;
    private JPanel panelFreeCell, panelFundaciones, panelTableau, panelControl, panelSuperior;
    private JButton botonHint, botonUndo, botonReiniciar, botonActualizar;
    private ArrayList<JLabel> posicionesFreeCell;
    private ArrayList<ArrayList<JLabel>> posicionesFundaciones;
    private ArrayList<ArrayList<JLabel>> posicionesCascadas;
    private JLabel labelEstado;

    public BakersgameGUI()
    {
        estadoJuego = false;
        colorPoker = new Color(53,94,59);
        baraja = new Baraja();
        bakersgame = new Bakersgame(baraja);
        origenDestino = new ArrayList<>(2);

        frame = new JFrame("Baker's Game");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Label para el estado final del juego
        labelEstado = new JLabel();
        labelEstado.setText("VICTORIA!!!");
        labelEstado.setOpaque(true);
        labelEstado.setVisible(true);
        labelEstado.setFont(new Font("Arial", Font.BOLD, 20));
        labelEstado.setBounds(300, 200,400,140);
        labelEstado.setHorizontalAlignment(SwingConstants.CENTER);
        labelEstado.setVerticalAlignment(SwingConstants.CENTER);
        labelEstado.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));


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

        botonActualizar = new JButton("Actualizar tablero");
        botonActualizar.addActionListener(action -> botonActualizar());
        botonActualizar.setPreferredSize(new Dimension(140, 20));
        botonActualizar.setFocusPainted(false);
        botonActualizar.setOpaque(true);
        botonActualizar.setBackground(new Color(236,234,227));

        panelControl.add(botonHint);
        panelControl.add(botonUndo);
        panelControl.add(botonReiniciar);
        panelControl.add(botonActualizar);

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
                    //Se obtiene el label origen es decir el que se movio
                    JLabel labelPresionado = (JLabel) e.getSource();
                    System.out.println(identificarCarta((ImageIcon) labelPresionado.getIcon()));

                    //Se agrega al array encargado de guardar la carta origen y destino
                    origenDestino.add(identificarCarta((ImageIcon) labelPresionado.getIcon()));

                    //Si el array su tamanyo es dos quiere decir que ya se eligio origen y destino por lo tanto entra
                    if (origenDestino.size() == 2) {

                        //En este ciclo identifica el indice destino comparando todos los labels con el label origen
                        int indiceDestino = -1;
                        for (int i = 0; i < posicionesFreeCell.size(); ++i) {
                            if (posicionesFreeCell.get(i).equals(labelPresionado)) {
                                indiceDestino = i;
                                break;
                            }
                        }
                        System.out.println("Carta origen: " + origenDestino.getFirst() + " Carta Destino: " + origenDestino.get(1) + " Indice destino: " + (indiceDestino+1) + " Lugar destino: " + "freeCell");
                        moverCarta(origenDestino.getFirst(), origenDestino.get(1), indiceDestino, "freecell");
                        origenDestino.clear();

                        //Si es un quiere decir que es solamente el origen entonces necesitamos comprobar si el origen es nulo
                        //en tal caso seria un movimiento invalido
                    } else if (origenDestino.size() == 1) {
                        if (origenDestino.getFirst() == null) {
                            origenDestino.clear();
                        }
                    }
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
            posicionesFundaciones.add(new ArrayList<>(13));
            for (int j = 0; j < 13; ++j) {
                JLabel labelF = new JLabel();
                labelF.setOpaque(true);
                labelF.setBorder(BorderFactory.createLineBorder(new Color(1,50,32)));
                labelF.setBackground(colorPoker);
                labelF.setPreferredSize(new Dimension(100,140));
                labelF.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        //Se obtiene el label fuente es decir cual se toco
                        JLabel labelPresionado = (JLabel) e.getSource();
                        System.out.println(identificarCarta((ImageIcon) labelPresionado.getIcon()));

                        //Se agrega la carta identificada al arrayList. En esta ocasion solo nos servira cuando sea destino
                        //ya que las fundaciones no pueden salir ya una vez ingresadas
                        origenDestino.add(identificarCarta((ImageIcon) labelPresionado.getIcon()));
                        System.out.println(origenDestino.size());
                        if (origenDestino.size() == 2) {

                            //Ciclo para identificar el indice del lugar destino
                            int indiceDestino = -1;
                            for (int i = 0; i < posicionesFundaciones.size(); ++i) {
                                for (int j = 0; j < posicionesFundaciones.get(i).size(); ++j) {
                                    if (posicionesFundaciones.get(i).get(j).equals(labelPresionado)) {
                                        indiceDestino = i;
                                        break;
                                    }
                                }
                                if (indiceDestino != -1) {
                                    break;
                                }
                            }

                            System.out.println("Carta origen: " + origenDestino.getFirst() + " Carta Destino: " + origenDestino.get(1) + " Indice destino: " + (indiceDestino+1) + " Lugar destino: " + "Fundaciones");
                            moverCarta(origenDestino.getFirst(), origenDestino.get(1), indiceDestino, "fundacion");

                            origenDestino.clear();
                        } else {
                            origenDestino.clear();
                        }
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
                posicionesFundaciones.get(i).add(labelF);
                panelFundaciones.add(labelF, gbc);
                panelFundaciones.setComponentZOrder(labelF, 0);
            }
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
                posicionesCascadas.add(new ArrayList<>());
            } else {
                posicionesCascadas.add(new ArrayList<>());
            }
        }

        int mulX = 60;
        int mulY = 70;
        for (int i = 0; i < 8; ++i) {
            ArrayList<JLabel> posicionCascada = posicionesCascadas.get(i);

            if (i < 4) {
                int totalCartas = 7;
                System.out.println("| Eje X | Eje Y | cascada " + (i+1));
                for (int j = 0; j < 7; ++j) {
                    JLabel labelCas = new JLabel();
                    System.out.println("|" + mulX + "|" + mulY + "|");
                    labelCas.setBounds(mulX, mulY, 100, 140);
                    labelCas.addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            JLabel labelPresionado = (JLabel) e.getSource();
                            System.out.println(identificarCarta((ImageIcon) labelPresionado.getIcon()));

                            origenDestino.add(identificarCarta((ImageIcon) labelPresionado.getIcon()));
                            if (origenDestino.size() == 2) {

                                int indiceDestino = bakersgame.getTablero().localizarCartaTableau(origenDestino.get(1));
                                System.out.println("Carta origen: " + origenDestino.getFirst() + " Carta Destino: " + origenDestino.get(1) + " Indice destino: " + (indiceDestino+1) + " Lugar destino: " + "tableau");
                                moverCarta(origenDestino.getFirst(), origenDestino.get(1), indiceDestino, "tableau");
                                origenDestino.clear();
                            }

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
                    panelTableau.setComponentZOrder(labelCas, 0);
                    mulY=mulY+45;

                }
                mulX=mulX+110;
            } else {
                int totalCartas = 6;
                System.out.println("| Eje X | Eje Y | cascada " + (i+1));
                for (int j = 0; j < 6; ++j) {
                    JLabel labelCas = new JLabel();
                    System.out.println("|" + mulX + "|" + mulY + "|");
                    labelCas.setBounds(mulX, mulY, 100, 140);
                    labelCas.addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            JLabel labelPresionado = (JLabel) e.getSource();
                            System.out.println(identificarCarta((ImageIcon) labelPresionado.getIcon()));

                            origenDestino.add(identificarCarta((ImageIcon) labelPresionado.getIcon()));
                            if (origenDestino.size() == 2) {

                                int indiceDestino = bakersgame.getTablero().localizarCartaTableau(origenDestino.get(1));
                                System.out.println("Carta origen: " + origenDestino.getFirst() + " Carta Destino: " + origenDestino.get(1) + " Indice destino: " + (indiceDestino+1) + " Lugar destino: " + "Tableau");
                                moverCarta(origenDestino.getFirst(), origenDestino.get(1), indiceDestino, "tableau");
                                origenDestino.clear();
                            }
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
                    panelTableau.setComponentZOrder(labelCas,0);
                    mulY=mulY+45;

                }
                mulX = mulX + 110;
            }
            mulY = 70;



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

        //Para evitar que Swing compare componentes no compatibles entre si, se desactiva el focus del frame
        frame.setFocusTraversalPolicy(null);
        frame.setFocusTraversalPolicyProvider(false);

        frame.add(panelControl, BorderLayout.SOUTH);
        frame.add(panelTableau, BorderLayout.CENTER);
        frame.add(panelSuperior, BorderLayout.NORTH);
        frame.setSize(1000,800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void actualizarTablero()
    {
        bakersgame.actualizarAses();
        bakersgame.getTablero().mostrarTablero();

        // Ciclo para poder meter las cartas actuales de tal cascada a la cascada GUI

        int mulX = 60;
        int mulY = 70;

        int cartasMovibles = bakersgame.calcularCascadaMovible();
        for (int i = 0; i < 8; ++i) {
            ArrayList<Carta> cascadaArray = transformarCascadaArray(i);
            ArrayList<JLabel> labelsCascada = posicionesCascadas.get(i);
            int tamanyo = cascadaArray.size();

            if (tamanyo == 0) {
                System.out.println("Tamanyo cero mira " + tamanyo);
                panelTableau.remove(labelsCascada.getFirst());
                JLabel labelCas = new JLabel();
                labelCas.setOpaque(true);
                labelCas.setVisible(true);
                labelCas.setIcon(null);
                labelCas.setBackground(colorPoker);
                int nuevoMulX = mulX + 110 * i;
                labelCas.setBounds(nuevoMulX, mulY,100,140);
                labelCas.setBorder(BorderFactory.createLineBorder(new Color(1,50,32)));
                labelCas.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        JLabel labelPresionado = (JLabel) e.getSource();
                        System.out.println(identificarCarta((ImageIcon) labelPresionado.getIcon()));

                        origenDestino.add(identificarCarta((ImageIcon) labelPresionado.getIcon()));
                        if (origenDestino.size() == 2) {

                            int indiceDestino = -1;

                            for (int i = 0; i < posicionesCascadas.size(); ++i) {
                                if (posicionesCascadas.get(i).contains(labelPresionado)) {
                                    indiceDestino = i;
                                    break;
                                }
                            }
                            System.out.println("Carta origen: " + origenDestino.getFirst() + " Carta Destino: " + origenDestino.get(1) + " Indice destino: " + (indiceDestino+1) + " Lugar destino: " + "Tableau");
                            moverCarta(origenDestino.getFirst(), origenDestino.get(1), indiceDestino, "tableau");
                            origenDestino.clear();
                        } else if (origenDestino.size() == 1) {
                            if (origenDestino.getFirst() == null) {
                                origenDestino.clear();
                            }
                        }
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
                while (labelsCascada.size() > tamanyo) {
                    JLabel extra = labelsCascada.remove(labelsCascada.size() - 1);
                    panelTableau.remove(extra);
                }
                panelTableau.add(labelCas);
                labelsCascada.add(labelCas);
                // Eliminar JLabels sobrantes
            } else {
                for (int j = 0; j < tamanyo; ++j) {
                    JLabel labelCas;

                    if (j < labelsCascada.size()) {
                        // Usar el JLabel ya existente y moverlo
                        labelCas = labelsCascada.get(j);
                    } else {
                        // Si no existe, crearlo y asignarle el listener solo una vez
                        labelCas = new JLabel();
                        labelCas.setOpaque(true);
                        labelCas.setBackground(colorPoker);
                        labelCas.setBorder(BorderFactory.createLineBorder(new Color(1,50,32)));
                        labelCas.addMouseListener(new MouseListener() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                JLabel labelPresionado = (JLabel) e.getSource();
                                System.out.println(identificarCarta((ImageIcon) labelPresionado.getIcon()));

                                origenDestino.add(identificarCarta((ImageIcon) labelPresionado.getIcon()));
                                if (origenDestino.size() == 2) {

                                    int indiceDestino = bakersgame.getTablero().localizarCartaTableau(origenDestino.get(1));
                                    System.out.println("Carta origen: " + origenDestino.getFirst() + " Carta Destino: " + origenDestino.get(1) + " Indice destino: " + (indiceDestino+1) + " Lugar destino: " + "Tableau");
                                    moverCarta(origenDestino.getFirst(), origenDestino.get(1), indiceDestino, "tableau");
                                    origenDestino.clear();
                                }
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
                        panelTableau.add(labelCas);
                        labelsCascada.add(labelCas);
                    }

                    // Asignar imagen y mover a nueva posición
                    labelCas.setIcon(cascadaArray.get(j).getImagen());
                    int nuevoMulY = mulY + 45 * j;
                    int nuevoMulX = mulX + 110 * i;
                    labelCas.setBounds(nuevoMulX, nuevoMulY, 100, 140);
                    labelCas.setVisible(true);
                    labelCas.setBorder(BorderFactory.createLineBorder(new Color(1,50,32)));
                    panelTableau.setComponentZOrder(labelCas, 0);
                }
                // Eliminar JLabels sobrantes
                while (labelsCascada.size() > tamanyo) {
                    JLabel extra = labelsCascada.remove(labelsCascada.size() - 1);
                    panelTableau.remove(extra);
                }
            }


            transformarCascadaLista(cascadaArray, i);
        }

        panelTableau.revalidate();
        panelTableau.repaint();

        // Ciclo para poder meter las cartas actuales del free cell logico a grafico
        for (int i = 0;i < posicionesFreeCell.size(); ++i) {
            if (bakersgame.getTablero().getFreeCells().get(i).getValor() == -1) {
                JLabel labelFC = posicionesFreeCell.get(i);

                labelFC.setIcon(null);
                labelFC.setBorder(BorderFactory.createLineBorder(new Color(1,50,32)));
                labelFC.setBackground(colorPoker);


            } else {
                JLabel labelFC = posicionesFreeCell.get(i);
                Carta carta = bakersgame.getTablero().getFreeCells().get(i);

                labelFC.setIcon(carta.getImagen());
                labelFC.setOpaque(true);
                labelFC.setBorder(BorderFactory.createLineBorder(new Color(1,50,32)));
                labelFC.setVisible(true);

            }
        }
        panelSuperior.repaint();
        panelSuperior.revalidate();

        // Ciclo para poder meter las cartas actuales de las fundaciones logico a grafico
        for (int i = 0;i < posicionesFundaciones.size(); ++i) {
            ArrayList<Carta> pilaArray = transformarPilaArray(i);

            if (pilaArray.isEmpty()) {
                for (int j = 0; j < posicionesFundaciones.get(i).size(); ++j) {
                    JLabel labelFD = posicionesFundaciones.get(i).get(j);
                    labelFD.setIcon(null);
                    labelFD.setBackground(colorPoker);
                    labelFD.setVisible(false);
                }
            } else if (pilaArray.size() < posicionesFundaciones.get(i).size()) {
                for (int j = 0; j < posicionesFundaciones.get(i).size(); ++j) {
                    if (pilaArray.size() < posicionesFundaciones.get(i).size() && j > pilaArray.size()-1) {
                        JLabel labelFD = posicionesFundaciones.get(i).get(j);
                        labelFD.setIcon(null);
                        labelFD.setBackground(colorPoker);
                        labelFD.setBorder(BorderFactory.createLineBorder(new Color(1,50,32)));
                        labelFD.setVisible(false);
                    } else {
                        JLabel labelFD = posicionesFundaciones.get(i).get(j);
                        labelFD.setIcon(pilaArray.get(j).getImagen());
                        labelFD.setOpaque(true);
                        labelFD.setVisible(true);
                        labelFD.setBorder(BorderFactory.createLineBorder(new Color(1,50,32)));
                    }
                }
            }

            transformarPilaArrayToPila(pilaArray, i);

        }
        //Ciclo para checar en cada fundacion si hay cartas, si no hay cartas entonces se debe imprimir el marco solamente
        // del label para simular el espacio de carta vacia. Como utilizamos setVisible(false) desaparecemos toda la carta
        //eso incluyendo el marco
        boolean hayCarta = false;
        for (int i = 0; i < posicionesFundaciones.size(); ++i) {
            hayCarta = false;
            for (int j = 0; j < posicionesFundaciones.get(i).size(); ++j) {
                if (posicionesFundaciones.get(i).get(j).getIcon() != null) {
                    hayCarta = true;
                    break;
                }
            }
            if (!hayCarta) {
                posicionesFundaciones.get(i).getLast().setVisible(true);
            }
        }



        if (bakersgame.determinarVictoria()) {
            panelTableau.add(labelEstado, SwingConstants.CENTER);
            panelSuperior.setEnabled(false);
            panelTableau.setEnabled(false);
            botonActualizar.setEnabled(false);
            botonHint.setEnabled(false);
            botonUndo.setEnabled(false);
        } else if (bakersgame.determinarCartasNoDisp()) {
            labelEstado.setText("No hay movimientos disponibles");
            panelTableau.add(labelEstado, SwingConstants.CENTER);
            panelSuperior.setEnabled(false);
            panelTableau.setEnabled(false);
            botonActualizar.setEnabled(false);
            botonHint.setEnabled(false);
            botonUndo.setEnabled(false);
        }



        panelFundaciones.repaint();
        panelFundaciones.revalidate();
        panelSuperior.repaint();
        panelSuperior.revalidate();

        frame.repaint();
        frame.revalidate();
    }

    /**
     * Metodo para localizar una carta a partir de la imagen
     * @param imagen
     * @return
     */
    private Carta identificarCarta(ImageIcon imagen) {

        // Localizar en tableau
        Carta carta = null;
        for (int i = 0; i < 8; i++) {
            ArrayList<Carta> cascadaArray = transformarCascadaArray(i);
            int tamanyo = cascadaArray.size();
            for (int j = 0; j < tamanyo; ++j) {
                if (cascadaArray.get(j).getImagen().equals(imagen)) {
                    carta = cascadaArray.get(j);
                }
            }
            transformarCascadaLista(cascadaArray, i);
        }

        // Localizar en free cells
        for (int i = 0; i < bakersgame.getTablero().getFreeCells().size(); ++i) {
            Carta cartaF = bakersgame.getTablero().getFreeCells().get(i);

            if (cartaF.getImagen().equals(imagen)) {
                carta = cartaF;
            }
        }

        // Localizar en fundaciones
        for (int i = 0; i < bakersgame.getTablero().getFundaciones().size(); ++i) {
            ArrayList<Carta> pilaArray = transformarPilaArray(i);
            for (int j = 0; j < pilaArray.size(); ++j) {
                if (pilaArray.get(j).getImagen().equals(imagen)) {
                    carta = pilaArray.get(j);
                }
            }
            transformarPilaArrayToPila(pilaArray, i);
        }
        return carta;
    }

    /**
     * Metodo que mueve la carta origen a destino utilizando metodos del juego logico
     * @param origen
     * @param destino
     * @return
     */
    private boolean moverCarta(Carta origen, Carta destino, int indiceDestino, String destinoString)
    {
        //Si es null quiere decir que no hay elementos en la parte de donde se encuentra destino
        System.out.println("Origen: " + origen + " Destino: " + destino + " Indice: " + indiceDestino + " DestinoString: " + destinoString);

        if (destino == null) {
            if (bakersgame.localizarCarta(origen).equals("tableau")) {
                if (destinoString.equals("tableau")) {
                    bakersgame.moverCartaEnTableau(origen, indiceDestino);
                } else if (destinoString.equals("freecell")) {
                    bakersgame.freCellYTableau(origen, "tableau", indiceDestino);
                } else if (destinoString.equals("fundacion")) {

                    bakersgame.moverCartaAFundacion(origen, indiceDestino);
                }
            } else if (bakersgame.localizarCarta(origen).equals("freecell")) {
                if (destinoString.equals("tableau")) {

                    bakersgame.freCellYTableau(origen, "freecell", indiceDestino);
                } else if (destinoString.equals("fundacion")) {


                    bakersgame.moverCartaAFundacion(origen, indiceDestino);
                }
            }
        } else {
            if (bakersgame.localizarCarta(origen).equals("tableau")) {
                if (bakersgame.localizarCarta(destino).equals("tableau")) {
                    bakersgame.moverCartaEnTableau(origen, bakersgame.getTablero().localizarCartaTableau(destino));
                } else if (bakersgame.localizarCarta(destino).equals("freecell")) {
                    bakersgame.freCellYTableau(origen, "tableau", bakersgame.getTablero().getFreeCells().indexOf(destino));
                } else if (bakersgame.localizarCarta(destino).equals("fundacion")) {

                    // Se verifica en que pila se encuentra la carta
                    Pila<Carta> pilaTemp = null;
//                    for (int i = 0; i < bakersgame.getTablero().getFundaciones().size(); ++i) {
//                        pilaTemp = bakersgame.getTablero().getFundaciones().get(i);
//                        if (pilaTemp.peek().equals(destino)) {
//                            break;
//                        }
//                    }
                    bakersgame.moverCartaAFundacion(origen, indiceDestino);
                }
            } else if (bakersgame.localizarCarta(origen).equals("freecell")) {
                if (bakersgame.localizarCarta(destino).equals("tableau")) {
                    bakersgame.freCellYTableau(origen, "freecell", indiceDestino);
                } else if (bakersgame.localizarCarta(destino).equals("fundacion")) {
                    // Se verifica en que pila se encuentra la carta
//                    Pila<Carta> pilaTemp = null;
//                    for (int i = 0; i < bakersgame.getTablero().getFundaciones().size(); ++i) {
//                        pilaTemp = bakersgame.getTablero().getFundaciones().get(i);
//                        if (pilaTemp.peek().equals(destino)) {
//                            break;
//                        }
//                    }
                    bakersgame.moverCartaAFundacion(origen, indiceDestino);
                }
            }
        }
        actualizarTablero();

        return true;
    }

    public void iniciarJuego()
    {
        actualizarTablero();
    }

    /**
     * Metodo para el boton Hint. Se obtienen las dos cartas y apartir de esas dos se realzan graficamente
     */
    private void botonHint()
    {
        ListaSimple<Carta> hint = bakersgame.hint();

        if (!hint.listaVacia()) {
            Carta cartaMenor = hint.eliminarInicio();
            Carta cartaMayor = hint.eliminarInicio();
            System.out.println(cartaMayor + " --> " + cartaMenor);
            boolean encontrado = false;

            //Verificacion de la carta menor
            if (bakersgame.localizarCarta(cartaMenor).equals("freecell")) {
                for (int i = 0; i < posicionesFreeCell.size(); ++i) {
                    if (cartaMenor.getImagen().equals(posicionesFreeCell.get(i).getIcon())) {
                        JLabel labelFC = posicionesFreeCell.get(i);
                        labelFC.setBorder(BorderFactory.createLineBorder(Color.yellow,5));
                        break;
                    }
                }
            } else if (bakersgame.localizarCarta(cartaMenor).equals("tableau")) {
                for (int i = 0; i < posicionesCascadas.size(); ++i) {
                    ArrayList<JLabel> posicionCascada = posicionesCascadas.get(i);

                    for (int j = 0; j < posicionCascada.size(); ++j) {
                        if (cartaMenor.getImagen().equals(posicionCascada.get(j).getIcon())) {
                            JLabel labelCas = posicionCascada.get(j);
                            labelCas.setBorder(BorderFactory.createLineBorder(Color.yellow,5));
                            encontrado = true;
                            break;
                        }
                    }
                    if (encontrado) {
                        break;
                    }
                }
            }
            encontrado = false;

            //Verificacion carta mayor
            if (bakersgame.localizarCarta(cartaMayor).equals("freecell")) {
                for (int i = 0; i < posicionesFreeCell.size(); ++i) {
                    if (cartaMayor.getImagen().equals(posicionesFreeCell.get(i).getIcon())) {
                        JLabel labelFC = posicionesFreeCell.get(i);
                        labelFC.setBorder(BorderFactory.createLineBorder(Color.yellow,5));
                        break;
                    }
                }
            } else if (bakersgame.localizarCarta(cartaMayor).equals("tableau")) {
                for (int i = 0; i < posicionesCascadas.size(); ++i) {
                    ArrayList<JLabel> posicionCascada = posicionesCascadas.get(i);

                    for (int j = 0; j < posicionCascada.size(); ++j) {
                        if (cartaMayor.getImagen().equals(posicionCascada.get(j).getIcon())) {
                            JLabel labelCas = posicionCascada.get(j);
                            labelCas.setBorder(BorderFactory.createLineBorder(Color.yellow,5));
                            encontrado = true;
                            break;
                        }
                    }
                    if (encontrado) {
                        break;
                    }
                }
            }
            frame.repaint();
            frame.revalidate();

        }
    }

    private void botonUndo()
    {
        bakersgame.undo();
        actualizarTablero();
    }

    private void botonReiniciar()
    {
        baraja = new Baraja();
        bakersgame = new Bakersgame(baraja);
        origenDestino = new ArrayList<>(2);
        actualizarTablero();
    }

    private void botonActualizar()
    {
        actualizarTablero();
    }

    /**
     * Metodo para transformar una pila(fundacion) a un array(para pasarlo a GUI) sin modificar el orden y posiciones de esta
     * @param indice
     * @return
     */
    private ArrayList<Carta> transformarPilaArray(int indice) {
        ArrayList<Carta> pilaFundacionArray = new ArrayList<>(13);

        while (!bakersgame.getTablero().getFundaciones().get(indice).pilaVacia()) {
            pilaFundacionArray.add(bakersgame.getTablero().getFundaciones().get(indice).pop());
        }
        Collections.reverse(pilaFundacionArray);

        return pilaFundacionArray;
    }

    /**
     * Metodo que restaura una fundacion
     * @param pilaArray
     * @param indice
     */
    public void transformarPilaArrayToPila(ArrayList<Carta> pilaArray, int indice) {
        for (int i = 0; i < pilaArray.size(); ++i) {
            bakersgame.getTablero().getFundaciones().get(indice).push(pilaArray.removeLast());
        }
    }

    public ArrayList<Carta> transformarCascadaArray(int indice) {
        ArrayList<Carta> cascadaCarta = new ArrayList<>();
        while (!bakersgame.getTablero().getTableau().get(indice).listaVacia()) {
            cascadaCarta.add(bakersgame.getTablero().getTableau().get(indice).eliminarInicio());
        }
        return cascadaCarta;
    }

    public void transformarCascadaLista(ArrayList<Carta> cascadaCarta, int indice) {
        // Restauramos de forma inversa si insertarInicio mete al principio
        while (!cascadaCarta.isEmpty()) {
            Carta carta = cascadaCarta.removeFirst();
            bakersgame.getTablero().getTableau().get(indice).insertarFin(carta);
        }
    }

    public ArrayList<Carta> transformarFundaciones()
    {
        return null;
    }


}
