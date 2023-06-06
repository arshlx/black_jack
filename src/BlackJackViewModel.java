import java.util.ArrayList;

public class BlackJackViewModel {
    private final StackOfCards stackOfCards;
    public final int MIN = 1, MAX = 10000;
    public int nextCardIndex = 0;
    public ArrayList<Card> cardStack;

    private ArrayList<Player> players;
    public Dealer dealer;

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public BlackJackViewModel() {
        stackOfCards = new StackOfCards();
        cardStack = stackOfCards.getCardList();
    }

    public void initDealer(){
        var dealerCards = new ArrayList<Card> ();
        var indexLimit = nextCardIndex-2;
        while (nextCardIndex > indexLimit){
            dealerCards.add(cardStack.get(nextCardIndex));
            nextCardIndex--;
        }
        dealer = new Dealer(dealerCards);
    }

    public void resetStack() {
        stackOfCards.shuffleAndReset();
    }

    public ArrayList<Card> throwTwoCards(){
        var indexLimit = nextCardIndex-2;
        var twoCardList = new ArrayList<Card>();
        while (nextCardIndex>indexLimit){
            twoCardList.add(cardStack.get(nextCardIndex));
            addToDiscardPile();
        }
        return twoCardList;
    }

    public void hit(int playerNum){
        var player = players.get(playerNum);
        player.getCards().add(cardStack.get(nextCardIndex));
        player.setTotal();
        nextCardIndex--;
    }
    private void addToDiscardPile(){
        players.forEach(stackOfCards::addCardToDiscardPile);
        nextCardIndex--;
    }
}
