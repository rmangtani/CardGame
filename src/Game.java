// BS CardGame by Ruchi Mangtani 12/7/2022

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private ArrayList<Player> players;
    private Deck deck;
    private ArrayList<Card> cardPile;

    public Game(int numPlayers) {
        // Initialize Deck object
        String[] ranks = {"Ace","2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        String[] suits = {"Heart", "Diamond", "Spade", "Club"};
        int[] points = {1, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
        deck = new Deck(ranks, suits, points);
        cardPile = new ArrayList<Card>();
        // Initialize players
        players = new ArrayList<Player>();
        setUp(numPlayers);
    }

    public void setUp(int numPlayers) {
        Scanner input = new Scanner(System.in);
        for (int i = 0; i < numPlayers; i++) {
            // Ask for player name
            System.out.println("Type player " + (i+1) + "'s name:");
            String name = input.nextLine();
            ArrayList<Card> hand = new ArrayList<Card>();
            // Deal cards to player's hand
            for (int j = 0; j < deck.getSize()/numPlayers; j++) {
                hand.add(deck.deal());
            }
            // Add player to players arraylist (w name & hand)
            players.add(new Player(name, hand));
        }
    }

    public void printInstructions() {
        System.out.println("Welcome to BS! [instructions]");
    }

    public void playGame() {
        printInstructions();
        String[] ranks = {"Ace","2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        Scanner input = new Scanner(System.in);
        int turn = 0;
        // System.out.println(players.get(turn).getName() + " starts the game. They play their ace of spades.");
        int currCard = 0;
        int numberOfCards;
        while (true) {
            System.out.println("It's " + players.get(turn).getName() + "'s turn.");
            System.out.println("Type anything on the keyboard to see your hand.");
            input.nextLine();
            input.nextLine();
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println("This is your hand: " + players.get(turn).getHand());
            System.out.println("Type anything on the keyboard to hide your hand.");
            input.nextLine();
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println("How many "  + ranks[currCard] + "'s are you putting down?");
            numberOfCards = input.nextInt();
            // Remove these cards from the player's hand and add those cards to the card pile
            addToPile(ranks[currCard], numberOfCards, players.get(turn).getHand());
            // ask if anybody thinks this is a lie
            System.out.println("This is your updated hand: " + players.get(turn).getHand());
            currCard++;
            if (currCard >= ranks.length) {
                currCard = 0;
            }
            turn++;
            if (turn >= players.size()) {
                turn = 0;
            }
            // continuously check if anybody has no cards left - that means they won
        }
    }

    // Finding the player who starts the game
//    public int findStartingPlayer() {
//        Player currPlayer;
//        Card aceOfSpades = new Card("A", "Spade", 1);
//        for (int i = 0; i < players.size(); i++) {
//            currPlayer = players.get(i);
//            for (int j = 0; j < currPlayer.getHandSize(); j++) {
//                if (currPlayer.getHand().get(j).equals(aceOfSpades)) {
//                    currPlayer.getHand().remove(j);
//                    return i;
//                }
//            }
//        }
//        return -1;
//    }

    public void addToPile(String rank, int numCards, ArrayList<Card> hand) {
        int count = 0;
        for (int i = 0; i < hand.size(); i++) {
            if ((hand.get(i).getRank().equals(rank)) && (count < numCards)) {
                cardPile.add(hand.remove(i));
                count++;
                i--;
            }
            if (count >= numCards) {
                break;
            }
        }

        // If the user was lying and they don't actually have the cards they said they had, select random cards to add to the pile
        if (count < numCards) {
            for (int i = 0; i < (numCards-count); i++) {
                cardPile.add(hand.remove(i));
            }
        }

    }

    public static void main(String[] args) {
        // create new game object and call playGame
        Scanner input = new Scanner(System.in);
        System.out.println("How many players?");
        int numPlayers = input.nextInt();
        Game game = new Game(numPlayers);
        game.playGame();
    }

}
