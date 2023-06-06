public enum CardSuit {
    SPADE('\u2664'), CLUB('\u2667'),  HEART('\u2661'), DIAMOND('\u2662');
    private final char symbol;

    CardSuit(char unicodeCharacter) {
        this.symbol = unicodeCharacter;
    }

    public char getSymbol() {
        return symbol;
    }
}
