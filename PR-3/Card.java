import java.util.Objects;

public class Card {
    private final String rank;
    private final String suit;

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Card)) {
            return false;
        }
        Card other = (Card) object;
        return Objects.equals(rank, other.rank)
                && Objects.equals(suit, other.suit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, suit);
    }
}
