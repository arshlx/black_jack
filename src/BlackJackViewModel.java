import java.util.ArrayList;

public class BlackJackViewModel {
    private final StackOfCards stackOfCards;
    public final int MIN = 5, MAX = 50000;
    public int nextCardIndex = 0;
    public ArrayList<Card> cardStack;

    public ArrayList<Player> players;
    public Dealer dealer;

    public BlackJackViewModel() {
        stackOfCards = new StackOfCards();
        cardStack = stackOfCards.getCardList();
        var dealerCards = new ArrayList<Card> ();
        while (nextCardIndex < 2){
            dealerCards.add(cardStack.get(nextCardIndex));
            nextCardIndex++;
        }
        dealer = new Dealer(dealerCards);
    }

    public void resetStack() {
        stackOfCards.shuffleAndReset();
    }

    public ArrayList<Card> throwTwoCards(){
        var indexLimit = nextCardIndex+2;
        var twoCardList = new ArrayList<Card>();
        while (nextCardIndex<indexLimit){
            twoCardList.add(cardStack.get(nextCardIndex));
        }
        return twoCardList;
    }
}
