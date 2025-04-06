public class Carta {
    private int valor;
    private String palo;

    public Carta()
    {
        valor = -1;
        palo = "//";
    }

    public Carta(int valor, String palo)
    {
        this.valor = valor;
        this.palo = palo;
    }

    public Carta(Carta carta)
    {
        valor = carta.getValor();
        palo = carta.getPalo();
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getPalo() {
        return palo;
    }

    public void setPalo(String palo) {
        this.palo = palo;
    }

    public String toString()
    {
        return "[" + palo + "|" + valor + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Carta otraCarta = (Carta) obj;
        return this.valor == otraCarta.valor && this.palo.equals(otraCarta.palo);
    }
}
