import java.util.ArrayList;
import java.util.Collections;

//Clase que modela una baraja utilizando listasDoblesCirculares
public class Baraja {

    //Atributos esenciales, uno para la baraja donde se almacenarán las cartas y otra es un banco para asignar
    //los palos
    private ListaDobleCircular<Carta> baraja;
    private String[] palos = {"Diamante", "Corazon", "Trebol", "Pico"};

    /**
     * Constructor preterminado, donde genera las cartas
     */
    public Baraja()
    {
        //Inicialización de la baraja junto a su generación
        baraja = new ListaDobleCircular<>();
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 13; ++j) {
                baraja.insertarFin(new Carta((j+1), palos[i]));
            }
        }
    }

    /**
     * Método donde muestra todas las cartas de la baraja
     * @return
     */
    public String mostrarBaraja()
    {
        return baraja.mostrarRecursivo();
    }

    /**
     * Método que determina si la baraja esta vacía
     * @return
     */
    public boolean barajaVacia()
    {
        return baraja.listaVacia();
    }

    /**
     * Método que determina el tamaño actual de una baraja
     * @return
     */
    public int tamanio()
    {
        return baraja.tamanio();
    }

    /**
     * Método encargado para revolver las cartas de la lista.
     */
    public void revolver()
    {
        //Se inicializa el objeto para almacenar todas las cartas(Se utiliza una arrayList para poder
        // usar Collectiones y no sea complicado revolver las cartas)
        ArrayList<Carta> barajaArevolver = new ArrayList<>(52);

        //Ciclo para sacar las cartas de la baraja(de la listaDobleCircular) y meterlas en el ArrayList(donde se revolverán)
        for (int i = 0; i < 52; ++i) {
            barajaArevolver.add(baraja.eliminarInicio());
        }

        //Utilizando Collections, revolvemos las cartas del ArrayList
        Collections.shuffle(barajaArevolver);

        //Ciclo para ahora sacar las cartas del arrayList(ya revueltas) a la baraja(lista doble circular)
        for (int i = 0; i < 52; ++i) {
            baraja.insertarFin(barajaArevolver.removeFirst());
        }
        System.out.println("Baraja revolvida");
    }

    /**
     * Método para sacar todas las cascadas a meter al tableau de la baraja
     * @return
     */
    public ArrayList<ListaSimple<Carta>> sacarCascadas()
    {
        //Se crea el objeto el cual regresaremos con todas las cascadas generadas
        ArrayList<ListaSimple<Carta>> cascadas = new ArrayList<>(8);

        //Ciclo para sacar cada cascada y meterlo al ArrayList de cascadas
        for (int i = 0; i < 8; ++i) {

            //Se crea una lista simple de cascadas para meter las cartas de baraja aquí
            ListaSimple<Carta> cascada = new ListaSimple<>();

            //Condición para determinar la cantidad de cartas a meter, como son 52, si la dividimos entre 8 nos da decimal
            //entonces quedaría 4 de 6 y 4 de 7
            if (i <= 3) {
                //Ciclo que mete las seis cartas que representan una cascada
                for (int j = 0; j < 6; ++j) {
                    cascada.insertarFin(baraja.eliminarInicio());
                }
            } else {
                //Ciclo que mete 7 cartas que representan una cascada
                for (int j = 0; j < 7; ++j) {
                    cascada.insertarFin(baraja.eliminarInicio());
                }
            }
            //Ya terminados los cilos internos, se mete las listaSimple generada(cascada) al arrayList de cascadas
            cascadas.add(cascada);
        }

        //Por ultimo se regresan las cascadas
        return cascadas;
    }
}
