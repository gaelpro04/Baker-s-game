public class NodoDoble<T> {

    private T info;
    private NodoDoble<T> anterior;
    private NodoDoble<T> siguiente;

    public NodoDoble() {
        anterior = null;
        siguiente = null;
        info = null;
    }

    public NodoDoble(T info)
    {
        this.info = info;
        siguiente = null;
        anterior = null;
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    public NodoDoble<T> getAnterior() {
        return anterior;
    }

    public void setAnterior(NodoDoble<T> anterior) {
        this.anterior = anterior;
    }

    public NodoDoble<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoDoble<T> siguiente) {
        this.siguiente = siguiente;
    }
}