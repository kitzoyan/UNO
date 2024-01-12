public class Human extends Player {
    private boolean tutorialMode = false;

    public Human(String name) {
        super(name);
    }

    public Human(String name, Deck deck, boolean tutorialMode) {
        super(deck, name);
        this.tutorialMode = tutorialMode;
    }
}
