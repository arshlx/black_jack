import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class StackOfCards {
    private final ArrayList<Card> cardList = new ArrayList<>();

    public StackOfCards() {
        var cardColour = CardColour.values();
        var cardSuit = CardSuit.values();
        var cardFace = CardFace.values();
        for (int colourNumber = 0; colourNumber < CardColour.values().length; colourNumber++) {
            for (int suitNumber = 0; suitNumber < CardSuit.values().length; suitNumber++) {
                for (int faceNumber = 0; faceNumber < CardFace.values().length; faceNumber++) {
                    cardList.add(new Card(cardColour[colourNumber], cardFace[faceNumber], cardSuit[suitNumber]));
                }
            }
        }
    }

    public ArrayList<Card> getCardList() {
        return cardList;
    }

    public void resetStackStack(){
        cardList.forEach(card -> card.setDealt(false));
        Collections.shuffle(cardList);
    }
}
