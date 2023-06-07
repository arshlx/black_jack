import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Dealer {

    public Dealer(){
        setTotal();
    }
    private final int BLACKJACK = 21;

    private final ArrayList<Card> cards = new ArrayList<>();
    private int total = 0;
    private PlayerState state;

    public PlayerState getState() {
        return state;
    }

    public void setState(PlayerState state) {
        this.state = state;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public int getTotal() {
        setTotal();
        return total;
    }

    public void setTotal() {
        AtomicInteger aceCount = new AtomicInteger();
        cards.forEach(card -> {
            if (card.getCardFace() == CardFace.ACE) aceCount.getAndIncrement();
        });
        total = 0;
        cards.forEach(card -> {
            total += card.getValue();
        });
        int potentialValue = total + 10;
        if (aceCount.get() > 0 && potentialValue <= 21) {
            total = potentialValue;
        }
        if (total == BLACKJACK) {
            setState(PlayerState.BLACKJACK);
        } else if (total > BLACKJACK) setState(PlayerState.BUST);
        else setState(PlayerState.DONE);
    }
}
