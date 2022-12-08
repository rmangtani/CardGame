// BS CardGame by Ruchi Mangtani 12/7/2022

import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> cards;
    private int cardsLeft;

    public Deck(String[] ranks, String[] suits, int[] points) {
        // Initializing cards ArrayList
        cards = new ArrayList<Card>();
        // Initializing the cards in the ArrayList
        // Each suite has all of the ranks, and each rank corresponds to a point at the same index
        for (int i = 0; i < suits.length; i++) {
            for (int j = 0; j < ranks.length; j++) {
                cards.add(new Card(ranks[j], suits[i], points[j]));
                // cardsLeft is the number of cards in the deck
                cardsLeft++;
            }
        }
        shuffle();
    }

    // Returns true when there are 0 cards left in the deck
    public boolean isEmpty() {
        if (cardsLeft == 0) {
            return true;
        }
        return false;
    }

    public int getCardsLeft() {
        return cardsLeft;
    }

    /**
     * Returns a card out of the deck by selecting the card with the index of cardLeft (which reduces by 1
     * every time a card is dealt
     */
    public Card deal() {
        if (cards.isEmpty()) {
            return null;
        }
        cardsLeft--;
        return (cards.get(cardsLeft));
    }

    public int getSize() {
        return cards.size();
    }
    /**
     * Shuffles the deck by exchanging each card with a random card that appears before it in the ArrayList
     */
    public void shuffle() {
        for (int i = cards.size()-1; i >= 0; i--) {
            // Random is a random integer between 0 and i inclusive; it is the index of the card that is going to be swapped
            int random = (int)(Math.random()*(i+1));
            // Swapping the cards
            Card temp = cards.get(i);
            cards.set(i, cards.get(random));
            cards.set(random, temp);
        }
    }

}
