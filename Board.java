import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Board {
    private int width;
    private int height;
    private Integer[][] board;
    private Integer[][] visited; // 1:Player 1, 2:Player 2, 0:Not visited
    public HashMap<Integer, String> colorMap;
    private int p1Score = 1;
    private int p2Score = 1;

    public Board() {
        this.width = 5;
        this.height = 5;
        this.board = new Integer[width][height];
        fillBoard();
        this.visited = new Integer[width][height];
        setVisited();
        this.colorMap = new HashMap<>();
        setColorMap();
    }

    public Board(int w, int h) {
        this.width = w;
        this.height = h;
        this.board = new Integer[width][height];
        fillBoard();
        this.visited = new Integer[width][height];
        setVisited();
        this.colorMap = new HashMap<>();
        setColorMap();
    }

    private void setColorMap() {
        colorMap.put(0, "R");
        colorMap.put(1, "G");
        colorMap.put(2, "Y");
        colorMap.put(3, "B");
        colorMap.put(4, "P");
        colorMap.put(5, "X");
    }

    private void setVisited() {
        for (Integer[] row : visited)
            Arrays.fill(row, 0);
    }

    private void updateHelper(String targetColor, String originalColor, boolean p1Turn, int w, int h,
            Integer[][] newBoard, boolean checkTargets) {
        if (w < 0 || w >= width || h < 0 || h >= height) {
            return; // Out of bounds
        }
        if ((p1Turn && visited[w][h] == 1) || (!p1Turn && visited[w][h] == 2)) {
            return; // Already visited
        }
        if (board[w][h] != colorToInt(originalColor) && board[w][h] != colorToInt(targetColor)) {
            return; // Color not applicable
        }
        if (checkTargets && board[w][h] != colorToInt(targetColor)) {
            return; // Not target
        }

        visited[w][h] = p1Turn ? 1 : 2;

        if (board[w][h] == colorToInt(targetColor)) {
            updateHelper(targetColor, originalColor, p1Turn, w, h - 1, newBoard, true); // UP
            updateHelper(targetColor, originalColor, p1Turn, w + 1, h, newBoard, true); // RIGHT
            updateHelper(targetColor, originalColor, p1Turn, w - 1, h, newBoard, true); // LEFT
            updateHelper(targetColor, originalColor, p1Turn, w, h + 1, newBoard, true); // DOWN
        }

        if (!checkTargets && board[w][h] == colorToInt(originalColor)) {
            updateHelper(targetColor, originalColor, p1Turn, w, h - 1, newBoard, false); // UP
            updateHelper(targetColor, originalColor, p1Turn, w + 1, h, newBoard, false); // RIGHT
            updateHelper(targetColor, originalColor, p1Turn, w - 1, h, newBoard, false); // LEFT
            updateHelper(targetColor, originalColor, p1Turn, w, h + 1, newBoard, false); // DOWN
        }

        newBoard[w][h] = colorToInt(targetColor);
    }

    public void updateBoard(String entry, boolean p1Turn) {
        Integer originalColor;
        setVisited();
        Integer[][] newBoard = board;

        if (p1Turn) {
            p1Score = 0;
            originalColor = board[0][height - 1];
            updateHelper(entry, colorMap.get(originalColor), p1Turn, 0, height - 1, newBoard, false);
        } else {
            p2Score = 0;
            originalColor = board[width - 1][0];
            updateHelper(entry, colorMap.get(originalColor), p1Turn, width - 1, 0, newBoard, false);
        }

        board = newBoard;

        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                if (p1Turn) {
                    if (visited[w][h] == 1) {
                        p1Score++;
                    }
                } else {
                    if (visited[w][h] == 2) {
                        p2Score++;
                    }
                }
            }
        }
    }

    public boolean checkGameOver() {
        HashMap<Integer, Integer> temp = new HashMap<Integer, Integer>();

        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                if (!temp.containsKey(board[w][h])) {
                    temp.put(board[w][h], 1);
                }
            }
        }

        if (temp.size() > 2) {
            return false;
        }

        return true;
    }

    private int colorToInt(String entry) {
        switch (entry) {
            case "R":
                return 0;
            case "G":
                return 1;
            case "Y":
                return 2;
            case "B":
                return 3;
            case "P":
                return 4;
            case "X":
                return 5;
        }
        return -1;
    }

    private void fillBoard() {
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                board[w][h] = new Random().nextInt(6);
            }
        }
        if (board[0][height - 1] == board[width - 1][0]) {
            fillBoard();
        }
        while (board[0][height - 2] == board[0][height - 1]) {
            board[0][height - 2] = new Random().nextInt(6);
        }
        while (board[1][height - 1] == board[0][height - 1]) {
            board[1][height - 1] = new Random().nextInt(6);
        }
        while (board[width - 2][0] == board[width - 1][0]) {
            board[width - 2][0] = new Random().nextInt(6);
        }
        while (board[width - 1][1] == board[width - 1][0]) {
            board[width - 1][1] = new Random().nextInt(6);
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getPos(int w, int h) {
        return board[w][h];
    }

    public void printScores() {
        System.out.println("Player 1 Score: " + p1Score + " | Player 2 Score: " + p2Score);
    }

    public void printWinner() {
        if (p1Score > p2Score) {
            System.out.println("Player 1 wins!");
        } else if (p2Score > p1Score) {
            System.out.println("Player 2 wins!");
        } else {
            System.out.println("It's a tie!");
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                sb.append(colorMap.get(board[w][h]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}