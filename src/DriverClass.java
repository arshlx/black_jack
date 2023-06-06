import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DriverClass {
    private static final PrintStream syso = System.out;
    private static final Scanner scan = new Scanner(System.in);
    private static final BlackJackViewModel viewModel = new BlackJackViewModel();

    public static void main(String[] args) {
        initPlayers();
        viewModel.initDealer();
        syso.println("Lets place the bets!");
        initBets(0);
        showTable();
    }

    private static void showTable() {
        selectMoves(0);
    }

    private static void initPlayers() {
        var numPlayers = 0;
        while (true) {
            syso.println("How many players do you want to add?");
            try {
                numPlayers = scan.nextInt();
                break;
            } catch (InputMismatchException e) {
                scan.nextLine();
                syso.println("Invalid input, please try again.");
            }
        }

        for (int index = 0; index < numPlayers; index++) {
            viewModel.getPlayers().add(initPlayerName());
        }
    }

    private static Player initPlayerName() {
        var playerName = "";
        while (true) {
            syso.println("WHat is the name of the player?");
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

        syso.println(name + "please enter starting amount: ");
        try {
            startingMoney = scan.nextInt();
            if (startingMoney == 0) {
                syso.println(name + ", you cannot enter the game with zero money.");
                createPlayer(name);
            } else if (startingMoney < 0) {
                syso.println(name + ", you cannot enter the game with no money.");
                createPlayer(name);
            }
        } catch (InputMismatchException e) {
            scan.nextLine();
            syso.println("Invalid input, please try again.");
        }
        return new Player(name, startingMoney, viewModel.throwTwoCards());
    }

    private static void initBets(int index) {
        for (; index < viewModel.getPlayers().size(); index++) {
            var player = viewModel.getPlayers().get(index);

            syso.println(player.getPlayerName() + ", how much do you want to bet?");
            try {
                var bet = scan.nextInt();
                if (bet == 0) {
                    syso.println(player.getPlayerName() + ", you cannot enter the game with zero money.");
                    initBets(index);
                } else if (bet < 0) {
                    syso.println(player.getPlayerName() + ", the bet needs to be at least $1");
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
            if (player.getState() != PlayerState.HIT) {
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

            var stateCheck = viewModel.getPlayers().stream().anyMatch(pla -> pla.getState() == PlayerState.HIT && pla.getMoney() > 0);
            if (stateCheck) selectMoves(0);
            else evaluate();
        }
    }

    private static void evaluate() {

    }
}
