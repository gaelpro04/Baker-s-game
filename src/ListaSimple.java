public class ListaSimple<T> {
    private Nodo<T> inicio;

    public ListaSimple()
    {
        inicio = null;
    }


    public void insertarInicio(T dato)
    {
        Nodo<T> nodo = new Nodo<>(dato);
        nodo.setSig(inicio);
        inicio = nodo;
    }

    public void insertarFin(T dato)
    {
        Nodo<T> nodo = new Nodo<>(dato);
        if (inicio == null) {
            inicio = nodo;
        } else {
            Nodo<T> r = inicio;
            while (r.getSig() != null) {
                r = r.getSig();
            }
            r.setSig(nodo);
            nodo.setSig(null);
        }
    }

    public T eliminarInicio()
    {
        T objeto = null;
        if (inicio == null) {
            System.out.println("Lista vacia");
        } else {
            objeto = inicio.getInfo();
            inicio = inicio.getSig();
        }

        return objeto;
    }

    public T eliminarFin()
    {
        T objeto = null;

        if (inicio == null) {
            System.out.println("Lista vacia");
        } else if (inicio.getSig() == null) {
            objeto = inicio.getInfo();
            inicio = null;
        } else {
            Nodo<T> r;
            Nodo<T> a;
            r = inicio;
            a = r;

            while (r.getSig() != null) {
                a = r;
                r = r.getSig();
            }

            a.setSig(null);
            objeto = r.getInfo();
        }
        return objeto;
    }

    public T visualizarFin()
    {
        T dato = null;
        if (inicio == null) {
            System.out.println("Lista vacia");
        } else if (inicio.getSig() == null) {
            dato = inicio.getInfo();
        } else {
            Nodo<T> r = inicio;

            while (r.getSig() != null) {
                r = r.getSig();
            }
            dato = r.getInfo();
        }
        return dato;
    }

    public T visualizarInicio()
    {
        T objeto = null;
        if (inicio == null) {
            System.out.println("Lista vacia");
        } else {
            objeto = inicio.getInfo();
        }

        return objeto;
    }

    public String mostrar()
    {
        Nodo<T> r = inicio;
        StringBuilder builder = new StringBuilder();

        while (r != null) {
            builder.append(r.getInfo() + " -> ");
            r = r.getSig();
        }
        return builder.append("null").toString();
    }

    public int tamanio()
    {
        int contador = 0;

        Nodo<T> nodo = inicio;
        while (nodo != null) {
            nodo = nodo.getSig();
            ++contador;
        }

        return contador;
    }

    public boolean listaVacia()
    {
        return inicio == null;
    }

    public T eliminarX(T dato)
    {
        T datoComp = null;
        if (inicio == null) {
            System.out.println("Lista vacia");
            return null;
        } else if (inicio.getInfo().equals(dato)) {
            datoComp = inicio.getInfo();
            inicio = inicio.getSig();
        } else {
            Nodo<T> nodo = inicio;

            while (nodo.getSig() != null) {

                if (nodo.getSig().getInfo().equals(dato)) {
                    datoComp = nodo.getSig().getInfo();
                    nodo.setSig(nodo.getSig().getSig());
                    break;
                }
                nodo = nodo.getSig();
            }
        }

        return datoComp;
    }

    public boolean buscar(T dato) {

        if (inicio == null) {
            System.out.println("Lista vacia");
            return false;
        } else if (inicio.getInfo().equals(dato)) {
            return true;
        } else {
            Nodo<T> nodoBasico = inicio;

            while(nodoBasico.getSig() != null) {

                if (nodoBasico.getSig().getInfo().equals(dato)) {
                    return true;
                } else {
                    nodoBasico = nodoBasico.getSig();
                }
            }
        }
        return false;
    }
}
