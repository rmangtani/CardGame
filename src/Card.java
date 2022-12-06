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

    public boolean equals(Card other) {
        return ((this.rank.equals(other.rank)) && (this.suit.equals(other.suit)) && (this.point == other.point));
    }

    @Override // what does this do?
    public String toString() {
        return rank + " of " + suit;
    }
}
