import java.util.ArrayList;

public class BlackJackViewModel {
    private final StackOfCards stackOfCards;
    private final int BLACKJACK = 21;
    private final ArrayList<Card> cardStack;
    public int nextCardIndex = 0;
    private Dealer dealer;
    private ArrayList<Player> players;

    public BlackJackViewModel() {
        stackOfCards = new StackOfCards();
        cardStack = stackOfCards.getCardList();
    }

    public Dealer getDealer() {
        return dealer;
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

    public void dealerHit() {
        var indexLimit = nextCardIndex - 2;
        while (dealer.getTotal() < 17) {
            while (nextCardIndex > indexLimit) {
                dealer.getCards().add(cardStack.get(nextCardIndex));
                nextCardIndex--;
            }
        }
        switch (dealer.getState()) {
            case BLACKJACK -> {
                players.forEach(player -> {
                    if (player.getState() == PlayerState.BLACKJACK) {
                        player.setState(PlayerState.PUSH);
                    }

                });
            }
            case BUST -> {
                players.forEach(player -> {
                    if (player.getState() == PlayerState.BUST) {
                        player.setState(PlayerState.PUSH);
                    } else if (player.getState() == PlayerState.STAY) {
                        player.setState(PlayerState.WON);
                    }
                });
            }
            case DONE -> {
                players.forEach(player -> {
                    if (player.getState() == PlayerState.STAY) {
                        if ((BLACKJACK - player.getTotal()) < (BLACKJACK - dealer.getTotal())) {
                            player.setState(PlayerState.WON);
                        } else player.setState(PlayerState.LOST);
                    }
                });
            }
        }
        addCardsToDiscardPile();
        setPlayerRewards();
    }

    private void setPlayerRewards() {
        players.forEach(player -> {
            switch (player.getState()) {
                case BUST, LOST -> {
                    var money = player.getMoney();
                    money -= player.getBet();
                    player.setMoney(money);
                }
                case WON -> {
                    var money = player.getMoney();
                    money += player.getBet();
                    player.setMoney(money);
                }
                case BLACKJACK -> {
                    var money = player.getMoney();
                    money += (1.5 * player.getBet());
                    player.setMoney(money);
                }
            }
        });
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

    public void addCardsToDiscardPile(Player player) {
        stackOfCards.addCardToDiscardPile(player);
    }

    public void addCardsToDiscardPile() {
        stackOfCards.addCardToDiscardPile(dealer);
    }
}
