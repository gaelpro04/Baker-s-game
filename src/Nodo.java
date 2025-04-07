public class Nodo<T> {

    private T info;
    private Nodo<T> sig;

    public Nodo(T info)
    {
        sig = null;
        this.info = info;
    }

    public Nodo()
    {
        sig = null;
        info = null;
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    public Nodo<T> getSig() {
        return sig;
    }

    public void setSig(Nodo<T> sig) {
        this.sig = sig;
    }

    public String toString()
    {
        return (String) info;
    }
}
