
/*=============================================================================
|  UnoRunner.java                                                             |
|-----------------------------------------------------------------------------|
|  Programmer:  Adrian Lock and Robin Yan                                     |
|  Last Modified:   Jan 18, 2024                                              |
|  Course:  ICS4U1                                                            |
|-----------------------------------------------------------------------------|
|  This class will run the Uno game by calling Uno.run() inside its main      |
|  method.                                                                    |
|=============================================================================*/
import java.text.SimpleDateFormat;
import java.util.*;
import Cards.*;
import Cards.Number;

public class UnoRunner {
    public final static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // simulation();
        Uno.run();
    }

    public static void simulation() {
        Deck fullDeck = new Deck("cards.txt");
        Deck drawDeck = new Deck(fullDeck);
        Deck discardDeck = new Deck();

        Card currentCard = drawDeck.drawRandom();
        String currentColour = currentCard.getColour();
        discardDeck.moveCard(drawDeck, currentCard);
        System.out.println(currentCard);
        // The drawDeck contains the same objects as the fullDeck but in different order
        // System.out.println(fullDeck);

        // System.out.println(discardDeck);

        Human vergil = new Human("Vergil");
        vergil.toggleTutorial();
        Cpu ken = new Cpu("Ken");
        for (int i = 0; i < 7; i++) {
            vergil.deck.moveCard(fullDeck, fullDeck.drawRandom());
            ken.deck.moveCard(fullDeck, fullDeck.drawRandom());
        }

        Card temporary;
        while (!(vergil.deck.isEmpty() || ken.deck.isEmpty())) {
            System.out.println("================================= YOUR TURN");
            temporary = vergil.play(currentCard, currentColour, drawDeck);
            if (temporary != null) {
                if (temporary.getColour().equals("black")) {
                    currentColour = "red";
                    System.out.println("Game set red");
                }
                currentCard = temporary;
                currentColour = currentCard.getColour();
                discardDeck.moveCard(vergil.deck, currentCard);
            }
            System.out.println("Card(s) left: " + vergil.deck.getNumCards());
            System.out.println("================================= CPU TURN");
            temporary = ken.play(currentCard, currentColour, drawDeck);

            if (temporary != null) {
                // System.out.println(temporary.getColour());
                if (temporary.getColour().equals("black")) {
                    currentColour = "red";
                    System.out.println("Game set red");
                }
                currentCard = temporary;
                currentCard.getColour();
                discardDeck.moveCard(ken.deck, currentCard);
                System.out.println("Cpu played " + currentCard);
            } else {
                // System.out.println("Cpu drew a card");
            }
            System.out.println("Card(s) left: " + ken.deck.getNumCards());
        }
        System.out.println(discardDeck);
    }
}
