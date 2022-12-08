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
            // deck.getSize()/numPlayers
            for (int j = 0; j < 6; j++) {
                hand.add(deck.deal());
            }
            // Add player to players arraylist (w name & hand)
            players.add(new Player(name, hand));
        }
    }

    public void printInstructions() {
        System.out.println("Welcome to BS! [instructions]\n");
    }

    public void playGame() {
        printInstructions();
        String[] ranks = {"Ace","2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        Scanner input = new Scanner(System.in);
        int turn = 0;
        int currCard = 0;
        int numberOfCards = 0;
        Player currPlayer = null;
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
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");

            System.out.println("Does anybody think " + currPlayer.getName() + " is lying? (y/n)");
            String isLie = input.nextLine();
            if (isLie.equals("y")) {
                String accuser = "";
                while (findPlayer(accuser) == null) {
                    System.out.println("Who thinks " + currPlayer.getName() + " is lying?");
                    accuser = input.nextLine();
                }
                if (addToPile(ranks[currCard], numberOfCards, currPlayer.getHand())) {
                    System.out.println("You're right! " + currPlayer.getName() + " takes all the cards from the pile.");
                    addCardPileToHand(currPlayer);
                }
                else {
                    System.out.println("You are incorrect. " + accuser + " takes all the cards from the pile.");
                    addCardPileToHand(findPlayer(accuser));
                }
            }
            else {
                // Remove these cards from the player's hand and add those cards to the card pile
                addToPile(ranks[currCard], numberOfCards, players.get(turn).getHand());
            }
            currCard++;
            if (currCard >= ranks.length) {
                currCard = 0;
            }
            turn++;
            if (turn >= players.size()) {
                turn = 0;
            }
        }
    }

    // Returns true if the user lied and false if the user said the truth
    // Also removes cards from user and adds to cardPile
    public boolean addToPile(String rank, int numCards, ArrayList<Card> hand) {
        // count is the number of cards that have currently been removed from hand & added to cardPile
        int count = 0;
        for (int i = 0; i < hand.size(); i++) {
            if ((hand.get(i).getRank().equals(rank))) {
                cardPile.add(hand.remove(i));
                count++;
                i--;
            }
            if (count == numCards) {
                break;
            }
        }

        // If the user was lying, and they don't actually have the number of cards they said they had, select any other cards from their hand to add to the pile
        if (count < numCards) {
            for (int i = 0; i < hand.size(); i++) {
                cardPile.add(hand.remove(i));
                i--;
                count++;
                if (count == numCards) {
                    break;
                }
            }
            return true;
        }
        else {
            return false;
        }

    }

    // finds the player from the name
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
            player.getHand().add(cardPile.remove(i));
            i--;
        }
    }

    public Player findWinner() {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getHand().isEmpty()) {
                System.out.println(players.get(i).getName() + " won!");
                return players.get(i);
            }
        }
        return null;
    }

    public static void main(String[] args) {
        // create new game object and call playGame
        Scanner input = new Scanner(System.in);
        int numPlayers = 0;
        while (numPlayers <= 0) {
            System.out.println("How many players?");
            numPlayers = input.nextInt();
        }
        Game game = new Game(numPlayers);
        game.playGame();
    }

}
