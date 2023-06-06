public enum CardSuit {
    SPADE('\u2664'), HEART('\u2661'), DIAMOND('\u2662'), CLUB('\u2667');
    private final char symbol;

    CardSuit(char unicodeCharacter) {
        this.symbol = unicodeCharacter;
    }

    public char getSymbol() {
        return symbol;
    }
}
