import Cards.*;

public abstract class Player {
    protected String name;
    protected Deck deck;

    /**
     * Class Constructor
     * This class create a new player with the name and new deck
     * 
     * @param name a String representing the name of the player
     */
    public Player(String name) {
        this.name = name;
        deck = new Deck();
    }

    /**
     * Class Constructor
     * This class load a player with the given name and new deck
     * 
     * @param name a String representing the name of the player
     */
    public Player(Deck deck, String name) {
        this.name = name;
        this.deck = deck;
    }

    public String getName() {
        return name;
    }

    public abstract Card play(Card currentCard, Deck fullDeck);
}
