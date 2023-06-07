import java.util.ArrayList;

public class Player {

    private final String playerName;
    private final int BLACKJACK = 21;
    private final ArrayList<Card> cards;
    public int money;
    public int bet = 0;

    public int total = 0;
    private PlayerState state;
    private boolean isFirstMove = true;

    public Player(String playerName, int money, ArrayList<Card> cards) {
        this.playerName = playerName;
        this.money = money;
        this.cards = cards;
        setTotal();
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

    public void setMoney(int money) {
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


    public int setTotal() {
        var hasAce = cards.stream().anyMatch(card -> card.getCardFace() == CardFace.ACE);
        if (hasAce) {
            var aceList = cards.stream().filter(card -> card.getCardFace() == CardFace.ACE);
            aceList.forEach(aceCard -> {
                aceCard.setAceValue(11);
            });
            aceList.forEach(card -> {
                var sum = 0;
                for (Card value : cards) {
                    sum += value.getValue();
                }
                if (sum > BLACKJACK) card.setAceValue(1);
            });
        }

        total = 0;
        cards.forEach(card -> {
            total += card.getValue();
        });

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
