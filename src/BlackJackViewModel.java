import java.util.ArrayList;

public class BlackJackViewModel{
    public StackOfCards stackOfCards;
    public ArrayList<Card> cardStack;

    public BlackJackViewModel() {
        stackOfCards = new StackOfCards();
        cardStack = stackOfCards.getCardList();
    }
}
