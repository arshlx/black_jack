import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Player {

    private final String playerName;
    private final int BLACKJACK = 21;
    private final ArrayList<Card> cards;
    public float money;
    public int bet = 0;

    public int total = 0;
    private PlayerState state;
    private boolean isFirstMove = true;

    public Player(String playerName, int money, ArrayList<Card> cards) {
        this.playerName = playerName;
        this.money = money;
        this.cards = cards;
        setPlayerState();
        setTotal();
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

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
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

    public int getTotal() {
        return total;
    }


    public void setTotal() {
        AtomicInteger aceCount = new AtomicInteger();
        cards.forEach(card -> {
            if (card.getCardFace() == CardFace.ACE) aceCount.getAndIncrement();
        });
        total = 0;
        cards.forEach(card -> total += card.getValue());
        int potentialValue = total + 10;
        if (aceCount.get() > 0 && potentialValue <= 21) {
            total = potentialValue;
        }
        if (total == BLACKJACK) {
            setState(PlayerState.BLACKJACK);
        } else if (total > BLACKJACK) setState(PlayerState.BUST);
    }

    private void setPlayerState() {
        if (total == BLACKJACK) {
            state = PlayerState.BLACKJACK;
        } else state = PlayerState.HIT;
    }
}
