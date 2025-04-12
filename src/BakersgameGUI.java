import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;

public class BakersgameGUI {

    //Atributos logicos del juego
    private Bakersgame bakersgame;
    private Baraja baraja;
    private ArrayList<JLabel> posicionesFreeCell;
    private ArrayList<ArrayList<JLabel>> posicionesFundaciones;
    private ArrayList<ArrayList<JLabel>> posicionesCascadas;
    private ArrayList<Carta> origenDestino;

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
        bakersgame = new Bakersgame(baraja);
        origenDestino = new ArrayList<>(2);

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
                    JLabel labelPresionado = (JLabel) e.getSource();
                    origenDestino.add(identificarCarta((ImageIcon) labelPresionado.getIcon()));
                    System.out.println(identificarCarta((ImageIcon) labelPresionado.getIcon()));
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
                        JLabel labelPresionado = (JLabel) e.getSource();
                        origenDestino.add(identificarCarta((ImageIcon) labelPresionado.getIcon()));
                        System.out.println(identificarCarta((ImageIcon) labelPresionado.getIcon()));
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
                for (int j = 0; j < 7; ++j) {
                    JLabel labelCas = new JLabel();
                    labelCas.setBounds(mulX, mulY, 100, 140);
                    labelCas.addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            JLabel labelPresionado = (JLabel) e.getSource();
                            System.out.println(identificarCarta((ImageIcon) labelPresionado.getIcon()));
                            origenDestino.add(identificarCarta((ImageIcon) labelPresionado.getIcon()));

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
                for (int j = 0; j < 6; ++j) {
                    JLabel labelCas = new JLabel();
                    labelCas.setBounds(mulX, mulY, 100, 140);
                    labelCas.addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            JLabel labelPresionado = (JLabel) e.getSource();
                            origenDestino.add(identificarCarta((ImageIcon) labelPresionado.getIcon()));
                            System.out.println(identificarCarta((ImageIcon) labelPresionado.getIcon()));

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

        frame.add(panelControl, BorderLayout.SOUTH);
        frame.add(panelTableau, BorderLayout.CENTER);
        frame.add(panelSuperior, BorderLayout.NORTH);
        frame.setSize(1000,800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void actualizarTablero()
    {
        bakersgame.getTablero().actualizarAses();
        // Ciclo para poder meter las cartas actuales de tal cascada a la cascada GUI
        for (int i = 0; i < 8; ++i) {
            ArrayList<Carta> cascadaArray = transformarCascadaArray(i);
            ArrayList<JLabel> labelsCascada = posicionesCascadas.get(i);

            int mulX = 60;
            int tamanyo = cascadaArray.size();
            for (int j = 0; j < tamanyo; ++j) {
                if (j >= labelsCascada.size()) {
                    // Nueva carta, crear nuevo JLabel
                    JLabel labelCas = new JLabel();
                    labelCas.setIcon(cascadaArray.get(j).getImagen());

                    int indexDesdeAbajo = cascadaArray.size() - j;
                    int yBase = i < 4 ? 345 : 300;
                    int deltaY = 45;
                    int y = yBase - (indexDesdeAbajo * deltaY); // se va moviendo hacia arriba
                    labelCas.setBounds(40 + mulX, y, 100, 140);
                    labelCas.setOpaque(true);
                    labelCas.setBackground(colorPoker);

                    panelTableau.add(labelCas);
                    labelsCascada.add(labelCas); // Añadir al modelo visual también
                } else {
                    // Actualizar JLabel existente
                    JLabel labelCas = labelsCascada.get(j);
                    labelCas.setIcon(cascadaArray.get(j).getImagen());
                    labelCas.setOpaque(true);
                }
                mulX = mulX + 45;
            }

            // Si hay más JLabels que cartas en el modelo, remover los sobrantes
            while (labelsCascada.size() > cascadaArray.size()) {
                JLabel extra = labelsCascada.remove(labelsCascada.size() - 1);
                panelTableau.remove(extra);
            }

            transformarCascadaLista(cascadaArray, i); // Actualiza lógica
        }

        panelTableau.repaint();
        panelTableau.revalidate();

        // Ciclo para poder meter las cartas actuales del free cell logico a grafico
        for (int i = 0;i < posicionesFreeCell.size(); ++i) {
            if (bakersgame.getTablero().getFreeCells().get(i).getValor() == -1) {
                JLabel labelFC = posicionesFreeCell.get(i);
                if (labelFC.getIcon() != null) {
                    labelFC.setIcon(null);
                    labelFC.setBackground(colorPoker);
                }
            } else {
                JLabel labelFC = posicionesFreeCell.get(i);
                Carta carta = bakersgame.getTablero().getFreeCells().get(i);

                if (labelFC.getIcon() == null || !labelFC.getIcon().equals(carta.getImagen())) {
                    labelFC.setIcon(carta.getImagen());
                    labelFC.setOpaque(true);
                }
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
                    panelFundaciones.setComponentZOrder(labelFD,0);
                }
            } else if (pilaArray.size() < posicionesFundaciones.get(i).size()) {
                for (int j = 0; j < posicionesFundaciones.get(i).size(); ++j) {
                    if (pilaArray.size() < posicionesFundaciones.get(i).size() && j > pilaArray.size()-1) {
                        JLabel labelFD = posicionesFundaciones.get(i).get(j);
                        labelFD.setIcon(null);
                        labelFD.setVisible(false);
                        panelFundaciones.setComponentZOrder(labelFD,0);
                    } else {
                        JLabel labelFD = posicionesFundaciones.get(i).get(j);
                        labelFD.setIcon(pilaArray.get(j).getImagen());
                        labelFD.setOpaque(true);
                        labelFD.setVisible(true);
                    }
                }
            }

        }
        panelFundaciones.repaint();
        panelFundaciones.revalidate();
        panelSuperior.repaint();
        panelSuperior.revalidate();
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
            transformarPilaArrayPila(pilaArray, i);
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
                    for (int i = 0; i < bakersgame.getTablero().getFundaciones().size(); ++i) {
                        pilaTemp = bakersgame.getTablero().getFundaciones().get(i);
                        if (pilaTemp.peek().equals(destino)) {
                            break;
                        }
                    }
                    bakersgame.moverCartaAFundacion(origen, bakersgame.getTablero().getFundaciones().indexOf(pilaTemp));
                }
            } else if (bakersgame.localizarCarta(origen).equals("freecell")) {
                if (bakersgame.localizarCarta(destino).equals("tableau")) {
                    bakersgame.freCellYTableau(origen, "freecell", bakersgame.getTablero().getFreeCells().indexOf(destino));
                } else if (bakersgame.localizarCarta(destino).equals("fundacion")) {
                    // Se verifica en que pila se encuentra la carta
                    Pila<Carta> pilaTemp = null;
                    for (int i = 0; i < bakersgame.getTablero().getFundaciones().size(); ++i) {
                        pilaTemp = bakersgame.getTablero().getFundaciones().get(i);
                        if (pilaTemp.peek().equals(destino)) {
                            break;
                        }
                    }
                    bakersgame.moverCartaAFundacion(origen, bakersgame.getTablero().getFundaciones().indexOf(pilaTemp));
                }
            }
        }


        actualizarTablero();
        bakersgame.getTablero().mostrarTablero();
        return true;
    }

    public void iniciarJuego()
    {
        bakersgame.getTablero().mostrarTablero();
        actualizarTablero();

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
    public void transformarPilaArrayPila(ArrayList<Carta> pilaArray, int indice) {
        for (int i = 0; i < pilaArray.size(); ++i) {
            bakersgame.getTablero().getFundaciones().get(indice).push(pilaArray.removeLast());
        }
    }

    public ArrayList<Carta> transformarCascadaArray(int indice) {
        ArrayList<Carta> cascadaCarta = new ArrayList<>();
        while (!bakersgame.getTablero().getTableau().get(indice).listaVacia()) {
            cascadaCarta.add(bakersgame.getTablero().getTableau().get(indice).eliminarFin());
        }
        return cascadaCarta;
    }

    public void transformarCascadaLista(ArrayList<Carta> cascadaCarta, int indice) {
        // Restauramos de forma inversa si insertarInicio mete al principio
        while (!cascadaCarta.isEmpty()) {
            Carta carta = cascadaCarta.removeFirst();
            bakersgame.getTablero().getTableau().get(indice).insertarInicio(carta);
        }
    }

    public ArrayList<Carta> transformarFundaciones()
    {
        return null;
    }
}
