import java.util.ArrayList;
import java.util.Collections;

public class StackOfCards {
    private final ArrayList<Card> cardList = new ArrayList<>();
    public ArrayList<Card> discardPile = new ArrayList<>();

    public StackOfCards() {
        var cardSuit = CardSuit.values();
        var cardFace = CardFace.values();
        for (int suitNumber = 0; suitNumber < 4; suitNumber++) {
            for (int faceNumber = 0; faceNumber < CardFace.values().length; faceNumber++) {
                cardList.add(new Card(cardFace[faceNumber], cardSuit[suitNumber]));
            }
        }
        shuffleAndReset();
    }

  /*  public static void main(String[] args) {


    }*/

    public ArrayList<Card> getCardList() {
        return cardList;
    }

    public void shuffleAndReset() {
        cardList.addAll(discardPile);
        cardList.forEach(card -> card.setDealt(false));
        Collections.shuffle(cardList);
    }

    public void addCardToDiscardPile(Player player){
        player.getCards().forEach(card -> {
            discardPile.add(card);
            cardList.remove(card);
        });
    }
}
