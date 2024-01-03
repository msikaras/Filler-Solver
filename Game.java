import java.util.*;

public class Game {
    private Board board;
    private Map<Integer, String> colorsAvailable;
    private boolean p1Turn = true;
    private String entry = "";
    private Scanner scan = new Scanner(System.in);

    public Game() {
        this.board = new Board();
        this.colorsAvailable = new HashMap<Integer, String>();
        fillColorsAvailable();
    }

    public Game(Board b) {
        this.board = b;
        this.colorsAvailable = new HashMap<Integer, String>();
        fillColorsAvailable();
    }

    public void fillColorsAvailable() {
        colorsAvailable.put(0, "R");
        colorsAvailable.put(1, "G");
        colorsAvailable.put(2, "Y");
        colorsAvailable.put(3, "B");
        colorsAvailable.put(4, "P");
        colorsAvailable.put(5, "X");
        colorsAvailable.remove(board.getPos(0, board.getHeight() - 1));
        colorsAvailable.remove(board.getPos(board.getWidth() - 1, 0));
    }

    public void run() {
        System.out.println("Filler Game || Enter q to quit\n");
        while (true) {
            System.out.print(board);
            board.printScores();
            System.out.println("Best Color: " + board.findBestMove(this, 4, p1Turn));
            askInput();
            board.updateBoard(entry, p1Turn);
            p1Turn = !p1Turn;
            fillColorsAvailable();
            if (board.checkGameOver()) {
                System.out.print(board);
                board.printScores();
                System.out.println("Game Over!");
                board.printWinner();
                break;
            }
        }
    }

    private void askInput() {
        entry = "";
        while (true) {
            if (p1Turn) {
                System.out.print("Player 1 enter a color ");
                printColorsAvailable();
                System.out.print(": ");
            } else {
                System.out.print("Player 2 enter a color ");
                printColorsAvailable();
                System.out.print(": ");
            }
            entry = scan.nextLine().toUpperCase();

            if (entry.equalsIgnoreCase("q")) {
                System.out.println("Quitting...");
                scan.close();
                return;
            }

            if (colorsAvailable.containsValue(entry)) {
                break;
            }
    
            System.out.println("Invalid color. Please enter one of the available colors.");
        }
    }

    private void printColorsAvailable() {
        for (Map.Entry<Integer, String> entry : colorsAvailable.entrySet()) {
            System.out.print(entry.getValue() + " ");
        }
    }

    public Set<String> getAvailableColors() {
        return new HashSet<>(colorsAvailable.values());
    }
}
