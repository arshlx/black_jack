import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DriverClass {
    private static final PrintStream syso = System.out;
    private static final Scanner scan = new Scanner(System.in);
    private static final BlackJackViewModel viewModel = new BlackJackViewModel();

    public static void main(String[] args) {
        initPlayers();
    }

    private static void showTable() {

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
            viewModel.players.add(initPlayerName());
        }
    }

    private static Player initPlayerName() {
        var playerName = "";
        while (true) {
            syso.println("How many players do you want to add?");
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

        syso.println(name + "please enter starting amount (in multiples of 5): ");
        try {
            startingMoney = scan.nextInt();
            if (startingMoney == 0) {
                syso.println(name + ", you cannot enter the game with zero money.");
                createPlayer(name);
            } else if (startingMoney < 0) {
                syso.println(name + ", you cannot enter the game when you owe money.");
                createPlayer(name);
            } else if ((startingMoney % 5) != 0) {
                syso.println(name + ", your bets as well as starting money needs to be in multiples of 5");
                createPlayer(name);
            }
        } catch (InputMismatchException e) {
            scan.nextLine();
            syso.println("Invalid input, please try again.");
        }
        return new Player(name, startingMoney, viewModel.throwTwoCards());
    }


}
