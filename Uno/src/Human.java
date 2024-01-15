import java.util.InputMismatchException;
import java.util.Scanner;

import Cards.Card;

public class Human extends Player {
    private boolean tutorialMode = false;

    public Human(String name) {
        super(name);
    }

    public Human(String name, Deck deck, boolean tutorialMode) {
        super(deck, name);
        this.tutorialMode = tutorialMode;
    }

    public void turnOnTutorial() {
        tutorialMode = true;
    }

    public Card play(Card currentCard) {
        int x = 3;
        int input;
        boolean exit = false, exit2 = false;
        Scanner sc = new Scanner(System.in);
        if (tutorialMode) {
            x = 4;
            System.out.println("============================================= YOUR TURN (Tutorial)\n" +
                    "\t1. Attempt to Call Uno\n" +
                    "\t2. Reveal Cards\n" +
                    "\t3. Quit and Save Game" +
                    "Your Cards\n");
        } else {
            System.out.println("============================================= YOUR TURN\n" +
                    "\t1. Attempt to Call Uno\n" +
                    "\t2. Quit and Save Game\n" +
                    "Your Cards\n");
        }
        for (int i = 0; i < deck.getNumCards(); i++) {
            System.out.println("\t"+ x+ ". "+ deck[i]);
            x++;
        }
        System.out.println("\t"+ x +". Draw a Card");
            if (tutorialMode) {
                while(!exit){
                    exit2 = false;
                try{
                    input = sc.nextInt();
                    while(!(input > 0 && input < x)){
                        if(input == 1 && ){//player manager for anyone that need to calluno but didn't call uno
                            
                        }else if(input == 2){
                            //save the game
                        }else if(input == 3){
                            System.out.println("\t1. Reveal a Player"+ 
                            "\t2. Reveal Draw Pile"+
                            "\t3. Reveal Discard Pile" +
                            "\t4. Go Back"
                            );
                            while(!exit2){
                                try{
                                    input = sc.nextInt();
                                    while(!(input > 0 && input <= 4)){
                                        if(input == 1){

                                        }else if(input ==2){
                                            
                                        }else if(input ==3){
                                        }else if(input == 4){
                                            exit2 = true;
                                        }
                                        
                            }
                                }catch(InputMismatchException ie){
                                    System.out.println("Please enter a valid number!!");
                                }
                            }

                        } else if(input > 3 && input < x){
                            while(!exit2){
                                System.out.println("\t1. Play Card"+ 
                                    "\t2. Explain Definition"+"\t3. Go back");
                                try{
                                    input = sc.nextInt();
                                        if(input == 1){
                                            if(deck[input].isValidMove(currentCard)){
                                                exit2 = true;
                                                exit = true;
                                                System.out.println("You played" + deck[i]);
                                                System.out.println(deck.getNumCards() + "cards in hand.");
                                                if (deck.getNumCards() == 1) {
                                                    System.out.println("You played" + deck[i]);
                                                    System.out.println("1 Card in hand.\nYou called Uno");
                                                }
                                        
                                            }else{
                                                System.out.println("You cannot play this card");
                                            }
                                        }else if(input == 2){
                                            System.out.println(deck[input] + ":\n"+deck[input].getDefinition());
                                        }else if(input ==3){
                                            exit2= true;
                                        }else{
                                            System.out.println("This is not a valid number");
                                        }
                                }catch(InputMismatchException ie){
                                    System.out.println("Please enter a valid number!!");
                                }
                            }
                        }else if(input == x){
                            System.out.println("You couldn't go.\nYou drew a Card");
                            System.out.println(deck.getNumCards() + "cards in hand.");
                            exit = true;
                        }else{
                            System.out.println("Enter a number within the range.");
                        }
                        
                    }
                }catch(InputMismatchException ie){
                        System.out.println("Please enter a valid number!!");
                }
            }
        }else{
            while(!exit){
                exit2 = false;
                try{
                    input = sc.nextInt();
                    while(!(input > 0 && input < x)){
                        if(input == 1){//player manager for anyone that need to calluno but didn't call uno
                            
                        }else if(input == 2){
                            //save the game
                        } else if(input > 2 && input <= x){
                            System.out.println("\t1. Play Card"+ 
                            "\t2. Explain Definition");
                            while(!exit2){
                                try{
                                    input = sc.nextInt();
                                        if(input == 1){
                                            if(deck[input].isValidMove(currentCard)){
                                                exit = true;
                                                exit2 = true;
                                                System.out.println("You played" + deck[i]);
                                                System.out.println(deck.getNumCards() + "cards in hand.");
                                                if (deck.getNumCards() == 1) {
                                                    System.out.println("You played" + deck[i]);
                                                    System.out.println("1 Card in hand.\nYou called Uno");
                                                }
                                            }else{
                                                System.out.println("You cannot play this card.");
                                            }
                                        }else if(input ==2){
                                            System.out.println(deck[input] + ":\n"+deck[input].getDefinition());
                                        }else{
                                            System.out.println("This is not a valid number");
                                        }
                                }catch(InputMismatchException ie){
                                    System.out.println("Please enter a valid number!!");
                                }
                            }
                        }else if(input == x){
                            System.out.println("You couldn't go.\nYou drew a Card");
                            System.out.println(deck.getNumCards() + "cards in hand.");
                            exit = true;
                        }else{
                            System.out.println("Enter a number within the range.");
                        }
                    }
                }catch(InputMismatchException ie){
                    System.out.println("Please enter a valid number!!");
                }
            }
        }
        
        
    }
}
