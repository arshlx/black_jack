import java.util.ArrayList;

public class BlackJackViewModel {
    private final StackOfCards stackOfCards;
    public ArrayList<Card> cardStack;

    public BlackJackViewModel() {
        stackOfCards = new StackOfCards();
        cardStack = stackOfCards.getCardList();
    }

    public void resetStack() {
        stackOfCards.resetStackStack();
    }
}
