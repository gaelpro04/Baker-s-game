public class Pila<T> {
    //Atributos de la pila
    private int tope;
    private T[] elemento;

    /**
     * Constructor por pretermindado donde tomará como valor máximo 10
     */
    public Pila() {

        elemento = (T[]) new Object[10];

        //Siempre se pone menos uno iniciando ya que quiere decir que está vacio
        tope = -1;
    }

    /**
     * Otro constructor que pide ocmo parametro máximo de elementos
     * @param tope
     */
    public Pila(int tope) {

        elemento = (T[]) new Object[tope];
        //Una vez utilizado el tope para indicar el número máximo de elementos, cambiamos el tope a -1 para hacer saber
        //que está vacio
        this.tope = -1;
    }

    /**
     * Método que regresa un valor booleano de acuerdo si está vacio la pila(si es -1)
     * @return
     */
    public boolean pilaVacia() {

        return tope == -1;
    }

    /**
     * Método para saber si está lleno la pila
     * @return
     */
    public boolean pilaLlena()
    {
        //Si el tope es igual a la longitud del arreglo - 1 (Es la cantidad de elementos que puede tener)
        return tope == elemento.length - 1;
    }

    /**
     * Método que mete elementos desde abajo
     * @param objeto
     */
    public void push(T objeto)
    {
        //Si la pila está llena ya no se puede meter datos
        if (pilaLlena()) {
            System.out.println("Desbordamiento");
            //caso contrario aumentamos el tope a 0(ya hay un elemento) y asignamos el valor
        } else {
            ++tope;
            elemento[tope] = objeto;

        }
    }

    /**
     * Método para sacar elementos de la pila
     * @return
     */
    public T pop()
    {

        T objeto = null;

        //Si la pila está vacia , no hay nada que sacar
        if (pilaVacia()) {
            System.out.println("No se puede");

            //Caso contrario solamente tomamos el valor del elemento para regresarlo y disminuimos el tope, simulando que ese
            //dato ya no existe
        } else {
            objeto = elemento[tope];
            --tope;
        }

        return objeto;
    }

    public T peek()
    {
        T objeto = null;

        if (pilaVacia()) {
            System.out.println("No se puede");
        } else {
            objeto = elemento[tope];
        }
        return objeto;
    }

    public int tamano() {
        return tope + 1;
    }
}
