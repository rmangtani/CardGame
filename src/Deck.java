// BS CardGame by Ruchi Mangtani 12/7/2022

import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> cards;
    private int cardsLeft;

    public Deck(String[] ranks, String[] suits, int[] points) {
        cards = new ArrayList<Card>();
        for (int i = 0; i < suits.length; i++) {
            for (int j = 0; j < ranks.length; j++) {
                cards.add(new Card(ranks[j], suits[i], points[j]));
                cardsLeft++;
            }
        }
        shuffle();
    }

    public boolean isEmpty() {
        if (cardsLeft == 0) {
            return true;
        }
        return false;
    }

    public int getCardsLeft() {
        return cardsLeft;
    }

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
     *
     */
    public void shuffle() {
        for (int i = cards.size()-1; i >= 0; i--) {
            // check that random is correct i think it is (it's inclusive)
            int random = (int)(Math.random()*(i+1));
            Card temp = cards.get(i);
            cards.set(i, cards.get(random));
            cards.set(random, temp);
        }
    }

}
