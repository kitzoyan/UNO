import java.util.*;
import Cards.*;
import Cards.Number;

public class UnoRunner {
    public final static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Deck d = new Deck("cards.txt");
        // System.out.println(d);
        Card currentCard = new Number("green", 0);
        Human h = new Human("Vergil");

        for (int i = 0; i < 5; i++) {
            h.deck.moveCard(d, d.drawRandom());
        }
        // System.out.println(h.deck.getNumCards());
        h.toggleTutorial();
        h.play(currentCard, d);

    }

}
