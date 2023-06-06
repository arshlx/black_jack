import java.util.ArrayList;

public class BlackJackViewModel {
    private final StackOfCards stackOfCards;
    public int nextCardIndex = 0;
    public ArrayList<Card> cardStack;
    public Dealer dealer;
    private ArrayList<Player> players;

    public BlackJackViewModel() {
        stackOfCards = new StackOfCards();
        cardStack = stackOfCards.getCardList();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void initDealer() {
        var dealerCards = new ArrayList<Card>();
        resetStack();
        var indexLimit = nextCardIndex - 2;
        while (nextCardIndex > indexLimit) {
            dealerCards.add(cardStack.get(nextCardIndex));
            nextCardIndex--;
        }
        dealer = new Dealer(dealerCards);
    }

    public void resetStack() {
        if (nextCardIndex < 0)
            stackOfCards.shuffleAndReset();
        nextCardIndex = cardStack.size() - 1;
    }

    public ArrayList<Card> throwTwoCards() {
        resetStack();
        var indexLimit = nextCardIndex - 2;
        var twoCardList = new ArrayList<Card>();
        while (nextCardIndex > indexLimit) {
            twoCardList.add(cardStack.get(nextCardIndex));
        }
        return twoCardList;
    }

    public void hit(int playerNum) {
        resetStack();
        var player = players.get(playerNum);
        player.getCards().add(cardStack.get(nextCardIndex));
        player.setTotal();
        nextCardIndex--;
    }


    private void addToDiscardPile() {
        players.forEach(stackOfCards::addCardToDiscardPile);
        nextCardIndex--;
    }
}
