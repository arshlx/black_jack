import java.util.ArrayList;

public class Player {
    public final String playerName;
    public final int BLACKJACK = 21;
    public ArrayList<Card> cards;
    public int money;
    public PlayerState state;
    public int total = 0;

    public Player(String playerName, int money, ArrayList<Card> cards) {
        this.playerName = playerName;
        this.money = money;
        this.cards = cards;
        cards.forEach(card -> {
            total += card.getValue();
        });
        setPlayerState(PlayerState.HIT);
    }

    private void setPlayerState(PlayerState plState) {
        if (total == BLACKJACK) {
            state = PlayerState.BLACKJACK;
        } else state = plState;
    }
}
