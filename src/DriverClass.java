import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DriverClass {
    public static final int MIN = 1, MAX = 10000;
    private static final PrintStream syso = System.out;
    private static final Scanner scan = new Scanner(System.in);
    private static final BlackJackViewModel viewModel = new BlackJackViewModel();

    public static void main(String[] args) {
        initPlayers();
        viewModel.initDealer();
        syso.println("Lets place the bets!");
        initBets(0);
        showTable(false);
        selectMoves(0);
    }

    private static void showTable(boolean revealDealerCards) {
        syso.println("\tDealer cards");
        if (revealDealerCards) {
            viewModel.getDealer().getCards().forEach(Card::getCardDisplay);
        } else syso.println(viewModel.getDealer().getCards().get(0));
        syso.println("\tPlayer cards");
        viewModel.getPlayers().forEach(player -> {
            syso.println(player.getPlayerName() + "'s cards");
            player.getCards().forEach(Card::getCardDisplay);
        });
    }

    private static void initPlayers() {
        while (true) {
            syso.println("How many players do you want to add?");
            try {
                viewModel.setNumPlayers(scan.nextInt());
                if (viewModel.getNumPlayers() < 1) {
                    syso.println("At least 1 player is needed to play the game");
                    initPlayers();
                }
                break;
            } catch (InputMismatchException e) {
                scan.nextLine();
                syso.println("Invalid input, please try again.");
            }
        }

        for (int index = 0; index < viewModel.getNumPlayers(); index++) {
            viewModel.getPlayers().add(initPlayerName());
        }
    }

    private static Player initPlayerName() {
        var playerName = "";
        var playerNumber = 0;
        if (viewModel.getPlayers().size() < 2)
            playerNumber = viewModel.getPlayers().size() + 1;
        else playerNumber = viewModel.getPlayers().size();
        while (true) {
            syso.println("What is the name of player " + playerNumber);
            playerName = scan.next();
            if (playerName.isEmpty()) {
                syso.println("Player name cannot be empty");
                initPlayerName();
            } else break;
        }
        return createPlayer(playerName);
    }

    private static Player createPlayer(String name) {
        var startingMoney = 0;

        syso.println(name + " please enter starting amount: ");
        try {
            startingMoney = scan.nextInt();
            if (startingMoney == 0) {
                syso.println(name + ", you cannot enter the game with zero money.");
                createPlayer(name);
            } else if (startingMoney < 0) {
                syso.println(name + ", you cannot enter the game with no money.");
                createPlayer(name);
            }
            syso.println(name + "money: " + startingMoney);
        } catch (InputMismatchException e) {
            scan.nextLine();
            syso.println("Invalid input, please try again.");
            createPlayer(name);
        }
        return new Player(name, startingMoney, viewModel.throwTwoCards());
    }

    private static void initBets(int index) {
        for (; index < viewModel.getNumPlayers(); index++) {
            var player = viewModel.getPlayers().get(index);

            syso.println(player.getPlayerName() + ", how much do you want to bet?");
            try {
                var bet = scan.nextInt();
                if (bet < MIN) {
                    syso.println(player.getPlayerName() + ", the bet needs to be at least $1");
                    initBets(index);
                } else if (bet > MAX) {
                    syso.println(player.getPlayerName() + ", the bet cannot be greater than $10,000");
                    initBets(index);
                } else if (bet > player.getMoney()) {
                    syso.println(player.getPlayerName() + ", you cannot bet more than the amount that you have in your account.");
                    initBets(index);
                } else {
                    player.setBet(bet);
                }

            } catch (InputMismatchException e) {
                scan.nextLine();
                syso.println("Invalid input, please try again.");
                initBets(index);
            }
        }
    }

    private static void selectMoves(int index) {
        for (; index < viewModel.getPlayers().size(); index++) {
            var player = viewModel.getPlayers().get(index);
            if (player.getState() == PlayerState.HIT) {
                syso.println(player.getPlayerName() + ", What's your move?\n0 to stay\n1 to hit\n2 to surrender");
                try {
                    var move = scan.nextInt();
                    switch (move) {
                        case 0 -> {
                            player.setState(PlayerState.STAY);
                            player.setFirstMove(false);
                        }
                        case 1 -> {
                            player.setState(PlayerState.HIT);
                            player.setFirstMove(false);
                            viewModel.hit(index);
                            showTable(false);
                            var hitAgain = true;
                            while (hitAgain && player.getState() != PlayerState.BUST && player.getState() != PlayerState.BLACKJACK) {
                                syso.println(player.getPlayerName() + ", do you want to hit again?(y/n)");
                                var response = scan.next();
                                switch (response.toLowerCase().charAt(0)) {
                                    case 'y' -> {
                                        viewModel.hit(index);
                                        showTable(false);
                                    }
                                    case 'n' -> hitAgain = false;
                                    default -> syso.println("Invalid input. Please try again");
                                }
                                if (response.charAt(0) == 'n') {
                                    hitAgain = false;
                                }
                            }
                        }
                        case 2 -> {
                            if (player.getFirstMove()) player.setState(PlayerState.SURRENDER);
                            else {
                                syso.println("You cannot surrender after making a move, you can either hit or stay.");
                                selectMoves(index);
                            }
                        }
                        default -> {
                            syso.println("Invalid entry, please try again.");
                            selectMoves(index);
                        }
                    }
                } catch (InputMismatchException e) {
                    scan.nextLine();
                    syso.println("Invalid input, please try again.");
                    selectMoves(index);
                }
            }
        }
        viewModel.dealerHit();
        showTable(true);
        displayResults();
    }

    private static void displayResults() {
        viewModel.getPlayers().forEach(player -> {
            switch (player.getState()) {
                case WON ->
                        syso.println(player.getPlayerName() + ", you won! your winnings: $" + player.getBet() + "\nYour total money is now $" + player.getMoney());
                case LOST ->
                        syso.println(player.getPlayerName() + ", you lost! Total amount lost: $" + player.getBet() + "\nYour total money is now $" + player.getMoney());
                case BUST ->
                        syso.println(player.getPlayerName() + ", you went bust! Total amount lost: $" + player.getBet() + "\nYour total money is now $" + player.getMoney());
                case PUSH ->
                        syso.println(player.getPlayerName() + ", your won! your winnings: $" + player.getBet() + "\nYour total money is now $" + player.getMoney());
                case BLACKJACK ->
                        syso.println(player.getPlayerName() + ", you won! You hot a blackjack! your winnings: $" + (1.5 * player.getBet()) + "\nYour total money is now $" + player.getMoney());
            }
//            player.setState(PlayerState.HIT);
        });
        viewModel.resetStack();
//        playAgain();
    }

//    private static void playAgain() {
//        var playAgain = true;
//        syso.println("Do you want to play again? (y/n)");
//        var response = scan.next();
//        if ()
//    }
}
