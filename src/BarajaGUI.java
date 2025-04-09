import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BarajaGUI {

    private ArrayList<CartaGUI> barajaGUI;

    public BarajaGUI(Baraja baraja)
    {
        barajaGUI = new ArrayList<>(52);
        String[] palos = {"diamonds", "hearts", "clubs", "spades"};
        for (int i = 0; i < 4; i++) {
            String palo = palos[i];
            for (int j = 1; j <= 13; j++) {
                Carta carta = baraja.sacarInicio();
                CartaGUI cartaGUI = new CartaGUI(carta);
                cartaGUI.setImagen(new ImageIcon(crearURL(j,palo)),100,140);
                barajaGUI.add(cartaGUI);
            }
        }
    }

    private String crearURL(int i, String palo)
    {
        return "C:\\Users\\Usuario\\IdeaProjects\\Baker-s-game\\PNG-cards-1.3\\"+ i +"_of_"+ palo +".png";
    }

    public void probarBaraja()
    {
        JFrame frame = new JFrame("Prueba");
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        for (int i = 0; i < barajaGUI.size(); ++i) {
            panel.add(barajaGUI.get(i).getCartaImagen());
        }

        frame.add(panel, BorderLayout.CENTER);
        frame.setSize(1000,1000);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public ListaDobleCircular<Carta> getCartasLogicas()
    {
        ListaDobleCircular<Carta> baraja = new ListaDobleCircular<>();
        for (int i = 0; i < 52; ++i) {
            baraja.insertarFin(barajaGUI.get(i).getCarta());
        }

        return baraja;
    }

    public ArrayList<CartaGUI> getBarajaGUI() {
        return barajaGUI;
    }

    public void setBarajaGUI(ArrayList<CartaGUI> barajaGUI) {
        this.barajaGUI = barajaGUI;
    }
}
