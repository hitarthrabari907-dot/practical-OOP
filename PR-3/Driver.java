public class Driver {
    public static void main(String[] args) {
        Card[] cards = new Card[5];
        Card[] incomingCards = {
            new Card("Ace", "Hearts"),
            new Card("King", "Clubs"),
            new Card("Ace", "Spades"),
            new Card("Queen", "Diamonds"),
            new Card("Ace", "Spades")
        };

        int cardCount = 0;
        for (Card incomingCard : incomingCards) {
            boolean duplicate = false;
            for (int previous = 0; previous < cardCount; previous++) {
                if (incomingCard.equals(cards[previous])) {
                    duplicate = true;
                    break;
                }
            }
            if (duplicate) {
                System.out.println("Duplicate found: " + incomingCard);
                break;
            }

            cards[cardCount] = incomingCard;
            cardCount++;
        }
    }
}
