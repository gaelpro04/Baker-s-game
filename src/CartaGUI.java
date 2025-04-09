import javax.swing.*;
import java.awt.*;

public class CartaGUI {
    private Carta carta;
    private JLabel cartaImagen;

    public CartaGUI()
    {
        carta = new Carta();
        cartaImagen = new JLabel();
    }

    public CartaGUI(Carta carta, ImageIcon imagen)
    {
        this.carta = carta;
        cartaImagen = new JLabel(imagen);
    }

    public CartaGUI(Carta carta)
    {
        this.carta = carta;
        cartaImagen = new JLabel();
    }

    public Carta getCarta() {
        return carta;
    }

    public void setCarta(Carta carta) {
        this.carta = carta;
    }

    public JLabel getCartaImagen() {
        return cartaImagen;
    }

    public void setCartaImagen(JLabel cartaImagen) {
        this.cartaImagen = cartaImagen;
    }

    public void setImagen(ImageIcon imagen, int x, int y)
    {
        Image imagen1 = imagen.getImage().getScaledInstance(x,y, Image.SCALE_SMOOTH);
        cartaImagen.setIcon(new ImageIcon(imagen1));
    }
}
