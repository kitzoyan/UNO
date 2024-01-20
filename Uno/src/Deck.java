
/*=============================================================================
|  Deck.java                                                                  |
|-----------------------------------------------------------------------------|
|  Programmer:  Adrian Lock and Robin Yan                                     |
|  Last Modified:   Jan 18, 2024                                              |
|  Course:  ICS4U1                                                            |
|-----------------------------------------------------------------------------|
|  This class is the manager of the card objects. This class has a maximum of |
|  108 elements in the array and it will be able to move a card to another    |
|  deck, draw a random card from itself, sort the deck by color/type/size.    |
|=============================================================================*/
import java.io.*;
import Cards.*;
import Cards.Number;

public class Deck {
    private static final int MAX_CARDS = 108;
    private int numCards;
    private Card[] cards;

    /**
     * Create a new deck object using a file
     * 
     * @param file the file name in String
     */
    public Deck(String file) {
        cards = new Card[MAX_CARDS];
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            numCards = Integer.parseInt(reader.readLine());
            for (int i = 0; i < numCards; i++) {
                Card c = null;
                String colour = reader.readLine();
                String type = reader.readLine();
                c = makeNewCard(colour, type);
                cards[i] = c;
            }
        } catch (IOException e) {
            System.out.println("SYSTEM: (Deck) There was an error reading from the file: " + file);
        }
    }

    /**
     * Create an empty deck
     */
    public Deck() {
        numCards = 0;
        cards = new Card[MAX_CARDS];
    }

    /**
     * Create a new deck by replicating an identical list of the original. All cards
     * still point to the same objects.
     * 
     * @param other the explicit deck
     */
    public Deck(Deck other) {
        cards = new Card[MAX_CARDS];
        this.numCards = other.numCards;
        for (int i = 0; i < numCards; i++) {
            cards[i] = other.cards[i];
        }
    }

    /**
     * Generates a a new deck given the file to read from and range to read. This
     * should be called upon loading old decks. When the file reads the deck, it
     * will
     * find a matching Card from the already made fullDeck and copy it to this deck.
     * 
     * @param fileSlot the file to read from containg game information
     * @param start    the starting line of the deck
     * @param end      the line after the last line of the deck (if the deck ends at
     *                 index 3, pass in
     *                 4)
     * @param fullDeck the fullDeck from Uno
     * 
     * @deprecated this method may not actually be used. Game class may be the one
     *             making and assigning decks.
     */
    public Deck(String fileSlot, int start, int end, Deck fullDeck) {
        cards = new Card[MAX_CARDS];
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileSlot));
            for (int i = 0; i < start; i++) {
                reader.readLine();
            }
            numCards = Integer.parseInt(reader.readLine());
            for (int i = 0; i < numCards; i++) {
                Card c = null;
                String colour = reader.readLine();
                String type = reader.readLine();
                c = makeNewCard(colour, type);
                cards[i] = c;
            }
        } catch (IOException e) {
            System.out.println("SYSTEM: (Deck) There was an error reading from the file: " + fileSlot);
        }
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < numCards; i++) {
            s += cards[i] + "\n";
        }
        return s;
    }

    /**
     * Makes a new card object with the given items
     * 
     * @param colour
     * @param type   a single capital letter String or class name represeting the
     *               type.
     *               (T = PlusTwo, F = PlusFour, C = ColourChange, R = Reverse, S =
     *               Skip, a whole number = Number)
     *               To create a Number card, input the digit instead of "N."
     * @return a card of identical attributes
     */
    private Card makeNewCard(String colour, String type) {
        Card c = null;
        if (colour.equals("black")) {
            if (type.equals("C") || type.equals("ColourChange")) {
                c = new ColourChange();
            } else if (type.equals("F") || type.equals("PlusFour")) {
                c = new PlusFour();
            }
        } else {
            try {
                int num = Integer.parseInt(type);
                c = new Number(colour, num);
            } catch (NumberFormatException e) {
                if (type.equals("T") || type.equals("PlusTwo")) {
                    c = new PlusTwo(colour);
                } else if (type.equals("R") || type.equals("Reverse")) {
                    c = new Reverse(colour);
                } else if (type.equals("S") || type.equals("Skip")) {
                    c = new Skip(colour);
                }
            }

        }
        // System.out.println(colour + " " + type);
        return c;
    }

    /**
     * Transfer a card from the explicit deck to the implicit deck
     * 
     * @param donor    the deck giving the card
     * @param fromThis the card coming from the other deck
     * @return <code>true</code> if the transfer was successful. Unsuccessful when
     *         the reciever deck is max sized, or donor deck has size of 0
     */
    public boolean moveCard(Deck donor, Card fromThis) {
        if (numCards == MAX_CARDS || donor.isEmpty()) {
            // String system = (numCards == MAX_CARDS ? "1" : " 2");
            // System.out.println("SYSTEM: (Deck) unsuccessful transfer1 " + system);
            return false;
        }
        int index = donor.searchSpecificCard(fromThis);
        if (index == -1) {
            return false;
        }
        Card removed = donor.removeCard(index);
        boolean successful = addCard(removed);
        if (!successful) {
            // System.out.println("SYSTEM: (Deck) unsuccessful transfer2");
            removeCard(searchSpecificCard(removed));
            donor.addCard(removed);
        }
        superSort();
        donor.superSort();
        return successful;

    }

    /**
     * Return the index of the first instance of the specified card in the deck
     * 
     * @param c the card being searched for
     * @return the int index. Return -1 if not found.
     */
    private int searchSpecificCard(Card c) {
        for (int i = 0; i < numCards; i++) {
            if (c.equals(cards[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Searches and returns the index of the first card that match the given colour
     * and type.
     * 
     * @param colour a String colour with first character lowercase
     * @param type   a String of the actual class name. If an whole number, searches
     * @return the index of the specific. Return -1 if no cards are
     *         found.
     */
    public int searchSpecificCard(String colour, String type) {
<<<<<<< HEAD
        // type = "class Cards." + type;
        Card temporary = makeNewCard(colour, type);
        if (temporary == null) {
            System.out.println("null");
        }
=======
        Card temporary = makeNewCard(colour, type);
        // System.out.println(temporary);
>>>>>>> RevealCards-Robin
        return searchSpecificCard(temporary);
    }

    /**
     * Add a card to the end of the list
     * 
     * @param c the card to be added
     * @return <code>true</code> if the transfer was successful.
     */
    private boolean addCard(Card c) {
        if (numCards == MAX_CARDS) {
            return false;
        }
        cards[numCards] = c;
        numCards++;
        return true;
    }

    /**
     * Removes a card from the deck
     * 
     * @param index the index of which card being removed
     * @return the actual card object from the deck. Returns null if the deck is
     *         empty.
     */
    private Card removeCard(int index) {
        if (isEmpty()) {
            return null;
        }
        Card toBeRemoved = cards[index];
        cards[index] = null;
        numCards--;
        sortNull();
        return toBeRemoved;
    }

    /**
     * Finds a null (empty) card inside the list and move the card at the end of the
     * list to the vacant space
     */
    private void sortNull() {
        for (int i = 0; i < numCards; i++) {
            if (cards[i] == null) {
                cards[i] = cards[numCards];
                cards[numCards] = null;
            }
        }
    }

    /**
     * Draw a random card from the available cards in this deck
     * 
     * @return a card. Return null if the deck is empty.
     */
    public Card drawRandom() {
        if (isEmpty()) {
            return null;
        }
        int random = Math.round((float) Math.random() * numCards) - 1;
        if (random < 0) {
            random = 0;
        }
        // System.out.println(random);
        Card c = cards[random];
        // removeCard(random); // Should not be removing it
        return c;
    }

    /**
     * Sorts the deck by colour then type in each colour and ascending order for
     * Number cards
     */
    public void superSort() {
        if (isEmpty()) {
            return;
        }
        sortNull();
        sortByType();
        sortByAscendingNumber();
        sortByColour();
    }

    /**
     * Locates the beginning and end indexes of the list and sorts all numbers in
     * ascending order, despite colour. This sort uses selectio sort, and assumes
     * the list has already been type-sorted.
     */
    private void sortByAscendingNumber() {
        int start = -1;
        int end = -1;
        for (int i = 0; i < numCards; i++) {
            if (cards[i] instanceof Number) {
                if (start == -1) {
                    start = i;
                }
            } else {
                if (start != -1) {
                    if (end == -1) {
                        end = i;
                    }
                }
            }

        }
        // System.out.println(start + " " + end);
        for (int i = start; i < end; i++) {
            Card min = cards[i];
            int index = i;
            for (int j = i + 1; j < end; j++) {

                if (((Number) cards[j]).getNumber() < ((Number) min).getNumber()) {
                    min = cards[j];
                    index = j;
                }
            }

            cards[index] = cards[i];
            cards[i] = min;

        }
    }

    /**
     * Sorts the deck by alphabetical type order. This sort uses bubble sort with
     * early termination
     */
    private void sortByType() {
        boolean quit = false;
        for (int j = 0; j < numCards && !quit; j++) {
            quit = true;
            for (int i = 1; i < numCards - j; i++) {
                if (String.valueOf(cards[i - 1].getClass()).compareTo(String.valueOf(cards[i].getClass())) > 0) {
                    // System.out.println(
                    // String.valueOf(cards[i -
                    // 1].getClass()).compareTo(String.valueOf(cards[i].getClass())) + " "
                    // + cards[i - 1] + " " + cards[i]);
                    quit = false;
                    Card temp = cards[i];
                    cards[i] = cards[i - 1];
                    cards[i - 1] = temp;
                }

            }
        }

    }

    /**
     * Sorts all cards by alphabetical colour order. This uses stable insertion
     * sort, and does not break any prior arragnments of the deck.
     */
    private void sortByColour() {
        for (int i = 1; i < numCards; i++) {
            for (int j = i; j > 0; j--) {
                if (cards[j].getColour().compareTo(cards[j - 1].getColour()) < 0) {
                    Card temp = cards[j - 1];
                    cards[j - 1] = cards[j];
                    cards[j] = temp;
                }
            }

        }

        // The issue in my initial insertion here was that I was not making space by
        // shifting all the cards, I
        // was straight up just swapping, without organizing any cards in between
    }

    /**
     * @return <code>true</code> if the deck has 0 cards
     */
    public boolean isEmpty() {
        return (numCards == 0);
    }

    /**
     * Searches and returns the total number of cards of a specified colour within
     * the deck
     * 
     * @param colour
     * @return an integer. Return 0 if none match.
     */
    public int searchColourCards(String colour) {
        int sum = 0;
        for (int i = 0; i < numCards; i++) {
            if (cards[i].getColour().equals(colour)) {
                sum++;
            }
        }
        return sum;
    }

    public int getNumCards() {
        return numCards;
    }

    /**
     * Gets the card at the specified index
     * 
     * @param i the specified index
     * @return the Card in that index
     */
    public Card getCard(int i) {
        if (i > numCards - 1 || i < 0) {
            return null;
        }
        return cards[i];
    }
}
