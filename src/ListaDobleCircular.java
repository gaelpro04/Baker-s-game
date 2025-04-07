public class ListaDobleCircular<T> {
    NodoDoble<T> inicio;
    NodoDoble<T> fin;

    public ListaDobleCircular()
    {
        inicio = null;
        fin = null;
    }

    public void insertarInicio(T objeto)
    {
        NodoDoble<T> n = new NodoDoble<>();
        n.setInfo(objeto);

        if (inicio == null) {
            inicio = fin = n;
            n.setSiguiente(inicio);
            n.setAnterior(inicio);
        } else {
            n.setSiguiente(inicio);
            n.setAnterior(fin);
            inicio.setAnterior(n);
            inicio = n;
            fin.setSiguiente(inicio);

        }

    }

    public void insertarFin(T objeto)
    {
        NodoDoble<T> n = new NodoDoble<>();
        n.setInfo(objeto);

        if (inicio == null) {
            inicio = fin = n;
            n.setSiguiente(inicio);
            n.setAnterior(inicio);

        } else {
            n.setSiguiente(inicio);
            n.setAnterior(fin);
            fin.setSiguiente(n);
            fin = n;
            inicio.setAnterior(n);
        }

    }

    public T eliminarInicio()
    {
        T objeto = null;

        if (inicio == null) {
            System.out.println("Lista vacia");
        } else {
            objeto = inicio.getInfo();
            if (inicio == fin) {
                inicio = fin = null;
            }  else {
                inicio = inicio.getSiguiente();
                inicio.setAnterior(fin);
                fin.setSiguiente(inicio);
            }
        }

        return objeto;
    }

    public T eliminarFin()
    {
        T objeto = null;

        if (inicio == null) {
            System.out.println("Lista vacia");
        } else {
            objeto = fin.getInfo();
            if (inicio == fin) {
                inicio = fin = null;
            } else {
                NodoDoble<T> r = fin.getSiguiente();
                r.setSiguiente(inicio);
                inicio.setAnterior(r);
                fin = r;
            }
        }

        return objeto;
    }

    public void ordenarLista() {
        if (inicio == null || inicio == fin) {
            return;
        }

        boolean swapped;
        do {
            swapped = false;
            NodoDoble<T> actual = inicio;

            do {
                NodoDoble<T> siguiente = actual.getSiguiente();
                if (((Comparable<T>) actual.getInfo()).compareTo(siguiente.getInfo()) > 0) {
                    T temp = actual.getInfo();
                    actual.setInfo(siguiente.getInfo());
                    siguiente.setInfo(temp);

                    swapped = true;
                }
                actual = siguiente;
            } while (actual != fin);
        } while (swapped);
    }

    public String mostrarRecursivo() {
        if (inicio == null) {
            return "Lista vacía";
        }
        return mostrarRecursivo(inicio, new StringBuilder());
    }

    // Método recursivo privado que recorre la lista y construye la cadena de salida
    private String mostrarRecursivo(NodoDoble<T> nodo, StringBuilder builder) {
        builder.append(nodo.getInfo()).append(" ");

        if (nodo.getSiguiente() != inicio) {
            mostrarRecursivo(nodo.getSiguiente(), builder);
        }

        return builder.toString();
    }

    public boolean listaVacia()
    {
        return inicio == null;
    }

    public int tamanio()
    {
        if (inicio == null) {
            return 0;
        } else if (inicio.getSiguiente() == inicio) {
            return 1;
        } else {
            int contador = 1;
            NodoDoble<T> recorrer = inicio;

            while (recorrer.getSiguiente() != fin) {
                ++contador;
                recorrer = recorrer.getSiguiente();
            }
            return contador;
        }
    }
}
