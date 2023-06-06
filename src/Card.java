public class Card {
    private int cardValue;
    private final CardSuit cardSuit;
    private final CardColour cardColour;
    private final CardFace cardFace;
    private boolean isDealt = false;

    public Card(CardColour cardColour, CardFace cardFace, CardSuit cardSuit) {
        this.cardColour = cardColour;
        this.cardFace = cardFace;
        this.cardSuit = cardSuit;
        setCardValue();
    }

    private void setCardValue() {
        cardValue = switch (cardFace) {
            case ONE -> 1;
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

    public CardColour getCardColour() {
        return cardColour;
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

    public void getCardDisplay(){
        if (cardColour == CardColour.RED){
            System.out.print("\u001B[31m");
            System.out.println(cardFace + " "+ cardSuit.getSymbol());
            System.out.print("\u001B[0m");
        }else {
            System.out.println("This text will be displayed in red.");
        }
    }
}
