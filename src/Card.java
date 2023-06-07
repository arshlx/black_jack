public class Card {
    private final CardSuit cardSuit;
    private final CardFace cardFace;
    private int cardValue;
    private boolean isDealt = false;

    public Card(CardFace cardFace, CardSuit cardSuit) {
        this.cardFace = cardFace;
        this.cardSuit = cardSuit;
        setCardValue();
    }

    private void setCardValue() {
        cardValue = switch (cardFace) {
            case TWO -> 2;
            case THREE -> 3;
            case FOUR -> 4;
            case FIVE -> 5;
            case SIX -> 6;
            case SEVEN -> 7;
            case EIGHT -> 8;
            case NINE -> 9;
            case TEN, JACK, KING, QUEEN -> 10;
            default -> 0;
        };
    }

    public int getValue() {
        return cardValue;
    }

    public CardSuit getCardSuit() {
        return cardSuit;
    }

    public CardFace getCardFace() {
        return cardFace;
    }

    public boolean isDealt() {
        return isDealt;
    }

    public void setDealt(boolean dealt) {
        isDealt = dealt;
    }

    public void setAceValue(int value) {
        cardValue = value;
    }

    public void getCardDisplay() {
        if (cardSuit == CardSuit.DIAMOND || cardSuit == CardSuit.HEART) {
            System.out.print("\u001B[31m");
            System.out.println("\t" + cardFace + " " + cardSuit.getSymbol());
            System.out.print("\u001B[0m");
        } else {
            System.out.println(cardFace + " " + cardSuit.getSymbol());
        }
    }
}
