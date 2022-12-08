// BS CardGame by Ruchi Mangtani 12/7/2022

public class Card {
    private String rank;
    private String suit;
    private int point;

    public Card(String rank, String suit, int point) {
        this.rank = rank;
        this.suit = suit;
        this.point = point;
    }

    // Getter and Setter methods of the instance variables
    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    // Returns true if this card equals the other card and false if not
    public boolean equals(Card other) {
        return ((rank.equals(other.rank)) && (suit.equals(other.suit)) && (point == other.point));
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}
