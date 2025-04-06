import java.util.ArrayList;
import java.util.List;

public class Tablero {

    private ArrayList<Carta> freeCells;
    private ArrayList<Pila<Carta>> fundaciones;
    private ArrayList<ListaSimple<Carta>> tableau;
    private Baraja baraja;

    /**
     * Constructor preteminado donde inicializamos los atributos y se hace la incersión de cartas en el tableua
     */
    public Tablero()
    {
        baraja = new Baraja();
        baraja.revolver();
        freeCells = new ArrayList<>(4);
        for (int i = 0; i < 4; ++i) {
            freeCells.add(new Carta());
        }
        fundaciones = new ArrayList<>(4);
        for (int i = 0;i < 4; ++i) {
            fundaciones.add(new Pila<>());
        }

        //Ingreso de las cascadas
        ArrayList<ListaSimple<Carta>> cascadasInsercion = baraja.sacarCascadas();

        tableau = new ArrayList<>(8);
        for (int i =  0; i < 8; ++i) {
            tableau.add(cascadasInsercion.removeFirst());
        }
    }

    /**
     * Constructor que pide directamente los atributos
     * @param freeCells
     * @param fundaciones
     * @param tableau
     */
    public Tablero(ArrayList<Carta> freeCells, ArrayList<Pila<Carta>> fundaciones, ArrayList<ListaSimple<Carta>> tableau)
    {
        this.freeCells = freeCells;
        this.fundaciones = fundaciones;
        this.tableau = tableau;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Area de getters y setters

    public ArrayList<Carta> getFreeCells() {
        return freeCells;
    }

    public void setFreeCells(ArrayList<Carta> freeCells) {
        this.freeCells = freeCells;
    }

    public ArrayList<Pila<Carta>> getFundaciones() {
        return fundaciones;
    }

    public void setFundaciones(ArrayList<Pila<Carta>> fundaciones) {
        this.fundaciones = fundaciones;
    }

    public ArrayList<ListaSimple<Carta>> getTableau() {
        return tableau;
    }

    public void setTableau(ArrayList<ListaSimple<Carta>> tableau) {
        this.tableau = tableau;
    }

    public Baraja getBaraja() {
        return baraja;
    }

    public void setBaraja(Baraja baraja) {
        this.baraja = baraja;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Area de moviemientos y aspectos lógicos

    /**
     * Método que detecte automaticamente si hay un As en el ultimo elemento de la cascada para meterlo a alguna fundacion
     * disponible
     */
    public void actualizarAses()
    {
        //Ciclo para verificar cada cascada
        for (int i = 0; i < 8; ++i) {
            ListaSimple<Carta> cascada = tableau.get(i);

            //Se toma el ultimo elemento de la cascada actual sin eliminarla y se compara si es uno para mandarlo a la
            //fundación disponible
            Carta elementoComparado = cascada.visualizarFin();
            if (elementoComparado.getValor() == 1) {

                //Ciclo para ingresar la carta
                for (int j = 0; j < 4; ++j) {
                    Pila<Carta> fundacion = fundaciones.get(j);

                    //Checa en la iteración actual si la fundación está vacia para ingresar la A
                    if (fundacion.pilaVacia()) {
                        cascada.eliminarFin();
                        fundacion.push(elementoComparado);
                        break;
                    }
                }
            }
        }
    }

    public void mostrarTablero()
    {
        System.out.println("FUNDACIONES");
        for (int i = 0; i < 4; ++i) {
            if (fundaciones.get(i).pilaVacia()) {
                System.out.print("[   ] ");
            } else {
                System.out.print("[" + fundaciones.get(i).peek() + "] ");
            }

        }
        System.out.println();
        System.out.println("FREE CELLS");
        for (int i = 0; i < 4; ++i) {
            if (freeCells.get(i).getValor() == -1) {
                System.out.print("[   ] ");
            } else {
                System.out.print("[" + freeCells.get(i) + "] ");
            }

        }
        System.out.println();
        System.out.println("TABLEAU");
        for (int i = 0;i < 8; ++i) {
            System.out.println((i+1) + "]" + tableau.get(i).mostrar());
        }
    }
}
