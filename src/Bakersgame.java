import java.util.*;

public class Bakersgame {

    private Baraja baraja;
    private Tablero tablero;
    private Pila<Carta> undo;
    private Pila<Integer> undoNumeroLugar;
    private Pila<String> undoLugar;



    public Bakersgame(Baraja baraja)
    {
        this.baraja = baraja;
        tablero = new Tablero(this.baraja);
        undo = new Pila<>(10000);
        undoLugar = new Pila<>(10000);
        undoNumeroLugar = new Pila<>(100000);
    }

    public void iniciarJuego()
    {
        String opcion;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Baker's Game!!=================");
        while (!determinarCartasNoDisp() && !determinarVictoria()) {

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

                        System.out.println("A donde lo quieres mover");
                        System.out.println("a) Fundacion");
                        System.out.println("b) Tableau");
                        System.out.println("c) Free cell");
                        opcion = scanner.next();
                        switch (opcion) {
                            case "a":
                                System.out.println("Ingresa la fundacion");
                                if (moverCartaAFundacion(cartaTemp, scanner.nextInt())) {
                                    System.out.println("Movida exitosamente");
                                } else {
                                    System.out.println("Movimiento invalido");
                                }
                                break;
                            case "b":
                                System.out.println("Ingrese la cascada");
                                if (moverCartaEnTableau(cartaTemp, scanner.nextInt())) {
                                    System.out.println("Movimiento exitoso");
                                } else {
                                    System.out.println("Movimiento invalido");
                                }
                                break;
                            case "c":
                                System.out.println("Ingresa a que free cell lo quieres ingresar");
                                if (freCellYTableau(cartaTemp, "tableau", scanner.nextInt())) {
                                    System.out.println("Movimiento exitoso");
                                } else {
                                    System.out.println("Movimiento invalido");
                                }
                                break;
                        }


                    } else if (localizarCarta(cartaTemp).equals("freecell")) {

                        System.out.println("A donde lo quieres mover");
                        System.out.println("a) Fundacion");
                        System.out.println("b) Tableau");
                        opcion = scanner.next();

                        switch (opcion) {
                            case "a":
                                System.out.println("Ingresa la fundacion");
                                if (moverCartaAFundacion(cartaTemp, scanner.nextInt())) {
                                    System.out.println("Movida exitosamente");
                                } else {
                                    System.out.println("Movimiento invalido");
                                }
                                break;
                            case "b":
                                System.out.println("Ingrese la cascada");
                                if (freCellYTableau(cartaTemp, "freecell", scanner.nextInt())) {
                                    System.out.println("Movimiento exitoso");
                                } else {
                                    System.out.println("Movimiento invalido");
                                }
                                break;

                        }

                    } else {
                        System.out.println("Carta invalida");
                    }
                    break;
                case "b":
                    System.out.println("Pista");
                    ListaSimple<Carta> hint = hint();
                    if (!hint.listaVacia()) {
                        System.out.println(hint.mostrar());
                    } else {
                        System.out.println("No hay pistas por el momento");
                    }
                    break;
                case "c":
                    if (undo()) {
                        System.out.println("Undo hecho");
                    } else {
                        System.out.println("Undo invalido");
                    }
                    break;
                case "d":
                    tablero = new Tablero(baraja);
                    undo = new Pila<>(10000);
                    undoLugar = new Pila<>(10000);
                    undoNumeroLugar = new Pila<>(100000);
                    break;
                default:
                    System.out.println("Invalido");
            }
        }

        if (determinarCartasNoDisp()) {
            System.out.println("Has perdido!!!!");
        } else if (determinarVictoria()) {
            System.out.println("Has ganado!!!!1");
        }
    }

    /**
     * Metodo que simula el undo en el juego
     * @return
     */
    public boolean undo()
    {
        boolean postBooelan = false;
        if (!undo.pilaVacia()) {
            if (localizarCarta(undo.peek()).equals("tableau")) {
                int indiceTemp = undoNumeroLugar.peek();
                while (!undoLugar.pilaVacia() && undoLugar.peek().equals("tableau") && indiceTemp == undoNumeroLugar.peek()) {
                    System.out.println("UNDO ACTUAL: " + undo.peek() + " INDICE DONDE PERTENECE: " + undoNumeroLugar.peek() + " LUGAR: " + undoLugar.peek());
                    if (tablero.localizarCartaTableau(undo.peek()) == -1) {
                        System.out.println("ERROR: Carta no encontrada en ninguna cascada: " + undo.peek());
                        break; // o break, dependiendo del contexto
                    }
                    tablero.getTableau().get(tablero.localizarCartaTableau(undo.peek())).eliminarX(undo.peek());
                    tablero.getTableau().get(undoNumeroLugar.pop()).insertarFin(undo.pop());
                    undoLugar.pop();
                    postBooelan = true;
                } if (postBooelan) {
                    return true;
                } else if (undoLugar.peek().equals("freecell")) {
                    tablero.getTableau().get(tablero.localizarCartaTableau(undo.peek())).eliminarFin();
                    if (tablero.getFreeCells().get(undoNumeroLugar.peek()) == null || tablero.getFreeCells().get(undoNumeroLugar.peek()).getValor() == -1) {
                        tablero.getFreeCells().set(undoNumeroLugar.pop(), undo.pop());
                        undoLugar.pop();
                        return true;
                    }
                }
            } else if (localizarCarta(undo.peek()).equals("freecell")) {
                for (int i = 0; i < tablero.getFreeCells().size(); i++) {
                    if (undo.peek().equals(tablero.getFreeCells().get(i))) {
                        tablero.getFreeCells().set(i, new Carta());
                        break;
                    }
                }
                if (undoLugar.peek().equals("tableau")) {
                    tablero.getTableau().get(undoNumeroLugar.pop()).insertarFin(undo.pop());
                    undoLugar.pop();
                    return true;
                }

            } else if (localizarCarta(undo.peek()).equals("fundacion")) {
                for (int i = 0; i < tablero.getFundaciones().size(); i++) {
                    Pila<Carta> fundacion = tablero.getFundaciones().get(i);
                    if (!fundacion.pilaVacia() && fundacion.peek().equals(undo.peek())) {
                        System.out.println("Deshaciendo carta: " + undo.peek());
                        fundacion.pop();
                        break;
                    }
                }

                if (undoLugar.peek().equals("tableau")) {
                    tablero.getTableau().get(undoNumeroLugar.pop()).insertarFin(undo.pop());
                    undoLugar.pop();
                    return true;
                } else if (undoLugar.peek().equals("freecell")) {
                    tablero.getFreeCells().set(undoNumeroLugar.pop(), undo.pop());
                    undoLugar.pop();
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Metodo que simula Hint del juego, devuelve la carta a mover y en que carta colocar
     * @return
     */
    public ListaSimple<Carta> hint()
    {
        //Se crea la lista que se regresara, corresponde a la carta a mover y en que carta colocar
        ListaSimple<Carta> hint = new ListaSimple<>();
        //Coleccion que contiene las ultimas 8 cartas del tableau
        ArrayList<Carta> ultimasCartas = new ArrayList<>(8);

        //Ciclo para obtener el ultimo elemento de cada cascada
        for (int i = 0; i < tablero.getTableau().size(); i++) {
            if (!tablero.getTableau().get(i).listaVacia()) {
                ultimasCartas.add(tablero.getTableau().get(i).visualizarFin());
            } else {
                ultimasCartas.add(new Carta());
            }
        }

        //Ciclo que verifica si alguna de las ultimas cartas de las cascadas
        // es valida con alguna fundacion
        for (int i = 0; i < ultimasCartas.size(); i++) {
            for (int j = 0; j < tablero.getFundaciones().size(); j++) {
                if (!tablero.getFundaciones().get(j).pilaVacia()) {
                    if (esSecuencialCon(ultimasCartas.get(i), tablero.getFundaciones().get(j).peek())) {
                        hint.insertarFin(ultimasCartas.get(i));
                        hint.insertarFin(tablero.getFundaciones().get(j).peek());
                        return hint;
                    }
                }

            }
        }

        //Ciclo que verifica si alguna de las cartas en free cells es compatible con alguna fundacion
        for (int i = 0; i < tablero.getFreeCells().size(); i++) {
            if (tablero.getFreeCells().get(i).getValor() >= 1) {
                for (int j = 0; j < tablero.getFundaciones().size(); j++) {
                    if (!tablero.getFundaciones().get(j).pilaVacia() && esSecuencialCon(tablero.getFreeCells().get(i), tablero.getFundaciones().get(j).peek())) {
                        hint.insertarFin(tablero.getFreeCells().get(i));
                        hint.insertarFin(tablero.getFundaciones().get(j).peek());
                        return hint;
                    }
                }
            }
        }

        //Ciclo que verifica si alguna de las cartas en free cells es compatible con alguna cascada(su ultimo elemento)
        for (int i = 0; i < tablero.getFreeCells().size(); i++) {
            if (tablero.getFreeCells().get(i).getValor() >= 1) {
                for (int j = 0; j < tablero.getTableau().size(); j++) {
                    if (!tablero.getTableau().get(j).listaVacia()) {
                        if (tablero.getTableau().get(j).visualizarFin().getValor() >= 1) {
                            if (esSecuencialCon(tablero.getTableau().get(j).visualizarFin(), tablero.getFreeCells().get(i))) {
                                hint.insertarFin(tablero.getFreeCells().get(i));
                                hint.insertarFin(tablero.getTableau().get(j).visualizarFin());
                                return hint;
                            }
                        }
                    } else if (tablero.getFreeCells().get(i).getValor() == 13) {
                        hint.insertarFin(tablero.getFreeCells().get(i));
                        hint.insertarFin(new Carta());
                    }
                }
            }
        }

        //Ciclo que verifica si una cascada es compatible con alguna otra cascada del mismo tableau
        ListaSimple<Carta> cascadaMovimiento = new ListaSimple<>();
        for (int i = 0; i < tablero.getTableau().size(); ++i) {
            ListaSimple<Carta> cascada = tablero.getTableau().get(i);

            if (cascada.buscar(ultimasCartas.get(i))) {

                Carta actual = cascada.eliminarFin();
                cascadaMovimiento.insertarInicio(actual);

                if (cascada.visualizarFin() != null) {
                    while (esSecuencialCon(cascada.visualizarFin(), actual)) {

                        actual = cascada.eliminarFin();
                        cascadaMovimiento.insertarInicio(actual);
                        if (cascada.visualizarFin() == null) {
                            break;
                        }
                    }
                }

                if (cascadaMovimiento.tamanio() <= calcularCascadaMovible()) {
                    hint.insertarInicio(cascadaMovimiento.visualizarInicio());

                    for (int j = 0; j < tablero.getTableau().size(); ++j) {
                        if (i == j || tablero.getTableau().get(j).listaVacia()) {
                            continue;
                        } else if (!tablero.getTableau().get(j).listaVacia() && esSecuencialCon(tablero.getTableau().get(j).visualizarFin(), hint.visualizarInicio())) {
                            System.out.println(tablero.getTableau().get(j).visualizarFin() + "///" + hint.visualizarInicio());
                            hint.insertarFin(tablero.getTableau().get(j).visualizarFin());
                            regresarCartas(cascadaMovimiento, cascada);
                            System.out.println("Entro en cascada");
                            return hint;
                        }
                    }
                }
                while (!hint.listaVacia()) {
                    hint.eliminarInicio();
                }
                regresarCartas(cascadaMovimiento, cascada);

            }
        }
        while (!hint.listaVacia()) {
            hint.eliminarInicio();
        }

        //Ciclo que verifica si alguna carta de las ultimas de cada cascada es compatible con alguna del mismo tableau
        for (int i = 0; i < ultimasCartas.size(); i++) {
            for (int j = 0; j < tablero.getTableau().size(); j++) {
                if (i == j || tablero.getTableau().get(j).listaVacia()) {
                    if (tablero.getTableau().get(j).listaVacia()) {
                        if (ultimasCartas.get(i).getValor() == 13) {
                            hint.insertarFin(ultimasCartas.get(i));
                            hint.insertarFin(new Carta());
                            return hint;
                        }
                    }
                } else if (!tablero.getTableau().get(j).listaVacia() && esSecuencialCon(tablero.getTableau().get(j).visualizarFin(), ultimasCartas.get(i))) {
                    hint.insertarFin(ultimasCartas.get(i));
                    hint.insertarFin(tablero.getTableau().get(j).visualizarFin());
                    return hint;
                }
            }
        }


        return hint;
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

        //Este ciclo solamente se accede si se usa undo
        for (int i = 0; i < 4; ++i) {
            Pila<Carta> fundacion = tablero.getFundaciones().get(i);
            if (!fundacion.pilaVacia()) {
                if (fundacion.peek().equals(carta)) {
                    return "fundacion";
                }
            }

        }

        return "null";
    }

    public boolean freCellYTableau(Carta carta, String origen, int indexDestino)
    {

        System.out.println(indexDestino);
        if (origen.equals("tableau")) {
            ListaSimple<Carta> cascada = tablero.getTableau().get(tablero.localizarCartaTableau(carta));

            if (cascada.visualizarFin().equals(carta) && tablero.getFreeCells().get(indexDestino).getValor() == -1) {
                undo.push(cascada.visualizarFin());
                undoNumeroLugar.push(tablero.localizarCartaTableau(carta));
                undoLugar.push("tableau");
                tablero.getFreeCells().set(indexDestino, cascada.eliminarFin());

                return true;
            } else {
                System.out.println("No se puede meter al free cell");
            }
        } else if (origen.equals("freecell")) {
            ListaSimple<Carta> cascada = tablero.getTableau().get(indexDestino);

            for (int i = 0; i < tablero.getFreeCells().size(); ++i) {
                if (tablero.getFreeCells().get(i).equals(carta)) {
                    if (cascada.listaVacia() && carta.getValor() == 13) {
                        undo.push(tablero.getFreeCells().get(i));
                        undoNumeroLugar.push(i);
                        undoLugar.push("freecell");
                        System.out.println(tablero.getFreeCells().get(i));
                        cascada.insertarFin(tablero.getFreeCells().get(i));
                        tablero.getFreeCells().set(i, new Carta());
                        return true;
                    }  else if (!cascada.listaVacia() && esSecuencialCon(cascada.visualizarFin(), carta)) {
                        undo.push(tablero.getFreeCells().get(i));
                        undoNumeroLugar.push(i);
                        undoLugar.push("freecell");
                        System.out.println(tablero.getFreeCells().get(i));
                        cascada.insertarFin(tablero.getFreeCells().get(i));
                        tablero.getFreeCells().set(i, new Carta());
                        return true;
                    } else {
                        System.out.println("No se puede mover al tableau");
                    }
                }
            }
        }
        return false;
    }

    public boolean moverCartaAFundacion(Carta carta, int indiceFundacion)
    {
        if (indiceFundacion < tablero.getFundaciones().size() && indiceFundacion >= 0) {
            Pila<Carta> fundacion = tablero.getFundaciones().get(indiceFundacion);

            if (localizarCarta(carta).equals("freecell")) {
                if (fundacion.pilaVacia() && carta.getValor() == 1) {
                    for (int i = 0; i < tablero.getFreeCells().size(); ++i) {
                        if (tablero.getFreeCells().get(i).equals(carta)) {
                            undo.push(tablero.getFreeCells().get(i));
                            undoNumeroLugar.push(i);
                            undoLugar.push("freecell");
                            fundacion.push(tablero.getFreeCells().get(i));
                            tablero.getFreeCells().set(i, new Carta());
                            return true;
                        }
                    }
                } else if (!fundacion.pilaVacia() && esSecuencialCon(carta, fundacion.peek())) {
                    //Localizar la carta que saco el usuario
                    for (int i = 0; i < tablero.getFreeCells().size(); ++i) {
                        if (tablero.getFreeCells().get(i).equals(carta)) {
                            undo.push(tablero.getFreeCells().get(i));
                            undoNumeroLugar.push(i);
                            undoLugar.push("freecell");
                            fundacion.push(tablero.getFreeCells().get(i));
                            tablero.getFreeCells().set(i, new Carta());
                            return true;
                        }
                    }
                }
            } else if (localizarCarta(carta).equals("tableau")) {
                ListaSimple<Carta> cascada = tablero.getTableau().get(tablero.localizarCartaTableau(carta));
                if (fundacion.pilaVacia() && carta.getValor() == 1 && cascada.visualizarFin().equals(carta)) {
                    undo.push(cascada.visualizarFin());
                    undoNumeroLugar.push(tablero.localizarCartaTableau(carta));
                    undoLugar.push("tableau");
                    fundacion.push(cascada.eliminarFin());
                    return true;
                } else if (!fundacion.pilaVacia() && esSecuencialCon(carta, fundacion.peek()) && cascada.visualizarFin().equals(carta)) {
                    undo.push(cascada.visualizarFin());
                    undoNumeroLugar.push(tablero.localizarCartaTableau(carta));
                    undoLugar.push("tableau");
                    fundacion.push(cascada.eliminarFin());
                    return true;
                } else {
                    System.out.println("Carta invalida");
                }
            } else {
                System.out.println("La carta no esta en un origen valido");
            }

        }
        return false;
    }

    /**
     * Método para hacer y verificar movimientos en el tableau
     * @param carta
     * @param cascadaIndex
     * @return
     */
    public boolean moverCartaEnTableau(Carta carta, int cascadaIndex)
    {
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
                undo.push(actual);
                undoNumeroLugar.push(i);
                undoLugar.push("tableau");
                System.out.println("Metido a undo: " + actual + " Indice del lugar: " + i + " Lugar: " + "tableau");

                //Mientras que la carta actual no sea igual a la que se identificó(en algún punto llegará)
                while (!actual.equals(carta)) {

                    //Se obtiene la carta próxima a sacar para compararlo con la carta actual(si son secuenciales)
                    Carta siguiente = cascada.visualizarFin();
                    if (esSecuencialCon(siguiente, actual)) {

                        //Si se cimple la carte eliminada será la nueva actual y se mete a la cascada o carta que se moverá
                        actual = cascada.eliminarFin();
                        cascadaMovimiento.insertarInicio(actual);
                        undo.push(actual);
                        undoNumeroLugar.push(i);
                        undoLugar.push("tableau");
                        System.out.println("Metido a undo: " + actual + " Indice del lugar: " + i + " Lugar: " + "tableau");

                    } else {
                        break;
                    }
                }

                //Ya una vez obtenido la carta o la cascada se procede a ver si es valido moverla donde específico el
                // usuario. Primero se verifica si el tamaño de las cartas obtenidas corresponde al calculo de cuantas
                //cartas de pueden mover(ya sea igual o menor)
                if (cascadaMovimiento.tamanio() <= calcularCascadaMovible()) {

                    //Ahora se analiza los posibles casos.

                    //Si la cascada donde se quiere meter la o las carta/s está vacía y la carta que se identificó
                    //es igual a rey entonces si es valido y se procede a meterlas ahí
                    if (puedeInsertarse(destino, carta, cascadaMovimiento)) {
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
            }
        }

        return false;
    }

    private boolean puedeInsertarse(ListaSimple<Carta> destino, Carta carta, ListaSimple<Carta> cascadaMovimiento)
    {
        if (destino.listaVacia() && carta.getValor() == 13 && cascadaMovimiento.buscar(carta)) {
            return true;
        } else if (!destino.listaVacia()) {
            if ((destino.visualizarFin().getValor()-1) == carta.getValor() && destino.visualizarFin().getPalo().equals(carta.getPalo()) && cascadaMovimiento.buscar(carta)) {
                return true;
            }
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
        return tablero.freeCellsLlenas() && hint().listaVacia();
    }

    public boolean determinarVictoria()
    {
        return tablero.FundacionesOrdenadas();
    }

    public boolean esSecuencialCon(Carta superior, Carta inferior) {
        return superior.getValor() == inferior.getValor() + 1 &&
                superior.getPalo().equals(inferior.getPalo());
    }

    /**
     * Metodo para actualizar los Ases dentro de la clase Bakers
     * @return
     */
    public boolean actualizarAses()
    {
        //Como el metodo de actualizar Ases no esta conectado con el undo de la clase, es necesario poder comunicarlo
        //por esto mismo es que se retorna la carta y el indice origen
        HashMap<Carta, Integer> asMovido = tablero.actualizarAses();
        if (!asMovido.isEmpty()) {

            for (Map.Entry<Carta, Integer> entry : asMovido.entrySet()) {
                Carta carta = entry.getKey(); // Obtener la clave (Carta)
                Integer indice = entry.getValue(); // Obtener el valor (Integer)

                undo.push(carta);
                undoNumeroLugar.push(indice);
                undoLugar.push("tableau");

                // Imprimir o procesar los valores
                System.out.println("Carta: " + carta + ", Índice: " + indice);
            }

            return true;
        }
        return false;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Area de getters y setters

    public Tablero getTablero() {
        return tablero;
    }

    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }

    public Pila<Carta> getUndo() {
        return undo;
    }

    public void setUndo(Pila<Carta> undo) {
        this.undo = undo;
    }

    public Pila<Integer> getUndoNumeroLugar() {
        return undoNumeroLugar;
    }

    public void setUndoNumeroLugar(Pila<Integer> undoNumeroLugar) {
        this.undoNumeroLugar = undoNumeroLugar;
    }

    public Pila<String> getUndoLugar() {
        return undoLugar;
    }

    public void setUndoLugar(Pila<String> undoLugar) {
        this.undoLugar = undoLugar;
    }
}
