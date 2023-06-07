import java.util.ArrayList;

public class Dealer {
    private final int BLACKJACK = 21;

    private final ArrayList<Card> cards;
    private int total = 0;
    private PlayerState state;

    public Dealer(ArrayList<Card> cards) {
        this.cards = cards;
        setTotal();
    }

    public static void main(String[] args) {

    }

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

    public int setTotal() {
        var hasAce = cards.stream().anyMatch(card -> card.getCardFace() == CardFace.ACE);

        if (hasAce) {
            var aceList = cards.stream().filter(card -> card.getCardFace() == CardFace.ACE);
            aceList.forEach(aceCard -> {
                aceCard.setAceValue(11);
            });
            total = 0;
            cards.forEach(card -> {
                total += card.getValue();
            });
            if (total > 16) {
                aceList.forEach(card -> {
                    var sum = 0;
                    for (Card value : cards) {
                        sum += value.getValue();
                    }
                    if (sum > BLACKJACK) card.setAceValue(1);
                });
            }
        }

        total = 0;
        cards.forEach(card -> {
            total += card.getValue();
        });

        if (total == BLACKJACK) {
            setState(PlayerState.BLACKJACK);
        } else if (total > BLACKJACK) setState(PlayerState.BUST);
        else setState(PlayerState.DONE);
        return total;
    }
}
