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
                if (colour.equals("black")) {
                    if (type.equals("C")) {
                        c = new ColourChange();
                    } else if (type.equals("F")) {
                        c = new PlusFour();
                    }
                } else {
                    try {
                        int num = Integer.parseInt(type);
                        c = new Number(colour, num);
                    } catch (NumberFormatException e) {
                        if (type.equals("T")) {
                            c = new PlusTwo(colour);
                        } else if (type.equals("R")) {
                            c = new Reverse(colour);
                        } else if (type.equals("S")) {
                            c = new Skip(colour);
                        }
                    }

                }

                cards[i] = c;
            }
        } catch (IOException e) {
            System.out.println("SYSTEM: (Deck) There was an error reading from the file: " + file);
        }
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
        this.cards = other.cards;
    }

    /**
     * Create an empty deck
     */
    public Deck() {
        numCards = 0;
        cards = new Card[MAX_CARDS];

    }

    public String toString() {
        String s = "";
        for (int i = 0; i < numCards; i++) {
            s += cards[i] + "\n";
        }
        return s;
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
            return false;
        }
        int index = donor.searchSpecificCard(fromThis);
        if (index == -1) {
            return false;
        }

        return addCard(donor.removeCard(index));

    }

    /**
     * Return the index of the first instance of the specified card in the deck
     * 
     * @param c the card being searched for
     * @return the int index. Return -1 if not found.
     */
    private int searchSpecificCard(Card c) {
        for (int i = 0; i < numCards; i++) {
            if (cards[i].equals(c)) {
                return i;
            }
        }
        return -1;
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
        // System.out.println(random);
        Card c = cards[random];
        removeCard(random);
        return c;
    }

    /**
     * Sorts the deck by colour then type in each colour and ascending order for
     * Number cards
     */
    public void superSort() {
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
     * Searches and returns the total sum of cards that match the given colour and
     * type
     * 
     * @param colour a String colour with first character lowercase
     * @param type   a String of the actual class name
     * @return the integer count of total cards matching. Return 0 if no cards are
     *         found.
     */
    public int searchColourTypeCards(String colour, String type) {
        int sum = 0;
        type = "class Cards." + type;
        for (int i = 0; i < numCards; i++) {
            if (cards[i].getColour().equals(colour) && String.valueOf(cards[i].getClass()).equals(type)) {
                sum++;
            }
        }
        return sum;
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
