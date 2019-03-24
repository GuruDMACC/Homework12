package edu.dmacc.codedsm.blackjack;


public class Card {

    public String suit;
    public Integer value;

    public Card(String suit, Integer value) {
        this.suit = suit;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Card{" +
                "suit='" + suit + '\'' +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (suit != null ? !suit.equals(card.suit) : card.suit != null) return false;
        return value != null ? value.equals(card.value) : card.value == null;

    }

    @Override
    public int hashCode() {
        int result = suit != null ? suit.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}