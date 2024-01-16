import Cards.*;
import Cards.Number;

public class Main {
    public static void main(String[] args) {
        Card c = new Reverse("red");
        // System.out.println(String.valueOf(c.getClass()));
        Deck d = new Deck("cards.txt");
        // Deck g = new Deck();
        // System.out.println(d);
        // g.moveCard(d, c);
        // System.out.println(g);
        // System.out.println(d);
        // System.out.println(d.drawRandom());
        // d.superSort();
        // System.out.println(d);
        System.out.println(d.getCard(d.searchSpecificCard("green", "9")));
        // System.out.println(d.searchColourCards("yellow"));
        // System.out.println(d.searchColourTypeCards("red", "Skip"));
    }
}
