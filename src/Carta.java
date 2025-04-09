import javax.swing.*;
import java.awt.*;

public class Carta {
    private int valor;
    private String palo;
    private ImageIcon imagen;

    public Carta()
    {
        valor = -1;
        palo = "//";
        imagen = new ImageIcon();
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

    public ImageIcon getImagen() {
        return imagen;
    }

    public void setImagen(ImageIcon imagen) {
        this.imagen = imagen;
    }

    public void setImagen(ImageIcon imagen, int x, int y)
    {
        Image imagen1 = imagen.getImage().getScaledInstance(x,y, Image.SCALE_SMOOTH);
        imagen.setImage(imagen1);
    }
}
