public class Main {
    public static void main(String[] args) {
        Board b1 = new Board(8, 7, "BYRXRYBGYPBYXRGBGBYPYXBRYXRYGRYBGBXGPYRPBGYPYPXRBRPRBRPX");
        // Board b = new Board(8, 7);
        Game g = new Game(b1);
        g.run();
    }
}