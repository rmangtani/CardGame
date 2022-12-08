// BS CardGame by Ruchi Mangtani 12/7/2022

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private ArrayList<Player> players;
    private Deck deck;
    private ArrayList<Card> cardPile;

    public Game(int numPlayers) {
        // Initializing deck
        String[] ranks = {"Ace","2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        String[] suits = {"Hearts", "Diamonds", "Spades", "Clubs"};
        int[] points = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        deck = new Deck(ranks, suits, points);
        // Initializing other instance variables
        cardPile = new ArrayList<Card>();
        players = new ArrayList<Player>();
        setUp(numPlayers);
    }

    // Initializes the ArrayList of players with their names and hands
    public void setUp(int numPlayers) {
        Scanner input = new Scanner(System.in);
        for (int i = 0; i < numPlayers; i++) {
            // Ask for the player's name
            System.out.println("Type player " + (i+1) + "'s name:");
            String name = input.nextLine();
            ArrayList<Card> hand = new ArrayList<Card>();
            // Deal cards from the deck to the player's hand (the deck is split evenly between each player)
            for (int j = 0; j < deck.getSize()/numPlayers; j++) {
                hand.add(deck.deal());
            }
            // Add the player to the players ArrayList
            players.add(new Player(name, hand));
        }
    }

    public void printInstructions() {
        System.out.println("Welcome to BS! In this card game, the deck will be divided evenly between the players.\n" +
                        "The first player will be asked to put down their aces in the card pile, the second player will\n" +
                        "be asked to put down their 2's, and so on. Once all the players have gone, it will wrap back\n" +
                        "around to the first player who will be asked to put down the next consecutive card (continuing the process).\n" +
                        "\nWhen it is your turn, you choose how many cards to put down, and you can also choose to lie.\n" +
                        "For instance, you can say you put down 2 aces when you actually put down a 4 and a King.\n" +
                        "Other players can then guess if you are lying. If a player guesses you are lying and are correct,\n" +
                        "you will have to add all of the cards in the card pile to your hand. If a player guesses you are\n" +
                        "lying but are incorrect, they will have to take all of the cards in the card pile. The winner is\n" +
                        "whoever empties their hand first.");
    }

    public void playGame() {
        printInstructions();
        String[] ranks = {"Ace","2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        Scanner input = new Scanner(System.in);
        // The variable turn is the index of the player who is currently putting their cards down
        int turn = 0;
        // currCard is the index in ranks of the ranking of the card the player is asked to put down
        int currCard = 0;
        // numberOfCards is the number of cards the user has decided to put down in the card pile
        int numberOfCards = 0;
        // currPlayer is the player who is currently playing (at the index of turn)
        Player currPlayer = null;
        // Continue this while loop until a winner is found
        while (findWinner() == null) {
            currPlayer = players.get(turn);
            System.out.println("\nIt's " + currPlayer.getName() + "'s turn.");
            System.out.println("Type anything on the keyboard to see your hand.");
            input.nextLine();
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println("This is your hand: " + currPlayer.getHand());
            System.out.println("How many " + ranks[currCard] + "'s are you putting down?");
            numberOfCards = input.nextInt();
            input.nextLine();
            System.out.println("Type anything on the keyboard to hide your hand.");
            input.nextLine();
            // There's no easy way to clear the terminal so this print statement adds many spaces to effectively clear it
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");

            System.out.println("Does anybody think " + currPlayer.getName() + " is lying? (y/n)");
            // isLie = "y" if a player thinks the current player is lying about the cards they put down and equals "n" otherwise
            String isLie = input.nextLine();
            if (isLie.equals("y")) {
                // accuser is the name of the player who thinks the current player is lying
                String accuser = "";
                // Continuously asking the user the name of the accuser until the user types the name of an actual player in the
                // players ArrayList
                while (findPlayer(accuser) == null) {
                    System.out.println("Who thinks " + currPlayer.getName() + " is lying?");
                    accuser = input.nextLine();
                }
                // If the accuser is correct that the current player lied, add all cards in cardPile to the current
                // player's hand
                if (addToPile(ranks[currCard], numberOfCards, currPlayer.getHand())) {
                    System.out.println("You're right! " + currPlayer.getName() + " takes all the cards from the pile.");
                    addCardPileToHand(currPlayer);
                }
                // If the accuser is incorrect and the current player told the truth, add all the cards in cardPile to
                // the accuser's hand
                else {
                    System.out.println("You are incorrect. " + accuser + " takes all the cards from the pile.");
                    addCardPileToHand(findPlayer(accuser));
                }
            }

            // Else, remove the number of cards (with the correct ranking if possible) that the current player indicated
            // from their hand and add those cards to cardPile
            else {
                addToPile(ranks[currCard], numberOfCards, players.get(turn).getHand());
            }

            // Incrementing to the next ranking; wrap back to 0 if necessary
            currCard++;
            if (currCard >= ranks.length) {
                currCard = 0;
            }
            // Incrementing to the next player's turn; wrap back to 0 if necessary
            turn++;
            if (turn >= players.size()) {
                turn = 0;
            }
        }
    }

    // Returns true if the user lied and false if the user said the truth
    // Additionally removes the number of cards the user indicated from their hand with the correct ranking if possible
    // and adds them to cardPile
    public boolean addToPile(String rank, int numCards, ArrayList<Card> hand) {
        // The variable count is the number of cards that have currently been removed from hand and added to cardPile
        int count = 0;
        // Removing the cards with the correct rank and adding them to cardPile
        for (int i = 0; i < hand.size(); i++) {
            if ((hand.get(i).getRank().equals(rank))) {
                cardPile.add(hand.remove(i));
                count++;
                i--;
            }
            // If the number of cards that have been removed is equal to the number of cards that the user indicated, break
            if (count == numCards) {
                break;
            }
        }

        // If the user was lying, and they don't actually have the number of cards they said they had,
        // Select other cards from their hand to add to the pile
        if (count < numCards) {
            for (int i = 0; i < hand.size(); i++) {
                cardPile.add(hand.remove(i));
                i--;
                count++;
                // Break once we have reached the number of the cards the user intended to put down
                if (count == numCards) {
                    break;
                }
            }
            return true;
        }
        return false;

    }

    // Finds the player from the name
    public Player findPlayer(String name) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getName().equals(name)) {
                return players.get(i);
            }
        }
        return null;
    }

    // Adds all current cards in cardPile to the player's hand and simultaneously removes those cards from the cardPile
    public void addCardPileToHand(Player player) {
        for (int i = 0; i < cardPile.size(); i++) {
            player.addCard(cardPile.remove(i));
            i--;
        }
    }

    // Returns the winner. If there is no winner, returns null.
    public Player findWinner() {
        for (int i = 0; i < players.size(); i++) {
            // The winner is the player with no cards
            if (players.get(i).getHand().isEmpty()) {
                System.out.println(players.get(i).getName() + " won!");
                return players.get(i);
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int numPlayers = 0;
        // While loop until the user types a positive number of players
        while (numPlayers <= 0) {
            System.out.println("How many players?");
            numPlayers = input.nextInt();
        }
        // Plays the game once
        Game game = new Game(numPlayers);
        game.playGame();
    }

}
