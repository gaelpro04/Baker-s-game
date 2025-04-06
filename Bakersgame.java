import java.util.List;
import java.util.Scanner;

public class Bakersgame {

    private Baraja baraja;
    private Tablero tablero;
    private Pila<Carta> undo;

    public Bakersgame()
    {
        baraja = new Baraja();
        tablero = new Tablero();
        undo = new Pila<>();
    }

    public void iniciarJuego()
    {
        String opcion;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Baker's Game!!=================");
        while (!determinarCartasNoDisp()) {

            tablero.actualizarAses();
            tablero.mostrarTablero();
            Carta cartaTemp = new Carta();

            System.out.println("a) Mover carta");
            System.out.println("b) Hint");
            System.out.println("c) Undo");
            System.out.println("d) reiniciar");
            opcion = scanner.next();

            switch (opcion) {
                case "a":
                    System.out.println("Ingresa el palo de la carta");
                    cartaTemp.setPalo(scanner.next());
                    System.out.println("Ingresa el valor de la carta");
                    cartaTemp.setValor(scanner.nextInt());

                    if (localizarCarta(cartaTemp).equals("tableau")) {
                        System.out.println("Ingrese la cascada");
                        if (moverCartaEnTableau(cartaTemp, scanner.nextInt())) {
                            System.out.println("Movimiento exitoso");
                        } else {
                            System.out.println("Movimiento invalido");
                        }
                    } else if (localizarCarta(cartaTemp).equals("freecell")) {

                    } else {
                        System.out.println("Carta invalida");
                    }
                    break;
                case "b":
                    System.out.println("b");
                    break;
                case "c":
                    System.out.println("c");
                    break;
                case "d":
                    System.out.println("d");
                    break;
                default:
                    System.out.println("Invalido");
            }
        }
    }

    public String localizarCarta(Carta carta)
    {

        for (int i = 0; i < tablero.getTableau().size(); ++i) {
            ListaSimple<Carta> cascada = tablero.getTableau().get(i);

            if (cascada.buscar(carta)) {
                return "tableau";
            }
        }

        for (int i = 0; i < tablero.getFreeCells().size(); ++i) {
            Carta cartaFreeCell = tablero.getFreeCells().get(i);

            if (cartaFreeCell.equals(carta)) {
                return "freecell";
            }
        }

        return "null";
    }

    /**
     * Método para hacer y verificar movimientos en el tableau
     * @param carta
     * @param cascadaIndex
     * @return
     */
    public boolean moverCartaEnTableau(Carta carta, int cascadaIndex)
    {
        --cascadaIndex;
        //Se crea una instancia donde se almacenarán las cartas que se quieren mover(puede ser desde una carta hasta más)
        ListaSimple<Carta> cascadaMovimiento = new ListaSimple<>();
        ListaSimple<Carta> destino = tablero.getTableau().get(cascadaIndex);

        //Ciclo para verificar en que cascada se encuentra la carta que queremos mover
        for (int i = 0; i < tablero.getTableau().size(); ++i) {

            //Se crea una instancia de la cascada actual a partir del tablero
            ListaSimple<Carta> cascada = tablero.getTableau().get(i);

            //Si la cascada actual se encuentra la carta que se quiere mover se procede a sacarla
            if (cascada.buscar(carta)) {

                //Se obtiene la primer carta de donde se quiere eliminar.
                Carta actual = cascada.eliminarFin();
                cascadaMovimiento.insertarInicio(actual);

                //Mientras que la carta actual no sea igual a la que se identificó(en algún punto llegará)
                while (!actual.equals(carta)) {

                    //Se obtiene la carta próxima a sacar para compararlo con la carta actual(si son secuenciales)
                    Carta siguiente = cascada.visualizarFin();
                    if (esSecuencialCon(siguiente, actual)) {

                        //Si se cimple la carte eliminada será la nueva actual y se mete a la cascada o carta que se moverá
                        actual = cascada.eliminarFin();
                        cascadaMovimiento.insertarInicio(actual);
                    } else {
                        return false;
                    }
                }

                //Ya una vez obtenido la carta o la cascada se procede a ver si es valido moverla donde específico el
                // usuario. Primero se verifica si el tamaño de las cartas obtenidas corresponde al calculo de cuantas
                //cartas de pueden mover(ya sea igual o menor)
                if (cascadaMovimiento.tamanio() <= calcularCascadaMovible()) {

                    //Ahora se analiza los posibles casos.

                    //Si la cascada donde se quiere meter la o las carta/s está vacía y la carta que se identificó
                    //es igual a rey entonces si es valido y se procede a meterlas ahí
                    if (puedeInsertarse(destino, carta)) {
                        moverCartas(cascadaMovimiento, destino);
                        return true;

                        //En dado caso que no se cumpla simplemente es invaldia y la lista de las cartas sacadas se vuelven a
                        //meter
                    } else {
                        System.out.println("Movimiento invalido");
                        regresarCartas(cascadaMovimiento, cascada);
                        return false;
                    }
                    //Si la lista de cartas obtenidas es mayor entonces no puede ser valida el movimiento
                } else {
                    System.out.println("Cantidad de cartas invalida");
                    regresarCartas(cascadaMovimiento, cascada);
                    return false;
                }
            } else {
                System.out.println("No se encuentra");
            }
        }

        return false;
    }

    private boolean puedeInsertarse(ListaSimple<Carta> destino, Carta carta)
    {
        if (destino.listaVacia() && carta.getValor() == 13) {
            return true;
        } else if ((destino.visualizarFin().getValor()-1) == carta.getValor() && destino.visualizarFin().getPalo().equals(carta.getPalo())) {
            return true;
        }
        return false;
    }

    private void moverCartas(ListaSimple<Carta> cascadaMovimiento, ListaSimple<Carta> destino)
    {
        while (!cascadaMovimiento.listaVacia()) {
            destino.insertarFin(cascadaMovimiento.eliminarInicio());
        }
    }

    private void regresarCartas(ListaSimple<Carta> cascadaMovimiento, ListaSimple<Carta> emisor)
    {
        while (!cascadaMovimiento.listaVacia()) {
            emisor.insertarFin(cascadaMovimiento.eliminarInicio());
        }
    }

    /**
     * Método para obtener la cantidad de cartas que se pueden mover en una cascada
     * @return
     */
    public int calcularCascadaMovible()
    {

        int freeCellsLibres = 0;
        for (int i = 0; i < tablero.getFreeCells().size(); ++i) {

            if (tablero.getFreeCells().get(i).getValor() == -1) {
                ++freeCellsLibres;
            }
        }

        int cascadasVacias = 0;
        for (int i = 0;i < tablero.getTableau().size(); ++i) {

            if (tablero.getTableau().get(i).listaVacia()) {
                ++cascadasVacias;
            }
        }

        return (freeCellsLibres + 1) * (cascadasVacias + 1);

    }

    public boolean determinarCartasNoDisp()
    {
        boolean aja = false;




        return aja;
    }

    public void moverCarta()
    {

    }

    public boolean esSecuencialCon(Carta superior, Carta inferior) {
        return superior.getValor() == inferior.getValor() + 1 &&
                superior.getPalo().equals(inferior.getPalo());
    }
}
