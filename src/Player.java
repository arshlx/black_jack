import java.util.ArrayList;

public class Player {

    private final String playerName;
    private final int BLACKJACK = 21;

    public ArrayList<Card> cards;
    public int money;
    public int bet = 0;
    public int total = 0;
    private PlayerState state;
    private boolean isFirstMove = true;

    public Player(String playerName, int money, ArrayList<Card> cards) {
        this.playerName = playerName;
        this.money = money;
        this.cards = cards;
        cards.forEach(card -> {
            total += card.getValue();
        });
        setPlayerState(PlayerState.HIT);
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getMoney() {
        return money;
    }

    public PlayerState getState() {
        return state;
    }

    public void setState(PlayerState state) {
        this.state = state;
    }

    public boolean getFirstMove() {
        return isFirstMove;
    }

    public void setFirstMove(boolean firstMove) {
        isFirstMove = firstMove;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public int setTotal() {
        var lastCard = cards.get(cards.size() - 1);
        var ace1 = total + 1;
        var ace11 = total + 11;

        if (lastCard.getCardFace() == CardFace.ACE) {
            if ((BLACKJACK - ace1) > (BLACKJACK - ace11)) {
                total = ace11;
            } else total = ace1;
        } else total += lastCard.getValue();
        if (total == BLACKJACK) {
            setState(PlayerState.BLACKJACK);
        } else if (total > BLACKJACK) setState(PlayerState.BUST);
        return total;
    }

    private void setPlayerState(PlayerState plState) {
        if (total == BLACKJACK) {
            state = PlayerState.BLACKJACK;
        } else state = plState;
    }
}
